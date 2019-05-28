import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cliente implements LeeRed{
	Red r;
	ArrayList<String> songs;
	String ipAct;
	JFrame ventana;
	JList<String> jL;
	public Cliente(){
		r = new Red(this);
		ArrayList<String> lista = listaArchivos();
		r.escribeRed(lista);
		songs = new ArrayList<String>();
		crearVentana();
	}

	public void crearVentana(){
		ventana = new JFrame();
		ventana.setSize(500,500);
		ventana.setVisible(true);		

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

	public static void main(String[] ards){
		new Cliente();
	}


	public void leeRed(Object obj){
		if(obj instanceof ArrayList){
			songs = (ArrayList<String>)obj;
			System.out.println(songs);// La arraylist song es el que tienes que enseñar en la interfaz
		}
		else if(obj instanceof String){
			ipAct = (String)obj;
		}	
	}

}
