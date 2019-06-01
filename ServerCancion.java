import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCancion{
	ServerSocket server;
    Socket connection;
 
    DataOutputStream output;
    BufferedInputStream bis;
    BufferedOutputStream bos;
 
    byte[] receivedData;
    int in;
    String file;

    ServerCancion(int PORT){
        try{
            server = new ServerSocket( PORT );
            while ( true ) {
            connection = server.accept();
            receivedData = new byte[1024];
            bis = new BufferedInputStream(connection.getInputStream());
            DataInputStream dis=new DataInputStream(connection.getInputStream());
            file = dis.readUTF();
            file = file.substring(file.indexOf('\\')+1,file.length());
            bos = new BufferedOutputStream(new FileOutputStream("Music/"+file));
            System.out.println(file);
            while ((in = bis.read(receivedData)) != -1){
            bos.write(receivedData,0,in);
            }
            System.out.println("Terminado");

            bos.close();
            dis.close();
            server.close();
            }
        }catch (Exception e ) {
            System.err.println(e);
        }
    }
}
