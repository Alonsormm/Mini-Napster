import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
	ArrayList<ObjectOutputStream> clientObjectOutputStreams;
	public class ClientHandler implements Runnable {
         ObjectOutputStream writer;
         ObjectInputStream reader;
         Socket sock;

         public ClientHandler(Socket clientSocket, ObjectOutputStream writer) {
         	try{
            	this.writer= writer;
            	sock = clientSocket;
            	reader = new ObjectInputStream(sock.getInputStream());
	   			System.out.println(reader);		 
           }catch(Exception ex){
				System.out.println("Exce Servidor reader " + ex);
             	ex.printStackTrace();
			}
		} // close constructor

		public void run(){
			Object obj;
            Map<String,String> map = new HashMap<String,String>();
			try{
				while(true){
					obj =(Object)reader.readObject();
					String a = sock.getRemoteSocketAddress().toString();
					if(obj instanceof ArrayList){
						ArrayList<String> l = (ArrayList<String>) obj;
					 	//System.out.println(l);
						for(String i: l)
							map.put(i,a);
					}
					System.out.println(map.get("a.mp3"));
				}
			}	
           catch(Exception ex){
				   ex.printStackTrace();
		   }
       }
	}
    public static void main (String[] args) {
         new Server().go();
    }
	public void go(){
    	clientObjectOutputStreams = new ArrayList<ObjectOutputStream>();
		try {
			ServerSocket serverSock = new ServerSocket(5000);
       		while(true){
          		Socket clientSocket = serverSock.accept();
         		ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());        
         	 	clientObjectOutputStreams.add(writer);
       			Thread t = new Thread(new ClientHandler(clientSocket, writer));
       			t.start();
       			System.out.println("got a conexion");
     		}          
		}catch(Exception ex) {
         ex.printStackTrace();
		 }
	}
	public void tellEveryone(Object obj, ObjectOutputStream writerp){
		Iterator it = clientObjectOutputStreams.iterator();
		while(it.hasNext()) {
        	try{
           		ObjectOutputStream writer = (ObjectOutputStream) it.next();
	   			if(writer.equals(writerp)){
                	writer.writeObject(obj);
           			writer.flush();
	   			}
         }catch(Exception ex){
              ex.printStackTrace();
         }
      
       }
   }
}
