package de.zakath.simplecrypt;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.logging.*;

import javax.crypto.*;

/**
 * 
 * @author cw
 */
public class AES
{

  public static final int KEY_SIZE = 256;

  /**
   * Encrypts a given byte array with the given byte array key using AES. It's
   * strongly recommended, to use this method and NOT use any overloading!
   * 
   * @param input
   *            The byte array that should be encrypted.
   * @param key
   *            The key used for encryptions. Has not be right-sized already.
   * @return The encrypted byte array.
   */
  public static byte[] encrypt(byte[] input, byte[] key)
  {
    try
    {
      Key k = PBKDF2.deriveKey(new String(key, "UTF-8").toCharArray(),
          KEY_SIZE);
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, k);
      return cipher.doFinal(input);

    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | InvalidKeyException
        | IllegalBlockSizeException
        | BadPaddingException
        | UnsupportedEncodingException ex)
    {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }

  /**
   * Encrypts a given byte array with the given string key. It's strongly
   * recommended to use {@link #decrypt(byte[], byte[]) } to ensure proper
   * encryption.
   * 
   * @param input
   *            The byte array that should be encrypted.
   * @param key
   *            The key used for encryptions. Has not be right-sized already.
   * @return The encrypted byte array.
   */
  public static byte[] encrypt(byte[] input, String key)
  {
    return encrypt(input, key.getBytes());
  }

  /**
   * Encrypts a given string with the given byte array key. It's strongly
   * recommended to use {@link #decrypt(byte[], byte[]) } to ensure proper
   * encryption.
   * 
   * @param input
   *            The string that should be encrypted.
   * @param key
   *            The key used for encryptions. Has not be right-sized already.
   * @return The encrypted string. Encoding is Base64.
   */
  public static String encrypt(String input, byte[] key)
  {
    return Hex.encode(encrypt(input.getBytes(), key));
  }

  /**
   * Encrypts a given string with the given string key. It's strongly
   * recommended to use {@link #decrypt(byte[], byte[]) } to ensure proper
   * encryption.
   * 
   * @param input
   *            The string that should be encrypted.
   * @param key
   *            The key used for encryptions. Has not be right-sized already.
   * @return The encrypted string. Encoding is Base64.
   */
  public static String encrypt(String input, String key)
  {
    return Hex.encode(encrypt(input.getBytes(), key.getBytes()));
  }

  /**
   * Decrypts a given byte array with the given byte array key. It's
   * recommended to use this method in order to ensure a proper encryption!
   * 
   * @param input
   *            The byte array that should be decrypted.
   * @param key
   *            The key used for decription. Has not to be right-sized.
   *            already, but has to be the same key as used for encryption.
   * @return The decryptet byte array.
   */
  public static byte[] decrypt(byte[] input, byte[] key)
  {
    try
    {
      Key k = PBKDF2.deriveKey(new String(key, "UTF-8").toCharArray(),
          KEY_SIZE);
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, k);
      return cipher.doFinal(input);

    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | InvalidKeyException
        | IllegalBlockSizeException
        | BadPaddingException
        | UnsupportedEncodingException ex)
    {
      Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }

  /**
   * Decrypts a given byte array with the given String key. It's strongly
   * recommended to use {@link #decrypt(byte[], byte[]) } in order to ensure a
   * proper encryption!
   * 
   * @param input
   *            The byte array that should be decrypted.
   * @param key
   *            The key used for decription. Has not to be right-sized.
   *            already, but has to be the same key as used for encryption.
   * @return The decryptet byte array.
   */
  public static byte[] decrypt(byte[] input, String key)
  {
    return decrypt(input, key.getBytes());
  }

  /**
   * Decrypts a given string with the given byte array key. It's strongly
   * recommended to use {@link #decrypt(byte[], byte[]) } in order to ensure a
   * proper encryption!
   * 
   * @param input
   *            The string that should be decrypted.
   * @param key
   *            The key used for decription. Has not to be right-sized
   *            already, but has to be the same key as used for encryption.
   * @return The decryptet String. It's very likely to get a 'wrong' text. If
   *         this happens, use the {@link #decrypt(byte[], byte[]) } method
   *         instead and convert the byte array one our own!
   */
  public static String decrypt(String input, byte[] key)
  {
    return new String(decrypt(Hex.decode(input), key));
  }

  /**
   * Decrypts a given string with the given string key. It's strongly
   * recommended to use {@link #decrypt(byte[], byte[]) } order to ensure a
   * proper encryption!
   * 
   * @param input
   *            The string that should be decrypted.
   * @param key
   *            The key used for decription. Has not to be right-sized
   *            already, but has to be the same key as used for encryption.
   * @return The decryptet String. It's very likely to get a 'wrong' text. If
   *         this happens, use the {@link #decrypt(byte[], byte[]) } method
   *         instead and convert the byte array one our own!
   */
  public static String decrypt(String input, String key)
  {
    return new String(decrypt(Hex.decode(input), key.getBytes()));
  }

}
