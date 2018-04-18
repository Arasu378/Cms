package com.cms.arasu.controllers;

import com.cms.arasu.entity.Staff;
import com.cms.arasu.entity.StaffPosition;
import com.cms.arasu.model.StaffModel;
import com.cms.arasu.util.Utils;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private JFXTextField firstName, lastName, email, position;


    @FXML
    private JFXPasswordField password, confirmPassword;

    private String errorTextMessage;

    private StaffModel staffModel;

    @FXML
    private StackPane stackPaneRegister;

    private final JFXDialogLayout dialogLayout = new JFXDialogLayout();
    private final TextFlow message = new TextFlow();
    private String alert, alertHeader, headerColor, buttonText;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffModel = new StaffModel();
        stackPaneRegister.setVisible(false);

    }
    @FXML
    public void goBack(Event event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Utils.window(getClass().getResource("/fxml/login.fxml"), "Login");
    }

    @FXML
    public void onRegister(ActionEvent event) throws Exception {
        validateEvent(event);
    }

    private void validateEvent(ActionEvent event) throws Exception {
        if (validateInput()) {
            String firstNameString = firstName.getText();
            String lastNameString = lastName.getText();
            String emailString = email.getText();
            String confirmPasswordString = confirmPassword.getText();
            String positionString = position.getText();
            Staff staff = new Staff();
            staff.setFirstName(firstNameString);
            staff.setLastName(lastNameString);
            staff.setEmail(emailString);
            staff.setPassword(DigestUtils.sha1Hex( confirmPasswordString));
            staff.setVerified(false);
            staff.setCreatedOn(Utils.getCurrentDate());
          Long id =  staffModel.insertStaff(staff);
          if (id != 0L) {
              StaffPosition staffPosition = new StaffPosition();
              staffPosition.setStaffId(id);
              staffPosition.setPosition(positionString);
              staffPosition.setRole("admin");
              staffPosition.setCreatedOn(Utils.getCurrentDate());
              Long idPosition = staffModel.insertStaffPosition(staffPosition);
              if (idPosition == 0L) {
                  alertHeader = "Failed to Register";
                  headerColor = "#495057";
                  alert = "\n Staff position is id  0 contact developer. \n";
                  buttonText = "Try Again";
                  showAlertMessage();
              }
              ((Node) (event.getSource())).getScene().getWindow().hide();
              alertHeader = "Register Success";
              headerColor = "#495057";
              alert = "\n Registered Successfully Please Login. \n";
              buttonText = "Login";
              showAlertMessage();
              Utils.window(getClass().getResource("/fxml/login.fxml"), "Login");
          } else  {
              alertHeader = "Failed to Register";
              headerColor = "#495057";
              alert = "\n Email is already exists! \n";
              buttonText = "Try Again";
              showAlertMessage();
          }



        } else {
            alertHeader = "Failed to Register";
            headerColor = "#495057";
            alert = "\n"+errorTextMessage+"\n";
            buttonText = "Try Again";
            showAlertMessage();
        }

    }

    private void showAlertMessage() {
        Utils.showDialog(stackPaneRegister, dialogLayout, message, alertHeader, buttonText, alert, headerColor);

    }

    private boolean validateInput() {
        String errorMessage = "";
        if (firstName.getText() ==  null || lastName.getText() == null ||
                email.getText() == null || password.getText() == null ||
                confirmPassword.getText() == null || position.getText() ==  null) {
            errorMessage += "Please enter All Fields!\n";
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            errorMessage += "Password and confirm password is not same!";
        }
        if (!Utils.validateEmail(email.getText())) {
            errorMessage += "Please enter valid email!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        }
        errorTextMessage = errorMessage;
        return false;
    }
    private void resetFields() {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        position.setText("");
        password.setText("");
        confirmPassword.setText("");
    }

}
