package com.lilike.homework;

import org.apache.commons.lang.StringUtils;
import sun.swing.StringUIClientPropertyKey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;

/**
 *  字节码文件的学习
 *
 * @Author llk
 * @Date 2020/10/18 10:47
 * @Version 1.0
 */
public class ByteCodeResearch {


    public static void main(String[] args) throws Exception {
        LilikeClassLoader lilikeClassLoader = new LilikeClassLoader();
        File file = new File(lilikeClassLoader.getClass().getClassLoader().getResource("Hello.xlass").toURI().getPath());

        byte[] bytes = LilikeClassLoader.fileConvertToByteArray(file);
        for (int i = 0; i < bytes.length; i++) {

            String s = StringUtils.leftPad(Integer.toBinaryString((255 - bytes[i]) & 0xff), 8, '0');
            System.out.print(s + "\t");
        }


    }




}
