import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class SaleDataReader {
    private File salesFile;
    private File customersFile;
    private File productsFile;

    public SaleDataReader(File salesFile, File customersFile, File productsFile) {
        this.salesFile = salesFile;
        this.customersFile = customersFile;
        this.productsFile = productsFile;
    }

    public List<Sale> readSalesData() throws IOException {
        List<Sale> salesData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(salesFile))) {
            String line;
            br.readLine(); // Skip header if present

            while ((line = br.readLine()) != null) {
                String[] saleInfo = line.split(";");
                if (saleInfo.length == 5) {
                    int saleId = Integer.parseInt(saleInfo[0]);
                    LocalDateTime saleDateTime = LocalDateTime.parse(saleInfo[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    int customerId = Integer.parseInt(saleInfo[2]);
                    int productId = Integer.parseInt(saleInfo[3]);
                    int saleAmount = Integer.parseInt(saleInfo[4]);
                    Sale sale = new Sale(saleId, saleDateTime, customerId, productId,saleAmount );
                    salesData.add(sale);
                }
            }
        }

        return salesData;
    }

    public List<Customer> readCustomersData() throws IOException {
        List<Customer> customersData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(salesFile))) {
            String line;
            br.readLine(); // Skip header if present

            while ((line = br.readLine()) != null) {
                String[] saleInfo = line.split(";");
                if (saleInfo.length == 3) {
                    int productID = Integer.parseInt(saleInfo[0]);
                    String productName = saleInfo[1];
                    String productCategory = saleInfo[1];
                    Customer customer = new Customer(productID,productName,productCategory);
                    customersData.add(customer);
                }
            }
        }

        return customersData;
    }

    public List<Product> readProductsData() throws IOException {
        List<Product> productsData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(salesFile))) {
            String line;
            br.readLine(); // Skip header if present

            while ((line = br.readLine()) != null) {
                String[] saleInfo = line.split(";");
                if (saleInfo.length == 3) {
                    int productID = Integer.parseInt(saleInfo[0]);
                    String productName = saleInfo[1];
                    String productCategory = saleInfo[1];
                    Product product = new Product(productID,productName,productCategory);
                    productsData.add(product);
                }
            }
        }

        return productsData;
    }
}
