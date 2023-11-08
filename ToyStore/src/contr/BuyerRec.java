package contr;

import model.Buyer;
import view.CheckingNumber;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static contr.ToyRec.sep;

public class BuyerRec {
    private static final String DELIMITER = ",";
    private static final String FILE_NAME_BUYERS = "/java/Toy_Store_Java/ToyStore/src/db/Buyers.csv";
    static List<Buyer> buyers = new ArrayList<>();


    public static void NewBuyer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Имя покупателя: ");
        String nameBuyer = scanner.nextLine();
        System.out.println("Фамилия покупателя: ");
        String surnameBuyer = scanner.nextLine();
        System.out.println("Номера чека: ");
        int check = CheckingNumber.choice();
        System.out.println("Номера телефона: ");
        int phoneNumber = CheckingNumber.choice();
        buyers.add(new Buyer(getBuyerId(), nameBuyer, surnameBuyer, check, phoneNumber));
        BuyerRec.writeToBuyersFile(FILE_NAME_BUYERS, buyers);
    }

    private static int getBuyerId() {
        return startId(FILE_NAME_BUYERS);
    }

    static int startId(String fileNameBuyers) {
        int maxId = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileNameBuyers));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0].replaceAll("\"", ""));
                if (id > maxId) {
                    maxId = id;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxId + 1;
    }

    public static void writeToBuyersFile(String fileName, List<Buyer> buyers) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            appendLineBuyers(buyers, writer, DELIMITER);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static void appendLineBuyers(List<Buyer> buyers, FileWriter writer, String delimiter) throws IOException {
        for (Buyer buyer : buyers) {
            writer.append(String.valueOf(buyer.id()));
            writer.append(delimiter);
            writer.append(buyer.name());
            writer.append(delimiter);
            writer.append(buyer.surname());
            writer.append(delimiter);
            writer.append(String.valueOf(buyer.checkNumber()));
            writer.append(delimiter);
            writer.append(String.valueOf(buyer.phoneNumber()));
            writer.append("\n");
        }
    }

    public static List<Buyer> readFromFile(String fileName) {
        List<Buyer> buyers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(DELIMITER);
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                String surname = fields[2];
                int checkNumber = Integer.parseInt(fields[3]);
                int phoneNumber = Integer.parseInt(fields[4]);
                buyers.add(new Buyer(id, name, surname, checkNumber, phoneNumber));
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return buyers;
    }


    public static void removeBuyer(String fileName, int id) {
        List<Buyer> buyerList = readFromFile(fileName);
        boolean isBuyerFound = false;

        for (Buyer buyer : buyerList) {
            if (buyer.id() == id) {
                buyerList.remove(buyer);
                System.out.println("Покупатель удален!");
                isBuyerFound = true;
                break;
            }
        }

        if (!isBuyerFound) {
            System.out.println("Нет покупателя с таким номером!");
        }

        overwriteFileBuyers(fileName, buyerList);
    }

    public static void overwriteFileBuyers(String fileName, List<Buyer> buyers) {
        try (FileWriter writer = new FileWriter(fileName)) {

            appendToFileLineBuyer(buyers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendToFileLineBuyer(List<Buyer> buyers, FileWriter writer) throws IOException {
        appendLineBuyers(buyers, writer, sep);

        writer.flush();
    }
}