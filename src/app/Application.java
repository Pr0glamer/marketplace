package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dao.ProductManagementDAO;
import dao.PurchaseManagementDAO;
import dao.UserManagementDAO;
import dbutil.DBUtil;
import pojo.Product;
import pojo.Purchase;
import pojo.User;

public class Application {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static ProductManagementDAO productDao = new ProductManagementDAO();
    static UserManagementDAO userDao = new UserManagementDAO();
    static PurchaseManagementDAO purchDao = new PurchaseManagementDAO();

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    static List<Purchase> purchases = new ArrayList<>();

    public static void main(String[] args) throws Exception
    {
        DBUtil.createTables();
        String option = "";
        do
        {
            System.out.println("1. View Products        | 6. View user's products");
            System.out.println("2. Add Product          | 7. View users who buy product");
            System.out.println("3. View Users           | 8. Delete product");
            System.out.println("4. Add User             | 9. Delete user");
            System.out.println("5. Make a purchase");

            System.out.println("10. Exit");
            System.out.println("===========================================");
            System.out.println("Enter an option");
            System.out.println("===========================================");
            option = br.readLine();
            System.out.println("\n");

            switch(option.toUpperCase())
            {
                case "1":
                    viewProducts();
                    break;
                case "2":
                    addProduct();
                    break;
                case "3":
                    viewUsers();
                    break;
                case "4":
                    addUser();
                    break;
                case "5":
                    makePurchase();
                    break;
                case "6":
                    viewUsersProducts();
                    break;
                case "7":
                    viewUsersWhoBuyProducts();
                    break;
                case "8":
                    deleteProduct();
                    break;
                case "9":
                    deleteUser();
                    break;

                case "10":
                    System.out.println("******************************THANK YOU********************");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Option! Please enter again");
                    break;
            }
        } while(!option.equals("F"));
    }
    public static void viewUsers()
    {
        System.out.println("-----------------------------------------------");

        //get all the product from the dao getallProducts() method and store
        //them to a Product type productList
        List<User> productList = userDao.getAllUsers();
        for(User user: productList)
        {
            //display product one by one
            displayUser(user);
        }
        System.out.println("-----------------------------------------------");
        System.out.println("\n");

    }

    public static void viewProducts()
    {
        System.out.println("-----------------------------------------------");

        //get all the product from the dao getallProducts() method and store
        //them to a Product type productList
        List<Product> productList = productDao.getAllProducts();
        for(Product product: productList)
        {
            //display product one by one
            displayProduct(product);
        }
        System.out.println("-----------------------------------------------");
        System.out.println("\n");

    }

    public static void deleteProduct()
    {
        int product_id;
        System.out.println("-----------------------------------------------");
        System.out.println("Enter Product id:");
        System.out.println("------------------------------------------------");
        try {
            product_id = Integer.parseInt(br.readLine());
        } catch (NumberFormatException | IOException exception) {
            System.out.println(ANSI_RED + "ID must be a number" + ANSI_RESET);
            return;
        }

        int status = productDao.deleteProduct(product_id);
        if(status > 0) {
            System.out.println("Product successfully deleted");
        }

        System.out.println("-----------------------------------------------");
        System.out.println("\n");

    }

    public static void deleteUser()
    {
        int user_id;
        System.out.println("-----------------------------------------------");
        System.out.println("Enter User id:");
        System.out.println("------------------------------------------------");
        try {
            user_id = Integer.parseInt(br.readLine());
        } catch (NumberFormatException | IOException exception) {
            System.out.println(ANSI_RED + "ID must be a number" + ANSI_RESET);
            return;
        }

        int status = userDao.deleteUser(user_id);
        if(status > 0) {
            System.out.println("Product successfully deleted");
        }

        System.out.println("-----------------------------------------------");
        System.out.println("\n");

    }

    public static void viewUsersWhoBuyProducts()
    {
        int product_id;
        System.out.println("-----------------------------------------------");
        System.out.println("Enter Product id:");
        System.out.println("------------------------------------------------");
        try {
            product_id = Integer.parseInt(br.readLine());
        } catch (NumberFormatException | IOException exception) {
            System.out.println(ANSI_RED + "ID must be a number" + ANSI_RESET);
            return;
        }

        //get all the product from the dao getallProducts() method and store
        //them to a Product type productList
        List<User> userList = purchDao.getAllUsersByProductId(product_id, userDao);

        for(User user: userList)
        {
            displayUser(user);
        }

        System.out.println("-----------------------------------------------");
        System.out.println("\n");

    }

    public static void viewUsersProducts()
    {
        int user_id;
        System.out.println("-----------------------------------------------");
        System.out.println("Enter User id:");
        System.out.println("------------------------------------------------");
        try {
            user_id = Integer.parseInt(br.readLine());
        } catch (NumberFormatException | IOException exception) {
            System.out.println(ANSI_RED + "ID must be a number" + ANSI_RESET);
            return;
        }

        List<Product> userList = purchDao.getAllProductsByUser(user_id, productDao);

        for(Product product: userList)
        {
            displayProduct(product);
        }
        System.out.println("-----------------------------------------------");
        System.out.println("\n");

    }




    public static void addProduct() throws Exception
    {
        System.out.println("Enter Product Name:");
        System.out.println("------------------------------------------------");
        String productName = br.readLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter Product Price:");
        System.out.println("------------------------------------------------");
        int productPrice = Integer.parseInt(br.readLine());
        //after user enters values, store them in a Product variable
        Product product = new Product(productName, productPrice);
        int prod_id = productDao.addProduct(product);
        if(prod_id > 0 )
        {
            System.out.println("Product added successfully");
            product.setId(prod_id);
        }
        else
        {
            System.out.println("ERROR while adding product");
        }
        System.out.println("\n");
    }

    public static void addUser() throws IOException {
        System.out.println("Enter User First Name:");
        System.out.println("------------------------------------------------");
        String userFirstName = br.readLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter User Last Name:");
        System.out.println("------------------------------------------------");
        String userLastName = br.readLine();
        System.out.println("------------------------------------------------");
        System.out.println("Enter Amount of money:");
        System.out.println("------------------------------------------------");

        int amount;
        try {
            amount = Integer.parseInt(br.readLine());
        } catch (NumberFormatException exception) {
            System.out.println(ANSI_RED + "Amount must be a number" + ANSI_RESET);
            return;
        }

        //after user enters values, store them in a Product variable
        User user = new User(userFirstName, userLastName, amount);
        int user_id = userDao.addUser(user);
        if(user_id > 0 )
        {
            System.out.println("User added successfully");
            user.setId(user_id);
        }
        else
        {
            System.out.println("ERROR while adding user");
        }
        System.out.println("\n");
    }

    public static void makePurchase()
    {
        int product_id, user_id, sum;

        System.out.println("Enter Product Id:");
        System.out.println("------------------------------------------------");

        try {
            product_id = Integer.parseInt(br.readLine());
        } catch (NumberFormatException | IOException exception) {
            System.out.println(ANSI_RED + "ID be a number" + ANSI_RESET);
            return;
        }
        System.out.println("------------------------------------------------");
        System.out.println("Enter User id:");
        System.out.println("------------------------------------------------");
        try {
            user_id = Integer.parseInt(br.readLine());
        } catch (NumberFormatException | IOException exception) {
            System.out.println(ANSI_RED + "ID must be a number" + ANSI_RESET);
            return;
        }

        User user = userDao.getUserByid(user_id);
        Product product = productDao.getProductByid(product_id);

        Purchase purchase = null;
        try {
            purchase = purchDao.makePurchase(user, product, userDao);
            if(purchase != null)
            {
                purchases.add(purchase);
                System.out.println("Purchase made successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n");
    }


    //ask user for productId and use dao method to display product
    public static void searchProduct() throws Exception
    {
        System.out.println("------------------------------------------------");
        System.out.println("Enter Product ID:");
        System.out.println("------------------------------------------------");
        String productId = br.readLine();
        Product product = productDao.getProductByid(Integer.valueOf(productId));
        displayProduct(product);
        System.out.println("\n");
    }

    public static void displayProduct(Product product)
    {
        System.out.println("Product ID: "+product.getId());
        System.out.println("Product Name: "+product.getName());
        System.out.println("Product Price: "+product.getPrice());
        System.out.println("\n");
    }

    public static void displayUser(User user)
    {
        System.out.println("User ID: "+user.getId());
        System.out.println("First Name: "+user.getFirstName());
        System.out.println("Last Name: "+user.getLastName());
        System.out.println("Amount of money: "+user.getAmountOfMoney());
        System.out.println("\n");
    }



}
