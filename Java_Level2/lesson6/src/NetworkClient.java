import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class NetworkClient {

    public static final int PORT = 8989;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        Thread scannerThread = new Thread(() -> {
            while (scanner.hasNextLine()){
                try {
                    out.writeUTF(scanner.nextLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        scannerThread.setDaemon(true);
        scannerThread.start();

        new Thread(() -> {
            while (true){
                try {
                    String in_message = in.readUTF();
                    System.out.println("Message from Server: " + in_message);
                    if (in_message.equals(":q")){
                        break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }
}
