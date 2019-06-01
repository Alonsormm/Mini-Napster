//En mysql: SET GLOBAL time_zone = '-3:00';
//Compilar: javac -cp .:beanutils.jar:commons-logging.jar *.java
//Ejecutar: java -cp .:mysqlcon.jar:beanutils.jar:commons-logging.jar:mysql-connector-java-8.0.15.jar Cliente
/*
Antes de probarlo:
-Modifica el usuario y la contrasena en caso de ser necesario
-Establece la zona horaria de mysql con el comando de la linea 1
-Debe de existir una base de datos llamada Napster, asi como la tabla con el campo de usuario y clave:
Create database Napster;
use Napster;
create table usuario(nombre varchar(25) not null,clave varchar(25) not null);
 */
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
class Login extends JFrame implements ActionListener{
    String dbName= "Napster"; //base de datos nombre
	String url= "jdbc:mysql://localhost:3306/"+dbName;
    Connection conex;
    JLabel lab1,lab2;
    JButton login,regis;
    JTextField user;
    JPasswordField pass;
    boolean loginyes;
    public Login(){
        try{         
			Class.forName("com.mysql.cj.jdbc.Driver"); 		
			conex=DriverManager.getConnection(url,"JBDC","root"); //usuario,contrasena
			System.out.println("Se ha conectado a la base de datos");
		}
		catch(Exception ex){
			System.out.println(ex); 
			System.exit(0); 
		}

        setLayout(null);
        lab1= new JLabel("User: ");
        lab2= new JLabel("Password: ");
        login= new JButton("Login");
        regis= new JButton("Registry");
        user= new JTextField(25);
        pass= new JPasswordField(25);
        loginyes = false;
        lab1.setBounds(80,10,90,30); add(lab1);
        lab2.setBounds(50,50,90,30); add(lab2);
        login.setBounds(50,100,90,30); add(login);
        regis.setBounds(150,100,90,30); add(regis);
        user.setBounds(120,10,120,30); add(user);
        pass.setBounds(120,50,120,30); add(pass);
        login.addActionListener(this);
        regis.addActionListener(this);
        setBounds(800,400,300,200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==login){
            String userS = user.getText();
            //String userS = "Alonsormm";
            //String passS = "Alonsormm2";
            String passS = pass.getText();
            try{
                Statement statement=conex.createStatement();
                ResultSet result= statement.executeQuery("select * from usuario where nombre= '"+userS+"' and clave= '"+passS+"'");
                if(result.next()){
                    JOptionPane.showMessageDialog(null,"Bienvenido "+user.getText());
                    new Cliente();
                    setVisible(false);
                    return;
                }
                else{ 
                    JOptionPane.showMessageDialog(null,"Usuario/clave incorrecto","Error",JOptionPane.WARNING_MESSAGE);
                }        
            }
            catch(Exception ex) { System.out.println("Error en el query"); }            
        }
        if(e.getSource()==regis){
            try{
                Statement statement= conex.createStatement();
                String usuario,clave;
                while(true){
                    usuario=  JOptionPane.showInputDialog("Ingresa algun nombre");
                    ResultSet result= statement.executeQuery("select nombre from usuario where nombre= '"+usuario+"'");
                    if(usuario.length()<1) 
                        JOptionPane.showMessageDialog(null,"Ingrese un usuario valido");
                    else if(result.next()) 
                        JOptionPane.showMessageDialog(null,"El usuario ya existe","Error",JOptionPane.WARNING_MESSAGE);
                    else break;
                }
                while(true){
                    clave= JOptionPane.showInputDialog("Ingresa alguna clave");
                    if(clave.length()<5) 
                        JOptionPane.showMessageDialog(null,"Ingrese una clave valida","Error",JOptionPane.WARNING_MESSAGE);
                    else break;
                }
                statement.executeUpdate("insert into usuario values('"+usuario+"','"+clave+"')");
                JOptionPane.showMessageDialog(null,"Usuario creado exitosamente");
            }
            catch(Exception ex) { System.out.println("Error en el query"); }
        }
    }
    public static void main(String[] args) {
        new Login();
    }

}