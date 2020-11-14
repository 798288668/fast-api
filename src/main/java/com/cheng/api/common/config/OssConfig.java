package com.cheng.api.common.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里OSS客户端配置
 *
 * @author FengLian
 * @date 2018-02-06 10:57
 **/
@Configuration
@ConfigurationProperties("oss")
public class OssConfig {

	private String accessKeyId;
	private String accessKeySecret;
	private String endpoint;
	private String fileDomain;
	private String bucketName;

	private String roleArn;
	private Long tokenExpireTime;

	@Bean
	public OSS ossClient() {
		return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getFileDomain() {
		return fileDomain;
	}

	public void setFileDomain(String fileDomain) {
		this.fileDomain = fileDomain;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getRoleArn() {
		return roleArn;
	}

	public void setRoleArn(String roleArn) {
		this.roleArn = roleArn;
	}

	public Long getTokenExpireTime() {
		return tokenExpireTime;
	}

	public void setTokenExpireTime(Long tokenExpireTime) {
		this.tokenExpireTime = tokenExpireTime;
	}
}
