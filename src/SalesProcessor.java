import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.util.Map;

public class SalesProcessor {
    private List<Sale> salesData;
    private List<Customer> customersData;
    private List<Product> productsData;

    public SalesProcessor(List<Sale> salesData, List<Customer> customersData, List<Product> productsData) {
        this.salesData = salesData;
        this.customersData = customersData;
        this.productsData = productsData;
    }

    public double calculateTotalSales() {
        return salesData.stream()
                .mapToDouble(Sale::getSaleAmount)
                .sum();
    }

    public List<Product> findTopFivePopularProducts() {
        Map<Integer, Long> productCounts = salesData.stream()
                .collect(Collectors.groupingBy(Sale::getProductId, Collectors.counting()));

        return productCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(5)
                .map(entry -> productsData.stream()
                        .filter(product -> product.getProductId() == entry.getKey())
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Product> findTopFiveUnpopularProducts() {
        Map<Integer, Long> productCounts = salesData.stream()
                .collect(Collectors.groupingBy(Sale::getProductId, Collectors.counting()));

        return productCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .map(entry -> productsData.stream()
                        .filter(product -> product.getProductId() == entry.getKey())
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Customer> findCustomersByTotalAmount(double amount) {
        Map<Integer, Double> customerTotalAmount = salesData.stream()
                .collect(Collectors.groupingBy(Sale::getCustomerId, Collectors.summingDouble(Sale::getSaleAmount)));

        return customerTotalAmount.entrySet().stream()
                .filter(entry -> entry.getValue() > amount)
                .map(entry -> customersData.stream()
                        .filter(customer -> customer.getCustomerId() == entry.getKey())
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public Map<String, Object> analyzeSalesTrends() {
        Map<String, Object> salesTrends = new HashMap<>();

        // Анализ тенденций продаж
        double totalSales = calculateTotalSales();

        List<Product> topFivePopularProducts = findTopFivePopularProducts();
        List<Product> topFiveUnpopularProducts = findTopFiveUnpopularProducts();

        List<Customer> highValueCustomers = findCustomersByTotalAmount(1000.0); // пример суммы

        salesTrends.put("TotalSales", totalSales);
        salesTrends.put("TopFivePopularProducts", topFivePopularProducts);
        salesTrends.put("TopFiveUnpopularProducts", topFiveUnpopularProducts);
        salesTrends.put("HighValueCustomers", highValueCustomers);

        return salesTrends;
    }


    public void generateReports(Map<String, Object> data) {
// Генерация отчетов на основе переданных данных
        try {
            FileWriter writer = new FileWriter("sales_report.txt");

// Запись общей суммы продаж в отчет
            writer.write("Total Sales: " + data.get("TotalSales") + "\n\n");

// Запись популярных товаров в отчет
            writer.write("Top Five Popular Products:\n");
            for (Product product : (List<Product>) data.get("TopFivePopularProducts")) {
                writer.write(product.getProductName() + "\n");
            }
            writer.write("\n");

// Запись непопулярных товаров в отчет
            writer.write("Top Five Unpopular Products:\n");
            for (Product product : (List<Product>) data.get("TopFiveUnpopularProducts")) {
                writer.write(product.getProductName() + "\n");
            }
            writer.write("\n");

// Запись информации о покупателях с высокими покупками
            writer.write("High Value Customers:\n");
            for (Customer customer : (List<Customer>) data.get("HighValueCustomers")) {
                writer.write(customer.getCustomerName() + " - " + customer.getCustomerEmail() + "\n");
            }

            writer.close();
            System.out.println("Reports generated successfully.");
        } catch (IOException e) {
            System.out.println("Error generating reports: " + e.getMessage());
        }
    }
}