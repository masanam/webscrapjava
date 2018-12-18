/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googlesearch16;

/**
 *
 * @author MasanaM
 */
import java.util.List;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.util.Calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.parser.ParseException;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class main extends JFrame  {

	private JPanel contentPane;
	private JTextField textField;
	private JList<String> list = new JList();
	private List<String> title = new LinkedList<String>();
	private List<String> links = new LinkedList<String>();
        private List<String> desc = new LinkedList<String>();
       	private String browser[] = {"Firefox","Chrome","Safari","Opera","Torch"};
	private String item[] = {"web","image","video","news"};
	private String page[] = {"1","2","3","4","5","6","7","8","9","10"};
        private JRadioButton all;
        private JRadioButton images;
        private JRadioButton news;
        private JRadioButton videos;
        private ButtonGroup group;
	private int tag;
        private Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        	/**
	 * Launch the application.
	 */
	public static void main(String[] args)throws SQLException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
                                        frame.setSize(700,500);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() throws IOException {
            
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("google.com", 80));
            InetAddress ipadress = socket.getLocalAddress();

DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
String tanggal = LocalDate.now().toString();
//date tanggal = dateFormat.format(date));

		DefaultListModel<String> model = new DefaultListModel<>();
		list  = new JList(model);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Keyword");
		lblNewLabel.setBounds(48, 18, 100, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(110, 15, 325, 21);
		contentPane.add(textField);
		textField.setColumns(20);
		
		JComboBox comboBox = new JComboBox(item);
		comboBox.setBounds(300, 35, 70, 21);
		
            all = new JRadioButton("all");all.setSelected(true);
            all.setActionCommand("web");
            images = new JRadioButton("images");
            images.setActionCommand("images");
            videos = new JRadioButton("videos");
            videos.setActionCommand("videos");
            news = new JRadioButton("news");
            news.setActionCommand("news");

            //Group the radio buttons.
            group = new ButtonGroup();
            group.add(all);
            group.add(images);
            group.add(videos);
            group.add(news);

            all.setBounds(100,35,50,41);    
            images.setBounds(150,35,70,41);    
            videos.setBounds(230,35,70,41);    
            news.setBounds(300,35,70,41);    
            contentPane.add(all);
            contentPane.add(images);
            contentPane.add(videos);
            contentPane.add(news);

		JComboBox comboBox_1 = new JComboBox(page);
		comboBox_1.setBounds(380, 45, 53, 21);
		contentPane.add(comboBox_1);
                
                JComboBox comboBox_2 = new JComboBox(browser);
		comboBox_2.setBounds(455, 45, 153, 21);
		contentPane.add(comboBox_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 90, 600, 320);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("search");
                btnNewButton.setBounds(452, 14, 87, 23);
		contentPane.add(btnNewButton);
                
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			Google url = new Google();
			String query1 = textField.getText();
                        String query = query1.replace(' ', '-');
               	 	String item = (String)group.getSelection().getActionCommand();
                        String browser = (String)comboBox_2.getSelectedItem();
                
                	model.removeAllElements();
			int pen = 0;
                
                	title.clear();
                	links.clear();
                        desc.clear();
                	Connection connDB = null;
				
              try {
                  Class.forName("org.apache.derby.jdbc.ClientDriver");
                       
                    connDB = DriverManager.getConnection("jdbc:derby://localhost/googlese;user=APP;pass=APP");

                    PreparedStatement ps;
                    Statement state = connDB.createStatement();

                    if(item.equals("web"))
                    {
                	state.execute("SELECT * FROM page where keyword = '"+ query +"'");
                        ResultSet rs = state.getResultSet();
                        System.out.println(pen);
                        while(rs.next())
                        	pen++;

                    }
                    else
                    {
                    	state.execute("SELECT * FROM image where keyword = '"+ query +"'");
                        ResultSet rs = state.getResultSet();
                        
                        while(rs.next())
                        	pen++;
                    }
  
                    if(pen == 0)
                    {
switch (item) {
 
case "web":
         url.search("https://www.google.com/search?hl=en&q=" + query + "&num=100", query, browser);
                             title.addAll(url.getTitle());
                             links.addAll(url.getLinks());  
                             desc.addAll(url.getDesc());  


                             tag = url.getLinks().size()/10;
                             
                             for(int i = 0;i < 10;i++)
                             {
                            	 model.addElement(title.get(i));
                            	 model.addElement(links.get(i));
                                 model.addElement(desc.get(i));
                             }

                             for(int i= 0;i < links.size();i++)
                             {
                            	 System.out.println(title.get(i));
                            	 System.out.println(links.get(i));
                            	 System.out.println(desc.get(i));
                                 
                             }
                             for(int i= 0;i < links.size();i++)
                             {
        String q = "INSERT INTO APP.SE_SEARCH_LIST (ID, UNIQUEID, USER_IP, DATE_TIME, KEYWORD, SEARCH_TYPE, TITLE, URL, SPINNER, IMAGE, VIDEO) VALUES ("+i+", '"+i+"', '"+ipadress+"', '"+tanggal+"', '"+query+"', '"+item+"', '"+ title.get(i) +"', '"+ links.get(i) +"', '"+ desc.get(i) +"', '"+ links.get(i) +"', '"+ links.get(i) +"')";
                                 state.addBatch(q);
                             }                           
                            state.executeBatch();
                            state.close();
                            connDB.close();
        break;    
  case "news":      
       url.news("https://www.google.com/search?hl=en&tbm=nws&num=100&q=" + query, query, browser);
                             title.addAll(url.getTitle());
                             links.addAll(url.getLinks());  
                             desc.addAll(url.getDesc());  


                             tag = url.getLinks().size()/10;
                             
                             for(int i = 0;i < 10;i++)
                             {
                                 model.addElement(title.get(i));
                            	 model.addElement(links.get(i));
                                 model.addElement(desc.get(i));
                             }

                             for(int i= 0;i < links.size();i++)
                             {
                            	 System.out.println(title.get(i));
                            	 System.out.println(links.get(i));
                            	 System.out.println(desc.get(i));                                
                             }
                             
                             for(int i= 0;i < links.size();i++)
                             {
        String q = "INSERT INTO APP.SE_SEARCH_LIST (ID, UNIQUEID, USER_IP, DATE_TIME, KEYWORD, SEARCH_TYPE, TITLE, URL, SPINNER, IMAGE, VIDEO) VALUES ("+i+", '"+i+"', '"+ipadress+"', '"+tanggal+"', '"+query+"', '"+item+"', '"+ title.get(i) +"', '"+ links.get(i) +"', '"+ desc.get(i) +"', '"+ links.get(i) +"', '"+ links.get(i) +"')";
                                state.addBatch(q);
                             }
                            
                            connDB.commit();
                            state.close();
                            connDB.close();
        break;              
  case "images": 
        url.img("https://www.google.com/search?tbm=isch&q=" + query, query, browser);

                             //title.addAll(url.getTitle());
                             links.addAll(url.getLinks());  
                             //desc.addAll(url.getDesc());  

                             tag = url.getLinks().size()/10;
                             
                             for(int i = 0;i < 10;i++)
                             {
                            	 //model.addElement(title.get(i));
                            	 model.addElement(links.get(i));
                                 //model.addElement(desc.get(i));
                                 
                             }

                             for(int i= 0;i < links.size();i++)
                             {
                            	 //System.out.print(title.get(i));
                            	 System.out.println(links.get(i));
                            	 // System.out.println(desc.get(i));
                              }
                             
                             for(int i= 0;i < links.size();i++)
                             {

        String q = "INSERT INTO APP.SE_SEARCH_LIST (ID, UNIQUEID, USER_IP, DATE_TIME, KEYWORD, SEARCH_TYPE, TITLE, URL, SPINNER, IMAGE, VIDEO) VALUES ("+i+", '"+i+"', '"+ipadress+"', '"+tanggal+"', '"+query+"', '"+item+"', '"+ title.get(i) +"', '"+ links.get(i) +"', '"+ desc.get(i) +"', '"+ links.get(i) +"', '"+ links.get(i) +"')";
                                 state.addBatch(q);
                             }
                            state.executeBatch();
                            state.close();
                            connDB.close();
        break;     
  case "videos":        
             url.search("https://www.google.com/search?q="+query+"&num=100&source=lnms&tbm=vid", query, browser);
                             title.addAll(url.getTitle());
                             links.addAll(url.getLinks());  
                             desc.addAll(url.getDesc());  


                             tag = url.getLinks().size()/10;
                             
                             for(int i = 0;i < 10;i++)
                             {
                            	 model.addElement(title.get(i));
                            	 model.addElement(links.get(i));
                                 model.addElement(desc.get(i));
                             }

                             for(int i= 0;i < links.size();i++)
                             {
                            	 System.out.print(title.get(i));
                            	 System.out.println(links.get(i));
                            	 System.out.println(desc.get(i));
                                 
                             }

                             for(int i= 0;i < links.size();i++)
                             {
        String q = "INSERT INTO APP.SE_SEARCH_LIST (ID, UNIQUEID, USER_IP, DATE_TIME, KEYWORD, SEARCH_TYPE, TITLE, URL, SPINNER, IMAGE, VIDEO) VALUES ("+i+", '"+i+"', '"+ipadress+"', '"+tanggal+"', '"+query+"', '"+item+"', '"+ title.get(i) +"', '"+ links.get(i) +"', '"+ desc.get(i) +"', '"+ links.get(i) +"', '"+ links.get(i) +"')";
                                 state.addBatch(q);
                             }
                            state.executeBatch();
                            state.close();
                            connDB.close();
        break;
 
  default:
             url.search("https://www.google.com/search?hl=en?num=100&q=" + query, query, browser);
                             title.addAll(url.getTitle());
                             links.addAll(url.getLinks());  
                             desc.addAll(url.getDesc());  


                             tag = url.getLinks().size()/10;
                             
                             for(int i = 0;i < 10;i++)
                             {
                            	 model.addElement(title.get(i));
                            	 model.addElement(links.get(i));
                                 model.addElement(desc.get(i));
                             }

                             for(int i= 0;i < links.size();i++)
                             {
                            	 System.out.print(title.get(i));
                            	 System.out.println(links.get(i));
                            	 System.out.println(desc.get(i));
                                 
                             }

                             for(int i= 0;i < links.size();i++)
                             {

        String q = "INSERT INTO APP.SE_SEARCH_LIST (ID, UNIQUEID, USER_IP, KEYWORD, SEARCH_TYPE, TITLE, URL, SPINNER, IMAGE, VIDEO) VALUES ("+i+", '"+i+"', '127.0.0.0', '"+query+"', '"+item+"', '"+ title.get(i) +"', '"+ links.get(i) +"', '"+ desc.get(i) +"', '"+ links.get(i) +"', '"+ links.get(i) +"')";
                                 state.addBatch(q);
                             }
                            state.executeBatch();
                            state.close();
                            connDB.close();
        break;
}

                    	 
                    }
                    else
                    {
                    	if(item.equals("web"))
                    	{
                    	    state.execute("SELECT * FROM APP.SE_SEARCH_LIST where keyword = '"+ query +"'");
                            ResultSet res = state.getResultSet();
                        	 
                        	while(res.next())
                            {
                        		title.add(res.getString("title"));
                                        links.add(res.getString("web"));
                                        desc.add(res.getString("desc"));
                                        
                            }
                        	
                        	
                        	for(String link:links)
                        	{
                        		System.out.println(link);
                        	}
                        	
                        	
                        	for(int i = 0;i < 10;i++)
                            {
                        		model.addElement(title.get(i));
                            	        model.addElement(links.get(i));
                            	        model.addElement(desc.get(i));
                                        
                            }
                    	}
                    	else
                    	{
                    		state.execute("SELECT * FROM APP.SE_SEARCH_LIST where keyword = '"+ query +"'");
                            ResultSet res = state.getResultSet();
                        	 
                        	while(res.next())
                            {
                            	links.add(res.getString("web"));
                            }
                        	
                        	
                        	for(int i = 0;i < 10;i++)
                            {
                            	model.addElement(links.get(i));
                            }
                    	}

                    }
  
                    state.close();
                    connDB.close();
                }catch(ClassNotFoundException e)
                {
                    System.out.println("Driver loading failed!");
                }catch(SQLException e)
                {
                    System.out.println(e.getNextException());
                }           
              catch (ParseException ex) {  
                                ex.printStackTrace();
                            }
                
                list.setBounds(48, 113, 502, 255);
        	contentPane.add(list);
                list.setVisible(true);
		scrollPane.setViewportView(list);
			}

                    
		});
		

		
		
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String page = (String)comboBox_1.getSelectedItem();
				String item = (String)comboBox.getSelectedItem();
				int j;
				
				model.removeAllElements();
				
				
				if(page.equals("1"))
				{
					if(tag < 1)
						j = links.size();
					else
						j = 10;
					
					if(item.equals("web"))
					{
						for(int i = 0;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 0;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
else if(page.equals("2"))
				{
					if(tag < 2)
						j = links.size();
					else
						j = 20;
					
					if(item.equals("web"))
					{
						for(int i = 10;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 10;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
else if(page.equals("3"))
				{
					if(tag < 3)
						j = links.size();
					else
						j = 30;
					
					if(item.equals("web"))
					{
						for(int i = 20;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 20;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
else if(page.equals("4"))
				{
					if(tag < 4)
						j = links.size();
					else
						j = 40;
					
					if(item.equals("web"))
					{
						for(int i = 30;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 30;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
else if(page.equals("5"))
				{
					if(tag < 5)
						j = links.size();
					else
						j = 50;
					
					if(item.equals("web"))
					{
						for(int i = 40;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 40;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
else if(page.equals("6"))
				{
					if(tag < 6)
						j = links.size();
					else
						j = 60;
					
					if(item.equals("web"))
					{
						for(int i = 50;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 50;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
else if(page.equals("7"))
				{
					if(tag < 7)
						j = links.size();
					else
						j = 70;
					
					if(item.equals("web"))
					{
						for(int i = 60;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 60;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
else if(page.equals("8"))
				{
					if(tag < 8)
						j = links.size();
					else
						j = 80;
					
					if(item.equals("web"))
					{
						for(int i = 70;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 70;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
else if(page.equals("9"))
				{
					if(tag < 9)
						j = links.size();
					else
						j = 90;
					
					if(item.equals("web"))
					{
						for(int i = 80;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 80;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
else
				{
					if(tag < 10)
						j = links.size();
					else
						j = 100;
					
					if(item.equals("web"))
					{
						for(int i = 90;i < j;i++)
						{
							model.addElement(title.get(i));
							model.addElement(links.get(i));
							model.addElement(desc.get(i));
                                                        
						}
					}
					else
					{
						for(int i = 90;i < j;i++)
						{
							model.addElement(links.get(i));
						}
					}
				}
                                
					
			}
		});
		
		
	}
        


}
