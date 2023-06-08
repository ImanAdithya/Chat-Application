package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Client1Controller {
    public TextField client1Name;
    public TextField txtclientMassage;
    public TextArea txtAreaClient1;
    public VBox vBox;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Socket socket;
    final int port=8000;
    String massage="";

    static String sendMassage;
    private static String userName;

    public static void setUSerName(String text) {
        Client1Controller.userName=text;
        System.out.println (userName+"setUserNAme");
    }

    public  void initialize(){
        new Thread (()->{
            try {

                 socket=new Socket ("localhost",8006);
                 dataOutputStream=new DataOutputStream (socket.getOutputStream ());
                 dataInputStream=new DataInputStream (socket.getInputStream ());


                dataOutputStream.writeUTF(Clinet1LoginController.userName);
                dataOutputStream.flush();

                while(!massage.equals ("finished")){
                   massage=dataInputStream.readUTF ();

                    Platform.runLater (()->{

                        Label text = new Label ();
                        text.setStyle("    -fx-background-radius: 20;"+
                                "    -fx-background-color: #7190e0;\n" +
                                "    -fx-font-family: \"fantasy\";\n" +
                                "    -fx-font-size: 12; -fx-padding: 8; -fx-start-margin: 200 ; -fx-text-fill: #fff");
                        text.setText (" " + massage + " ");
                        text.setMinWidth (200);
                        final Group root = new Group ();

                        final GridPane gridPane = new GridPane ();
                        gridPane.setPadding (new Insets (5));
                        gridPane.setHgap (10);
                        gridPane.setVgap (10);
                        gridPane.minHeight (30);
                        text.maxHeight (200);
                        gridPane.maxHeight (200);

                        GridPane.setHalignment (text, HPos.CENTER);
                        gridPane.add (text, 0, 0);
                        gridPane.setAlignment (Pos.CENTER_LEFT);

                        root.getChildren ().add(gridPane);
                        vBox.getChildren ().add(gridPane);

                    });


              }


            } catch (Exception e) {
                e.printStackTrace ();
            }


        }).start ();
    }
    public void onActionNameSave(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(client1Name.getText ());
        dataOutputStream.flush();
        client1Name.clear ();
    }

    public void onActionSend(ActionEvent actionEvent) throws IOException {
        sendMassage=txtclientMassage.getText ();

        Platform.runLater (()->{

            Label text = new Label ();
            text.setStyle("     -fx-background-radius: 20;"+
                    "    -fx-background-color: #7190e0;\n" +
                    "    -fx-font-family: \"fantasy\";\n" +
                    "    -fx-font-size: 12; -fx-padding: 8; -fx-start-margin: 200 ; -fx-text-fill: #fff");
            text.setText (" " + sendMassage + " ");
            final Group root = new Group ();

            final GridPane gridPane = new GridPane ();
            gridPane.setPadding (new Insets (5));
            gridPane.setHgap (10);
            gridPane.setVgap (10);
            gridPane.minHeight (30);
            text.maxHeight (200);
            gridPane.maxHeight (200);

            GridPane.setHalignment (text, HPos.CENTER);
            gridPane.add (text, 0, 0);
            gridPane.setAlignment (Pos.CENTER_RIGHT);

            root.getChildren ().add(gridPane);
            vBox.getChildren ().add(gridPane);

        });


        dataOutputStream.writeUTF (txtclientMassage.getText ());
        dataOutputStream.flush ();

    }


    public void onActionFile(ActionEvent actionEvent) {
    }
}
