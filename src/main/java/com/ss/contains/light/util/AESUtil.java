package com.ss.contains.light.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

/**
 * @author NotEdibleSalt
 */
public class AESUtil {

    private static final String key = "1111111111111111";

    public static String decrypt(String data) {

        AES aes = new AES(Mode.ECB, Padding.ZeroPadding, key.getBytes());
        return aes.decryptStr(data);
    }


}
