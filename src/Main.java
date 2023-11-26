import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        File customersFile = new File("C:\\Users\\Grigoriy\\IdeaProjects\\untitled\\src\\customers.csv"); // Путь к файлу с данными о покупателях
        File productsFile = new File("C:\\Users\\Grigoriy\\IdeaProjects\\untitled\\src\\products.csv"); // Путь к файлу с данными о товарах
        File salesFile = new File("C:\\Users\\Grigoriy\\IdeaProjects\\untitled\\src\\sales.csv"); // Путь к файлу с данными о продажах

        try {
            SaleDataReader reader = new SaleDataReader(salesFile, customersFile, productsFile);
            List<Sale> salesData = reader.readSalesData();
            List<Customer> customersData = reader.readCustomersData();
            List<Product> productsData = reader.readProductsData();

            SalesProcessor processor = new SalesProcessor(salesData, customersData, productsData);

            UserInterface.start(processor); // Запуск пользовательского интерфейса

        } catch (IOException e) {
            System.out.println("Error reading data files: " + e.getMessage());
        }
    }
}