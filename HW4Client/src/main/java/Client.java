import Controller.Controller;
import Model.Model;
import View.CalcView;

import java.io.IOException;
import java.net.Socket;

/**
 * Main Class of the Client-Side of the Project
 */
public class Client {
    public static void main(String[] args) {

        int PORT_NUM = 9121;

        try {
            //this will try to connect and set up the model that way
            Socket socket = new Socket("localhost", PORT_NUM);
            System.out.println("Connection Successful");
            Model myModel = new Model(socket);
            Controller myController = new Controller(myModel);
            CalcView myView = new CalcView(myController);
            myModel.setView(myView);
        } catch (IOException e) {
            //if connection failed, do everything as normal but never log to server
            System.out.println("Connection Failed");
            Model myModel = new Model();
            Controller myController = new Controller(myModel);
            CalcView myView = new CalcView(myController);
            myModel.setView(myView);
        }
    }
}
