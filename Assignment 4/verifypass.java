import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class verifypass {
	private static SecretKeySpec secretKey;
	private static byte[] key;

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		final String secretKey = "elonmusk";
		BufferedReader br = new BufferedReader(new FileReader("password.txt"));
		String currentLine;
		HashMap<String, String> map = new HashMap<String, String>();
		while ((currentLine = br.readLine()) != null) {
			String[] strArgs = currentLine.split(" ");
			map.put(strArgs[0], strArgs[1]);
		}
		br.close();

		Scanner input = new Scanner(System.in);
		System.out.println("Enter user ID");
		String user_id = input.nextLine();
		System.out.println("Enter Password");
		String password = input.nextLine();
		boolean isKeyPresent = map.containsKey(user_id);
		if (isKeyPresent == true) {
			String encrypted_pass = map.get(user_id);
			String decryptedString = verifypass.decrypt(encrypted_pass, secretKey);
			if (decryptedString.equals(password)) {
				System.out.println("Password is correct");
			} else {
				System.out.println("Password is incorrect");
			}

		} else {
			System.out.println("ID Not present");

		}
		input.close();
	}

	public static String decrypt(String encrypted_pass, String secret)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		setKey(secret);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted_pass)));
	}

	public static void setKey(String myKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest sha = null;

		key = myKey.getBytes("UTF-8");
		sha = MessageDigest.getInstance("SHA-1");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16);
		secretKey = new SecretKeySpec(key, "AES");

	}

}
