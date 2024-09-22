/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dbconnection;

import java.sql.*;
import java.util.Scanner;

public class Dbconnection {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/store";
    private static final String USER = "root";
    private static final String PASS = "";
    private static Connection con;

    public static void main(String[] args) {
        try {
            // Load Driver mySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Loading Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection Built Successfully");
            
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Add Product");
                System.out.println("2. Read Product");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");             //MENU FOR CRUD
                System.out.print("Select an option: "); 
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addProduct(scanner);
                        break;
                    case 2:
                        readProduct(scanner);
                        break;
                    case 3:
                        updateProduct(scanner);
                        break;
                    case 4:
                        deleteProduct(scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        con.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            
           

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void addProduct(Scanner scanner) {
        System.out.println("Welcome to ADD new product in store");

        String name, category, id;
        int price;

        System.out.print("Enter Product Name: ");
        name = scanner.nextLine();
        System.out.print("Enter Category of the Product: ");
        category = scanner.nextLine();
        System.out.print("Enter ID of Product: ");
        id = scanner.nextLine();
        System.out.print("Enter Price of Product: ");
        price = scanner.nextInt();

        String qry = "INSERT INTO product (id, name, category, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(qry)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, category);
            pstmt.setInt(4, price);
            pstmt.executeUpdate();
            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    static void readProduct(Scanner scanner) {
        System.out.print("Enter Product ID, Name or Category to search: ");
        String searchTerm = scanner.nextLine();

        String qry = "SELECT * FROM product WHERE id = ? OR name = ? OR category = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(qry)) {
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm); //it is setting value in that string modern coding :)
            pstmt.setString(3, searchTerm);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Category: " + rs.getString("category"));   //Showing retrived data from table we get from
                System.out.println("Price: " + rs.getInt("price"));            //Selcet Qeury
                System.out.println("---------------");
            }
            if (!found) {
                System.out.println("No product found with the given criteria.");
            }
        } catch (SQLException e) {
            System.out.println("Error reading product: " + e.getMessage());
        }
    }

    static void updateProduct(Scanner scanner) {
        System.out.print("Enter Product ID to update: ");
        String id = scanner.nextLine();

        System.out.print("Enter new Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Category of the Product: ");
        String category = scanner.nextLine();
        System.out.print("Enter new Price of Product: ");
        int price = scanner.nextInt();

        String qry = "UPDATE product SET name = ?, category = ?, price = ? WHERE id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(qry)) {
            pstmt.setString(1, name);
            pstmt.setString(2, category);      //Setting values in qeury string 
            pstmt.setInt(3, price);
            pstmt.setString(4, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("No product found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    static void deleteProduct(Scanner scanner) {
        System.out.print("Enter Product ID to delete: ");
        String id = scanner.nextLine();

        String qry = "DELETE FROM product WHERE id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(qry)) {
            pstmt.setString(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("No product found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }
}
