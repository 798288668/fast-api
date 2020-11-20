/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api.modules.sys.web;

import com.cheng.api.common.base.Result;
import com.cheng.api.common.base.ResultCode;
import com.cheng.api.common.config.RedisUtil;
import com.cheng.api.common.constant.RedisConst;
import com.cheng.api.common.constant.SysEnum;
import com.cheng.api.common.security.JwtTokenUtil;
import com.cheng.api.common.security.RequestLimit;
import com.cheng.api.common.util.*;
import com.cheng.api.modules.sys.bean.*;
import com.cheng.api.modules.sys.service.SysUserService;
import com.cheng.api.modules.sys.utils.UserUtils;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
@RestController
public class LoginController {

	private final SysUserService sysUserService;
	private final RedisTemplate<String, String> redisTemplate;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtTokenUtil jwtTokenUtil;

	@Autowired
	public LoginController(RedisTemplate<String, String> redisTemplate, AuthenticationManager authenticationManager,
			@Qualifier("jwtUserDetailsServiceImpl") UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil,
			SysUserService sysUserService) {
		this.redisTemplate = redisTemplate;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.sysUserService = sysUserService;
	}

	/**
	 * 获取验证码
	 */
	@RequestLimit(count = 3)
	@PostMapping(value = "/getCaptcha")
	public void getCaptcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
		String width = request.getParameter("width");
		String height = request.getParameter("height");

		//生成验证码
		String uuid = UUID.randomUUID().toString();

		Map<String, Object> image = CaptchaUtils.createImage(width, height);

		//将验证码以<key,value>形式缓存到redis
		redisTemplate.opsForValue().set(uuid, (String) image.get("code"), 3, TimeUnit.MINUTES);

		//将验证码key，及验证码的图片返回
		Cookie cookie = new Cookie("CaptchaCode", uuid);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		OutputStream out = response.getOutputStream();
		ImageIO.write((BufferedImage) image.get("image"), "JPEG", out);
		out.close();

	}

	/**
	 * 获取二维码
	 */
	@GetMapping("/getQrCode")
	public ResponseEntity<byte[]> getQrCode(String inviterId, String platform) throws IOException, WriterException {
		//二维码内的信息
		String info = Servlets.getRequestPath() + "/register?inviterId=" + inviterId + "&platform=" + platform;
		byte[] qrcode = QrCodeGenerator.getQrCodeImage(info, 360, 360);
		// Set headers
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(qrcode, headers, HttpStatus.CREATED);
	}

	/**
	 * 用户注册
	 */
	@RequestLimit(count = 10)
	@ResponseBody
	@PostMapping("/register/submit")
	public Result<String> submit(@Validated @RequestBody SysUserH5AddQueryDto dto, HttpServletRequest request) {
		return sysUserService.register(dto);
	}

	/**
	 * 登录
	 * @return 处理结果
	 */
	@RequestLimit(count = 10)
	@PostMapping("/login")
	public Result<Object> login(@Validated @RequestBody LoginQueryDto dto, HttpServletRequest request) {
		SysUser user = UserUtils.getUserByLoginName(dto.getLoginName());
		if (user == null) {
			return Result.fail(ResultCode.INVALID_NAME_OR_PASS);
		}
		if (dto.getUserType() == SysEnum.UserType.APP && user.getUserType() != dto.getUserType()) {
			return Result.fail(ResultCode.INVALID_NAME_OR_PASS);
		}
		if (user.getUserStatus() == SysEnum.UserStatus.FREEZE) {
			return Result.fail(ResultCode.INVALID_NAME_FREEZE);
		}
		if (user.getUserStatus() == SysEnum.UserStatus.AUDIT) {
			return Result.fail(ResultCode.INVALID_NAME_AUDIT);
		}
		try {
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getLoginName(), dto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			return Result.fail(ResultCode.INVALID_NAME_OR_PASS);
		}

		// Reload password post-security so we can generate token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getLoginName());
		final String token = jwtTokenUtil.generateToken(userDetails);

		RedisUtil.setString(RedisConst.USER_LOGIN_NAME + dto.getLoginName(), token, JwtTokenUtil.EXPIRATION);
		RedisUtil.setString(RedisConst.USER_LOGIN_TOKEN + token, dto.getLoginName(), JwtTokenUtil.REDIS_EXPIRATION);

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", token);
		map.put("menu", UserUtils.getMenuList(UserUtils.getUserId()).stream()
				.filter(e -> e.getType() == SysEnum.MenuType.MENU && !Objects.equals(e.getParentId(), "0"))
				.map(SysMenu::getPermission).collect(Collectors.toList()));
		ThreadPoolUtil.run(() -> sysUserService.updateLoginInfo(user.getId(), request));
		return Result.success(map);
	}

	/**
	 * 获取当前用户信息
	 */
	@PostMapping("/getUser")
	public Result<UserCenterDto> getUser() {
		SysUser user = UserUtils.getUser();
		SysUserDto userDto = BeanMapper.map(user, SysUserDto.class);
		UserCenterDto dto = new UserCenterDto();
		dto.setUser(userDto);
		return Result.success(dto);
	}

	/**
	 * 重置密钥
	 * @return 处理结果
	 */
	@RequestLimit(count = 10)
	@PostMapping("/resetSecret")
	public Result<Object> resetSecret(@Validated @RequestBody IdQueryDto dto, HttpServletRequest request) {
		sysUserService.resetSecret(dto.getId());
		return Result.success();
	}

	/**
	 * 修改密码
	 * @return 处理结果
	 */
	@RequestLimit(count = 10)
	@PostMapping("/modifyPassword")
	public Result<Object> modifyPassword(@Validated @RequestBody ModifyPassQueryDto dto, HttpServletRequest request) {
		SysUser sysUserByLoginName = UserUtils.getUserByLoginName(dto.getLoginName());
		boolean b = Encodes.validatePassword(dto.getOldPassword(), sysUserByLoginName.getPassword());
		if (!b) {
			return Result.fail(ResultCode.INVALID_PASS);
		}
		sysUserService.modifyPassword(dto.getLoginName(), dto.getPassword());
		return Result.success();
	}

	/**
	 * 公共数据
	 */
	@PostMapping("/checkToken")
	public Result<Object> checkToken() {
		return Result.success();
	}

}
