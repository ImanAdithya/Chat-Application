package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Client2Controller {
    public TextField txtClient2Name;
    public TextArea txtAreaClient2;
    //public TextField txtMassageClient2;
    public VBox vbox;
    public Label lblName;
    public JFXTextField txtMassageSend;
    public VBox vBox2;
    public ScrollPane scrollPaneID;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Socket socket;
    final int port=8000;
    String massage="";

     String sendMassage;

    public  void initialize(){
        lblName.setText (Client2LoginController.userName);
        scrollPaneID.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneID.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        new Thread (()->{
            try {
                 socket=new Socket ("localhost",8007);
                 dataOutputStream=new DataOutputStream (socket.getOutputStream ());
                dataInputStream=new DataInputStream (socket.getInputStream ());

                dataOutputStream.writeUTF(Client2LoginController.userName);
                dataOutputStream.flush();

                while(!massage.equals ("finished")){
                    massage=dataInputStream.readUTF ();



                    if (massage.startsWith ("img")){

                        setImage(massage);

                    }else {
                        setMessage(massage);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace ();
            }


        }).start ();
    }

    private void setMessage(String massage) {
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
            vBox2.getChildren ().add(gridPane);

        });
    }

    private void setImage(String massage) {
        Platform.runLater (() ->{
            //

            String[] paths = massage.split ("`");
            System.out.println (paths[1]);

            //ImageView imageView = new ImageView (new Image (paths[1]));

            Image image1 = new Image (paths[1], 100, 300, true, true);
            ImageView image = new ImageView (image1);
            final Group root = new Group ();

            final GridPane gridpane = new GridPane ();
            gridpane.setPadding (new Insets (5));
            gridpane.setHgap (10);
            gridpane.setVgap (10);
            gridpane.minHeight (30);
            gridpane.maxHeight (200);


            GridPane.setHalignment (image, HPos.CENTER);
            gridpane.add (image, 0, 0);
            gridpane.setAlignment (Pos.CENTER_LEFT);

            root.getChildren ().add (gridpane);

            vBox2.getChildren ().add (gridpane);

        });

    }

//    public void onActionNameSave(ActionEvent actionEvent) throws IOException {
//        dataOutputStream.writeUTF(txtClient2Name.getText ());
//        dataOutputStream.flush();
//        txtClient2Name.clear ();
//    }

    public void onActionSend(ActionEvent actionEvent) throws IOException {
        //dataOutputStream=new DataOutputStream (socket.getOutputStream ());

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


    public void sendMassageOnActopn(ActionEvent actionEvent) throws IOException {
        sendMassage=txtMassageSend.getText ();


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
            vBox2.getChildren ().add(gridPane);

        });


        dataOutputStream.writeUTF (txtMassageSend.getText ());
        dataOutputStream.flush ();
        txtMassageSend.clear ();

    }

    public void onActionSendImage(ActionEvent actionEvent) throws IOException {
        FileChooser chooser = new FileChooser ();
        File file = chooser.showOpenDialog (new Stage ());
        if (file != null) {

            String path = file.toURI ().toString ();
            System.out.println (path);

            System.out.println (file.getPath ());
            Image image1 = new Image (path, 100, 300, true, true);
            ImageView image = new ImageView (image1);
            final Group root = new Group ();

            final GridPane gridpane = new GridPane ();
            gridpane.setPadding (new Insets (5));
            gridpane.setHgap (10);
            gridpane.setVgap (10);
            gridpane.minHeight (30);
            gridpane.maxHeight (200);


            GridPane.setHalignment (image, HPos.CENTER);
            gridpane.add (image, 0, 0);
            gridpane.setAlignment (Pos.CENTER_RIGHT);

            root.getChildren ().add (gridpane);

            vBox2.getChildren ().add (gridpane);


            dataOutputStream.writeUTF ("img`" + path);
            dataOutputStream.flush ();
        }
    }
}
