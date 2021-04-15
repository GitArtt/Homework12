package ru.geekbrains.homework12;

import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    static final int half = size / 2;

    public static void main(String[] args) {
        float[] arr1 = createArray(size);
        firstMethod(arr1);
        float[] arr2 = createArray(size);
        secondMethod(arr2);
        System.out.println("arr1 & arr2 equals: " + Arrays.equals(arr1, arr2));
    }

    static void firstMethod(float[] arr) {
        long a = System.currentTimeMillis();
        countIt(arr);
        System.out.println((System.currentTimeMillis() - a) / 1000.0 + "s");
    }

    private static float[] createArray(int size) {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        return arr;
    }

    static void secondMethod(float[] arr) {
        float[] a1 = new float[half];
        float[] a2 = new float[half];
        System.arraycopy(arr, 0, a1, 0, half);
        System.arraycopy(arr, half, a2, 0, half);
        long b = System.currentTimeMillis();
        Thread t1 = new Thread(() -> countIt(a1));
        Thread t2 = new Thread(() -> countIt(a2, half));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        System.arraycopy(a1, 0, arr, 0, half);
        System.arraycopy(a2, 0, arr, half, half);
        System.out.println((System.currentTimeMillis() - b) / 1000.0 + "s");
        System.out.println("Две части одного массива a1 & a2 equals: " + Arrays.equals(a1, a2));
    }

    static void countIt(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    static void countIt(float[] arr, int offset) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + offset) / 5) *
                    Math.cos(0.2f + (i + offset) / 5) * Math.cos(0.4f + (i + offset) / 2));
        }
    }

}

