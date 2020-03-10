package com.example.nio;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Fizz
 */
public class FileChannelDemo {

    public static void main(String[] args) throws IOException {
//        write();
//        read();
//        copy();
        copy2();
    }

    public static void write() throws IOException {
        File file = new File("C:\\Users\\Fizz\\Desktop\\file01.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.wrap("hello, 中国!".getBytes());
        channel.write(byteBuffer);
        fileOutputStream.close();
    }

    public static void read() throws IOException {
        File file = new File("C:\\Users\\Fizz\\Desktop\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }

    public static void copy() throws IOException {
        long start = System.currentTimeMillis();
        File file = new File("C:\\Users\\Fizz\\Desktop\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel inChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 70);
        File file2 = new File("C:\\Users\\Fizz\\Desktop\\file02.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel outChannel = fileOutputStream.getChannel();
        int i = -1;
        while ((i = inChannel.read(byteBuffer)) != -1) {
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        fileInputStream.close();
        fileOutputStream.close();
        if (file.length() != file2.length()) {
            throw new RuntimeException("复制失败！");
        }
        long x = System.currentTimeMillis() - start;
        System.out.println(x);
    }

    public static void copy2() throws IOException {
        long start = System.currentTimeMillis();
        File file = new File(ResourceUtils.getURL("classpath:").getPath() + "file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel inChannel = fileInputStream.getChannel();
        File file2 = new File("C:\\Users\\Fizz\\Desktop\\file02.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel outChannel = fileOutputStream.getChannel();
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        fileInputStream.close();
        fileOutputStream.close();
        if (file.length() != file2.length()) {
            throw new RuntimeException("复制失败！");
        }
        long x = System.currentTimeMillis() - start;
        System.out.println(x);
    }
}
