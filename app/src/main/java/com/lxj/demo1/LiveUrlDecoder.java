package com.lxj.demo1;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class LiveUrlDecoder {
    private static final String IV_STRING = "daf0eadcaf1d89bd";
    private static final String defaultSecretKey = "2b490f0ea43dcaf1";
    public static String decode(String data) {
        try {
            if (data.isEmpty() ) {
                return null;
            }
            //判断是加密还是解密
            int mode = Cipher.DECRYPT_MODE;
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            //true 加密内容 false 解密内容
            if (encrypt) {
                content = data.getBytes();
            } else {
                content = Base64.decode(data, Base64.DEFAULT);
            }

            //4.获得原始对称密钥的字节数组
            byte[] enCodeFormat = defaultSecretKey.getBytes();
            //5.根据字节数组生成AES密钥
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, defaultSecretKey);
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            //6.根据指定算法AES自成密码器
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            // 初始化
            cipher.init(mode, keySpec, ivParameterSpec);
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
                // 同样对加密后数据进行 base64 编码
//                Base64.Encoder encoder = Base64.getEncoder();
                return Base64.encodeToString(result, Base64.DEFAULT);
//                return encoder.encodeToString(result);
            } else {
                return new String(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
