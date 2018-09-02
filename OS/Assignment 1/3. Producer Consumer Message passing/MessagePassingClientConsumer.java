import java.io.*;
import java.net.*;
import java.util.Scanner;
 
public class MessagePassingClientConsumer 
{
    final static int ServerPort = 1133;
 
    public static void main(String args[]) throws UnknownHostException, IOException 
    {
        Scanner scan = new Scanner(System.in);
         
        InetAddress ip = InetAddress.getByName("localhost");
         
        Socket socket = new Socket(ip, ServerPort);
         
        DataInputStream inputstream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputstream = new DataOutputStream(socket.getOutputStream());
 
        Thread sendMessage = new Thread(new Runnable() 
        {
            @Override
            public void run() {
                while (true) {
 
                    String message = scan.nextLine();
                     
                    try {
                        outputstream.writeUTF(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
         
        Thread readMessage = new Thread(new Runnable() 
        {
            @Override
            public void run() {
 
                while (true) {
                    try {
                        String message = inputstream.readUTF();
                        System.out.println(message);
                    } catch (IOException e) {
 
                        e.printStackTrace();
                    }
                }
            }
        });
 
        sendMessage.start();
        readMessage.start();
 
    }
}