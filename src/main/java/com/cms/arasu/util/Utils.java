package com.cms.arasu.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final String IMAGE_LOC = "/resources/icon.png";
    private static final String ALPABHETS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static Pattern pattern;
    private static Matcher matcher;
    public static String randomPassword() {
        SecureRandom secureRandom = new SecureRandom();
        String randomPassword = null;
        StringBuilder stringBuilder = new StringBuilder( 5 );
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(ALPABHETS.charAt(secureRandom.nextInt(ALPABHETS.length())));
            randomPassword = stringBuilder.toString();
        }
        return randomPassword;
    }
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static void window(URL url, String title) throws Exception {
        Parent parent  = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle(title);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setScene(scene);
        stage.show();
    }
    public static boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static void showDialog(StackPane stackPane, JFXDialogLayout dialogLayout, TextFlow MESSAGE_FLOW, String alertHeader, String buttonText, String alert, String headerColor) {
        stackPane.setVisible(true);
        MESSAGE_FLOW.getChildren().clear();
        Text header =  new Text(alertHeader);
        header.setFont(new Font("System", 18));
        header.setFill(Paint.valueOf(headerColor));
        JFXButton hideMessageButton =  new JFXButton(buttonText);
        hideMessageButton.setStyle("-fx-background-color: #4dadf7");
        hideMessageButton.setTextFill(Paint.valueOf("#FFFFFF"));
        hideMessageButton.setRipplerFill(Paint.valueOf("#FFFFFF"));
        hideMessageButton.setButtonType(JFXButton.ButtonType.RAISED);

        MESSAGE_FLOW.getChildren().add(new Text(alert));
        dialogLayout.setHeading(header);
        dialogLayout.setBody(MESSAGE_FLOW);
        dialogLayout.setActions(hideMessageButton);
        JFXDialog alertView = new JFXDialog(stackPane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        alertView.setOverlayClose(false);
        hideMessageButton.setOnAction(e -> {
            alertView.close();
            stackPane.setVisible(false);
        });
        alertView.show();
    }
}
