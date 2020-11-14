package com.cheng.api.modules.sys.web;

import com.cheng.api.common.base.Result;
import com.cheng.api.common.security.RequestLimit;
import com.cheng.api.modules.sys.bean.CommonDto;
import com.cheng.api.modules.sys.utils.DictUtils;
import com.cheng.api.modules.sys.utils.UploadUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author fengcheng
 * @version 2019-07-02
 */
@RestController
public class CommonController {

	/**
	 * 公共数据
	 */
	@PostMapping("/common")
	public Result<CommonDto> common() {
		CommonDto dto = new CommonDto();
		dto.setCurrentTime(new Date());
		dto.setVersion("1.0.0");
		dto.setDict(DictUtils.getDictMap());
		return Result.success(dto);
	}

	/**
	 * 上传图片
	 *
	 * @param file 上传文件
	 * @return json
	 */
	@RequestLimit(count = 1, time = 2000)
	@PostMapping("/uploadFile")
	public Object uploadFile(@NotNull MultipartFile file, HttpServletRequest request) {
		return UploadUtils.uploadFile(file);
	}

	/**
	 * 上传图片
	 *
	 * @param file 上传文件
	 * @return json
	 */
	@RequestLimit(count = 1, time = 2000)
	@PostMapping("/uploadImage")
	public Object uploadImage(@NotNull MultipartFile file, HttpServletRequest request) {
		return UploadUtils.uploadImage(file);
	}

	/**
	 * 获取oss临时授权(Security Token Service)
	 *
	 * @return json
	 */
	@GetMapping("/getSts")
	public Map<String, String> getSts() {
		return UploadUtils.getSys();
	}

}
