/*
  Receiver.java
  Establishes connection between virtual service and server
  Accepts messages from the server and is capable of sending messages back
*/

import java.io.*;
import javax.net.ssl.SSLSocket;
import java.net.UnknownHostException;

public class Receiver{
  private SSLSocket sslSocket = null;
  private SSLClientSocket mSSLClientSocket;
  private BufferedReader br;
  private PrintWriter pw;

  public Receiver(String server, int port){ //Constructor. Opens up SSL socket
    mSSLClientSocket = new SSLClientSocket(server, port);

    if(mSSLClientSocket.checkAndAddCertificates()) sslSocket = mSSLClientSocket.getSSLSocket();
    else return;

    try{
      sslSocket.setSoTimeout(1000);
      br = new BufferedReader(new InputStreamReader(sslSocket.getInputStream())); // initializations
      pw = new PrintWriter(sslSocket.getOutputStream());
    }
    catch(Exception e){
      e.printStackTrace();
    }

    System.out.println("\033[1m\033[32mSocket successfully set up\033[0m");
  }

  public void sendMessage(String message){ //sends message to server
    try{
      pw.println(message);
      System.out.println(message);
      pw.flush();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public String getMessage(){ //receives message from server
    String message = "";

    try{
      message = br.readLine().trim();
      return message;
    }
    catch(Exception e){
      return "";
    }
  }

}
