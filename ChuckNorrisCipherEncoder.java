import java.util.Scanner;

public class ChuckNorrisCipherEncoder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String operation = "";
        while (!operation.equals("exit")) {
            System.out.print("Please input operation (encode/decode/exit): \n");
            operation = scanner.nextLine();
            if (operation.equals("encode")) {
                System.out.print("Input string: \n");
                String input = scanner.nextLine();
                String encoded = encode(input);
                System.out.println("Encoded string: \n" + encoded);
            } else if (operation.equals("decode")) {
                System.out.print("Input encoded string: \n");
                String input = scanner.nextLine();
                String decoded = decode(input);
                if (decoded == null) {
                    System.out.println("Encoded string is not valid.");
                } else {
                    System.out.println("Decoded string: \n" + decoded);
                }
            } else if (!operation.equals("exit")) {
                System.out.println("There is no '" + operation + "' operation");
            }
        }
        System.out.println("Bye!");
    }


    public static String encode(String inputString) {
        StringBuilder binaryString = new StringBuilder();
        for (byte b : inputString.getBytes()) {
            String binary = String.format("%7s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            binaryString.append(binary);
        }

        StringBuilder encodedString = new StringBuilder();
        int i = 0;
        while (i < binaryString.length()) {
            char c = binaryString.charAt(i);
            int count = 1;
            for (int j = i + 1; j < binaryString.length() && binaryString.charAt(j) == c; j++) {
                count++;
            }
            encodedString.append(c == '0' ? "00 " : "0 ");
            encodedString.append("0".repeat(Math.max(0, count)));
            encodedString.append(' ');
            i += count;
        }

        return encodedString.toString().trim();
    }

    public static String decode(String encodedInputString) {
        if (encodedInputString.matches(".*[^0 ]+.*")) {
            return null;
        }
        String[] blocks = encodedInputString.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blocks.length; i += 2) {
            if (!blocks[i].equals("0") && !blocks[i].equals("00")) {
                return null;
            }
            int count = blocks[i + 1].length();
            String binary = blocks[i].equals("0") ? "1" : "0";
            sb.append(binary.repeat(count));
        }
        String binaryString = sb.toString();
        if (binaryString.length() % 7 != 0) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 7) {
            String substring = binaryString.substring(i, i + 7);
            int asciiCode = Integer.parseInt(substring, 2);
            result.append((char) asciiCode);
        }
        return result.toString();
    }
}
