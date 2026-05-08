/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva2.ShoppingCartModule;

import viva2.InventoryManagementModule.Product;
import viva2.UndoStack; //new
public class CartList {
    private UndoStack history = new UndoStack(); //new
    private CartNode head;
    int size;
    
    public CartList(){
        head = null;
        size = 0;
    }
    
    //Adds new node at the end; if product exists, updates quantity
    public void addItem (Product p, int qty){
        if (qty<=0){
            System.out.println("Invalid quantity");
            return;
        }
        if (qty > p.getStock()){
                    System.out.println("Insufficient stock");
                    return;
        }
        
        CartNode current = head;
        while (current != null){
            // if product exists, update qty
            if (current.product.getId() == p.getId()){
                if (qty > p.getStock()){
                    System.out.println("Insufficient stock");
                    return;
                }
                p.setStock(p.getStock()-qty);
                current.quantity += qty;
                System.out.println("Product successfully added");
                //history.push(new UndoStack.CartAction(p.getId(), qty));
                return;
            }    
            if (current.next == null)
                break;
            current = current.next;
        }
        
        // add new node at the end
        p.setStock(p.getStock()-qty);
        CartNode newNode = new CartNode(p,qty);
        if (head == null){
            head = newNode;
            System.out.println("Product successfully added");
        } 
        else {
            current.next = newNode;
            System.out.println("Product successfully added");
        }
        size++;
        //history.push(new UndoStack.CartAction(p.getId(), qty));
    }
    
    // Removes node with matching product ID
    public void removeItem (int productId){
        // empty cart
        if (head == null){
            System.out.println("Cart is empty");
            return;
        }    
        // if head is the targeted product
        if (head.product.getId() == productId){
            head.product.setStock(head.product.getStock() + head.quantity);
            head = head.next;
            size--;
            System.out.println(productId +" is removed");
            return;
        }
        // targeted product is in between    
        CartNode current = head;
        while (current.next != null){
            if (current.next.product.getId() == productId){
                current.next.product.setStock(current.next.product.getStock() + current.next.quantity);
                current.next = current.next.next;
                size--;
                System.out.println(productId +" is removed");
                return;
            }
            current = current.next;
        }
        System.out.println("Proudct " + productId + " not found in the cart");
    }
    
    //Changes quantity of existing cart item
    public void updateQuantity (int productId, int newQty){
        if (newQty<0){
            System.out.println("Invalid quantity");
            return;
        }
        CartNode item = findItem(productId);
        if (item != null){
            int diff = newQty - item.quantity;
            if (diff > item.product.getStock()){
                System.out.println("Insufficient stock");
                return;
            }
            else{
                item.product.setStock(item.product.getStock() - diff);
                item.quantity = newQty;
                return;
            }
        }
            System.out.println("Product not found");
    }
    
    // Returns CartNode or null
    public CartNode findItem (int productId){
        CartNode current = head;
        while (current != null){
            if (current.product.getId() == productId){
                return current;
            }
            current = current.next;
        }
        return null;
    }
    
    //Prints all cart items with subtotals
    public void displayCart(){
        if (isEmpty()){
            System.out.println("Cart is empty");
            return;
        }
        System.out.printf("%-15s %-10s %-10s %-10s\n", "Name","Quantity", "Unit Price", "Subtotal");
        CartNode current = head;
        while (current != null){
            double subtotal = current.product.getPrice() * current.quantity;
            System.out.printf("%-15s %-10d %-10.2f %-10.2f\n", current.product.getName(), current.quantity, current.product.getPrice(), subtotal);
            current = current.next;
        }
    }
    
    // Returns total bill amount as double
    public double calculateTotal(){
        double total=0;
        CartNode current = head;
        while (current != null){
            total += (current.product.getPrice() * current.quantity);
            current = current.next;    
        }
        return total;
    }
    
    // Empties the entire cart (sets head to null)
    public void clear(){
        CartNode current = head;
        while (current != null){
            current.product.setStock(current.product.getStock() + current.quantity);
            current = current.next;
        }
        head = null;
        size = 0;
        System.out.println("Cart successfully cleared and stock restored");
    }
    
    // to remove last added item/product
    public Product undo(){
        if (head == null)
            return null;
        
        if (head.next == null) {
            head.product.setStock(head.product.getStock() + head.quantity);
            Product p = head.product;
            head = null;
            size--;
            return p;
        }
        
        CartNode current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        CartNode lastNode = current.next;
        lastNode.product.setStock(lastNode.product.getStock() + lastNode.quantity);
        Product p = lastNode.product;
        current.next = null;
        size--;
        return p;  
    }
    
    // Returns number of items in cart
    public int getSize(){
        return size;
    }
    
    // Checks if cart is empty
    public boolean isEmpty(){
        return head==null;
    }
    
}

