/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.satyam.inventorymanagementsystem;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;

/**
 *
 * @author Satyam
 */


public class Item {

   

public Item()
{}
    public Item(String name, int quantity, double price)
    { this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    

 

   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        this.total=this.price*this.quantity;
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
    
   
    private String name;
    
    private int quantity;
    private double price;
    
    private double total;
    
}
