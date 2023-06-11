import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    final  int port=8000;
    private static List<ClinetHandler> clients = new ArrayList<> ();


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket (8007);

            for (int i = 0; i < 5; i++) {
                Socket socket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String clientName = dataInputStream.readUTF();
                System.out.println("Client connected: " + clientName);

                ClinetHandler clientHandler = new ClinetHandler (socket,clientName);
                clients.add(clientHandler);
                clientHandler.start ();
            }

        } catch (Exception e) {
            e.printStackTrace ();




        }
    }
    public static void broadcastMessage(String message,String name) {
        for (ClinetHandler client : clients) {
               if(!client.name.equals (name)){
                   client.sendMessage (message);
               }
            }
        }

}