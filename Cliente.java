import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import javax.management.relation.RoleInfo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

//javac -cp .:beanutils.jar:commons-logging.jar:JID3.jar:jl-1.0.1.jar:basicplayer3.0.jar *.java
//java -cp .:mysqlcon.jar:beanutils.jar:commons-logging.jar:mysql-connector-java-8.0.15.jar:JID3.jar:jl-1.0.1.jar:basicplayer3.0.jar Cliente

public class Cliente implements LeeRed, ActionListener {
	Red r;
	ArrayList<String> songs;
	String ipAct;
	JFrame ventana;
	JList<String> jL;
	ArrayList<String> lista;
	JButton play, pause;
	DefaultListModel<String> listModel;
	String nombreCancion;
	Post2 p;
	int PORT;

	public Cliente() {

		r = new Red(this);
		lista = listaArchivos();
		PORT = 6000;
		listModel = new DefaultListModel<String>();
		r.escribeRed(lista);
	}

	public void agregarFaltantes() {
			for (int i = 0; i < songs.size(); i++) {
				if (listModel.contains(songs.get(i))) {
					continue;
				}
				listModel.addElement(songs.get(i));
			}

	}

	void quitarSobrantes(ArrayList<String> songs2){
		songs2.remove(0);
		for (int i = 0; i < lista.size(); i++) {
			if(songs.contains(lista.get(i)));
				songs.remove(lista.get(i));
		}
		for (int i = 0; i < songs.size(); i++) {
			songs2.remove(songs.get(i));
		}
		for (int i = 0; i < lista.size(); i++) {
			songs.add(lista.get(i));
		}
		listModel.clear();
	}

	public void crearVentana() {
		if (ventana == null) {
			agregarFaltantes();
			JPanel botones = new JPanel();
			botones.setLayout(new GridLayout(1, 2));
			jL = new JList<String>(listModel);
			jL.setBounds(10, 10, 200, 200);
			ventana = new JFrame();
			ventana.setLayout(new GridLayout(3, 1));
			ventana.setSize(500, 500);
			ventana.setVisible(true);
			ventana.add(jL);
			play = new JButton("play");
			play.addActionListener(this);
			pause = new JButton("Pause");
			pause.addActionListener(this);
			botones.add(play);
			botones.add(pause);
			ventana.add(botones);
		} else {
			agregarFaltantes();
		}
	}

	public void actionPerformed(ActionEvent e) {
		JButton temp = (JButton) e.getSource();
		if (temp == play) {
			nombreCancion = jL.getSelectedValue();
			r.escribeRed(nombreCancion);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(p!=null){
				p.accion(3, "mp3");				
			}
			p = new Post2("Music/"+ nombreCancion);
			ventana.add(p);
			lista.add(nombreCancion);
		}
		if(temp == pause){
			p.accion(3, "a");
		}
	}

	public ArrayList<String> listaArchivos(){
		File folder = new File("Music");
				File[] listOfFiles = folder.listFiles();
        ArrayList<String> lista = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile())
					lista.add(listOfFiles[i].getName());
        }
		return lista;
	}

	public void leeRed(Object obj){
		if(obj instanceof ArrayList){
			songs = (ArrayList<String>)obj;
			if(songs.get(0).equals("\0")){
				quitarSobrantes(songs);
				crearVentana();
			}
			else{

				crearVentana();
			}
		}
		else if(obj instanceof String){
			String nombreCancion = (String) obj;
			crearCliente(PORT, nombreCancion);
			
		}	
		else if(obj instanceof Boolean){
			crearServidor(PORT);
		}
	}



	public void crearServidor(int PORT) {
		new ServerCancion(PORT);
		System.out.println("Servidor detenido");
	}

	public void crearCliente(int PORT, String Nombre){
		new ClienteCancion(PORT, Nombre);
		System.out.println("Cliente Detenido");
	}

	public static void main(String[] args) {
		new Cliente();
	}

}
