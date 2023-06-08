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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client2Controller {
    public TextField txtClient2Name;
    public TextArea txtAreaClient2;
    public TextField txtMassageClient2;
    public VBox vbox;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Socket socket;
    final int port=8000;
    String massage="";

     String sendMassage;

    public  void initialize(){
        new Thread (()->{
            try {
                 socket=new Socket ("localhost",8006);
                 dataOutputStream=new DataOutputStream (socket.getOutputStream ());
                dataInputStream=new DataInputStream (socket.getInputStream ());

                dataOutputStream.writeUTF(Client2LoginController.userName);
                dataOutputStream.flush();

                while(!massage.equals ("finished")){
                    massage=dataInputStream.readUTF ();
                    txtAreaClient2.appendText ("\n"+massage.trim ());
                    /*===================================*/

                    Platform.runLater (()->{

                        Label text = new Label ();
                        text.setStyle("    -fx-background-radius: 20;" +
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
                        vbox.getChildren ().add(gridPane);

                    });

                }

            } catch (Exception e) {
                e.printStackTrace ();
            }


        }).start ();
    }

//    public void onActionNameSave(ActionEvent actionEvent) throws IOException {
//        dataOutputStream.writeUTF(txtClient2Name.getText ());
//        dataOutputStream.flush();
//        txtClient2Name.clear ();
//    }

    public void onActionSend(ActionEvent actionEvent) throws IOException {
        //dataOutputStream=new DataOutputStream (socket.getOutputStream ());
        sendMassage=txtMassageClient2.getText ();


        Platform.runLater (()->{

            Label text = new Label ();
            text.setStyle("     -fx-background-radius: 20;" +
                    "    -fx-background-color: #7190e0;\n" +
                    "    -fx-font-family: \"fantasy\";\n" +
                    "    -fx-font-size: 12; -fx-padding: 8; -fx-start-margin: 200 ; -fx-text-fill: #fff");
            text.setText (" " + sendMassage + " ");
//            text.setMinWidth (200);
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
            vbox.getChildren ().add(gridPane);

        });


        dataOutputStream.writeUTF (txtMassageClient2.getText ());
        dataOutputStream.flush ();
        txtMassageClient2.clear ();

    }
    public void setMassange(String massage){
            if (sendMassage.equals (massage)){
                Platform.runLater (()->{
                    HBox hBox=new HBox();
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    Text text=new Text(massage);
                    TextFlow textFlow=new TextFlow(text);

                    textFlow.setMaxWidth(130);

                    hBox.getChildren().add(textFlow);
                    vbox.getChildren().add(hBox);
                });
            }


        }

    public void onActionNameSave(ActionEvent actionEvent) {
    }
}
