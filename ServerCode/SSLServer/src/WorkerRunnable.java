import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.net.SocketAddress;
import java.nio.channels.*;
import java.nio.*;
import java.net.*;

/*
 * WorkerRunnable.java
 * 7/11/17
 * Handles messaging between client and server (Iot Device and Virtual Service)
 * Ryan Goggins
 * Adapted from: http://tutorials.jenkov.com/java-multithreaded-servers/thread-pooled-server.html
 */

public class WorkerRunnable implements Runnable{

  protected Socket clientSocket;

  /*
   * Constructor takes in a socket and a message (unused)
   */
  public WorkerRunnable(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {
    boolean running = true;
    try {
      while (running) {
        if (running) {
          //Create a writer and a reader to pass messages from client to server
          BufferedReader msgReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());

          //Nonblocking reading messages
          System.out.println("Starting that stupid loop");
          long timeEnd = System.currentTimeMillis() + 1000;
          String data = "";
          System.out.println("Buffered reader readline method:" + msgReader.readLine());
          while (System.currentTimeMillis() < timeEnd) {
            System.out.println("In the loop");
            System.out.println("Buffereader reader ready::" + msgReader.ready());
            System.out.println("readline:" + msgReader.readLine());
            if ((msgReader.ready())) {
              System.out.println("BufferedReader read method returns:" + msgReader.read());
              System.out.println("in the if statement");
              data += msgReader.readLine();
            }
          }
          System.out.println("Data= " + data);

          //Nonbocking for sending messages
          BufferedReader msgTaker = new BufferedReader(new InputStreamReader(System.in));
          System.out.println("");
          long end=System.currentTimeMillis()+1500;
          //non-blocking text input such that the user is given some time to enter a message
          String message = "";
          while((System.currentTimeMillis()<end)) {
            if (msgTaker.ready())
            message += msgTaker.readLine();
          }

          //Message handling
          if (message.equals("")) {
            System.out.println("No input on socket: " + clientSocket.getRemoteSocketAddress().toString());
          }
          else {
            System.out.println("The message is " + message + " on socket " + clientSocket.getRemoteSocketAddress().toString());
          }
          pw.println(message);

          //Check if client has disconnected
          if (pw.checkError()) {
            running = false;
            System.out.println("Client disconnected");
          }

          pw.flush();
        }
      }
      clientSocket.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
