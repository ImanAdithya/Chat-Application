import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;

public class ClinetHandler extends Thread{
    private Socket clientSocket;
    DataOutputStream dataOutputStream;
    String name;

    public ClinetHandler(Socket socket,String name) {
        this.clientSocket = socket;
        this.name=name;
    }

    public void run() {
        try {
            dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader (System.in));

            while (true){
                String message = dataInputStream.readUTF();

                if (message.equalsIgnoreCase("CLOSE")){
                    System.out.println("Chat Closed");
                    break;
                }
                if (message.startsWith ("img")){

                    Server.broadcastMessage(message,this.name);

                }else {
                    String received=this.name+" : "+message;
                    Server.broadcastMessage(received,this.name);
                }


            }
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendMessage(String reply) {
        try {
            dataOutputStream.writeUTF(reply);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



