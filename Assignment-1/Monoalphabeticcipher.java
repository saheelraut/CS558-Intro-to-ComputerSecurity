import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Monoalphabeticcipher {

    static char[] englishSet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    static ArrayList<Integer> numberList = new ArrayList<Integer>();
    static int index;

    static void encrypt(String input, String output, ArrayList<Integer> randomList) throws IOException {
        File infile = new File(input);
        File outfile = new File(output);
        if (outfile.createNewFile()) {
            System.out.println("\nOutput file " +outfile+" created\n");
        } else {
            System.out.println("\nFile already exists. Overwriting the file - "+outfile);
        }
        FileWriter myWriter = new FileWriter(output);
        if (infile.exists()) {
            Scanner readInputEn = new Scanner(infile);
            while (readInputEn.hasNextLine()) {
                String En_data = readInputEn.nextLine();
                StringBuilder encryptedString = new StringBuilder();
                for (int i = 0; i < En_data.length(); i++) {
                    for (int j = 0; j < englishSet.length; j++) {
                        if (englishSet[j] == (En_data.charAt(i))) {
                            index = randomList.get(j);
                            encryptedString.append(englishSet[index]);
                        }
                    }
                }
                myWriter.append(encryptedString);
                myWriter.close();

            }
            readInputEn.close();
        } else {
            System.out.println("File does not exists");
        }
    }

    static void decrypt(String input, String output, ArrayList<Integer> randomList) throws IOException {
        File infile = new File(input);
        FileWriter myWriter = new FileWriter(output);
        if (infile.exists()) {
            System.out.println("\n");
            Scanner readInputDe = new Scanner(infile);
            while (readInputDe.hasNextLine()) {
                String De_data = readInputDe.nextLine();
                StringBuilder decryptedString = new StringBuilder();
                for (int i = 0; i < De_data.length(); i++) {
                    for (int j = 0; j < englishSet.length; j++) {
                        if (englishSet[j] == (De_data.charAt(i))) {
                            index = randomList.indexOf(j);
                            decryptedString.append(englishSet[index]);
                        }
                    }
                }
                myWriter.append(decryptedString);
                myWriter.close();

            }
            readInputDe.close();
        } else {
            System.out.println("File does not exists");
        }
    }

    public static void main(String[] args) {
        int choice;
        if (args.length == 3) {
            try {
                choice = Integer.parseInt(args[2]);
                String input = args[0];
                String output = args[1];
                int seed_key = 5;
                int numberOfElements = 26;

                for (int i = 0; i < 26; i++) {
                    numberList.add(i);
                }
                Random rand = new Random(seed_key);
                ArrayList<Integer> randomList = new ArrayList<Integer>();
                for (int i = 0; i < 26; i++) {
                    int currentValue = rand.nextInt(numberOfElements);
                    if (!randomList.contains(numberList.get(currentValue)))
                        randomList.add(numberList.get(currentValue));
                    else
                        i--;
                }
                System.out.println("Mappings");

                for (int i = 0; i < 26; i++) {
                    System.out.print(englishSet[i] + "-" + englishSet[randomList.get(i)] + " , ");
                }
                if (choice == 1) {
                    encrypt(input, output, randomList);

                } else if (choice == 0) {
                    decrypt(input, output, randomList);

                } else {
                    System.out.println("Invalid Option entered \n Select 1: encryption, 0: decryption");
                }
            } catch (NumberFormatException | IOException e) {
                System.err.println("Argument " + args[2] + " must be an integer");
                System.exit(1);
            }
        } else if (args.length < 3 && args.length > 0) {
            System.out.println("Arguments length less than 3");
        } else {
            System.out.println("No commandline arguments found");
        }
    }
}
