import AgreedUponObjects.EquationStorage;
import AgreedUponObjects.PDU;

import java.io.*;
import java.net.Socket;

/**
 * Run by Server on a new thread for each client
 * Prints to Console each equation as its received and then stores it
 * When Client disconnects it prints how many it received and prints all of them
 * Hardly does anything but that's the assignment specifications
 */
public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    public ClientHandler(Socket sock) {
        this.clientSocket = sock;
    }

    @Override
    public void run() {
        EquationStorage storage = new EquationStorage();
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                PDU data = (PDU) in.readObject();

                for (String s : data.getEquations()) {
                    System.out.println(s);
                    storage.store(s);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\n_____________________________\n");
            System.out.println("Number of Equations saved: " + storage.getEquations().size());
            System.out.println("____________________");
            System.out.println("Equations in order:");
            for (String s: storage.getEquations()) {
                System.out.println(s);
            }
        }
    }
}
