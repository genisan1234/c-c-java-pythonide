package steganography;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
public class steganography extends JFrame
{
   public steganography()
   {
	   super();
	   initComponents();
   }
	//Initialisation
	void initComponents()
	{
		frame=new JFrame("Steganography");
		frame.setSize(600,800);
		frame.setLocation(0, 0);
		frame.setLayout(null);
		//Connection to database
		String driver="com.mysql.cj.jdbc.Driver";
		String url="jdbc:mysql://localhost:3306/steganography";
		String username="sanusaurav8082";
		String password="sanu1234";
		try {
		Class.forName(driver);
		conn=DriverManager.getConnection(url,username,password);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Database unreachable at the moment");
		}
		//instantiating components
		label1=new JLabel("Username");
		label2=new JLabel("Password");
		field1=new JTextField("");
		field2=new JTextField("");
		button1=new JButton("Login");
		button2=new JButton("SignUp");
		//Adding the components to the jframe
		frame.add(label1);
		frame.add(label2);
		frame.add(field1);
		frame.add(field2);
		frame.add(button1);
		frame.add(button2);
		//specifying the size of the components
		label1.setSize(70,20);
		label2.setSize(70,20);
		field1.setSize(200,20);
		field2.setSize(200,20);
		button1.setSize(80,30);
		button2.setSize(80,30);
		//specifying the location of the components
		label1.setLocation(150,150);
		label2.setLocation(150,180);
		field1.setLocation(235,150);
		field2.setLocation(235,180);
		button1.setLocation(355, 215);
		button2.setLocation(265,215);
		//specifying the action listeners for the buttons
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				 if(make_login_request())
				 {
					 ide_interface ide=new ide_interface();
					 frame.dispose();
				 }
			}
		});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				make_register_query();
			}
		});
		frame.setVisible(true);
		
	}
	public static void main(String args[])
	{
		steganography new_stegan=new steganography();
	}
	public void make_register_query()
	{
		try {
		String uname=field1.getText().trim().toString();
		String pwd=field2.getText().trim().toString();
        String query_before="SELECT * FROM users where username=?";
        PreparedStatement st=conn.prepareStatement(query_before);
        st.setString(1,uname);
        ResultSet rs=st.executeQuery();
        if(rs.next())
        {
        	JOptionPane.showMessageDialog(null,"Username or password is taken");
        	return;
        }
        String query="INSERT INTO users(username,password) VALUES(?,?)";
        PreparedStatement statement=conn.prepareStatement(query);
        statement.setString(1,uname);
        statement.setString(2,pwd);
        statement.executeUpdate();
        JOptionPane.showMessageDialog(frame,"User Registration Successful");
		}
		catch(Exception ex)
		{
		  JOptionPane.showMessageDialog(frame,"Service Error");
		}
	}
	public boolean make_login_request()
	{
		String uname=field1.getText().trim().toString();
		String pwd=field2.getText().trim().toString();
		String query="SELECT * FROM users";
		try {
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery(query);
		while(rs.next())
		{
			String m=rs.getString("username");
			String t=rs.getString("password");
			if(m.equals(uname)&&t.equals(pwd))
			{
				JOptionPane.showMessageDialog(frame,"Login Successful");
				return true;
			}
		}
		JOptionPane.showMessageDialog(frame,"REGISTER FIRST");
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(frame,"Service Error");
		}
		return false;
		
	}
	//variable declaration
	JFrame frame;
	JLabel label1;
    JLabel label2;
    JTextField field1;
    JTextField field2;
    JButton button1;
    JButton button2;
    Connection conn;
}
