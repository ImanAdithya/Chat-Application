package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client3Controller {
    public TextField txtclient3Name;
    public TextArea txtAreaName3;
    // public TextField txtMassage;
    public VBox vBox;
    public Label lblName;
    public JFXTextField txtSendMassage;
    public VBox vBox2;
    public ScrollPane scrollPaneID;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Socket socket;
    final int port = 8000;
    String massage = "";
    String sendMassage;
    FileChooser fileChooser;
    File filePath;
    PrintWriter writer;

    String userName;

    BufferedReader reader;


    public void initialize() throws IOException {
       userName= Client3LoginController.userName;
        lblName.setText (Client3LoginController.userName);

        scrollPaneID.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneID.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        new Thread (() -> {
            try {
                socket = new Socket ("localhost", 8007);
                dataOutputStream = new DataOutputStream (socket.getOutputStream ());
                dataInputStream = new DataInputStream (socket.getInputStream ());

                reader=new BufferedReader (new InputStreamReader (socket.getInputStream ()));
                writer=new PrintWriter (socket.getOutputStream (),true);


                dataOutputStream.writeUTF (Client3LoginController.userName);
                dataOutputStream.flush ();

                while (!massage.equals ("finished")) {
                    massage = dataInputStream.readUTF ();

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

    private void setImage(String massage) {
        Platform.runLater (() ->{
            ImageView imageView = new ImageView (new Image (massage));

            String[] paths = massage.split ("`");
            System.out.println (paths[1]);

        });
    }

    void setMessage(String msg){

        Platform.runLater (() -> {
            Label text = new Label ();
            text.setStyle ("    -fx-background-radius: 20;" +
                    "    -fx-background-color: #7190e0;\n" +
                    "    -fx-font-family: \"fantasy\";\n" +
                    "    -fx-font-size: 12; -fx-padding: 8; -fx-start-margin: 200 ; -fx-text-fill: #fff");
            text.setText (" " + msg + " ");
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

            root.getChildren ().add (gridPane);
            vBox2.getChildren ().add (gridPane);
        });
    }


    public void onActionSendMassage(ActionEvent actionEvent) throws IOException {
        sendMassage = txtSendMassage.getText ();

        Platform.runLater (() -> {

            Label text = new Label ();
            text.setStyle ("     -fx-background-radius: 20;" +
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

            root.getChildren ().add (gridPane);
            vBox2.getChildren ().add (gridPane);

        });

        dataOutputStream.writeUTF (sendMassage);
        dataOutputStream.flush ();
        txtSendMassage.clear ();

    }

    public void brouseImageOnActions(MouseEvent mouseEvent) throws IOException {
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


            dataOutputStream.writeUTF ("img`"+path);
            dataOutputStream.flush ();

        }

//        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
//          fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Image");
//        this.filePath = fileChooser.showOpenDialog(stage);
//        writer.println("img`" + filePath.getPath());

    }
}
