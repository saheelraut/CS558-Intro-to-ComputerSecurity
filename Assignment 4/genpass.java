import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class genpass {

	private static SecretKeySpec secretKey;
	private static byte[] key;

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException,
			IllegalBlockSizeException, IOException {
		final String secretKey = "elonmusk";

		Scanner input = new Scanner(System.in);
		System.out.println("Enter user ID");
		String user_id = input.nextLine();
		System.out.println("Enter Password");
		String password_original = input.nextLine();
		String encryptedPass = genpass.encrypt(password_original, secretKey);
		File f = new File("password.txt");
		boolean bool = f.exists();
		BufferedWriter dataFile = new BufferedWriter(new FileWriter("password.txt", true));
		if (bool == true) {
			dataFile.newLine();
			dataFile.append(user_id + " ");
			dataFile.append(encryptedPass);
			dataFile.close();
		} else {
			dataFile.append(user_id + " ");
			dataFile.append(encryptedPass);
			dataFile.close();
		}
		input.close();

	}

	public static String encrypt(String password_original, String secretKey2) throws UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
		MessageDigest sha = null;
		key = secretKey2.getBytes("UTF-8");
		sha = MessageDigest.getInstance("SHA-1");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16);
		secretKey = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(cipher.doFinal(password_original.getBytes("UTF-8")));
	}
}
