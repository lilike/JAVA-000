package com.lilike.homework;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @Author llk
 * @Date 2020/10/16 21:14
 * @Version 1.0
 */
public class LilikeClassLoader extends ClassLoader {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, URISyntaxException {

        // 得到一个字节数组,然后对自己数组进行处理.
        LilikeClassLoader lilikeClassLoader = new LilikeClassLoader();
        File file = new File( lilikeClassLoader.getResource());

        byte[] bytes = fileConvertToByteArray(file);

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        Object o = lilikeClassLoader.defineClass("Hello", bytes, 0, bytes.length).newInstance();
        Method hello = o.getClass().getDeclaredMethod("hello");
        hello.invoke(o);

    }

    public String getResource() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("Hello.xlass");
        return url.toURI().getPath();
    }


    /**
     * 把一个文件转化为byte字节数组。
     *
     * @return
     */
    public static byte[] fileConvertToByteArray(File file) {
        byte[] data = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            data = baos.toByteArray();

            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}
