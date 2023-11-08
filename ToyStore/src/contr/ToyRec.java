package contr;

import model.GiftsPrize;
import model.Prize;
import model.Toy;
import view.CheckingNumber;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static contr.BuyerRec.startId;

public class ToyRec {
    static final String sep = ",";
    private static final String FILE_NAME_TOYS = "/java/Toy_Store_Java/ToyStore/src/db/Toys.csv";
    static List<Toy> toys = new ArrayList<>();


    public static void writeToFile(String fileName, List<Toy> toys) {
        try (FileWriter writer = new FileWriter(fileName, true)) {

            appendToFileLine(toys, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void NewToy() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Имя игрушки: ");
        String name = scanner.nextLine();
        System.out.println("Количество игрушек: ");
        int amount = CheckingNumber.choice();
        System.out.println("Шанс выпадения игрушки: ");
        int dropFrequency = CheckingNumber.choice();
        toys.add(new Toy(getNewToyId(), name, amount, dropFrequency));
        ToyRec.writeToFile(FILE_NAME_TOYS, toys);
    }

    private static int getNewToyId() {
        return startId(FILE_NAME_TOYS);
    }

    private static void appendToFileLine(List<Toy> toys, FileWriter writer) throws IOException {
        for (Toy toy : toys) {
            writer.append(String.valueOf(toy.getId()));
            writer.append(sep);
            writer.append(toy.getName());
            writer.append(sep);
            writer.append(String.valueOf(toy.getAmount()));
            writer.append(sep);
            writer.append(String.valueOf(toy.getDropFrequency()));
            writer.append("\n");
        }

        writer.flush();
    }

    public static void updateToyDropFrequencyById(String fileName, int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            List<Toy> toys = new ArrayList<>();
            String line;

            boolean idFound = false;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(sep);
                int toyId = Integer.parseInt(fields[0]);
                String name = fields[1];
                int amount = Integer.parseInt(fields[2]);
                int dropFrequency = Integer.parseInt(fields[3]);
                int changeWeight;
                if (toyId == id) {
                    System.out.println("Новый шанс выпадения игрушки: ");
                    changeWeight = CheckingNumber.choice();
                    toys.add(new Toy(toyId, name, amount, changeWeight));
                    System.out.println("Шанс выпадения игрушки изменён!");
                    idFound = true;
                } else {
                    toys.add(new Toy(toyId, name, amount, dropFrequency));
                }

            }
            if (!idFound) {
                System.out.println("Ошибка, нет игрушки с таким номером!");
            }

            overwriteFile(fileName, toys);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Toy> readFromFile(String fileName) {
        List<Toy> toys = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] fields = line.split(sep);

                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                int amount = Integer.parseInt(fields[2]);
                int dropFrequency = Integer.parseInt(fields[3]);


                Toy toy = new Toy(id, name, amount, dropFrequency);
                toys.add(toy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toys;
    }


    public static void removeToy(String fileName, int id) {
        List<Toy> toys = readFromFile(fileName);
        boolean isToyFound = false;
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                toys.remove(toy);
                System.out.println("Игрушка удалена!");
                isToyFound = true;
                break;
            }
        }
        if (!isToyFound) {
            System.out.println("Нет игрушки с таким номером!");
        }

        overwriteFile(fileName, toys);
    }

    public static void overwriteFile(String fileName, List<Toy> toys) {
        try (FileWriter writer = new FileWriter(fileName)) {

            appendToFileLine(toys, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeToPrizeToys(String fileName, List<Prize> prizes) {
        try (FileWriter writer = new FileWriter(fileName, true)) {

            appendToPrize(prizes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendToPrize(List<Prize> prizes, FileWriter writer) throws IOException {
        for (Prize prize : prizes) {
            writer.append(String.valueOf(prize.getId()));
            writer.append(sep);
            writer.append(prize.getName());
            writer.append(sep);
            writer.append(String.valueOf(prize.getAmount()));
            writer.append(sep);
            writer.append(String.valueOf(prize.getDropFrequency()));
            writer.append("\n");
        }

        writer.flush();
    }


    public static void ToyPrize(String fileName, String fileTwo) {
        List<Toy> toys = readFromFile(fileName);
        if (toys.isEmpty()) {
            System.out.println("Игрушек пока что нет!");
            return;
        }
        Toy randomToy = getRandomToy(toys);
        assert randomToy != null;
        if (randomToy.getAmount() <= 0) {
            System.out.println("Такой игрушки больше нет!");
            return;
        }
        randomToy.setAmount(randomToy.getAmount() - 1);
        overwriteFile(fileName, toys);
        List<Prize> prizeToys = new ArrayList<>();
        Prize prizeToy = new Prize(randomToy.getId(), randomToy.getName());
        prizeToys.add(prizeToy);
        writeToPrizeToys(fileTwo, prizeToys);
    }

    public static Toy getRandomToy(List<Toy> toyList) {
        if (toyList.isEmpty()) {
            System.out.println("Игрушек пока что нет!");
            return null;
        }
        int totalWeight = 0;
        for (Toy toy : toyList) {
            totalWeight += toy.getDropFrequency();
        }
        int randomWeight = new Random().nextInt(totalWeight);
        int currentWeight = 0;
        for (Toy toy : toyList) {
            currentWeight += toy.getDropFrequency();
            if (currentWeight >= randomWeight) {
                return toy;
            }
        }
        return null;
    }

    public static void addRandomPrizeToAwardedFileAndDeleteAwardedPrize(String fileName, String fileTwo) {
        List<Prize> prizeList = readPrizeFile(fileName);
        if (prizeList.isEmpty()) {
            System.out.println("Призов пока что нет!");
            return;
        }
        List<GiftsPrize> giftsPrizeList = new ArrayList<>();
        Random random = new Random();
        Prize randomPrize = prizeList.get(random.nextInt(prizeList.size()));
        GiftsPrize giftsPrize = new GiftsPrize(randomPrize.getId(), randomPrize.getName());
        giftsPrizeList.add(giftsPrize);
        writeToAwardedPrizeToys(fileTwo, giftsPrizeList);
        removePrizeFromListAndRewriteFile(fileName, randomPrize.getName());
        System.out.println("Игрушка " + randomPrize.getName() + " выдана!");
    }

    public static void removePrizeFromListAndRewriteFile(String fileName, String prizeName) {
        List<Prize> prizeList = readPrizeFile(fileName);
        for (Prize prize : prizeList) {
            if (prize.getName().equals(prizeName)) {
                prizeList.remove(prize);
                break;
            }
        }
        overwritePrizeFile(fileName, prizeList);
    }

    public static void overwritePrizeFile(String fileName, List<Prize> prizes) {
        try (FileWriter writer = new FileWriter(fileName)) {

            appendToPrizeFileLine(prizes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendToPrizeFileLine(List<Prize> prizes, FileWriter writer) throws IOException {
        for (Prize prize : prizes) {
            writer.append(String.valueOf(prize.getId()));
            writer.append(sep);
            writer.append(prize.getName());
            writer.append("\n");
        }

        writer.flush();
    }


    public static void writeToAwardedPrizeToys(String fileName, List<GiftsPrize> giftsPrizes) {
        try (FileWriter writer = new FileWriter(fileName, true)) {

            appendToAwardedPrize(giftsPrizes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendToAwardedPrize(List<GiftsPrize> giftsPrizes, FileWriter writer) throws IOException {
        for (Prize prize : giftsPrizes) {
            writer.append(String.valueOf(prize.getId()));
            writer.append(sep);
            writer.append(prize.getName());
            writer.append("\n");
        }

        writer.flush();
    }


    public static List<Prize> readPrizeFile(String fileName) {
        List<Prize> prizes = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(sep);
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                Prize prize = new Prize(id, name);
                prizes.add(prize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prizes;
    }

}