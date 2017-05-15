package com.study.okhttpdemo.xutils.security;

import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名验签
 *
 */
public class RSA {

	private static final String algorithm = "SHA1withRSA";
	private static final Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * 获取Base64的签名数据
	 * @param privateKeyBase64	64位私钥
	 * @param origin			签名原文
	 * @return Base64签名
	 */
	public static String signBase64(String privateKeyBase64, String origin) {
		return signBase64(privateKeyBase64, origin, CHARSET);
	}

	/**
	 * 获取Base64的签名数据
	 * @param privateKeyBase64	64位私钥
	 * @param origin			签名原文
	 * @param charset			签名原文编码集
	 * @return Base64签名
	 */
	public static String signBase64(String privateKeyBase64, String origin, Charset charset) {
		if (privateKeyBase64 == null || origin == null || charset == null) {
			return null;
		}

		try {
			// 获取私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyBase64));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

			// 签名
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(priKey);
			signature.update(origin.getBytes(charset));
			byte[] signData = signature.sign();

			return Base64.encode(signData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 验证签名,默认签名原文为UTF-8
	 * @param publicKeyBase64 	64位公钥 #Base64
	 * @param origin 			签名原文
	 * @param signBase64		签名数据
	 * @return 签名是否合法
	 */
	public static boolean verify(String publicKeyBase64, String origin, String signBase64) {
		return verify(publicKeyBase64, origin, signBase64, CHARSET);
	}

	/**
	 * 验证签名
	 * @param publicKeyBase64 	64位公钥 #Base64
	 * @param origin 			签名原文
	 * @param signBase64		签名数据
	 * @param charset			签名原文编码集
	 * @return 签名是否合法
	 */
	public static boolean verify(String publicKeyBase64, String origin, String signBase64, Charset charset) {

		if (publicKeyBase64 == null || origin == null || signBase64 == null || charset == null) {
			return false;
		}

		try {
			// 获取公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyBase64));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

			// 验证签名
			Signature verifier = Signature.getInstance(algorithm);
			verifier.initVerify(pubKey);
			verifier.update(origin.getBytes(charset));
			return verifier.verify(Base64.decode(signBase64));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
