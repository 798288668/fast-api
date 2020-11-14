package com.cheng.api.modules.sys.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cheng.api.common.base.BaseException;
import com.cheng.api.common.config.Global;
import com.cheng.api.common.config.OssConfig;
import com.cheng.api.common.config.SpringContextHolder;
import com.cheng.api.common.constant.OssFileAclEnum;
import com.cheng.api.common.util.Encodes;
import com.cheng.api.common.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author fengcheng
 * @version 2017/5/23
 */
@Slf4j
public class UploadUtils {
	private UploadUtils() {
	}

	private static final OssConfig OSS_CONFIG = SpringContextHolder.getBean(OssConfig.class);
	private static final OSSClient OSS_CLIENT = SpringContextHolder.getBean(OSSClient.class);

	private static final String ALLOW_TYPE_IMG = "jpg|gif|jpeg|png|bmp|JPG|GIF|JPEG|PNG|BMP";

	public static Object uploadFile(MultipartFile file) {
		String fileName = "";
		String realPath = "";
		try {
			if (file != null && file.getOriginalFilename() != null) {
				fileName = file.getOriginalFilename();
				String path = Global.getFileUploadPath();
				realPath += "files/";
				FileUtils.createDirectory(path + realPath);
				file.transferTo(new File(path + realPath + fileName));
			} else {
				throw new BaseException("上传文件不能为空！");
			}
		} catch (Exception e) {
			throw new BaseException("文件上传失败: " + e.getMessage(), e);
		}
		return realPath + fileName;
	}

	public static String uploadImage(MultipartFile file) {
		String fileName = "";
		String realPath = "";
		try {
			if (file != null && file.getOriginalFilename() != null) {
				String uploadFileName = file.getOriginalFilename();
				String filetype = uploadFileName.substring(uploadFileName.lastIndexOf('.') + 1);
				// 文件类型限制
				boolean allowed = ALLOW_TYPE_IMG.contains(filetype);
				if (!allowed) {
					throw new BaseException("文件格式不支持！请上传 " + ALLOW_TYPE_IMG + " 格式图片");
				}
				String path = Global.getFileUploadPath();
				realPath += "images/";
				fileName = UUID.randomUUID().toString().replace("-", "") + "." + filetype;
				FileUtils.createDirectory(path + realPath);
				file.transferTo(new File(path + realPath + fileName));
			} else {
				throw new BaseException("上传文件不能为空！");
			}
		} catch (Exception e) {
			throw new BaseException("文件上传失败: " + e.getMessage(), e);
		}
		return realPath + fileName;
	}

	public static String upload2Oss(MultipartFile file) {
		String fileName = Encodes.uuid() + getFileNameSuffix(file.getOriginalFilename());
		try {
			OSS_CLIENT.putObject(new PutObjectRequest(OSS_CONFIG.getBucketName(), fileName, file.getInputStream()));
			OSS_CLIENT.setObjectAcl(OSS_CONFIG.getBucketName(), fileName,
					CannedAccessControlList.parse(OssFileAclEnum.PublicRead.getCode()));
		} catch (Exception e) {
			log.error("文件上传失败:{0}", e);
		}
		return OSS_CONFIG.getFileDomain() + fileName;
	}

	private static String getFileNameSuffix(String fileName) {
		String filenameSuffix = "";
		if (!StringUtils.isEmpty(fileName)) {
			int indexOf = fileName.lastIndexOf('.');
			if (indexOf > 0) {
				filenameSuffix = fileName.substring(fileName.lastIndexOf('.'));
			}
		}
		return filenameSuffix;
	}

	public static Map<String, String> getSys() {
		String policy = "{\"Statement\":[{\"Action\":[\"oss:*\"],\"Effect\":\"Allow\",\"Resource\":[\"acs:oss:*:*:*\"]}],\"Version\":\"1\"}";
		// RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
		// 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
		// 具体规则请参考API文档中的格式要求
		String roleSessionName = "alice-001";

		Map<String, String> respMap;
		try {
			final AssumeRoleResponse stsResponse = assumeRole(OSS_CONFIG.getAccessKeyId(),
					OSS_CONFIG.getAccessKeySecret(), OSS_CONFIG.getRoleArn(), roleSessionName, policy,
					OSS_CONFIG.getTokenExpireTime());
			respMap = new LinkedHashMap<>();
			respMap.put("StatusCode", "200");
			respMap.put("AccessKeyId", stsResponse.getCredentials().getAccessKeyId());
			respMap.put("AccessKeySecret", stsResponse.getCredentials().getAccessKeySecret());
			respMap.put("SecurityToken", stsResponse.getCredentials().getSecurityToken());
			respMap.put("Expiration", stsResponse.getCredentials().getExpiration());
		} catch (ClientException e) {
			respMap = new LinkedHashMap<>();
			respMap.put("StatusCode", "500");
			respMap.put("ErrorCode", e.getErrCode());
			respMap.put("ErrorMessage", e.getErrMsg());
		}
		return respMap;
	}

	private static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
			String roleSessionName, String policy, long durationSeconds) throws ClientException {

		// 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
		String region = "cn-hangzhou";
		String stsApiVersion = "2015-04-01";

		// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
		IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
		DefaultAcsClient client = new DefaultAcsClient(profile);

		// 创建一个 AssumeRoleRequest 并设置请求参数
		final AssumeRoleRequest request = new AssumeRoleRequest();
		request.setVersion(stsApiVersion);
		request.setMethod(MethodType.POST);
		request.setProtocol(ProtocolType.HTTPS);
		request.setRoleArn(roleArn);
		request.setRoleSessionName(roleSessionName);
		request.setPolicy(policy);
		request.setDurationSeconds(durationSeconds);
		return client.getAcsResponse(request);

	}
}
