package com.cheng.api.common.util;

import com.cheng.api.common.constant.SysConst;
import com.cheng.api.modules.sys.bean.IpDto;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author fengcheng
 * @version 2017/12/28
 */
@Slf4j
public class ApiUtils {

	private ApiUtils() {
	}

	static OkHttpClient client = new OkHttpClient();
	public static final MediaType HTTP_JSON = MediaType.get("application/json; charset=utf-8");
	public static final MediaType HTTP_FORM = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");

	public static boolean notifyRequest(Map<String, String> data, String url) {
		if (StringUtils.isEmpty(url)) {
			return false;
		}
		if (!url.startsWith(SysConst.HTTP) && !url.startsWith(SysConst.HTTPS)) {
			return false;
		}
		RequestBody body = RequestBody.create(JsonUtils.toJson(data), HTTP_JSON);
		Request request = new Request.Builder().url(url).post(body).build();
		String result = call(request);
		return Objects.equals(result, "success");
	}

	public static IpDto getIpInfo(String url) {
		try{
			OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(2, TimeUnit.SECONDS)
					.writeTimeout(2, TimeUnit.SECONDS).readTimeout(2, TimeUnit.SECONDS).build();
			Request request = new Request.Builder().url(url).addHeader("Content-Type", "text/html;charset=utf-8")
					.addHeader("User-Agent",
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
					.get().build();
			String result = call(client, request);
			if (result != null) {
				return JsonUtils.toObject(result, IpDto.class);
			} else {
				return new IpDto();
			}
		}catch (Exception e){
			return new IpDto();
		}
	}

	public static String get(String url) {
		Request request = new Request.Builder().url(url).build();
		return call(request);
	}

	public static String post(String url, String json) {
		RequestBody body = RequestBody.create(json, HTTP_JSON);
		Request request = new Request.Builder().url(url).post(body).build();
		return call(request);
	}

	public static String postForm(String url, Map<String, String> param) {
		param.forEach((k, v) -> log.info("requestParam: {} = {}", k, v));
		FormBody.Builder builder = new FormBody.Builder();
		param.forEach(builder::add);
		RequestBody body = builder.build();
		Request request = new Request.Builder().url(url).post(body).build();
		return call(request);
	}

	private static String call(OkHttpClient client, Request request) {
		try (Response response = client.newCall(request).execute()) {
			log.info("request: {}", response.request().toString());
			log.info("response: {}", response.toString());
			if (response.isSuccessful() && response.body() != null) {
				String result = response.body().string();
				log.info("responseBody: {}", result);
				return result;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private static String call(Request request) {
		return call(client, request);
	}
}
