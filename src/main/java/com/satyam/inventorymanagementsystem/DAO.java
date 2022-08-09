/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.satyam.inventorymanagementsystem;


import java.util.List;
import javafx.scene.control.Alert;

import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Satyam This file is created for database operations purpose. This
 * file has all the methods needed to perform DB operations for User & Item
 * classes
 */
public class DAO {

    //Creating a static Session Factory reference variable so that it will create only once for a single DAO Object
    private static SessionFactory sessionFactory;

    //instantiating the static session factory reference variable with Annotated classes
    public DAO() {

        try {
            sessionFactory = new Configuration().
                    configure("com/satyam/inventorymanagementsystem/hibernate.cfg.xml").
                    buildSessionFactory();
        } catch (Exception ex) {
            //this will show an alert pop up to the user is we get any exceptions while creating session factory object
           System.out.println(ex);
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not able to connect with Database");
            alert.showAndWait();

        } finally {
            this.addAdmin();
        }

    }

    private void addAdmin() {
        User user = new User();
        user.setAccessType("Admin");
        user.setPassword("123");
        user.setUsername("Admin");
        try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            if (((Object) session.get(User.class, user.getUsername()) == null)) {
                Transaction transaction = session.beginTransaction();
                session.saveOrUpdate(user);
                transaction.commit();

            }

        } catch (Exception e) {
//            return false;
        }

    }

    //Following methods are related to Item Class only
    public boolean addNewItem(Item item) {

        try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(item);
            transaction.commit();
            return true;

        } catch (Exception e) {
            return false;
        }

    }
    
    public List<String> getMedicineNames()
    {
         try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            
            Query q=session.createQuery("select name from Item where quantity>0");
            
             
            return q.list();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Double getPrice(String name)
    {
         try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            
            Query q=session.createQuery("select price from Item where name='"+name.toUpperCase()+"'");
            
             
            return (Double)q.uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Integer getQuantity(String name)
    {
         try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            
            Query q=session.createQuery("select quantity from Item where name='"+name.toUpperCase()+"'");
            
             
            return (Integer)q.uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }
    public Integer setQuantity(String name,int quantity)
    {
         try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            
            Query q=session.createQuery("update item set quantity="+quantity+" where name='"+name.toUpperCase()+"'");
            
             
            return (Integer)q.uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    

    public Item getItem(String name) {

        try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            return session.get(Item.class, name);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Item> getAllItems() {
        try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            Query query = session.createQuery("from Item");

            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

    //Following methods are related to User Class only
    public boolean addNewUser(User user) {

        try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public User getUser(String name) {

        try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            return session.get(User.class, name);
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> getAllUsers() {
        try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            Query query = session.createQuery("from User");

            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Patient> getAllPatients()
    {
        try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            Query query = session.createQuery("from Patient");

            return query.list();
        } catch (Exception e) {
            return null;
        }
        
    }
    
    public boolean addPatient(Patient patient)
    {
           try ( Session session = sessionFactory.openSession()) {
            //session=sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(patient);
            transaction.commit();
            return true;

        } catch (Exception e) {
            return false;
        }
    
    }
    
    //Below code if for Testing of DAO
    public static void main(String[] args) {

        DAO d = new DAO();

      
        Medicine m1=new Medicine();
        m1.setName("Dolo");
        m1.setPrice(10);
        m1.setQuantity(50);
        Medicine m2=new Medicine();
        m2.setName("Dolo1");
        m2.setPrice(5);
        m2.setQuantity(10);
        Patient p=new Patient();
        p.setName("Satyam");
        p.setDate("2022-03-27");
        p.setTotal();
        p.getList().add(m2);
        p.getList().add(m1);
        
        d.addPatient(p);
        
        System.err.println(d.getPrice("DOLO"));
//        List<String> l=d.getMedicineNames();
//        for(String s:l)
//        {
//        System.out.println(s);}
//        
       // User u = new User("Admin", "Admin", "123", new Date());
      //  d.addNewUser(u);
//        Item item1=new Item("DOLO",20,50.0);
//        Item item2=new Item("DOLO1",20,50.0);
//        Item item3=new Item("DOLO2",20,50.0);
//        Item item4=new Item("DOLO4",20,50.0);
//        
//        
//        if(d.addNewItem(item1)&&d.addNewItem(item2)&&d.addNewItem(item3)&&d.addNewItem(item4))
//        {
//            System.err.println("Objects created");
//        }
//        item1=d.getItem("DOLO");
//        if(item1!=null)
//        {System.err.println(item1.getName()+" "+item1.getQuantity()+" "+item1.getPrice());
//        }
//        
//        List<Item> items=d.getAllItems();
//        
//        for(Item item:items)
//        {System.err.println(item.getName()+" "+item.getQuantity()+" "+item.getPrice());
//        }
        System.out.println("Hello");
    }

}
