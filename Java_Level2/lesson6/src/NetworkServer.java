import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class NetworkServer {
    public static void main(String[] args) {
        Socket socket = null;
        try(ServerSocket serverSocket = new ServerSocket(8989)){
            System.out.println("Server started");
            socket = serverSocket.accept();
            System.out.println("Client has connected to server");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream((socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String in_message = in.readUTF();
                            System.out.println("Message from client: " + in_message);
                            if (in_message.equals(":q")){
                                break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }

                    }
                }
            });
            readThread.start();

            Thread scannerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (scanner.hasNextLine()){
                        try {
                            out.writeUTF(scanner.nextLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            scannerThread.setDaemon(true);
            scannerThread.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
