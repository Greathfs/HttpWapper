package com.study.okhttpdemo.xutils.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * 字符串操作工具包
 * 
 */
public class StringUtils {
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	private final static Pattern IMG_URL = Pattern.compile(".*?(gif|jpeg|png|jpg|bmp)");

	private final static Pattern URL = Pattern.compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

	private final static Pattern PHONE_NUMBER = Pattern.compile("^1(3|5|7|8|4)\\d{9}");

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 *            要判断是否为空的字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 加密手机号
	 * 
	 * @param phoneNumber
	 *            手机号
	 * @return 加密手机号
	 */
	public static String encryptPhoneNumber(String phoneNumber) {
		if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.length() > 6) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < phoneNumber.length(); i++) {
				char c = phoneNumber.charAt(i);
				if (i >= 3 && i <= 6) {
					sb.append('*');
				} else {
					sb.append(c);
				}
			}

			return sb.toString();
		}
		return "";
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 *            email地址
	 * @return boolean
	 */
	public static boolean isEmail(String email) {
		return !(email == null || email.trim().length() == 0) && emailer.matcher(email).matches();
	}

	/**
	 * 判断一个url是否为图片url
	 * 
	 * @param url
	 *            图片链接
	 * @return boolean
	 */
	public static boolean isImgUrl(String url) {
		return !(url == null || url.trim().length() == 0) && IMG_URL.matcher(url).matches();
	}

	/**
	 * 判断是否为一个合法的url地址
	 * 
	 * @param str
	 *            url地址
	 * @return boolean
	 */
	public static boolean isUrl(String str) {
		return !(str == null || str.trim().length() == 0) && URL.matcher(str).matches();
	}

	public static String getString(String s) {
		return s == null ? "" : s;
	}

	/**
	 * 将一个InputStream流转换成字符串
	 * 
	 * @param is
	 *            InputStream流
	 * @return 字符串
	 */
	public static String toConvertString(InputStream is) {
		StringBuffer res = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader read = new BufferedReader(isr);
		try {
			String line;
			line = read.readLine();
			while (line != null) {
				res.append(line).append("<br>");
				line = read.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				isr.close();
				isr.close();
				read.close();
				read = null;
				is.close();
				is = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res.toString();
	}

	/***
	 * 截取字符串
	 * 
	 * @param start
	 *            从那里开始，0算起
	 * @param num
	 *            截取多少个
	 * @param str
	 *            截取的字符串
	 * @return subString result
	 */
	public static String getSubString(int start, int num, String str) {
		if (str == null) {
			return "";
		}
		int leng = str.length();
		if (start < 0) {
			start = 0;
		}
		if (start > leng) {
			start = leng;
		}
		if (num < 0) {
			num = 1;
		}
		int end = start + num;
		if (end > leng) {
			end = leng;
		}
		return str.substring(start, end);
	}

	/**
	 * 从后面开始截取字符串
	 *
	 * @param str
	 *            要截取的字符串
	 * @param stripChars
	 *            截取的标记字符
	 * @return 截取后的字符串
	 */
	public static String subFromEndToStart(String str, String stripChars) {
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		int length = str.length();
		int index = str.lastIndexOf(stripChars);
		str = str.substring(index + 1, length);
		if (str.contains("$")) {
			int i = str.indexOf("$");
			str = str.substring(0, i);
		}
		return str;
	}

	/**
	 * 转化成价格
	 * 
	 * @param price
	 *            需要转化的价格值
	 * @return 带“元”
	 */
	public static String toPricString(String price) {
		double priceVal = 0.00;
		try {
			priceVal = Double.parseDouble(price);
		} catch (Exception e) {
		}
		String format = String.format("%.2f", priceVal);
		return format + "元";
	}

	/**
	 * 检测是否是手机号
	 * 
	 * @param phone
	 *            需要检测的字符串
	 * @return boolean
	 */
	public static boolean isPhoneNumber(String phone) {
		return !(phone == null || phone.trim().length() == 0) && PHONE_NUMBER.matcher(phone).matches();
	}

	public static double toDouble(String amount) {
		try {
			return Double.parseDouble(amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.00;
	}

	/**
	 * 转化成价格
	 * 
	 * @param price
	 *            需要转化的价格值
	 * @return 带“元”
	 */
	public static String toPriceString(String price) {
		double priceVal = 0.00;
		try {
			priceVal = Double.parseDouble(price);
		} catch (Exception e) {
		}
		String format = String.format("%.2f", priceVal);
		return format + "元";
	}

	/**
	 * 获取一个字符串中某一个字符第几个位置的索引
	 * 
	 * @return
	 */
	public static int getAppearIndex(String str, String charStr, int position) {
		if (str.contains(charStr)) {
			Pattern pattern = Pattern.compile(charStr);
			Matcher findMatcher = pattern.matcher(str);
			int number = 0;
			while (findMatcher.find()) {
				number++;
				if (number == position) {
					break;
				}
			}
			int i = findMatcher.start();
			return i;
		} else {
			return 0;
		}
	}

}
