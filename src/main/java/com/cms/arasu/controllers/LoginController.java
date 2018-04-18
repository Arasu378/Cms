package com.cms.arasu.controllers;

import com.cms.arasu.entity.Staff;
import com.cms.arasu.model.StaffModel;
import com.cms.arasu.util.Utils;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private JFXTextField email_login;
    @FXML
    private JFXPasswordField password_login;
    @FXML
    private JFXButton button_login;

    @FXML
    private Hyperlink forgetPassword, signUpPage;

    @FXML
    private StackPane stackPane;

    private StaffModel staffModel;

    private final JFXDialogLayout dialogLayout =  new JFXDialogLayout();

    private final TextFlow MESSAGE_FLOW = new TextFlow();

    private String alert, alertHeader, headerColor, buttonText;

    private String errorTextMessage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackPane.setVisible(false);
        staffModel = new StaffModel();
        enterPressed();
    }
    @FXML
    public void loginAction(ActionEvent event) throws Exception {
        authenticate(event);
    }
    @FXML
    public void registration(Event event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Utils.window(getClass().getResource("/fxml/registration.fxml"), "Registration");

    }
    private void enterPressed() {

        email_login.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    authenticate(ke);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        password_login.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    authenticate(ke);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    private void authenticate(Event event) throws Exception {
        if (validateInput()) {
            String email = email_login.getText().trim();
            String password = DigestUtils.sha1Hex( password_login.getText().trim());
            System.out.println("Pass::: "+password);
            Staff staff = staffModel.checkStaff(email, password);
                if (staff != null) {
                   // if (staffModel.checkPassword(email, password)) {
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    Utils.window(getClass().getResource("/fxml/landing.fxml"), "Landing");

                    //  }
                } else  {
                    resetFields();
                    alertHeader = "Failed to Login";
                    headerColor = "#495057";
                    alert = "\nThe username and password you entered did not match our records."
                            + " Please double-check and try again.\n";
                    buttonText = "Try Again";
                    showAlertMessage();
                }


        } else {
            alertHeader = "Failed to Login";
            headerColor = "#495057";
            alert = "\n"+errorTextMessage+"\n";
            buttonText = "Try Again";
            showAlertMessage();
        }

    }

    private void showAlertMessage() {
        Utils.showDialog(stackPane, dialogLayout, MESSAGE_FLOW, alertHeader, buttonText, alert, headerColor);

    }
    @FXML
    private void forgetAction(Event event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Utils.window(getClass().getResource("/fxml/forget_password.fxml"), "Forget Password");
    }

    private void resetFields() {
        email_login.setText("");
        password_login.setText("");
    }
    private boolean validateInput() {
            String errorMessage = "";
            if (email_login.getText() ==  null || password_login.getText().length() == 0) {
                errorMessage += "Please enter credentials!\n";
            }
            if (!Utils.validateEmail(email_login.getText())) {
                errorMessage += "Please enter Valid email!\n";
            }
            if (errorMessage.length() == 0) {
                return true;
            }
            errorTextMessage = errorMessage;
            return false;
    }
}
