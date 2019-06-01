import java.io.*;
import java.net.Socket;

public class ClienteCancion{
	String HOST;
	int puerto;
	Socket sc;    
    DataInputStream input;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    int in;
    byte[] byteArray;
    //Fichero a transferir
    String filename;

	public ClienteCancion(int PORT, String nombre){
		HOST = "127.0.0.1";
        puerto = PORT;
        filename = "Music/"+nombre;
		try{
            final File localFile = new File( filename );
            Socket client = new Socket("localhost", PORT);
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bos = new BufferedOutputStream(client.getOutputStream());
            //Enviamos el nombre del fichero
            DataOutputStream dos=new DataOutputStream(client.getOutputStream());
            dos.writeUTF(localFile.getName());
            //Enviamos el fichero
            byteArray = new byte[8192];
            while ((in = bis.read(byteArray)) != -1){
            bos.write(byteArray,0,in);
            }
            
           bis.close();
            bos.close();
            
           }catch ( Exception e ) {
            System.err.println(e);
            }
    }
	
}
