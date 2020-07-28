package com.process.core.util;

import com.process.core.Component;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;

/**
 * @program: thriftdemo
 * @description: 序列化工具
 * @author: maoxiangyi
 * @create: 2020-07-06 18:47
 *
 * https://blog.csdn.net/qq_34446485/article/details/81542691
 */
public class SerializableUtil {
    public static String serializable(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        //方法1
//        String result = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        //方法2
        String result = toHexString(byteArrayOutputStream.toByteArray());
        return result;
    }

    public static Object deSerializable(String obj) throws IOException, ClassNotFoundException {
        //方法1
//        byte[] bytes = Base64.getDecoder().decode(obj);
        //方法2
        byte[] bytes = toByteArray(obj);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        return object;
    }


    public static byte[] toByteArray(String hexString) {
        if (StringUtils.isEmpty(hexString))
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**
     * 字节数组转成16进制表示格式的字符串
     *
     * @param byteArray 需要转换的字节数组
     * @return 16进制表示格式的字符串
     **/
    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }
}
