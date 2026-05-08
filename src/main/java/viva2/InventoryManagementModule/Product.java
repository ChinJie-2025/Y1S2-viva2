/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva2.InventoryManagementModule;

public class Product {
    private int id;     //Unique product identifier
    private String name;        // Product name
    private double price;       // Price per unit
    private int stock;      // Available quantity in inventory
    
    //Constructor
    public Product(int id, String name, double price, int stock){
        this.id=id;
        this.name=name;
        this.price=price;
        this.stock=stock;
    }
    
    //getter
    public int getId(){return id;}
    public String getName(){return name;}
    public double getPrice(){return price;}
    public int getStock(){return stock;}
    
    //setter
    public void setID(int id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPrice(double price){
        if (price>=0)
            this.price=price;
        else
            System.out.println("Price cannot be negative");
    }
    public void setStock(int stock){
        if (stock>=0)
            this.stock=stock;
        else
            System.out.println("Stock cannot be negative");
    }
    
    // toString method
    @Override
    public String toString(){
        return String.format("%-10d %-15s %-10.2f %-10d", this.id, this.name, this.price, this.stock);
    }
}
