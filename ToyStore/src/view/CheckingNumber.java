package view;

import java.util.Scanner;

public class CheckingNumber {
    public static int choice() {
        Scanner scanner = new Scanner(System.in);
        int num;
        while (true) {
            try {
                num = Integer.parseInt(scanner.nextLine());
                if (num < 1) {
                    System.out.println("\n\tЧисло должно быть больше 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n\tВведите число!");
            }
        }
        return num;
    }
}