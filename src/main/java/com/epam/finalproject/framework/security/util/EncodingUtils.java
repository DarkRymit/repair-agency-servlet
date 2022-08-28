package com.epam.finalproject.framework.security.util;

public interface EncodingUtils {


    static byte[] subArray(byte[] array, int beginIndex, int endIndex) {
        int length = endIndex - beginIndex;
        byte[] subarray = new byte[length];
        System.arraycopy(array, beginIndex, subarray, 0, length);
        return subarray;
    }

    static byte[] concatenate(byte[]... arrays) {
        int length = 0;
        int destPos = arrays.length;

        for (int var4 = 0; var4 < destPos; ++var4) {
            byte[] array = arrays[var4];
            length += array.length;
        }
        byte[] newArray = new byte[length];

        destPos = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, newArray, destPos, array.length);
            destPos += array.length;
        }

        return newArray;
    }
}
