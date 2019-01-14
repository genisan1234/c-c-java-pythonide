package steganography;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import org.apache.commons.io.FilenameUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
public class ide_interface extends JFrame
{
    //Defining the constructor
	public ide_interface()
	{
		super();
		initComponents();
	}
	public void initComponents()
	{
		frame=new JFrame("ide_interface");
		frame.setLocation(0,0);
		frame.setSize(1100,1000);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Adding the menu bar to the ide_interface
		bar=new JMenuBar();
		bar.setLocation(0,0);
		bar.setSize(1100,30);
		bar.setLayout(null);
		editmanager=new UndoManager();
		button1=new JButton("Compile");
		button2=new JButton("Run");
		button1.setLocation(850,650);
		button1.setSize(100,50);
		button2.setLocation(970,650);
		button2.setSize(100,50);
		button1.setEnabled(true);
		button2.setEnabled(true);
		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					  if(filename==null)
					  {
						  JOptionPane.showMessageDialog(frame,"Save the file first");
						  return;
					  }
					  if(extension.equals("c"))
	                  pb = new ProcessBuilder("cmd.exe", "/C", "gcc " +filename+" -o "+filename_without_extension);
					  else if(extension.equals("cpp"))
					  {
						  pb=new ProcessBuilder("cmd.exe","/C","g++ "+filename+" -o "+filename_without_extension);
					  }
					  else if(extension.equals("py"))
						  return;
					  else if(extension.equals("java"))
					  {
						  pb=new ProcessBuilder("cmd.exe","/C","javac "+filename);
					  }
	                  pb.directory(new File(directory_name));
	                  long a=System.currentTimeMillis();
	                  Process process=pb.start();
	                  DataInputStream dis;
	                  byte[]arr=new byte[1000];
	                  dis=new DataInputStream(process.getErrorStream());
	                  int value=dis.read(arr);
	                  long b=System.currentTimeMillis();
	                  String exe_extension=null;
	                  if(extension.equals("c")||extension.equals("cpp"))
	                  {
	                	  exe_extension="exe";
	                  }
	                  else if (extension.equals("java"))
	                  {
	                	  exe_extension="class";
	                  }
	                  if(value==-1)
	                  {
	                	  field1.setText("");
	                	  field1.setForeground(Color.GREEN);
	                	  field1.append("Output File: "+directory_name+filename_without_extension+"."+exe_extension);
	                	  field1.append("\n"+"Output Time: "+(b-a)+"ms");
	                	  File file=new File(directory_name+filename);
	                	  double c=(double)file.length()/(1024*1024);
	                	  field1.append("\n"+"File Size: "+c+"mb");
	                	  
	                  }
	                  else
	                  {
	                   field1.setForeground(Color.RED);
	                  while(value!=-1)
	                  {
	                	 field1.append(new String(arr));
	                	 value=dis.read(arr);
	                  }
	                  field1.append("\n");
	                  }
				}
				catch(Exception ex)
				{
					System.out.println(ex);
				}
			}
		});
		button1.setToolTipText("Don't compile for .py files");
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{   
					  if(filename==null)
					  {
						  JOptionPane.showMessageDialog(frame,"Save the file first:)");
						  return;
					  }
					  String exe_extension=null;
					  if(extension.equals("c")||extension.equals("cpp"))
					  {
						  exe_extension="exe";
					  }
					  if(extension.equals("java"))
					  {
						  exe_extension="class";
					  }
					  if(extension.equals("py"))
					  {
						  exe_extension="py";
					  }
					  File file1=new File(filename_without_extension+"."+exe_extension);
					  if(!file1.exists())
					  {
						  if(!extension.equals("java"))
						  JOptionPane.showMessageDialog(frame,"compile the file first!!!");
						  else
						  {
							  JOptionPane.showMessageDialog(frame,"The class file with the file name was not found!!!");
						  }
						  return;
					  }
					  
					  if(extension.equals("c")||extension.equals("cpp"))
	                  pb = new ProcessBuilder("./"+filename_without_extension);
					  else if(extension.equals("java"))
					  {
						  pb=new ProcessBuilder("cmd.exe","/C","java ",filename_without_extension);
					  }
					  else if(extension.equals("py"))
					  {
						  pb=new ProcessBuilder("cmd.exe", "/C", "python " +filename);
					  }
					  long a=System.currentTimeMillis();
	                  pb.directory(new File(directory_name));
	                  Process p=pb.start();
	                  long b=System.currentTimeMillis();
	                  
	                  DataInputStream dis=new DataInputStream(p.getInputStream());
	                  byte[]arr=new byte[1000];
	                  while((dis.read(arr))!=-1)
	                  {
	                	  field2.append(new String(arr));
	                  }
	                  dis=new DataInputStream(p.getErrorStream());
	                  int value=dis.read(arr);
	                  if(value==-1&&extension.equals("py"))
	                  {
	                	  field1.setText("");
	                	  field1.setForeground(Color.GREEN);
	                	  field1.append("Output Time: "+(b-a)+"ms");
	                	  File file=new File(directory_name+filename);
	                	  double c=(double)file.length()/(1024*1024);
	                	  field1.append("\n"+"File Size: "+c+"mb");
	                  }
	                  else if(extension.equals("py"))
	                  {
	                	  field1.setForeground(Color.red);
	                  while(value!=-1)
	                  {
	                	  
	                	  field1.append(new String(arr));
	                	  value=dis.read(arr);
	                  }
	                  }
	                  
				}
				catch(Exception ex)
				{
					
				}
			}
		});
	
		frame.add(button1);
		frame.add(button2);
		field1=new JTextArea("");
		field2=new JTextArea("");
		field1.setEditable(false);
		field2.setEditable(false);
		scroll1=new JScrollPane(field1);
		scroll1.setSize(350,300);
		scroll1.setLocation(0,650);
		scroll2=new JScrollPane(field2);
		scroll2.setSize(350,300);
		scroll2.setLocation(370,650);
		field2.setSize(scroll2.getSize());
		field1.setSize(scroll1.getSize());
		field2.setForeground(Color.BLUE);
		frame.add(scroll1);
		frame.add(scroll2);
		filemenu=new JMenu("File");
		filemenu.setSize(50,30);
		filemenu.setLocation(0,0);
		editmenu=new JMenu("Edit");
		editmenu.setSize(50,30);
		editmenu.setLocation(52,0);
		openfile=new JMenuItem("Open");
		openfile.setAccelerator(KeyStroke.getKeyStroke('O',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openfile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				open_file();
			}
		});
		saveonlyfile=new JMenuItem("Save");
		newfile=new JMenuItem("New");
		saveonlyfile.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		newfile.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		filemenu.add(newfile);
		filemenu.add(saveonlyfile);
		newfile.addActionListener(new ActionListener()
				{
			        public void actionPerformed(ActionEvent e)
			        {
			        	new_file();
			        }
				});
		saveonlyfile.addActionListener(new ActionListener()
				{
			          public void actionPerformed(ActionEvent e)
			          {
			        	  save_file();
			          }
				});
				
		filemenu.add(openfile);
		savefile=new JMenuItem("Save As");
		savefile.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		savefile.addActionListener(new ActionListener()
			{
			  public void actionPerformed(ActionEvent e)
			  {
	            save_as_file();
			  }
			});
		undo=new JMenuItem("Undo");
		undo.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		undo.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
           if(editmanager.canUndo())
           {
        	   editmanager.undo();
           }
		  }
		});
		redo=new JMenuItem("Redo");
		redo.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		redo.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
           if(editmanager.canRedo())
           {
        	   editmanager.redo();
           }
		  }
		});
		undo=new JMenuItem("Undo");
		undo.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
           if(editmanager.canUndo())
           {
        	   editmanager.undo();
           }
		  }
		});
		redo=new JMenuItem("Redo");
		redo.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
           if(editmanager.canRedo())
           {
        	   editmanager.redo();
           }
		  }
		});
		undo1=new JMenuItem("Undo");
		undo1.setAccelerator(KeyStroke.getKeyStroke('H', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		undo1.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
           if(editmanager.canUndo())
           {
        	   editmanager.undo();
           }
		  }
		});
		redo1=new JMenuItem("Redo");
		redo1.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		redo1.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
           if(editmanager.canRedo())
           {
        	   editmanager.redo();
           }
		  }
		});
		editmenu.add(undo1);
		editmenu.add(redo1);
		filemenu.add(savefile);
		bar.add(filemenu);
		bar.add(editmenu);
		frame.add(bar);
		area=new RSyntaxTextArea();
		scroll3=new JScrollPane(area);
		scroll3.setSize(1050,600);
		scroll3.setLocation(0,40);
		area.setSize(scroll3.getSize());
		area.getDocument().addUndoableEditListener(new UndoableEditListener()
		{
	    public void undoableEditHappened(UndoableEditEvent e)
	    {
	    	editmanager.addEdit(e.getEdit());
	    }
		});
		
        area.addMouseListener(new MouseAdapter() { 
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (editmanager.canRedo()) {
                        redo.setEnabled(true);
                    } else {
                        redo.setEnabled(false);
                    }
                    if (editmanager.canUndo()) {
                        undo.setEnabled(true);
                    } else {
                        undo.setEnabled(false);
                    }
                    JPopupMenu popup = new JPopupMenu();
                    popup.add(undo);
                    popup.add(redo);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
		frame.add(scroll3);
		frame.setVisible(true);
	}
	  public void open_file()
	  {
	    	if(filename!=null)
	    	{
	    		int dialogresult=JOptionPane.showConfirmDialog(frame,"Do you want to make changes to the file before closing?","Warning",JOptionPane.YES_NO_OPTION);
	    		if(dialogresult==JOptionPane.YES_OPTION)
	    		{
	    			save_file();
	    		}
	    		
	    	}
		  dialog=new FileDialog(frame,"open-file-interface");
		  dialog.setVisible(true);
		  directory_name=dialog.getDirectory();
		  filename=dialog.getFile();
		  filename_without_extension= FilenameUtils.removeExtension(filename);
		  extension=FilenameUtils.getExtension(directory_name+filename);
		  area.setText("");
		  set_editing_style();
		 
		  try 
		  {
			  FileInputStream fis=new FileInputStream(new File(directory_name+filename));
			  byte[] arr=new byte[1000];
			  int value;
			  while((value=fis.read(arr))!=-1)
			  {
				 area.append(new String(arr,0,value));
			  }
			  
			  
		  }
		  catch(Exception ex)
		  {
			  
		  }
		  
	  }
	   public void set_editing_style()
	   {
		   if(extension.equals("c"))
			   area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
		   else if(extension.equals("cpp"))
		   {
			   area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
		   }
		   else if(extension.equals("java"))
		   {
			   area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		   }
		   else if(extension.equals("py"))
		   {
			   area.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
		   }
	   }
	   public void save_file()
	   {
		   File file=null;
		   if(filename==null)
		   {
		   dialog =new FileDialog(frame,"ide-savefile-manager");
		   dialog.setVisible(true);
		   directory_name=dialog.getDirectory();
		   filename=dialog.getFile();
		   filename_without_extension= FilenameUtils.removeExtension(filename);
		   }
		   file=new File(directory_name+filename);
		   try 
		   {
			   FileOutputStream fos=new FileOutputStream(file);
			   String m=area.getText().toString().trim();
			   fos.write(m.getBytes());
		   }
		   catch(IOException ex)
		   {
		   }
		   extension=FilenameUtils.getExtension(directory_name+filename);
	       set_editing_style();	
	   }
	    public void new_file()
	    {
	    	if(filename!=null)
	    	{
	    		int dialogresult=JOptionPane.showConfirmDialog(frame,"Do you want to make changes to the file before closing?","Warning",JOptionPane.YES_NO_OPTION);
	    		if(dialogresult==JOptionPane.YES_OPTION)
	    		{
	    			save_file();
	    		}
	    		
	    	}
	    	area.setText("");
	    	extension=null;
	    	filename=null;
	    	directory_name=null;
	    	filename_without_extension=null;
	    }
	    public void save_as_file()
	    {
			   dialog =new FileDialog(frame,"ide-savefile-manager");
			   dialog.setVisible(true);
			   directory_name=dialog.getDirectory();
			   filename=dialog.getFile();
			   File file=new File(directory_name+filename);
			   filename_without_extension= FilenameUtils.removeExtension(filename);
			   try 
			   {
				   FileOutputStream fos=new FileOutputStream(file);
				   String m=area.getText().toString().trim();
				   fos.write(m.getBytes());
			   }
			   catch(IOException ex)
			   {
			   }
			   extension=FilenameUtils.getExtension(directory_name+filename);
		       set_editing_style();	
	    }
	//variable declaration
	JFrame frame;
	JMenu filemenu,editmenu;
	JMenuBar bar;
	JMenuItem savefile,openfile,newfile,saveonlyfile,redo,undo,undo1,redo1;
	RSyntaxTextArea area;
	FileDialog dialog;
	JButton button1,button2;
	ProcessBuilder pb;
	String filename;
	String extension;
	String directory_name;
	String filename_without_extension;
	JTextArea field1;
	JTextArea field2;
	JScrollPane scroll1;
	JScrollPane scroll2;
	JScrollPane scroll3;
	UndoManager editmanager;
	
}
