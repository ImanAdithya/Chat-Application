package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;

public class Clinet1LoginController {
    public  JFXTextField txtUserName;

    static String userName;
    public AnchorPane contextPane;

    public void onActionLOgin(ActionEvent actionEvent) throws IOException {

        userName=txtUserName.getText ();
        Stage stage3 = new Stage();
        stage3.setScene(new Scene (FXMLLoader.load(getClass().getResource("/view/client1.fxml"))));
        stage3.show();

        Stage stage4 = (Stage) contextPane.getScene().getWindow();
        stage4.close();

    }

}
