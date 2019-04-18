import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InterfazLogin implements ActionListener{
	JFrame log;
	JFrame reg;
	JPanel campos;
	JPanel redes;
	JPanel pregunta;
	ImageIcon logo;
	JTextField user;
	JTextField pass;
	JButton loginB;
	JButton facebook;
	JButton google;
	JLabel ad;
	JLabel register;

	public InterfazLogin(){
		log = new JFrame("SpotyMiniMegaUltraNapster");
		reg = new JFrame("SpotyMiniMegaUltraNapster");
		user = new JTextField("User");
		pass = new JTextField("Password");
		campos = new JPanel();
		JPanel tmp = new JPanel(); 
		logo = new ImageIcon("./images/logo.png");
		loginB = new JButton("Login");
		log.setLayout(new GridLayout(4,1,1,50));

		//Creacion de los campos de texto para el login

		campos.setLayout(new GridLayout(3,1,20,20));
		campos.add(user);
		campos.add(pass);
		tmp.setLayout(new BorderLayout());
		tmp.add(loginB,BorderLayout.EAST);
		campos.add(tmp);
			
		log.add(new JLabel(logo));
		log.add(campos);
		
		log.setSize(500,700);
		log.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e){

	}

	public static void main(String[] args){
		new InterfazLogin();
	}

}
