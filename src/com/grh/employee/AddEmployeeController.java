package com.grh.employee;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.grh.DAO.DepartementManager;
import com.grh.DAO.EmployeeManager;
import com.grh.DAO.JobManager;
import com.grh.tables.Employee;
import com.grh.utilities.Checks;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddEmployeeController implements Initializable{

	@FXML private JFXTextField firstName;
	@FXML private JFXTextField lastName;
	@FXML private JFXTextField email;
	@FXML private JFXTextField phone;
	@FXML private JFXTextField address;
	@FXML private JFXComboBox<String> job;
	@FXML private JFXComboBox<String> departement;
	@FXML private JFXDatePicker hiredDate;
	
	@FXML
	public void buttonPressed(KeyEvent event) throws SQLException
	{
	    if(event.getCode().toString().equals("ENTER"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	        addBtn(actionEvent);
	    }
	    if(event.getCode().toString().equals("ESCAPE"))
	    {
	    	ActionEvent actionEvent = new ActionEvent(event.getSource(),event.getTarget());
	        cancelBtn(actionEvent);
	    }
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			job.setItems(JobManager.getAllJobs());
			departement.setItems(DepartementManager.getAllDeparts());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addBtn(ActionEvent event) throws SQLException{
		if(firstName.getText().equals("") || lastName.getText().equals("") || email.getText().equals("")
				|| phone.getText().equals("") || address.getText().equals("")
				|| job.getSelectionModel().getSelectedItem()==null || departement.getSelectionModel().getSelectedItem()==null 
				|| hiredDate.getValue()==null){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("All Fields Are Required");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		if(!Checks.isValidEmail(email.getText())){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("Invalid Email Format");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		if(!Checks.isValidPhoneNumber(phone.getText())){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("Invalid Phone Number Format");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		if(!Checks.isLessThanCurrentDate(hiredDate.getValue().toString())){
			Alert dialog = new Alert(AlertType.WARNING);
			dialog.setTitle("Error");
			dialog.setHeaderText(null);
			dialog.setContentText("Invalid Date Format");
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/assets/icon.png"));
			dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
			dialog.showAndWait();
			return;
		}
		Employee employee = new Employee();
		employee.setFirstName(firstName.getText());
		employee.setLastName(lastName.getText());
		employee.setJobName(job.getSelectionModel().getSelectedItem().toString());
		employee.setDepartementName(departement.getSelectionModel().getSelectedItem().toString());
		employee.setEmail(email.getText());
		employee.setPhone(phone.getText());
		employee.setAddress(address.getText());	
		employee.setHiredDate(hiredDate.getValue().toString());
		if(EmployeeManager.insert(employee)){
			System.out.println("Insert Done !");
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
		}
	}
	
	public void cancelBtn(ActionEvent event){
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.close();
	}

}
