import java.io.File;
import java.util.ArrayList;

import javax.management.relation.RoleInfo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cliente implements LeeRed, ActionListener{
	Red r;
	ArrayList<String> songs;
	String ipAct;
	JFrame ventana;
	JList<String> jL;
	ArrayList<String> lista;
	JButton play;
	DefaultListModel<String> listModel;
	String nombreCancion;
	public Cliente(){
		
		r = new Red(this);
		lista = listaArchivos();
		r.escribeRed(lista);
	}
	
	public void agregarFaltantes() {
		if(listModel == null){
			listModel = new DefaultListModel<String>();
		}
		if(songs!=null){
		for(int i = 0 ; i < songs.size(); i++){
			if(listModel.contains(songs.get(i))){
				continue;
			}
			listModel.addElement(songs.get(i));
		}}
	}

	public void crearVentana(){
		if(ventana == null){	
			agregarFaltantes();
			jL = new JList<String>(listModel);
			ventana = new JFrame();
			ventana.setLayout(new FlowLayout());
			ventana.setSize(500,500);
			ventana.setVisible(true);
			ventana.add(jL);
			play = new JButton("play");
			play.addActionListener(this);
			ventana.add(play);
		}
		else{
			agregarFaltantes();
		}
	}

	public void actionPerformed(ActionEvent e){
		JButton temp = (JButton) e.getSource();
		if(temp == play){
			nombreCancion = jL.getSelectedValue();
			//System.out.println(nombreCancion);
			r.escribeRed(nombreCancion);
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
			crearVentana();
		}
		else if(obj instanceof String){
			String nombreCancion = (String) obj;
			crearCliente(6000, nombreCancion);
			
		}	
		else if(obj instanceof Boolean){
			crearServidor(6000);
		}
	}

	public void crearServidor(int PORT){
		new ServerCancion(6000);
	}

	public void crearCliente(int PORT, String Nombre){
		new ClienteCancion(6000, Nombre);
	}

	public static void main(String[] args) {
		new Cliente();
	}

}
