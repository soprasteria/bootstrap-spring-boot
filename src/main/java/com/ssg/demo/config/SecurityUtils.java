package com.ssg.demo.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mvincent on 21/09/2016.
 */
public class SecurityUtils {

  public static final String MAGIC_KEY = "demo";


  public static String createToken(UserDetails userDetails)
  {
		/* Expires in one hour */
    long expires = System.currentTimeMillis() + 1000L * 60 * 60;

    StringBuilder tokenBuilder = new StringBuilder();
    tokenBuilder.append(userDetails.getUsername());
    tokenBuilder.append(":");
    tokenBuilder.append(expires);
    tokenBuilder.append(":");
    tokenBuilder.append(computeSignature(userDetails, expires));

    return tokenBuilder.toString();
  }

  public static String createTemporaryToken(String candidate)
  {
		/* Expires in one hour */
    long expires = System.currentTimeMillis() + 1000L * 60 * 60;

    StringBuilder tokenBuilder = new StringBuilder();
    tokenBuilder.append("temp:");
    tokenBuilder.append(candidate);
    tokenBuilder.append(":");
    tokenBuilder.append(expires);
    tokenBuilder.append(":");
    tokenBuilder.append(computeSignature(candidate, expires));

    return tokenBuilder.toString();
  }

  public static String computeSignature(String candidate, long expires)
  {
    StringBuilder signatureBuilder = new StringBuilder();
    signatureBuilder.append(candidate);
    signatureBuilder.append(":");
    signatureBuilder.append(expires);
    signatureBuilder.append(":");
    signatureBuilder.append(MAGIC_KEY);

    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("No MD5 algorithm available!");
    }

    return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
  }

  public static String computeSignature(UserDetails userDetails, long expires)
  {
    StringBuilder signatureBuilder = new StringBuilder();
    signatureBuilder.append(userDetails.getUsername());
    signatureBuilder.append(":");
    signatureBuilder.append(expires);
    signatureBuilder.append(":");
    signatureBuilder.append(userDetails.getPassword());
    signatureBuilder.append(":");
    signatureBuilder.append(MAGIC_KEY);

    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("No MD5 algorithm available!");
    }

    return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
  }


  public static String getUserNameFromToken(String authToken)
  {
    if (null == authToken) {
      return null;
    }

    String[] parts = authToken.split(":");
    return parts[0];
  }


  public static boolean validateToken(String authToken, UserDetails userDetails)
  {
    String[] parts = authToken.split(":");
    long expires = Long.parseLong(parts[1]);
    String signature = parts[2];

    if (expires < System.currentTimeMillis()) {
      return false;
    }

    return signature.equals(computeSignature(userDetails, expires));
  }

  public static boolean validateTempToken(String authTempToken, String candidate)
  {
    String[] parts = authTempToken.split(":");
    long expires = Long.parseLong(parts[2]);
    String signature = parts[3];

    if (expires < System.currentTimeMillis()) {
      return false;
    }

    return signature.equals(computeSignature(candidate, expires));
  }
}

