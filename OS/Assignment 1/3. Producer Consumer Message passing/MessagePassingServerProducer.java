import java.io.*;
import java.util.*;


import java.net.*;

public class MessagePassingServerProducer 
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

            for (ClientHandler clientHandlerIter : MessagePassingServerProducer.activeClients) 
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
    static Integer producedItems = 0;
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
				String [] tokens = receivedFromClient.split(" from ");
				String messageToSend = tokens[0];
				String recipient = tokens[1];

                if(messageToSend == "Stop"){
                    break;
				}				

				for (ClientHandler clientHandlerIter : MessagePassingServerProducer.activeClients) 
				{
					if (clientHandlerIter.name.equals(recipient)) 
					{
                        int i = Integer.parseInt(messageToSend);

                        for (int m=0;m<i;m++)
                        {
                            ++producedItems;
                            clientHandlerIter.outstream.writeUTF(producedItems.toString());

                        }

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