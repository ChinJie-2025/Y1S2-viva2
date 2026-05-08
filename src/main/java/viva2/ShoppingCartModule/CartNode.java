/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva2.ShoppingCartModule;

import viva2.InventoryManagementModule.Product;
public class CartNode {
    protected Product product;
    protected int quantity;
    protected CartNode next;
    
    public CartNode (Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        this.next = null;
    }
}
