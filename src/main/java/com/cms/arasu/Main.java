package com.cms.arasu;

import hibernate.JPAUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.persistence.EntityManager;


public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        root.setOnMousePressed((MouseEvent mouseEvent) -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setTitle("CMS");
        primaryStage.getIcons().add(new Image("/images/icon.png"));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
//        EntityManager entityManager = JPAUtil.getFactory().createEntityManager();
//        entityManager.getTransaction().begin();
//        String sql = "select version()";
//        String result = (String) entityManager.createNativeQuery(sql).getSingleResult();
//        System.out.println(result);
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        JPAUtil.closeDB();
    }


    public static void main(String[] args) {
        if (JPAUtil.setFactory()) {
            launch(args);
            JPAUtil.getFactory().close();
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An error has occurred!");
                alert.setHeaderText("Database Connection Error!");
                alert.setContentText("Please contact the developer");
                alert.showAndWait();
                Platform.exit();
            });

        }
    }
}
