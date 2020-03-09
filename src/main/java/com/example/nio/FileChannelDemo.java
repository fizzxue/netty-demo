package com.example.nio;

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
        copy();
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
        File file = new File("C:\\Users\\Fizz\\Desktop\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);
        File file2 = new File("C:\\Users\\Fizz\\Desktop\\file02.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        fileOutputStream.write(byteBuffer.array());
        fileInputStream.close();
        fileOutputStream.close();
    }
}
