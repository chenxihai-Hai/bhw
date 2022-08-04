package com.bhw.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public final class Md5Util {

	public final static String[] HEXDIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public final static String[] LOGINCHECK = { "1", "2", "3", "4" };

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static String encodeByMd5(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		String newStr = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			newStr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newStr;
	}

	/**
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 * @Description: 转换字节数组为16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 
	 * @param b
	 *            字节数组
	 * @return
	 * @Description: 转换byte到16进制
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEXDIGITS[d1] + HEXDIGITS[d2];
	}

	/**
	 * 
	 * @param origin
	 * @return
	 * @Description: 编码（MessageDigest 为 JDK 提供的加密类 ）
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	/**
	 * 
	 * @param inStr
	 * @return
	 * @Description: MD5加码。32位
	 */
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		// System.out.println(md5Bytes);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String MD5(String inStr, String encode) {
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
		try {
			msgDigest.update(inStr.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System doesn't support your  EncodingException.");
		}
		byte[] bytes = msgDigest.digest();
		String md5Str = new String(encodeHex(bytes));
		return md5Str;
	}

	public static char[] encodeHex(byte[] data) {
		int l = data.length;
		char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}

	/**
	 * 
	 * @param inStr
	 * @return
	 * @Description: 可逆的加密算法
	 */
	public static String KL(String inStr) {
		// String s = new String(inStr);
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}

	/**
	 * 
	 * @param inStr
	 * @return
	 * @Description: 加密后解密
	 */
	public static String JM(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String k = new String(a);
		return k;
	}

	/**
	 * 加密请求参数
	 * 
	 * @param param
	 * @param token
	 * @return
	 */
	public static String MD5(Map<String, String> param, String token) {
		Set<String> keys = param.keySet();
		List<String> list = new ArrayList<String>(keys);
		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (String key : list) {
			String ret = param.get(key);
			if (null != ret) {
				sb.append(ret);
			}
		}
		sb.append(token);
		return MD5(sb.toString(), "utf-8");

	}

	/**
	 * 获取文件MD5值 方法描述：TODO
	 * 
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static String getFileMD5String(String fileName) {
		File file = new File(fileName);
		return getFileMD5String(file);
	}

	/**
	 * 获取文件MD5值 方法描述：TODO
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5String(File file) {
		MappedByteBuffer byteBuffer = null;
		try {
			FileInputStream in = new FileInputStream(file);
			FileChannel ch = in.getChannel();
			byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(byteBuffer);
		return bufferToHex(md.digest());
	}

	public static String getFileMD5String(byte[] bytes) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(bytes);
		return bufferToHex(md.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = DIGITS[(bt & 0xf0) >> 4];
		char c1 = DIGITS[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static void main(String args[]) {
		// System.out.println("694b7857a57c847a3d03094b82db35fad30f22557898d632008db78ee7b6153e".length());
		/*
		 * System.out.println(MD5("路径发给我饿哦")); String s = new String( "1" +
		 * "694b7857a57c847a3d03094b82db35fad30f22557898d632008db78ee7b6153e");
		 * System.out.println("原始：" + s); System.out.println("MD5后：" + MD5(s));
		 * 
		 * Map<String, String> param = new HashMap<String, String>();
		 * 
		 * param.put("userId", "123"); param.put("timestamp", "123231");
		 * param.put("keyword", "ss"); param.put("singer", "sss");
		 * param.put("categoryId", "sss"); param.put("currentPage", "sss");
		 * param.put("pageSize", "ssss"); for (int i = 0; i < 100; i++) {
		 * System.out.println("====>" + MD5(param, "this a token")); }
		 */

		// System.out.println("MD5:"+MD5(s,"UTF-8"));
		// System.out.println(MD5Encode(s));
		// System.out.println("MD5后再加密：" + KL(MD5(s)));
		// System.out.println("解密为MD5后的：" + JM(KL(MD5(s))));
		// //670b14728ad9902aecba32e22fa4f6bd

		System.out.println(getFileMD5String(new File(
				"C:\\Documents and Settings\\Administrator\\桌面\\文档\\BaiduMap_cityCode(1).txt")));
	}
}
