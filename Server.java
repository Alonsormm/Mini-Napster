import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
	ArrayList<ObjectOutputStream> clientObjectOutputStreams;
	ArrayList<String> ipClientes;
	Canciones songs;

	public class ClientHandler implements Runnable {
		ObjectOutputStream writer;
		ObjectInputStream reader;
		Socket sock;

		public ClientHandler(Socket clientSocket, ObjectOutputStream writer) {
			try {
				this.writer = writer;
				sock = clientSocket;
				reader = new ObjectInputStream(sock.getInputStream());
				System.out.println(reader);
			} catch (Exception ex) {
				System.out.println("Exce Servidor reader " + ex);
				ex.printStackTrace();
			}
		}

		public void run() {
			Object obj;
			Map<String, ArrayList<String>> canciones = new HashMap<String, ArrayList<String>>();
			try {
				while (true) {
					obj = (Object) reader.readObject();
					String a = sock.getRemoteSocketAddress().toString();
					if (obj instanceof ArrayList) {
						ArrayList<String> l = (ArrayList<String>) obj;
						for (String i : l) {
							songs.agregarCancion(a, i);
						}
						tellEveryone(songs.consultarTodasLasCanciones(), writer);
						System.out.println(songs.consultarTodasLasCanciones());
					}
					if (obj instanceof String){
						String nombreCancion = (String) obj;
						String ip = songs.consultarIp(nombreCancion);
						Response(true, writer);	

						Thread.sleep(500);

						tellOne(ip, nombreCancion);

					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Server().go();
	}

	public void go() {
		clientObjectOutputStreams = new ArrayList<ObjectOutputStream>();
		ipClientes = new ArrayList<String>();
		try {
			ServerSocket serverSock = new ServerSocket(5000);
			songs = new Canciones();
			while (true) {
				Socket clientSocket = serverSock.accept();
				ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
				clientObjectOutputStreams.add(writer);
				ipClientes.add(clientSocket.getRemoteSocketAddress().toString());
				Thread t = new Thread(new ClientHandler(clientSocket, writer));
				t.start();
				System.out.println("got a conexion");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void tellEveryone(Object obj, ObjectOutputStream writerp) {
		Iterator it = clientObjectOutputStreams.iterator();
		while (it.hasNext()) {
			try {
				ObjectOutputStream writer = (ObjectOutputStream) it.next();
				writer.writeObject(obj);
				writer.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void Response(Object obj, ObjectOutputStream writerp) {
		// Iterator it = clientOutputStreams.iterator();
		Iterator it = clientObjectOutputStreams.iterator();
		while (it.hasNext()) {
			try {
				// PrintWriter writer = (PrintWriter) it.next();
				ObjectOutputStream writer = (ObjectOutputStream) it.next();
				if (writer.equals(writerp)) {
					// writer.println(message);
					//System.out.println(writer.getClass());

					writer.writeObject(obj);
					writer.flush();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	public void tellOne(String ip, Object obj) {
		System.out.println(ipClientes);
		int i = ipClientes.indexOf(ip);
		if(i == -1)
			System.out.println("No encontrado");
		else{
			ObjectOutputStream writer = (ObjectOutputStream) clientObjectOutputStreams.get(i);
			try {
				writer.writeObject(obj);
			} catch (IOException e) {
			
			}
		}
	}  
}
