package view;

import contr.BuyerRec;
import contr.ToyRec;

import static contr.ToyRec.addRandomPrizeToAwardedFileAndDeleteAwardedPrize;
import static contr.ToyRec.ToyPrize;

public class conclusion {
    final String FILE_NAME_TOYS = "/java/Toy_Store_Java/ToyStore/src/db/Toys.csv";
    final String FILE_NAME_BUYERS = "/java/Toy_Store_Java/ToyStore/src/db/Buyers.csv";
    final String PRIZE_TOY = "/java/Toy_Store_Java/ToyStore/src/db/GiftsToy.csv";
    final String AWARDED_TOY = "/java/Toy_Store_Java/ToyStore/src/db/GiftsByuers.csv";

    public void run() {
        int numberOfMenu;
        do {
            ToyStory.pageHeadMenuView();
            numberOfMenu = CheckingNumber.choice();
            switch (numberOfMenu) {
                case 1:
                    runToys();
                    break;
                case 2:
                    runBuyers();
                    break;
                case 3:
                    runDrawingToys();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("\n\tНе верно. Попробуйте еще раз.");
            }
        } while (numberOfMenu != 4);
    }


    public void runToys() {
        int numberOfMenu;
        do {
            ToyStory.pageToyView();
            numberOfMenu = CheckingNumber.choice();
            switch (numberOfMenu) {
                case 1:
                    System.out.println(ToyRec.readFromFile(FILE_NAME_TOYS));
                    break;
                case 2:
                    ToyRec.NewToy();
                    break;
                case 3:
                    System.out.println(ToyRec.readFromFile(FILE_NAME_TOYS));
                    System.out.println("\n\tВведите № игрушки для изменения выпадения шанса: ");
                    int idToy = CheckingNumber.choice();
                    ToyRec.updateToyDropFrequencyById(FILE_NAME_TOYS, idToy);
                    break;
                case 4:
                    System.out.println(ToyRec.readFromFile(FILE_NAME_TOYS));
                    System.out.println("\n\tВведите № игрушки для ее удаления: ");
                    ToyRec.removeToy(FILE_NAME_TOYS, CheckingNumber.choice());
                    break;
                case 5:
                    break;
                default:
                    System.out.println("\n\tНе верно. Попробуйте еще раз.");
            }
        } while (numberOfMenu != 5);
    }

    public void runBuyers() {
        int numberOfMenu;
        do {
            ToyStory.pageBuyersView();
            numberOfMenu = CheckingNumber.choice();
            switch (numberOfMenu) {
                case 1:
                    System.out.println(BuyerRec.readFromFile(FILE_NAME_BUYERS));
                    break;
                case 2:
                    BuyerRec.NewBuyer();
                    System.out.println("\n\tНовый покупатель добавлен!");
                    break;
                case 3:
                    System.out.println(BuyerRec.readFromFile(FILE_NAME_BUYERS));
                    System.out.println("\n\tВведите № покупателя для его удаления: ");
                    int num = CheckingNumber.choice();
                    BuyerRec.removeBuyer(FILE_NAME_BUYERS, num);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("\n\tНе верно. Попробуйте еще раз.");
            }
        } while (numberOfMenu != 4);
    }

    public void runDrawingToys() {
        int numberOfMenu;
        do {
            ToyStory.pageToysDrawingView();
            numberOfMenu = CheckingNumber.choice();
            switch (numberOfMenu) {
                case 1:
                    System.out.println("\n\tСписок призовых игрушек.");
                    System.out.println(ToyRec.readPrizeFile(PRIZE_TOY));
                    break;
                case 2:
                    System.out.println("\n\tСписок разыгранных игрушек.");
                    System.out.println(ToyRec.readPrizeFile(AWARDED_TOY));
                    break;
                case 3:
                    ToyPrize(FILE_NAME_TOYS, PRIZE_TOY);
                    System.out.println("\n\tСписок призовых игрушек.");
                    System.out.println(ToyRec.readPrizeFile(PRIZE_TOY));
                    break;
                case 4:
                    addRandomPrizeToAwardedFileAndDeleteAwardedPrize(PRIZE_TOY, AWARDED_TOY);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("\n\tНе верно. Попробуйте еще раз.");
            }
        } while (numberOfMenu != 5);
    }
}