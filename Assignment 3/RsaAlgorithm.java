import java.util.Random;

public class RsaAlgorithm {

    // method to calculate gcd
    static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // method to check whether the entered numbers are prime
    static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;

    }

    public static void main(String[] args) {
        if (args.length == 2) {
            int p = Integer.parseInt(args[0]);
            int q = Integer.parseInt(args[1]);
            if ((isPrime(p)) && (isPrime(q))) {
                int n;
                int d = 0; // initialize d
                n = p * q;
                int euler = (p - 1) * (q - 1);
                System.out.println("n = " + n);
                System.out.println("ø(n) = " + euler);
                if ((p == 2 && q == 3) || (p == 3 && q == 2)) {
                    System.out.println("Random Encryption key e cannot be generated with negative bounds");
                    return;
                }
                int e;
                Random rand = new Random();
                int max = euler;
                int min = 2;

                do {
                    e = rand.nextInt(max - min) + min;
                    if (gcd(e, euler) == 1) {
                        System.out.println("e = " + e);
                        break;
                    }
                } while (e < euler);

                while (0 <= d && d <= n) {
                    if (e * d % euler == 1) {
                        System.out.println("d = " + d);
                        break;
                    }
                    d++;
                }
            } else {
                System.out.println("Some of the provided numbers are not Prime");
            }

        } else if (args.length == 1) {
            System.out.println("Program arguments incomplete");
        } else if (args.length < 1) {
            System.out.println("No command line arguments provided");
        } else {
            System.out.println("Argument length exceeded");
        }
    }
}
