import java.net.ServerSocket;
import java.net.Socket;

/**
 * Runs forever accepting new Clients
 */
public class Server {
    public static void main(String[] args) {

        ServerSocket server = null;
        int PORT_NUM = 9121;
        try {
            server = new ServerSocket(PORT_NUM);
            server.setReuseAddress(true);

            while (true) {
                Socket client = server.accept();

                ClientHandler clientSock = new ClientHandler(client);

                new Thread(clientSock).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
