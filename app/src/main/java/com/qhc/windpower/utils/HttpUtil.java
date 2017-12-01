package com.qhc.windpower.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 访问网络的工具包
 * 
 * @author any
 */
public class HttpUtil {

	public static final int HTTP_OK = 200;
	private static final int TIME_OUT_DEFAULT = 30 * 1000;

	private static String error = "error:";

	/**
	 * get访问返回字符串
	 */
	public String httpGet(String url) {
		return httpGet(url, TIME_OUT_DEFAULT);
	}

	/**
	 * get访问返回字符串
	 */
	public static String httpGet(String url, int timeout) {
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setConnectTimeout(timeout);
			int code = conn.getResponseCode();
			if (code == HTTP_OK) {
				return getResponseString(conn.getInputStream());
			} else {
				LogUtil.e("Net Error:", code + "");
				return error + code;
			}
		} catch (Exception e) {
			LogUtil.e("NetError", e);
			return null;
		}
	}

	/**
	 * post访问返回字符串
	 */
	public static String httpPost(String url, Map<String, Object> myParams) {
		return httpPost(url, myParams, TIME_OUT_DEFAULT, null);
	}

	/**
	 * post访问返回字符串
	 */
	public static String httpPost(String url, Map<String, Object> myParams, int timeout) {
		return httpPost(url, myParams, timeout, null);
	}

	/**
	 * post访问返回字符串
	 */
	public static String httpPost(String url, Map<String, Object> myParams, String cookie) {
		return httpPost(url, myParams, TIME_OUT_DEFAULT, cookie);
	}

	/**
	 * post访问返回字符串
	 */
	public static String httpPost(String url, Map<String, Object> myParams, int timeout, String cookie) {
		try {
			byte[] data = getRequestData(myParams, "UTF-8").toString().getBytes();//获得请求体
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			if (cookie != null) {
				conn.setRequestProperty("Cookie", cookie);
			}
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", data.length + "");
			conn.getOutputStream().write(data);
			conn.setConnectTimeout(timeout);
			int code = conn.getResponseCode();
			if (code == HTTP_OK) {
				return getResponseString(conn.getInputStream());
			} else {
				LogUtil.e("Net Error:", code + "");
				return null;
			}
		} catch (Exception e) {
			LogUtil.e("NetError", e);
			return null;
		}
	}

	/**
	 * json post访问返回字符串
	 */
	public static String jsonPost(String url, Object json, Map<String, String> header) {
		if (json instanceof String) {
			return jsonPost(url, (String)json, TIME_OUT_DEFAULT, header);
		} else {
			return jsonPost(url, JsonUtil.toJson(json), TIME_OUT_DEFAULT, header);
		}
	}

	/**
	 * json post访问返回字符串
	 */
	public static String jsonPost(String url, String json) {
		return jsonPost(url, json, TIME_OUT_DEFAULT, null);
	}

	/**
	 * json post访问返回字符串
	 */
	public static String jsonPost(String url, Map json) {
		return jsonPost(url, JsonUtil.toJson(json), TIME_OUT_DEFAULT, null);
	}

	/**
	 * json post访问返回字符串
	 */
	public static String jsonPost(String url, String json, int timeout, Map<String, String> header) {
		try {
			byte[] data = json.getBytes();//获得请求体
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("content-type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			if (header != null && header.size() > 0) {
				for (String key : header.keySet()) {
					conn.setRequestProperty(key, header.get(key));
				}
			}
			conn.setDoOutput(true);
			conn.getOutputStream().write(data);
			conn.setConnectTimeout(timeout);
			int code = conn.getResponseCode();
			if (code == HTTP_OK) {
				return getResponseString(conn.getInputStream());
			} else {
				LogUtil.e("Net Error:", code + "");
				return error + code;
			}
		} catch (Exception e) {
			LogUtil.e("NetError", e);
			return "";
		}
	}

	private static String getResponseString(InputStream inputStream) throws IOException {
//		BufferedReader re = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//		String line = null;
//		StringBuffer rst = new StringBuffer(100);
//		while ((line = re.readLine()) != null) {
//			rst.append(line);
//		}
//		return rst.toString();

		StringBuffer rst = new StringBuffer(100);
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) > 0) {
			String tmp = new String(buffer, 0, length, "UTF-8");
			rst.append(tmp);
		}
		return rst.toString();
	}

	/*
	 * Function : 封装请求体信息 Param : params请求体内容，encode编码格式
	 */
	public static StringBuffer getRequestData(Map<String, Object> params, String encode) throws Exception {
		StringBuffer stringBuffer = new StringBuffer(); // 存储封装好的请求体信息
		if (params != null) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey()).append("=").append(URLEncoder.encode(String.valueOf(entry.getValue()), encode)).append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1); // 删除最后的一个"&"
		}
		return stringBuffer;
	}
}