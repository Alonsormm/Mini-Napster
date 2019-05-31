import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cliente implements LeeRed, ActionListener {
	Red r;
	ArrayList<String> songs;
	String ipAct;
	JFrame ventana;
	JList<Object> jL;
	ArrayList<String> lista;
	JButton play;
	public Cliente(){
		try{
			new Login();
		}catch(Exception e){
			System.exit(0);
		}
		r = new Red(this);
		lista = listaArchivos();
		r.escribeRed(lista);
	}
	

	public void crearVentana(){
		
		//System.out.println(lista.toArray());
		jL = new JList<Object>(lista.toArray());
		ventana = new JFrame();
		ventana.setLayout(new FlowLayout());
		ventana.setSize(500,500);
		ventana.setVisible(true);
		ventana.add(jL);
		play = new JButton("play");
		play.addActionListener(this);
		ventana.add(play);
	}

	public void actionPerformed(ActionEvent e){
		JButton temp = (JButton) e.getSource();
		if(temp == play){
			System.out.println(jL.getSelectedValue());
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

	public static void main(String[] args){
		new Cliente();
	}


	public void leeRed(Object obj){
		if(obj instanceof ArrayList){
			songs = (ArrayList<String>)obj;
			//System.out.println(songs);// La arraylist song es el que tienes que enseñar en la interfaz
			crearVentana();
			
		}
		else if(obj instanceof String){
			ipAct = (String)obj;
		}	
	}

}
