package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client3Controller {
    public TextField txtclient3Name;
    public TextArea txtAreaName3;
    public TextField txtMassage;
    public VBox vBox;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Socket socket;
    final int port=8000;
    String massage="";

    public  void initialize(){
        new Thread (()->{
            try {
                socket=new Socket ("localhost",8006);
                dataOutputStream=new DataOutputStream (socket.getOutputStream ());
                dataInputStream=new DataInputStream (socket.getInputStream ());

                dataOutputStream.writeUTF(Client3LoginController.userName);
                dataOutputStream.flush();

                while(!massage.equals ("finished")){
                    massage=dataInputStream.readUTF ();
                    txtAreaName3.appendText ("\n"+massage.trim ());
                }

            } catch (Exception e) {
                e.printStackTrace ();
            }

        }).start ();
    }

    public void onActionSend(ActionEvent actionEvent) throws IOException {
//        dataOutputStream.writeUTF (txtMassage.getText ());
//        dataOutputStream.flush ();
//        txtMassage.clear ();

         String sendMassage=txtMassage.getText ();

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

        dataOutputStream.writeUTF (txtMassage.getText ());
        dataOutputStream.flush ();

    }
}
