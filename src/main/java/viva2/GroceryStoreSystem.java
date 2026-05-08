/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva2;
import java.util.Scanner;
import java.util.ArrayList;
import viva2.InventoryManagementModule.InventoryManager;
import viva2.ShoppingCartModule.CartList;
import viva2.InventoryManagementModule.Product;
public class GroceryStoreSystem {
    public static void main(String[] args) {
        InventoryManager inventory = new InventoryManager();
        CartList cart = new CartList();
        Scanner scan = new Scanner(System.in);
        
        // handle exception later
        inventory.loadFromFile("inventory.txt");
        
        boolean exit = false;
        while (!exit){
            System.out.println("\n--- Grocery Store Management System ---");
            System.out.println("1. Display all products");
            System.out.println("2. Search Product by ID/Name");
            System.out.println("3. Add/Remove product");
            System.out.println("4. Update stock quantity");
            System.out.println("5. Add item to cart");
            System.out.println("6. View cart");
            System.out.println("7. Remove item from cart");
            System.out.println("8. Update quantity in cart");
            System.out.println("9. Undo last addition");
            System.out.println("10. Clear cart");
            System.out.println("11. Checkout");
            System.out.println("12. Save and Exit");
            System.out.print("Enter your choice (1-12): ");
            int choice;
            try {
                 choice = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }
            
            switch (choice){
                case 1: 
                    inventory.displayAll(); 
                    break;
                    
                case 2:
                    System.out.println("\n--- Search Products ---");
                    System.out.println("1. Search by ID");
                    System.out.println("2. Search by Name");
                    System.out.print("Enter choice (1-2): ");
                    try {
                        int searchChoice = Integer.parseInt(scan.nextLine());
                        if (searchChoice == 1){
                            System.out.print("Enter product ID: ");
                            int id = Integer.parseInt(scan.nextLine());
                            Product p = inventory.searchById(id);
                            if (p != null) 
                                System.out.println(p);
                            else 
                                System.out.println("Product not found");
                        }
                        else if (searchChoice == 2){
                            System.out.print("Enter name to search: ");
                            String name = scan.nextLine();
                            ArrayList<Product> matches = inventory.searchByName(name);
                            if (matches.isEmpty()) 
                                System.out.println("Product not found");
                            else {
                                for (Product p : matches) 
                                    System.out.println(p);
                            }
                        }
                        else{
                            System.out.println("Invalid choice");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input format.");
                    }
                    break;
                    
                case 3:
                    System.out.println("\n--- Add/Remove Product ---");
                    System.out.println("1. Add new product");
                    System.out.println("2. Remove product");
                    System.out.print("Enter choice (1-2): ");    
                    try {
                        int addRemoveChoice = Integer.parseInt(scan.nextLine());
                        
                        if (addRemoveChoice == 1){
                            System.out.print("Enter ID: ");
                            int id = Integer.parseInt(scan.nextLine());
                            System.out.print("Enter Name: ");
                            String name = scan.nextLine();
                            System.out.print("Enter Price: ");
                            double price = Double.parseDouble(scan.nextLine());
                            System.out.print("Enter Stock: ");
                            int stock = Integer.parseInt(scan.nextLine());
                            Product newproduct = new Product(id, name, price, stock);
                            inventory.addProduct(newproduct);
                        } 
                        else if (addRemoveChoice == 2){
                            System.out.print("Enter ID of product to remove: ");
                            int id = Integer.parseInt(scan.nextLine());
                            inventory.removeProduct(id);
                        } 
                        else{
                            System.out.println("Invalid choice");
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Invalid input format");
                    }
                    break;
                    
                case 4:  
                    System.out.print("Enter Product ID to update: ");
                    try {
                        int id = Integer.parseInt(scan.nextLine());
                        System.out.print("Enter new stock quantity: ");
                        int newStock = Integer.parseInt(scan.nextLine());
                        inventory.updateStock(id, newStock);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input");
                    }
                    break;
                    
                case 5:
                    System.out.print("Enter Product ID to add to cart: ");
                    try {
                        int id = Integer.parseInt(scan.nextLine());
                        System.out.print("Enter Quantity: ");
                        int qty = Integer.parseInt(scan.nextLine());
                        
                        Product p = inventory.getProductById(id);
                        if (p != null){
                            cart.addItem(p, qty);
                        } 
                        else{
                            System.out.println("Product not found");
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Invalid input");
                    }
                    break;
                    
                case 6:  
                    cart.displayCart();
                    System.out.printf("\nTotal: RM %.2f\n", cart.calculateTotal());
                    break;
                    
                case 7:  
                    cart.displayCart();
                    System.out.print("Enter Product ID to remove from cart: ");
                    try {
                        int id = Integer.parseInt(scan.nextLine());
                        cart.removeItem(id);
                    }catch(NumberFormatException e){
                        System.out.println("Invalid input");
                    }
                    break;
                    
                case 8:
                    cart.displayCart();
                    System.out.print("Enter Product ID: ");
                    try {
                        int id = Integer.parseInt(scan.nextLine());
                        System.out.print("Enter new quantity: ");
                        int newQty = Integer.parseInt(scan.nextLine());
                        cart.updateQuantity(id, newQty);
                    }catch(NumberFormatException e){
                        System.out.println("Invalid input");
                    }
                    break;
                    
                case 9:
                    Product p = cart.undo();
                    if (p != null) {
                        System.out.println("Undo successful! Removed item: " + p.getName());
                    } else {
                        System.out.println("No item to undo");
                    }
                    break;
                    
                case 10:   
                    cart.clear();
                    break;
                    
                case 11:
                    System.out.println("\n================= BILL =================");
                    cart.displayCart();
                    System.out.println("----------------------------------");
                    System.out.printf("\nTotal: RM %.2f\n", cart.calculateTotal());
                    System.out.println("============================================");
                    System.out.println("Inventory stock reduced permanently.");
                    
                    cart = new CartList();
                    
                    System.out.print("Save inventory changes to file before exiting? (Y/N): ");
                    String saveOpt = scan.nextLine().trim().toLowerCase();
                    if (saveOpt.equals("y") || saveOpt.equals("yes")) {
                        inventory.saveToFile("inventory.txt");
                    }
                    break;
                    
                case 12:  
                    inventory.saveToFile("inventory.txt");
                    System.out.println("Inventory changes saved to file");
                    System.out.println("Exiting the program. Thank you for shopping!");
                    exit = true;
                    break;
                    
                default: 
                    System.out.println("Invalid choice. Please pick an integer from 1 to 12.");
                    break;
            }
        }
        
    }
}
