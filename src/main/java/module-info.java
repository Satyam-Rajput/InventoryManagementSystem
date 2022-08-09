module com.satyam.inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires poi.ooxml;
    requires poi;
    requires java.naming;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires org.hibernate.commons.annotations;
    opens com.satyam.inventorymanagementsystem to javafx.fxml,org.hibernate.orm.core;
    
    exports com.satyam.inventorymanagementsystem;
}
