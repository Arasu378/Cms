package com.cms.arasu.controllers;

import com.cms.arasu.entity.Staff;
import com.cms.arasu.model.StaffModel;
import com.cms.arasu.util.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextFlow;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgetPasswordController implements Initializable {
    @FXML
    private StackPane stackForget;

    @FXML
    private JFXTextField emailForget;

    @FXML
    private JFXPasswordField newPasswordForget, confirmPasswordForget;

    @FXML
    private JFXButton buttonChangePassword;

    private StaffModel staffModel;
    private final JFXDialogLayout dialogLayout = new JFXDialogLayout();
    private final TextFlow textFlow = new TextFlow();
    private String alert, alertHeader, headerColor, buttonText;
    private String errorTextMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackForget.setVisible(false);
        staffModel =  new StaffModel();
    }
    @FXML
    private void backLogin(Event event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Utils.window(getClass().getResource("/fxml/login.fxml"), "Login");
    }
    private void resetFields() {
        emailForget.setText("");
        newPasswordForget.setText("");
        confirmPasswordForget.setText("");
    }
    private void showAlertMessage() {
        Utils.showDialog(stackForget, dialogLayout, textFlow, alertHeader, buttonText, alert, headerColor);
    }
    private boolean validateInput() {
        String errorMessage = "";
        if (emailForget.getText() == null || newPasswordForget.getText() == null || confirmPasswordForget.getText() == null) {
            errorMessage += "Please enter all credentials!\n";
        }
        if (!Utils.validateEmail(emailForget.getText())) {
            errorMessage += "Please enter valid email!\n";
        }
        if (!newPasswordForget.getText().equals(confirmPasswordForget.getText())) {
            errorMessage += "new password and confirm password is not matched!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        }
        errorTextMessage = errorMessage;
        return false;
    }
    @FXML
    private void changePassword(ActionEvent event) throws Exception {
        if (validateInput()) {
                String email = emailForget.getText();
                String confirmPassword = confirmPasswordForget.getText();
                String encryptedPassword = DigestUtils.sha1Hex(confirmPassword.trim());
                 Staff staff =  staffModel.changePassword(email, encryptedPassword);
                 if (staff == null) {
                  //   resetFields();
                     alertHeader = "Failed";
                     headerColor = "#495057";
                     alert = "\n Please check the email!\n";
                     buttonText = "Try Again";
                     showAlertMessage();
                 } else {
                     alertHeader = "Updated Successfully";
                     headerColor = "#495057";
                     alert = "\n Password is Updated successfully!\n";
                     buttonText = "OK";
                     showAlertMessage();
//                     ((Node) (event.getSource())).getScene().getWindow().hide();d
//                     Utils.window(getClass().getResource("/fxml/login.fxml"), "Login");
                 }

        } else {
            alertHeader = "Failed";
            headerColor = "#495057";
            alert = "\n"+errorTextMessage+"\n";
            buttonText = "Try Again";
            showAlertMessage();
        }
    }
}
