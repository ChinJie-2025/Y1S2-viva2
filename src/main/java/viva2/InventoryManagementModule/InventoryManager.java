/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva2.InventoryManagementModule;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InventoryManager{
    private ArrayList <Product> manager;    //data structure
    
    public InventoryManager(){
        this.manager = new ArrayList <>();
    }
    
    //Reads inventory.txt and populates ArrayList
    public void loadFromFile(String filename){
        try{
            Scanner input = new Scanner(new FileInputStream (filename));
            while (input.hasNextLine()){
                String line = input.nextLine();
                String [] data = line.split(",");
                if (data.length == 4){
                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    double price = Double.parseDouble(data[2].trim());
                    int stock = Integer.parseInt(data[3].trim());
                    Product p = new Product (id, name, price, stock);
                    manager.add(p);
                }
            }
            input.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }
    
    // Writes current inventory to file
    public void saveToFile (String filename){
        try{
            PrintWriter output = new PrintWriter(new FileOutputStream(filename));
            for (int i=0; i<manager.size();i++){
                Product item = manager.get(i);
                output.println(item.getId() + "," + item.getName() + "," + item.getPrice() + "," + item.getStock());
            }
            output.close();
        }catch(IOException e){
            System.out.println("File saving error");
        }
    }
    
    // Adds product to ArrayList (check duplicate ID)
    public void addProduct (Product p){
        if (searchById(p.getId())==null)
            manager.add(p);
        else
            System.out.println("Duplicate ID");
    }
    
    // Removes product by ID
    public void removeProduct (int id){
            Product found = searchById(id);
            if (found!= null){
                manager.remove(found);
                return;
            }
        System.out.println("Product " + id + " not found");
    }
    
    // Returns product or null
    public Product searchById (int id){
        for (int i=0; i<manager.size(); i++){
            if (manager.get(i).getId() == id){
                return manager.get(i);
            }
        }
        return null;
    }
    
    // Returns ArrayList of matching products
    public ArrayList<Product> searchByName (String name){
        ArrayList<Product> result = new ArrayList<>();
        String searchKey = name.toLowerCase();
        for (int i=0; i<manager.size(); i++){
            String formattedName = manager.get(i).getName().toLowerCase();
            if (formattedName.contains(searchKey)){
                result.add(manager.get(i));
            }
        }
        return result;
    }
    
    // Updates stock quantity
    public void updateStock (int id, int newStock){
        Product check = searchById(id);
        if (check != null)
            check.setStock(newStock);
        else
            System.out.println("Product " + id + " not found");
    }
    
    // Prints all products in table format
    public void displayAll(){
        System.out.printf("%-10s %-15s %-10s %-10s\n", "ID", "Name", "Price", "Stock");
        for (Product item : manager){
            System.out.println(item.toString());
        }
    }
    
    // Returns product for cart operations
    public Product getProductById (int id){
        return searchById(id);
    }
    
    //Checks if sufficient stock exists
    public boolean isAvailable (int id, int requestedQty){
        Product check = searchById(id);
        if (check != null && check.getStock() >= requestedQty)
            return true;
        else
            return false;
    }
}
