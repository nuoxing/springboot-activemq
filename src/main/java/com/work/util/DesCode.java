package com.work.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DesCode {

	/**
	 * DES解密
	 * 
	 * @param message
	 *            需要解密的内容
	 * @param key
	 *            密钥
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decrypt(String message, String key) throws Exception {

		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	/**
	 * DES加密
	 * 
	 * @param message
	 *            需要加密的内容
	 * @param key
	 *            密钥
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static byte[] encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message.getBytes("UTF-8"));
	}

	/**
	 * 将字符串转换十六进制数组
	 * @param ss 需要转换的字符串
	 * @return 转换后的十六进制数组
	 */
	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	public static void main(String[] args) throws Exception {
		String key = "11010101";//密钥
		String value = "440101000019069";//需要加密的内容
		String jiami = java.net.URLEncoder.encode(value, "utf-8").toLowerCase();

		//输出加密后的数据
		System.out.println("加密数据:" + jiami);
		
		//将加密数据转换为十六进制字符串
		String a = toHexString(encrypt(jiami, key)).toUpperCase();
		
		//输出加密后的十六进制字符串
		System.out.println("加密后的数据为    :" + a);
		
		//数据校验
		System.out.println("浪潮加密后的数据为:EEF66BC84556E42D20594BDC25F5FB27");
		
		//数据解密
		String b = java.net.URLDecoder.decode(decrypt(a, key), "utf-8");
		
		//输出解密后的内容
		System.out.println("解密后的数据:" + b);

	}

	/**
	 * 将BYTE数组中的内容转换为十六进制的字符串
	 * @param b BYTE数组
	 * @return 转换后的十六进制的字符串
	 */
	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}

}
