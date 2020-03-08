package com.example.nio;

import java.nio.IntBuffer;

/**
 * @author Fizz
 */
public class A {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(3);
        intBuffer.put(1);
        intBuffer.put(2);
        intBuffer.put(3);
        intBuffer.flip();
        System.out.println(intBuffer.get());
        intBuffer.put(4);
        intBuffer.flip();
        System.out.println(intBuffer.get());
        System.out.println(intBuffer.get());
    }
}
