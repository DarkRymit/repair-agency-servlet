package com.epam.finalproject.framework.security.util;

public abstract class Hex {

    private Hex() {
    }

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();


    public static char[] encode(byte[] bytes) {
        int nBytes = bytes.length;
        char[] result = new char[2 * nBytes];
        int j = 0;

        for (byte aByte : bytes) {
            result[j++] = HEX_CHARS[(240 & aByte) >>> 4];
            result[j++] = HEX_CHARS[15 & aByte];
        }

        return result;
    }

    public static byte[] decode(CharSequence s) {
        int nChars = s.length();
        if (nChars % 2 != 0) {
            throw new IllegalArgumentException("Hex-encoded string must have an even number of characters");
        } else {
            byte[] result = new byte[nChars / 2];

            for (int i = 0; i < nChars; i += 2) {
                int msb = Character.digit(s.charAt(i), 16);
                int lsb = Character.digit(s.charAt(i + 1), 16);
                if (msb < 0 || lsb < 0) {
                    throw new IllegalArgumentException(
                            "Detected a Non-hex character at " + (i + 1) + " or " + (i + 2) + " position");
                }

                result[i / 2] = (byte) (msb << 4 | lsb);
            }

            return result;
        }
    }
}