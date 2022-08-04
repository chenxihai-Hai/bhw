package com.bhw.common.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	/**
	 * post请求 ，超时默认10秒, 默认utf-8
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public String post(String url, Map<String, String> params) throws Exception {
		return this.post(url, params, 10, HTTP.UTF_8);
	}

	/**
	 * post请求, 超时默认10秒
	 * 
	 * @param url
	 * @param params
	 * @param charset
	 *            编码方式
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Map<String, String> params, String charset) throws Exception {
		return this.post(url, params, 10, charset);
	}

	/**
	 * post请求, 默认utf-8
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Map<String, String> params, int timeout) throws Exception {
		return this.post(url, params, timeout, HTTP.UTF_8);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @return
	 * @throws IOException
	 */
	public String post(String url, Map<String, String> params, int timeout, String charset) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", timeout * 1000);
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		String retVal = "";
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					formparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
				}
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, charset);
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(entity);
			org.apache.http.HttpResponse resp = httpclient.execute(httppost);
			HttpEntity retEntity = resp.getEntity();
			Header[] h = resp.getHeaders("Content-Encoding");
			if (null != h && h.length >= 1) {

				if ("gzip".equals(h[0].getValue())) {
					retEntity = new GzipDecompressingEntity(retEntity);

				} else if ("deflate".equals(h[0].getValue())) {
					retEntity = new DeflateDecompressingEntity(retEntity);
				}
			}

			retVal = EntityUtils.toString(retEntity, charset);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}


	/**
	 * post请求, 超时默认10秒
	 *
	 * @param url
	 * @param params
	 * @param charset
	 *            编码方式
	 * @return
	 * @throws Exception
	 */

	/**
	 * post请求, 默认utf-8
	 *
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @return
	 * @throws Exception
	 */

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @param charset
	 *            编码方式
	 * @return
	 * @throws Exception
	 */

	public String postJson(String url, String json, int timeout, Object... objects) throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", timeout * 1000);
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		String retVal = "";
		try {
			String encoding = HTTP.UTF_8;
			if (objects != null && objects.length > 0) {
				encoding = objects[0].toString();
			}
			HttpPost httppost = new HttpPost(url);
			StringEntity params = new StringEntity(json, encoding);
			httppost.addHeader("Connection", "close");
			httppost.addHeader("content-type", "application/json");
			httppost.setEntity(params);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			if (objects == null || objects.length == 0) {
				retVal = new String(httpclient.execute(httppost, responseHandler).getBytes(HTTP.ISO_8859_1), HTTP.UTF_8);
			} else if (objects != null && objects[0].equals("utf-8")) {
				retVal = httpclient.execute(httppost, responseHandler);
			} else if (objects != null && objects[0].equals("gb2312")) {
				retVal = new String(httpclient.execute(httppost, responseHandler).getBytes("iso-8859-1"), "gb2312");
			} else {
				retVal = new String(httpclient.execute(httppost, responseHandler).getBytes(), HTTP.UTF_8);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @param charset
	 *            编码方式
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> params, int timeout, String charset) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", timeout * 1000);
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		String retVal = "";
		try {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					qparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
				}
			}
			String paramstr = URLEncodedUtils.format(qparams, charset);
			if (StringUtils.isNotEmpty(paramstr)) {
				url = url + "?" + paramstr;
			}
			// URL urlObj = new URL(url);
			// URI uri = new URI(urlObj.getProtocol(), urlObj.getHost(),
			// urlObj.getPath(), urlObj.getQuery(), null);
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Connection", "close");
			org.apache.http.HttpResponse resp = httpclient.execute(httpget);

			HttpEntity retEntity = resp.getEntity();
			Header[] h = resp.getHeaders("Content-Encoding");
			if (null != h && h.length >= 1) {

				if ("gzip".equals(h[0].getValue())) {
					retEntity = new GzipDecompressingEntity(retEntity);

				} else if ("deflate".equals(h[0].getValue())) {
					retEntity = new DeflateDecompressingEntity(retEntity);
				}
			}

			retVal = EntityUtils.toString(retEntity, charset);

			// retVal = EntityUtils.toString(resp.getEntity(), charset);

		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @param charset
	 *            编码方式
	 * @param fNameEndChar
	 *            方法名后结束字符 默认“?”
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> params, int timeout, String charset, String fNameEndChar)
			throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", timeout * 1000);
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		String retVal = "";
		fNameEndChar = (fNameEndChar == null || "".endsWith(fNameEndChar)) ? "?" : fNameEndChar;
		try {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					qparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
				}
			}
			String paramstr = URLEncodedUtils.format(qparams, charset);
			if (StringUtils.isNotEmpty(paramstr)) {
				url = url + fNameEndChar + paramstr;
			}
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Connection", "close");
			org.apache.http.HttpResponse resp = httpclient.execute(httpget);

			HttpEntity retEntity = resp.getEntity();
			Header[] h = resp.getHeaders("Content-Encoding");
			if (null != h && h.length >= 1) {

				if ("gzip".equals(h[0].getValue())) {
					retEntity = new GzipDecompressingEntity(retEntity);

				} else if ("deflate".equals(h[0].getValue())) {
					retEntity = new DeflateDecompressingEntity(retEntity);
				}
			}

			retVal = EntityUtils.toString(retEntity, charset);

			// retVal = EntityUtils.toString(resp.getEntity(), charset);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	public int getStatus(String url, Map<String, String> params) throws Exception {
		return this.getStatus(url, params, 10, HTTP.UTF_8, "?");
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @param charset
	 *            编码方式
	 * @param fNameEndChar
	 *            方法名后结束字符 默认“?”
	 * @return
	 * @throws Exception
	 */
	public int getStatus(String url, Map<String, String> params, int timeout, String charset, String fNameEndChar)
			throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", timeout * 1000);
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		fNameEndChar = (fNameEndChar == null || "".endsWith(fNameEndChar)) ? "?" : fNameEndChar;
		try {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					qparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
				}
			}
			String paramstr = URLEncodedUtils.format(qparams, charset);
			if (StringUtils.isNotEmpty(paramstr)) {
				url = url + fNameEndChar + paramstr;
			}
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Connection", "close");
			org.apache.http.HttpResponse resp = httpclient.execute(httpget);

			return resp.getStatusLine().getStatusCode();

		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * get请求,超时默认10秒
	 * 
	 * @param url
	 * @param params
	 * @param charset
	 *            编码方式
	 * @return
	 * @throws IOException
	 */
	public String get(String url, Map<String, String> params, String charset) throws Exception {
		return this.get(url, params, 10, charset);
	}

	/**
	 * get请求,超时默认10秒, 默认utf-8
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> params) throws Exception {
		return this.get(url, params, 10, HTTP.UTF_8);
	}

	/**
	 * @Description: post请求，支持文件上传
	 * @param url
	 *            请求地址
	 * @param params
	 *            附加参数
	 * @param timeout
	 *            超时时间
	 * @param file
	 *            上传的文件
	 * @param filePro
	 *            文件名称
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> params, int timeout, File file, String filePro)
			throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", timeout * 1000);
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		String retVal = "";
		try {
			MultipartEntity reqEntity = new MultipartEntity();
			HttpPost httppost = new HttpPost(url);
			httppost.addHeader("Connection", "close");
			httppost.setEntity(reqEntity);
			for (Map.Entry<String, String> param : params.entrySet()) {
				reqEntity.addPart(param.getKey(), new StringBody(param.getValue()));
			}
			if (file != null && file.exists()) {
				FileBody bin = new FileBody(file);
				reqEntity.addPart(filePro, bin);
			}
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			retVal = new String(httpclient.execute(httppost, responseHandler).getBytes(HTTP.ISO_8859_1), HTTP.UTF_8);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * Post请求byte[] 返回类型
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 * @param charset
	 * @return
	 * @throws Exception
	 * @return byte[] 返回类型
	 */
	public byte[] postAsByte(String url, Map<String, String> params, int timeout, String charset) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", timeout * 1000);
		httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		byte[] retVal = null;
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					formparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
				}
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, charset);
			HttpPost httppost = new HttpPost(url);
			httppost.addHeader("Connection", "close");
			httppost.setEntity(entity);
			org.apache.http.HttpResponse resp = httpclient.execute(httppost);

			HttpEntity retEntity = resp.getEntity();
			Header[] h = resp.getHeaders("Content-Encoding");
			if (null != h && h.length >= 1) {

				if ("gzip".equals(h[0].getValue())) {
					retEntity = new GzipDecompressingEntity(retEntity);

				} else if ("deflate".equals(h[0].getValue())) {
					retEntity = new DeflateDecompressingEntity(retEntity);
				}
			}

			retVal = EntityUtils.toByteArray(retEntity);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * get请求, 默认utf-8
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> params, int timeout) throws Exception {
		return this.get(url, params, timeout, HTTP.UTF_8);
	}


	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 json 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public String jsonPost(String url, JSONObject json) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数

			out.print(json);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
}
