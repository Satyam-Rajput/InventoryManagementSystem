/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.satyam.inventorymanagementsystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Satyam
 */
public class Patient {
   
    private  double total;
    private String name;
    private String date;
    private List<Medicine> list ;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient(String name, String date, List<Medicine> list) {
        this.name = name;
        this.date = date;
        this.list = list;
    }

    public Patient()
    {}
    public  double getTotal() {
        
        double tempTotal=0;
        for(Medicine m:list )
        {
            tempTotal+=m.getTotal();
        }
        this.total=tempTotal;
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
     public void setTotal() {
         
         int tempTotal=0;
         for(Medicine medicine:list)
         {
             tempTotal+=medicine.getTotal();
         }
        this.total = tempTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Medicine> getList() {
        return list;
    }

    public void setList(List<Medicine> list) {
        this.list = list;
    }
    
    
    
    
}
