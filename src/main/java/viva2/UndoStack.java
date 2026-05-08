/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viva2;

public class UndoStack{
    public static class CartAction {
        public int productId;
        public int quantity;
        public CartAction(int productId, int quantity){
            this.productId = productId;
            this.quantity = quantity;
        }
    }
    private class Node{
        CartAction action;
        Node next;
        Node(CartAction action){ 
            this.action = action; 
        }
    }
    
    private Node top;

    public void push(CartAction action){
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
    }

    public CartAction pop(){
        if (top == null)
            return null;
        CartAction action = top.action;
        top = top.next;
        return action;
    }
}
