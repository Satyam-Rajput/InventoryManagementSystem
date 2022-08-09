package com.satyam.inventorymanagementsystem;

import com.satyam.inventorymanagementsystem.App;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.xssf.usermodel.*;

public class MainController implements Initializable {
    
    
   //Regexes to validate the price & quantity
    private static final String REGEX_PATTERN_INTEGER = "^\\d+$";
    private static final String REGEX_PATTERN_DOUBLE="[+]?([0-9]*[.])?[0-9]+";
    
    public static DAO dao=null;
    
    //Variables related to medicine
    @FXML
    private TextField medicinePriceTxt;
    @FXML
    private Tab medicineTab;
    
    @FXML
    private TextField medicineQuantityTxt;
    @FXML
    private TextField medicineNameTxt;
    
    @FXML
    private TextField searchTxt;

    @FXML
    private TableView<Item> medicineTable;

    @FXML
    private TableColumn<Item, String> medicineNameCol;
    @FXML
    private TableColumn<Item, Integer> medicineQuantityCol;
    @FXML
    private TableColumn<Item, Double> medicinePriceCol;

    @FXML
    private TableColumn<Item, Double> medicineTotalCol;
    
    ObservableList<Item> medicineList = FXCollections.observableArrayList();
    
    //User related functions
    @FXML
    private Tab userTab;

    @FXML
    private TextField userNameTxt;

    @FXML
    private ChoiceBox<String> accessTypeTxt;

    @FXML
    private PasswordField passwordTxt;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, Integer> accessTypeCol;
    @FXML
    private TableColumn<User, Double> lastLoginCol;
    @FXML
    private TableView<User> userTable;
    ObservableList<String> accessTypeList = FXCollections.observableArrayList();
    ObservableList<User> userList = FXCollections.observableArrayList();
    
    //Patient related variables
    @FXML
    private TextField patientNameTxt;
    @FXML
    private DatePicker purchasedDateTxt;
    
    @FXML
    private ChoiceBox<String> medicineChoicesTxt;
    @FXML
    private TextField patientMedicineQuantityTxt;
    ObservableList<String> availableMedicineList = FXCollections.observableArrayList();
    
    ObservableSet<Medicine> purchasedMedicineList = FXCollections.observableSet();
    
    
    @FXML
    private TableView<Medicine> patientMedicineTableList;
    
    @FXML
    private TableColumn<Medicine, String> patientMedicineNameCol;

    @FXML
    private TableColumn<Medicine, Integer> patientMedicineQuantityCol;

    //Variable for file uploader
@FXML
    private ListView files;
    
    @FXML
    private TabPane tableTabPane;
    

    @FXML
    private TableColumn<Patient, String> patientNameCol;
    @FXML
    private TableColumn<Patient, String> purchasedDateCol;
    
    @FXML
    private TableColumn<Patient, Double> patientTotalAmountCol;
    
    @FXML
    private TableView<Patient> patientDetailTable;

    
    ObservableList<Patient> patientDetailList = FXCollections.observableArrayList();

    
    public void initializeMedicineTableView()
    {
        medicineNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        medicineQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        medicinePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        medicineTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
         public void initializePatientMedicineTableView()
    {
      patientMedicineNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientMedicineQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }
         
         public void initializePatientTableView()
    {
      patientNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        purchasedDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientTotalAmountCol.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    
         public void initializeAccessTypeChoiceBox()
         {
             accessTypeList.add("Admin");
        accessTypeList.add("Normal");
        accessTypeTxt.setItems(accessTypeList);
        accessTypeTxt.getSelectionModel().selectFirst();
         }
         
         public void initializeUserTableView()
         {
             usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        accessTypeCol.setCellValueFactory(new PropertyValueFactory<>("accessType"));
        lastLoginCol.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
         }
         
     
         public void initializeAllList()
         {
             try{
        dao=new DAO();
        medicineList=FXCollections.observableArrayList(dao.getAllItems());
        userList=FXCollections.observableArrayList(dao.getAllUsers());
        availableMedicineList=FXCollections.observableArrayList(dao.getMedicineNames());
        this.initializeAllTables();
        }
        catch(Exception e)
        {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Not able to connect with Database");
            alert.showAndWait();
            try{
            this.logout();}
            catch(Exception ex)
            {
                
            }
        }
         }
         
         public void initializeAllTables()
         {
             
        medicineChoicesTxt.setItems(availableMedicineList);
        medicineChoicesTxt.getSelectionModel().selectFirst();
        medicineTable.setItems(medicineList);
        userTable.setItems(userList);
         }
         
         public void initializeMedicineFields(Item item)
         {
             medicineNameTxt.setText(item.getName());
            medicinePriceTxt.setText(String.valueOf(item.getPrice()));
            medicineQuantityTxt.setText(String.valueOf(item.getQuantity()));
         }
          
         public void initializePatientFields(Patient patient)
            
          {patientNameTxt.setText(patient.getName());
        purchasedDateTxt.setValue(LocalDate.parse(patient.getDate()));
         patientMedicineTableList.setItems(FXCollections.observableArrayList(patient.getList()));
    }
         
         public void showErrorAlert(String msg)
         {Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.showAndWait();
             
         }
         public void showInformationAlert(String msg)
         {Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(msg);
        alert.showAndWait();
             
         }
         
         public boolean showConfirmation(String msg)
         {
             Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("Confirmation");
                        confirmationAlert.setHeaderText(msg);
                        
                        
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
                return true;
            else
                return false;
         }
         
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        this.initializeMedicineTableView();
        this.initializePatientMedicineTableView();
        this.initializePatientTableView();
        this.initializeAccessTypeChoiceBox();
        
        this.initializeUserTableView();
        
        purchasedDateTxt.setValue(LocalDate.now());
         this.initializeAllList();
        userTable.setItems(userList);

        if (User.user.getAccessType().equals("Normal")) {
            medicineTab.setDisable(true);
            userTab.setDisable(true);
            tableTabPane.getTabs().remove(1);
        }

    }

    @FXML
    private void logout() throws IOException {
       
        App.stage.setHeight(200.0);
        App.stage.setWidth(250.0);
        App.stage.centerOnScreen();
        App.setRoot("login");
    }

    @FXML
    public void close() {
        App.stage.close();
    }

    @FXML
    public void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About us");
        alert.setHeaderText("This is an Inventory Management System");
        alert.showAndWait();
    }

    @FXML
    public void search() {
        
        try{
            Item item=dao.getItem(searchTxt.getText().toUpperCase());
            if(item==null)
            {
                throw new Exception();
            }
            else
            {
                this.initializeMedicineFields(item);
            }
        }
        catch(Exception e)
        {
          this.showErrorAlert("Record not found");
        }
        
      

    }

    @FXML
    public void updateMed() {

        if (!(medicineNameTxt.getText().equals("") || medicineQuantityTxt.getText().equals("")
                || medicinePriceTxt.getText().equals("")) && medicinePriceTxt.getText().matches(REGEX_PATTERN_DOUBLE)
                && medicineQuantityTxt.getText().matches(REGEX_PATTERN_INTEGER)) {
//            medicineList.add();
            try{
            Item item=new Item();
            item.setName(medicineNameTxt.getText());
            item.setPrice(Double.parseDouble(medicinePriceTxt.getText()));
            item.setQuantity(Integer.parseInt(medicineQuantityTxt.getText()));
            
            dao.addNewItem(item);
            this.initializeAllList();
            }catch(Exception e)
            {
               this.showErrorAlert("Failed to add Data");     
            }
            
            medicineTable.setItems(medicineList);
            this.showInformationAlert("Data has been added successfully");
            

        } else {
            if (!medicinePriceTxt.getText().matches(REGEX_PATTERN_DOUBLE)) {
                this.showErrorAlert("Price must be a positive number");
            } else if (!medicineQuantityTxt.getText().matches(REGEX_PATTERN_INTEGER)) {
                this.showErrorAlert("Quantity must be a positive number");
            } else {
               this.showErrorAlert("Field cannot be empty");
            }
        }

    }

    @FXML
    public void updateUser() {

        if (!(userNameTxt.getText().equals("") || passwordTxt.getText().equals(""))) {
            //userList.add(new User(userNameTxt.getText(), accessTypeTxt.getSelectionModel().getSelectedItem(), passwordTxt.getText(), ""));
            
            try{
            User user=new User();
            user.setUsername(userNameTxt.getText());
            user.setPassword(passwordTxt.getText());
            user.setAccessType(accessTypeTxt.getSelectionModel().getSelectedItem());
            
            dao.addNewUser(user);
            userList=FXCollections.observableArrayList(dao.getAllUsers());
            }catch(Exception e)
            {
               this.showErrorAlert("Failed to add Data");
                
            }
            
            userTable.setItems(userList);
            this.showInformationAlert("Data has been added successfully");

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Field cannot be empty");
            alert.showAndWait();

        }
    }

    

  
    @FXML
    public void exportMedicineList() throws FileNotFoundException, IOException {
       
        try{
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");  
    Date date = new Date();  
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        
                XSSFSheet sheet = workbook.createSheet("Meds");
                
                XSSFRow header=sheet.createRow(0);
                XSSFCell nameHeader=header.createCell(0);
                XSSFCell quantityHeader=header.createCell(1);
                XSSFCell priceHeader=header.createCell(2);
                XSSFCell totalHeader=header.createCell(3);
                nameHeader.setCellValue("Name");
                quantityHeader.setCellValue("Quantity");
                priceHeader.setCellValue("Price");
                totalHeader.setCellValue("Total");
        
               for(int i=0;i<medicineList.size();i++)
               {
               XSSFRow row=sheet.createRow(i+1);
               row.createCell(0).setCellValue(medicineList.get(i).getName());
               row.createCell(1).setCellValue(medicineList.get(i).getQuantity());
               row.createCell(2).setCellValue(medicineList.get(i).getPrice());
               
               row.createCell(3).setCellFormula("PRODUCT(B"+(i+2)+",C"+(i+2)+")");
               
               }
               String path="C:\\Users\\Satyam\\OneDrive\\Desktop\\"+"MedicineList_"+formatter.format(date)+".xlsx";
               FileOutputStream outputStream=new FileOutputStream(new File(path));
               workbook.write(outputStream);
               
              this.showInformationAlert("File exported to "+path);
               
        }catch(Exception e)
        {
        System.out.println(e);}
               
                
    }
    
    @FXML
    public void tableRowClicked(Event event)
    {
        Item item=medicineTable.getSelectionModel().getSelectedItem();
        this.initializeMedicineFields(item);
                
       
        
    }

    @FXML
    public void uploadFile() {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("/"));
        fc.getExtensionFilters().addAll(new ExtensionFilter("XLSX Files", "*.xlsx"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
          
            
	if(this.showConfirmation("Do you want to upload "+selectedFile.getName())) {
		
             try {
                FileInputStream inputStream = new FileInputStream(selectedFile.getAbsoluteFile());
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator iterator = sheet.iterator();
                if (iterator.hasNext()) {
                    iterator.next();
                }
                while (iterator.hasNext()) {
                    XSSFRow row = (XSSFRow) iterator.next();
                    Iterator cellIterator = row.cellIterator();
                    Cell name = (Cell) cellIterator.next();
                    Cell quantity = (Cell) cellIterator.next();
                    Cell price = (Cell) cellIterator.next();
                    String medicineName = "";
                    int medicineQuantity = 0;
                    double medicinePrice = 0.0;
                    name.getCellType();
                    if (!String.valueOf(name.getCellType()).equals("STRING")) {
                        this.showErrorAlert("Error in row " + (name.getRowIndex() + 1) + ": name is invalid");
                        continue;
                    }
                    if (!String.valueOf(quantity.getCellType()).equals("NUMERIC")) {
                        this.showErrorAlert("Error in row " + (quantity.getRowIndex() + 1) + ": quantity is not a number");
                        continue;
                    }
                    if (!String.valueOf(price.getCellType()).equals("NUMERIC")) {
                        this.showErrorAlert("Error in row  " + (price.getRowIndex() + 1) + ": price is not a number");
                        continue;
                    }
                   
                    try
                    {
                        Item item=new Item();
                        item.setName(name.getStringCellValue());
                        item.setPrice(price.getNumericCellValue());
                        item.setQuantity((int)quantity.getNumericCellValue());
                        dao.addNewItem(item);
                    }
                    catch(Exception e)
                    {this.showErrorAlert("Error in row  " + (price.getRowIndex() + 1) + ": Failed to add");
                    }
                    

                }
                 this.initializeAllList();

            } catch (Exception e) {
                this.showErrorAlert("Failed to upload");
            }
	} 
            
           
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to upload");
            alert.showAndWait();
        }

    }

    
    @FXML
    public void AddPatientMedicineToList()
    {
        if(!patientMedicineQuantityTxt.getText().matches(REGEX_PATTERN_INTEGER))
        {
        this.showErrorAlert("Quantity must be positive number");
        
        }else{
        Medicine m=new Medicine();
        m.setName(medicineChoicesTxt.getSelectionModel().getSelectedItem());
        m.setQuantity(Integer.parseInt(patientMedicineQuantityTxt.getText()));
        try{
            if(dao.getQuantity(m.getName())<m.getQuantity())
            {
            
            throw new ArithmeticException();
            }
        m.setPrice(dao.getPrice(m.getName()));
        purchasedMedicineList.add(m);
        patientMedicineTableList.setItems(FXCollections.observableArrayList(purchasedMedicineList));
        
        }
        catch(ArithmeticException ex){
             this.showErrorAlert("Quantity Should be less than available");
        }
        catch(Exception e)
        {
           this.showErrorAlert("Unable to add medicine");
        }
        }
        
    }
    
    
    
    
    
    @FXML
    public void addPatient()
    {
    if(!(patientNameTxt.getText().equals("")||purchasedMedicineList.size()<1))
    {
    LocalDate date=purchasedDateTxt.getValue();
    String currDate=String.valueOf(date);
    System.out.println(patientNameTxt.getText()+" "+currDate);
    
    Patient patient=new Patient();
    patient.setName(patientNameTxt.getText());
    patient.setDate(currDate);
    patient.setList(new ArrayList<Medicine>(purchasedMedicineList));
    patientDetailList.add(patient);
    patientDetailTable.setItems(patientDetailList);
    this.showInformationAlert("Patient Details added");
    
    }
    else
    {
        this.showErrorAlert("All details are required");
        
    }
       
    
    }
    
     @FXML
    public void patientTableRowClicked(Event event)
    {
        Patient patient=patientDetailTable.getSelectionModel().getSelectedItem();
        
        
    this.initializePatientFields(patient);
       
        
    }
    
   
    @FXML
    public void resetPatientData()
    {
        
        
        patientNameTxt.setText("");
        purchasedDateTxt.setValue(LocalDate.now());
        purchasedMedicineList .clear();
        medicineChoicesTxt.getSelectionModel().selectFirst();
        medicineQuantityTxt.setText("");
         patientMedicineTableList.setItems(FXCollections.observableArrayList(purchasedMedicineList));
    
       
        
    }
    
    
    
}
