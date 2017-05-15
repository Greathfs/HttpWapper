package com.study.okhttpdemo.xutils.security;

import java.nio.charset.Charset;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密
 *
 */
public class AES {

	private static final int keySize = 128;
	private static final String algorithm = "AES/ECB/PKCS5Padding";
	private static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * 加密
	 * @param key 加密key
	 * @param content 待加密内容
	 * @return 加密后内容
	 */
	public static String encrypt(String key, String content) {
		return encrypt(key, content, CHARSET);
	}

	/**
	 * 加密
	 * @param key 加密key
	 * @param content 待加密内容
	 * @param charset 数据编码集
	 * @return 加密后内容
	 */
	public static String encrypt(String key, String content, Charset charset) {
		return Base64.encode(encrypt(getKey(Base64.decode(key)), content.getBytes(charset)));
	}

	/**
	 * 加密
	 * @param key 加密key
	 * @param content 待加密内容
	 * @return 加密后内容
	 */
	public static byte[] encrypt(Key key, byte[] content) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(content);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解密
	 * @param key 解密key
	 * @param cipherContent 密文
	 * @return 明文
	 */
	public static String decrypt(String key, String cipherContent) {
		return decrypt(key, cipherContent, CHARSET);
	}

	/**
	 * 解密
	 * @param key 解密key
	 * @param cipherContent 密文
	 * @param charset 数据编码集
	 * @return 明文
	 */
	public static String decrypt(String key, String cipherContent, Charset charset) {
		return new String(decrypt(getKey(Base64.decode(key)), Base64.decode(cipherContent)), charset);
	}

	/**
	 * 解密
	 * @param key 解密key
	 * @param cipherContent 密文
	 * @return 明文
	 */
	public static byte[] decrypt(Key key, byte[] cipherContent) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(cipherContent);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取key
	 * @param keyBytes 字节数组
	 * @return key
	 */
	public static Key getKey(byte[] keyBytes) {
		try {
			return new SecretKeySpec(keyBytes, "AES");
		} catch (Exception e) {
			e.printStackTrace();
			throw null;
		}
	}

	/**
	 * 生成16位key=128/8
	 * @return key字节数组
	 */
	public static byte[] create() {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			generator.init(keySize);
			//生成一个密钥  
			SecretKey secretKey = generator.generateKey();
			return secretKey.getEncoded();
		} catch (Exception e) {
			// 不会存在
			return null;
		}

	}

}
