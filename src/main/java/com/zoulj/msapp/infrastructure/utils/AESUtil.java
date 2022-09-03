package com.zoulj.msapp.infrastructure.utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

/**
 * @author leonard
 * @date 2022/9/3
 * @Description AES加密工具
 */
public class AESUtil {

    //AES模式下，key必须为16位
    private static final String key = "1998199819981998";
    private static AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
    private AESUtil(){}

    public static String encrypt(String text){
        return aes.encryptBase64(text);
    }

    public static String decrypt(String text){
        return aes.decryptStr(text);
    }

    public static void main(String[] args) {
        String key = "1234567812345678";
        String text = "HelloWorld";
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, key.getBytes());
        String s = aes.encryptBase64(text);
        String s1 = aes.decryptStr(s);
        System.out.println(text);
        System.out.println(s);
        System.out.println(s1);
    }
}
