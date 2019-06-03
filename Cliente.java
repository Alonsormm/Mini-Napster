import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import javax.management.relation.RoleInfo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

//javac -cp .:beanutils.jar:commons-logging.jar:JID3.jar:jl-1.0.1.jar:basicplayer3.0.jar *.java
//java -cp .:mysqlcon.jar:beanutils.jar:commons-logging.jar:mysql-connector-java-8.0.15.jar:JID3.jar:jl-1.0.1.jar:basicplayer3.0.jar login

public class Cliente implements LeeRed, ActionListener {
	Red r;
	ArrayList<String> songs;
	String ipAct;
	JFrame ventana;
	JList<String> jL;
	ArrayList<String> lista;
	JButton play, pause, logo;
	DefaultListModel<String> listModel;
	String nombreCancion;
	Post2 p;
	int PORT;
	JTextField nombrecool;
	JButton buscarAlChile;
	public Cliente() {

		r = new Red(this);
		lista = listaArchivos();
		PORT = 6000;
		listModel = new DefaultListModel<String>();
		p = new Post2();
		r.escribeRed(lista);
	}

	public void agregarFaltantes(){
		System.out.println(songs);
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

	public void quitarMios(){
		for(int i = 0 ; i < lista.size();i++){
			if(songs.contains(lista.get(i)))
				songs.remove(lista.get(i));
		}
	}

	public void crearVentana() {
		if (ventana == null) {
			quitarMios();
			agregarFaltantes();
			JPanel botones = new JPanel();
			JPanel busqueda = new JPanel();
			busqueda.setLayout(new GridLayout(2,1));
			botones.setLayout(new GridLayout(1,3));
			botones.setBackground(Color.BLACK);
			JScrollPane scrollPane = new JScrollPane();
			jL = new JList<String>(listModel);
			jL.setBounds(10, 10, 200, 200);
			jL.setBackground(Color.BLACK);
			jL.setForeground(Color.white);
			scrollPane.setViewportView(jL);
			ventana = new JFrame();
			nombrecool = new JTextField();
			buscarAlChile = new JButton("Buscar");
			buscarAlChile.addActionListener(this);
			ventana.setLayout(new GridLayout(4, 1));
			ventana.setSize(600, 600);
			ventana.setVisible(true);
			ventana.getContentPane().setBackground(Color.BLACK);
			logo = new JButton("",new ImageIcon("napster-logo.png"));
			logo.setBorderPainted(false);
			logo.setFocusPainted(false);
			logo.setContentAreaFilled(false);
			play = new JButton("",new ImageIcon("Play.png"));
			play.setBorderPainted(false);
			play.setFocusPainted(false);
			play.setContentAreaFilled(false);
			play.addActionListener(this);
			pause = new JButton("",new ImageIcon("Stop.png"));
			pause.setBorderPainted(false);
			pause.setFocusPainted(false);
			pause.setContentAreaFilled(false);
			pause.addActionListener(this);
			botones.add(play);
			botones.add(pause);
			busqueda.add(nombrecool);
			busqueda.add(buscarAlChile);
			botones.add(busqueda);
			//nombrecool.setText("Busqueda");
			p.setForeground(Color.BLACK);
			ventana.add(logo);
			ventana.add(scrollPane);
			ventana.add(botones);
			ventana.add(p);
			p.setText("Elije alguna cancion!");
			p.setForeground(Color.white);
		} else {
			quitarMios();
			agregarFaltantes();
		}
	}

	public void buscar(String a){
		for(int i = 0 ; i < songs.size();i++){
			if(songs.get(i).indexOf(a) == -1)
				listModel.removeElement(songs.get(i));
		}
		if(a.equals(""))
			agregarFaltantes();
	}

	public void actionPerformed(ActionEvent e) {
		JButton temp = (JButton) e.getSource();
		if (temp == play) {
			nombreCancion = jL.getSelectedValue();
			if(p.getText()=="Reproduciendo")
				p.pausa();
			if(!lista.contains(nombreCancion))
				r.escribeRed(nombreCancion);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(p!=null){
				p.accion(3, "mp3");
			}
			p.reproducir("Music/"+nombreCancion);
			p.setText("Reproduciendo");
			if(!lista.contains(nombreCancion))
				lista.add(nombreCancion);
		}
		if(temp == pause){
			p.pausa();
			p.setText("Elije alguna cacion");
		}
		if(temp == buscarAlChile){
			buscar(nombrecool.getText());
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
