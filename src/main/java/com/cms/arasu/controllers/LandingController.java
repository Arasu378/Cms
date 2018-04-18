package com.cms.arasu.controllers;

import com.cms.arasu.util.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LandingController implements Initializable {
    @FXML
    private JFXButton buttonLogout;

    @FXML
    private Label userName;

    @FXML
    private JFXHamburger navMenu;

    @FXML
    private JFXDrawer navigationDrawer;

    private   HamburgerBackArrowBasicTransition hamburgerBackArrowBasicTransition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initiateViews();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @FXML
    public void navMenuClicked(Event event) {
        hamburgerBackArrowBasicTransition.setRate(hamburgerBackArrowBasicTransition.getRate() * -1);
        hamburgerBackArrowBasicTransition.play();
        if (navigationDrawer.isShown()) {
            navigationDrawer.close();
        } else {
            navigationDrawer.open();
        }
    }

    private void initiateViews() throws Exception {
        VBox box = FXMLLoader.load(getClass().getResource("/fxml/navigation.fxml"));
        navigationDrawer.setSidePane(box);
         hamburgerBackArrowBasicTransition = new HamburgerBackArrowBasicTransition(navMenu);
        hamburgerBackArrowBasicTransition.setRate(-1);
//        navMenu.addEventHandler(MouseEvent.MOUSE_PRESSED, (EventHandler<T>) event -> {
//            hamburgerBackArrowBasicTransition.setRate(hamburgerBackArrowBasicTransition.getRate() * -1);
//            hamburgerBackArrowBasicTransition.play();
//            if (navigationDrawer.isShown()) {
//                navigationDrawer.close();
//            } else {
//                navigationDrawer.open();
//            }
//        });
    }

    @FXML
    public void logOutAction(ActionEvent event) throws Exception{
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Utils.window(getClass().getResource("/fxml/login.fxml"), "Login");
    }


}
