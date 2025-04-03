import java.io.*;
import java.net.*;

class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server is running...");
        Socket socket1 = serverSocket.accept();
        Socket socket2 = serverSocket.accept();
        new ClientHandler(socket1, socket2).start();
    }
}

class ClientHandler extends Thread {
    private Socket socket1;
    private Socket socket2;
    private BufferedReader in1;
    private BufferedReader in2;
    private PrintWriter out1;
    private PrintWriter out2;

    public ClientHandler(Socket socket1, Socket socket2) throws IOException {
        this.socket1 = socket1;
        this.socket2 = socket2;
        in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        out1 = new PrintWriter(socket1.getOutputStream(), true);
        in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        out2 = new PrintWriter(socket2.getOutputStream(), true);
    }

    public void run() {
        try {
            while (true) {
                String message1 = in1.readLine();
                if (message1 == null) {
                    break;
                }
                System.out.println("Client 1: " + message1);
                out2.println("Client 1: " + message1);

                String message2 = in2.readLine();
                if (message2 == null) {
                    break;
                }
                System.out.println("Client 2: " + message2);
                out1.println("Client 2: " + message2);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        } finally {
            try {
                socket1.close();
                socket2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

 class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Enter message: ");
            String message = consoleInput.readLine();
            out.println(message);
            String response = in.readLine();
            System.out.println(response);
        }
    }
}
