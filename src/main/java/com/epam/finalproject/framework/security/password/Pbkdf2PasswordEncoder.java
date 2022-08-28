package com.epam.finalproject.framework.security.password;

import com.epam.finalproject.framework.security.util.EncodingUtils;
import com.epam.finalproject.framework.security.util.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;


public class Pbkdf2PasswordEncoder implements PasswordEncoder {
    private static final String DEFAULT_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int DEFAULT_SALT_LENGTH = 8;
    private static final int DEFAULT_HASH_WIDTH = 256;
    private static final int DEFAULT_ITERATIONS = 185000;
    private final SecureRandom random;

    private final int saltLength;
    private final byte[] secret;
    private final int hashWidth;
    private final int iterations;
    private final String algorithm;
    private boolean encodeHashAsBase64;

    public Pbkdf2PasswordEncoder() {
        this("");
    }

    public Pbkdf2PasswordEncoder(CharSequence secret) {
        this(secret, DEFAULT_SALT_LENGTH, DEFAULT_ITERATIONS, DEFAULT_HASH_WIDTH);
    }

    public Pbkdf2PasswordEncoder(CharSequence secret, int saltLength) {
        this(secret, saltLength, DEFAULT_ITERATIONS, DEFAULT_HASH_WIDTH);
    }

    public Pbkdf2PasswordEncoder(CharSequence secret, int iterations, int hashWidth) {
        this(secret, DEFAULT_SALT_LENGTH, iterations, hashWidth);
    }

    public Pbkdf2PasswordEncoder(CharSequence secret, int saltLength, int iterations, int hashWidth) {
        this(secret,DEFAULT_ALGORITHM,saltLength,iterations,hashWidth);
    }

    public Pbkdf2PasswordEncoder(CharSequence secret,String algorithm, int saltLength, int iterations, int hashWidth) {
        this.algorithm = algorithm;
        this.secret = encodeUtf8(secret);
        this.iterations = iterations;
        this.hashWidth = hashWidth;
        this.random = new SecureRandom();
        this.saltLength = saltLength;
    }

    public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
        this.encodeHashAsBase64 = encodeHashAsBase64;
    }

    public String encode(CharSequence rawPassword) {
        byte[] salt = generateSalt();
        byte[] encoded = this.encode(rawPassword, salt);
        return this.encode(encoded);
    }

    private byte[] generateSalt() {
        byte[] bytes = new byte[this.saltLength];
        this.random.nextBytes(bytes);
        return bytes;
    }

    private String encode(byte[] bytes) {
        return this.encodeHashAsBase64 ? Base64.getEncoder().encodeToString(bytes) : String.valueOf(Hex.encode(bytes));
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        byte[] digested = this.decode(encodedPassword);
        byte[] salt = EncodingUtils.subArray(digested, 0, saltLength);
        return MessageDigest.isEqual(digested, this.encode(rawPassword, salt));
    }

    private byte[] decode(String encodedBytes) {
        return this.encodeHashAsBase64 ? Base64.getDecoder().decode(encodedBytes) : Hex.decode(encodedBytes);
    }


    private byte[] encode(CharSequence rawPassword, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(rawPassword.toString().toCharArray(), EncodingUtils.concatenate(salt,
                    this.secret), this.iterations, this.hashWidth);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            return EncodingUtils.concatenate(salt, skf.generateSecret(spec).getEncoded());
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Could not create hash", e);
        }
    }

    private static byte[] encodeUtf8(CharSequence cs) {
        ByteBuffer bb = StandardCharsets.UTF_8.encode(CharBuffer.wrap(cs));
        byte[] result = new byte[bb.remaining()];
        bb.get(result);
        return result;
    }
}
