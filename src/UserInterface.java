import java.util.Scanner;

public class UserInterface {
    private static Scanner scanner = new Scanner(System.in);

    public static void start(SalesProcessor processor) {
        System.out.println("Добро пожаловать!");
        boolean running = true;

        while (running) {
            displayMenu();
            String userInput = getUserInput();

            switch (userInput.toLowerCase()) {
                case "1":
// Логика для выбора параметров обработки данных
                    System.out.println("Операция 1 выбрана.");
                    break;
                case "2":
// Логика для изменения параметров обработки данных
                    System.out.println("Операция 2 выбрана.");
                    break;
                case "3":
// Логика для выполнения анализа данных и генерации отчетов
                    System.out.println("Операция 3 выбрана.");
                    processor.generateReports();
                    break;
                case "q":
                    running = false;
                    break;
                default:
                    System.out.println("Неизвесная опция. Попробуйте ещё раз!");
                    break;
            }
        }

        System.out.println("Выход из программы, прощайте!");
    }

    public static void displayMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Выберите параметры обработки");
        System.out.println("2. Изменение параметров обработки");
        System.out.println("3. Анализ данных и создание отчетов");
        System.out.println("Q. Выход");
        System.out.print("Выберете операцию: ");
    }

    public static String getUserInput() {
        return scanner.nextLine();
    }
}