package Server.utility;

import Server.ServerApp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashes password.
 */
public class PasswordHasher {
    /**
     * Hashes password;.
     *
     * @param password Password itself.
     * @return Hashed password.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(password.getBytes());
            BigInteger integers = new BigInteger(1, bytes);
            String newPassword = integers.toString(16);
            while (newPassword.length() < 32) {
                newPassword = "0" + newPassword;
            }
            return newPassword;
        } catch (NoSuchAlgorithmException exception) {
            //ServerApp.logger.error("Не найден алгоритм хэширования пароля!");
            System.out.println("ERROR IN PASSWORD HASHER");
            throw new IllegalStateException(exception);
        }
    }
}
