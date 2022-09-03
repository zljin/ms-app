package com.zoulj.msapp.infrastructure.utils;

import java.util.Random;

public class ValidateCodeUtils {

    public static String generateCode(){
        Random rdm = new Random();
        String hash1 = Integer.toHexString(rdm.nextInt());
        String capstr = hash1.substring(0, 4);
        return capstr;
    }
    public static String generateCode(int length){
        Random rdm = new Random();
        String hash1 = Integer.toHexString(rdm.nextInt());
        return hash1.substring(0, length);
    }
    /**
     * 随机生成验证码
     */
    public static String generateValidateCode(int length) {
        Integer code = null;
        if (length == 4) {
            code = new Random().nextInt(9999);//生成随机数，最大为9999
            if (code < 1000) {
                code = code + 1000;//保证随机数为4位数字
            }
        } else if (length == 6) {
            code = new Random().nextInt(999999);//生成随机数，最大为999999
            if (code < 100000) {
                code = code + 100000;//保证随机数为6位数字
            }
        } else {
            throw new RuntimeException("只能生成4位或6位数字验证码");
        }
        return code.toString();
    }
}
