package product_manager;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ProductManager {
    public static List<Product> productList = new ArrayList<>();
    public static final String FILE_NAME = "src/product_manager/products.txt";
    public static final File FILE = new File(FILE_NAME);

    public static void main(String[] args) {
        showMainMenu();
    }

    public  static void showMainMenu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("<-- MAIN MENU -->");
        System.out.println("1. Add New Product");
        System.out.println("2. Display All Product");
        System.out.println("3. Find A Product");
        System.out.println("4. Edit A Product");
        System.out.println("5. Delete A Product");
        System.out.println("6. Exit");
        System.out.println("Select Option");
        int option = scanner.nextInt();

        switch (option){
            case 1:
                addProduct();
                writeProductList(FILE);
                break;
            case 2:
                readProductList(FILE);
                break;
            case 3:
                findProduct();
                readProductList(FILE);
                break;
            case 4:
                editProduct();
                writeProductList(FILE);
                readProductList(FILE);
                break;
            case 5:
                deleteProduct();
                writeProductList(FILE);
                readProductList(FILE);
                break;
            case 6:
            default:
                System.exit(0);
        }
        showMainMenu();
    }

    public static void addProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input id: ");
        String id = scanner.nextLine();

        System.out.print("Input name: ");
        String name = scanner.nextLine();

        System.out.print("Input brand: ");
        String brand = scanner.nextLine();

        System.out.print("Input price: ");
        double price = scanner.nextDouble();

        System.out.print("Input description: ");
        String description = scanner.nextLine();

        productList.add(new Product(id, name, brand, price, description));
    }

    public static void findProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input product id: ");
        String id = scanner.nextLine();
        int index = -1;

        for (int i = 0; i < productList.size(); i++) {
            if (id.compareTo(productList.get(i).getId()) == 0){
                index = i;
                break;
            }
        }
        if (index != -1){
            System.out.println("Found result: ");
            System.out.println(productList.get(index));
        } else {
            System.out.println("Product id " + id + " not found");
        }
    }

    public static void editProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input product id: ");
        String id = scanner.nextLine();
        int index = -1;

        for (int i = 0; i < productList.size(); i++) {
            if (id.compareTo(productList.get(i).getId()) == 0){
                index = i;
                break;
            }
        }
        if (index != -1){
            Product product = productList.get(index);
            System.out.print("Input new Id: ");
            String newId = scanner.nextLine();

            System.out.print("Input new name: ");
            String newName = scanner.nextLine();

            System.out.print("Input new brand: ");
            String newBrand = scanner.nextLine();

            System.out.print("Input new price: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Input new description: ");
            String newDesc = scanner.nextLine();

            product.setId(newId);
            product.setName(newName);
            product.setBrand(newBrand);
            product.setPrice(newPrice);
            product.getDesc(newDesc);
        } else {
            System.out.println("Product id " + id + " not found!");
        }
    }

    public static void deleteProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input product id: ");
        String id = scanner.nextLine();
        int index = -1;

        for (int i = 0; i < productList.size(); i++) {
            if (id.compareTo(productList.get(i).getId()) == 0){
                index = i;
                break;
            }
        }
        if (index != -1){
            productList.remove(index);
        } else {
            System.out.println("Product id" + id + "not found!");
        }
    }

    public static void writeProductList(File file){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(productList);

            fileOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e){
            System.out.println("Error: " + e);
        }
    }

    public static void readProductList(File file){
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            if (!file.exists()){
                throw new FileNotFoundException("File does not exist.");
            } else {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);

                productList = (List<Product>) objectInputStream.readObject();

                SortProductPrice sortPrice = new SortProductPrice();
                Collections.sort(productList, sortPrice);
                for (Product product : productList){
                    System.out.println(product);
                }
                System.out.println("--------");
                fileInputStream.close();
                objectInputStream.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
