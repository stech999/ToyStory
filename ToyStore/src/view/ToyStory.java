package view;

public class ToyStory {
    public static void delimiter() {
        System.out.println("*".repeat(50));
    }

    public static void pageHeadMenuView() {
        delimiter();
        System.out.println("\t\tМагазин игрушек!");
        delimiter();
        System.out.println("Выберите номер:");
        System.out.println("1. Игрушки.");
        System.out.println("2. Покупатели.");
        System.out.println("3. Розыгрыш игрушек.");
        System.out.println("4. Выход из программы.");
    }
    public static void pageToyView() {
        delimiter();
        System.out.println("\t\t\tИгрушки");
        delimiter();
        System.out.println("Выберите номер:");
        System.out.println("1. Посмотреть все игрушки.");
        System.out.println("2. Добавить новую игрушку.");
        System.out.println("3. Изменить шанс выпадения игрушки.");
        System.out.println("4. Удалить игрушку.");
        System.out.println("5. Выход в главное меню.");
    }
    public static void pageBuyersView() {
        delimiter();
        System.out.println("\t\tПокупатели");
        delimiter();
        System.out.println("Выберите номер:");
        System.out.println("1. Посмотреть список покупателей.");
        System.out.println("2. Добавить нового покупателя.");
        System.out.println("3. Удалить покупателя.");
        System.out.println("4. Выход в главное меню.");
    }
    public static void pageToysDrawingView() {
        delimiter();
        System.out.println("\t\tРозыгрыш игрушек");
        delimiter();
        System.out.println("Выберите номер:");
        System.out.println("1. Список призовых игрушек.");
        System.out.println("2. Список разыгранных игрушек.");
        System.out.println("3. Добавит игрушку в список призовых игрушек.");
        System.out.println("4. Получить призовую игрушку.");
        System.out.println("5. Выход в главное меню.");
    }
}