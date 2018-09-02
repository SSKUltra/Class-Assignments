import java.io.*;
import java.util.*;
import java.net.*;

public class Server 
{
	static Vector<ClientHandler> activeClients = new Vector<>();
	
	static int i = 0;

	public static void main(String[] args) throws IOException 
	{
		ServerSocket serverSocket = new ServerSocket(1133);
		
		Socket socket;
		
		while (true) 
		{
			socket = serverSocket.accept();

			System.out.println("New client request has been received in : " + socket);
			
			DataInputStream instream = new DataInputStream(socket.getInputStream());
			DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
			
			System.out.println("Creating a new handler for this client.");

			ClientHandler clientHandlerObj = new ClientHandler(socket,"client" + Integer.toString(i), instream, outstream);

			Thread t = new Thread(clientHandlerObj);
			
            System.out.println("Adding this client to active client list");
            
			activeClients.add(clientHandlerObj);

            for (ClientHandler clientHandlerIter : Server.activeClients) 
				{
					System.out.println(clientHandlerIter.name);
				}

			t.start();
			i++;

		}
	}
}

class ClientHandler implements Runnable 
{
	public String name;
	final DataInputStream instream;
	final DataOutputStream outstream;
	Socket socket;
	
	public ClientHandler(Socket socket, String name, DataInputStream instream, DataOutputStream outstream) {
		this.instream = instream;
		this.outstream = outstream;
		this.name = name;
		this.socket = socket;
	}

	@Override
	public void run() {

		String receivedFromClient;
		while (true) 
		{
			try
			{
				receivedFromClient = instream.readUTF();
				
				System.out.println(receivedFromClient);
				String [] tokens = receivedFromClient.split(" to ");
				String messageToSend = tokens[0];
				String recipient = tokens[1];
				
                if(messageToSend == "Stop"){
                    break;
				}
				
				for (ClientHandler clientHandlerIter : Server.activeClients) 
				{
					if (clientHandlerIter.name.equals(recipient)) 
					{
						clientHandlerIter.outstream.writeUTF(this.name+" : "+messageToSend);
						break;
					}
				}
			} catch (IOException e) {
				
				e.printStackTrace();
            }
            
            
			
		}
		try
		{
			this.instream.close();
			this.outstream.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
