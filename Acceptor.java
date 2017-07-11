/*
  Acceptor.java
  Contains the thread that monitors for new connection
*/

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.*;
import Plugins.*;

public class Acceptor extends Thread {

  public void run(){

    PrintWriter out = null;
    BufferedReader in = null;

    int port = 2000;

    String line = "";

    ServerSocket serverSocket = null;

    try{

    }
    catch(Exception e){

    }

    while(true){

      Socket clientSocket = null;
      try{
        serverSocket = new ServerSocket(2000);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      }
      catch(Exception e){
        e.printStackTrace();
      }

      try{
        line = in.readLine();
      }
      catch(Exception e){
        e.printStackTrace();
      }

      if(line.indexOf(":") != -1){
        String serverIP = line.split(":")[0];
        int serverPort = Integer.parseInt(line.split(":")[1]);
        IoTDevice device = new LightSensorPlugin(serverPort, serverIP);

        VirtualMachine virtualMachine = new VirtualMachine(clientSocket, "test.txt", device);
        virtualMachine.start();
      }

      port++;

      try{
        Thread.sleep(10);
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }

  }
}
