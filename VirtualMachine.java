/*
  VirtualMachine.java
  Contains the individual thread running for each IoT device
*/

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import javax.net.ssl.SSLSocket;

public class VirtualMachine extends Thread {
  private String filename, server;
  private int serverPort;
  private Socket socket;
  private SSLSocket sslSocket;
  private IoTDevice device;
  private boolean isUsingSSl = false;

  public VirtualMachine(Socket ss, String f, IoTDevice d){
    super();

    filename = f;
    server = d.getServerIP();
    serverPort = d.getServerPort();
    socket = ss;
    device = d;
  }

  public VirtualMachine(SSLSocket ss, String f, IoTDevice d){
    super();

    filename = f;
    server = d.getServerIP();
    serverPort = d.getServerPort();
    sslSocket = ss;
    device = d;
    isUsingSSl = true;

    System.out.println("wassup");
  }

  public void run(){
    Logger logger = new Logger("logs/" + filename);
    Receiver receiver = new Receiver(server, serverPort);
    logger.println("Connected to " + server + " on port " + serverPort);

    Sender sender;

    if(isUsingSSl) sender = new Sender(sslSocket);
    else sender = new Sender(socket);


    String message = "";

    while(true){
      receiver.sendMessage("test");

      message = receiver.getMessage();
      logger.println("Got message \"" + message + "\"");

      message = device.filterMessage(message);

      sender.sendMessage(message);
      logger.println("Sent message \"" + message + "\" to IoT device");

      try{
        Thread.sleep(10);
      }
      catch(Exception e){
        logger.close();
        e.printStackTrace();
      }
    }

  }
}