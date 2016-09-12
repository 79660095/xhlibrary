package com.wxh.sdk.des3;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
	private static String cKey = "1234567891234567";

	// 加密
	public static String Encrypt(String sSrc){
		if (cKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (cKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		try {
			byte[] raw = cKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

			return Base64.encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return null;
		}
	}

	// 解密
	public static String Decrypt(String sSrc) throws Exception {
		try {
			// 判断Key是否正确
			if (cKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (cKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = cKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = Base64.decode(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * 此处使用AES-128-ECB加密模式，key需要为16位。
	 * 
	 * String cKey = "1234567890123456"; // 需要加密的字串 String cSrc = "1";
	 * System.out.println(cSrc); // 加密 String enString = AES.Encrypt(cSrc);
	 * System.out.println("加密后的字串是：" + enString);
	 * 
	 * // 解密 String DeString = AES.Decrypt(enString);
	 * System.out.println("解密后的字串是：" + DeString); }
	 */
}
