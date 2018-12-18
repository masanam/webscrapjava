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
import java.io.*;
import java.net.URL;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Googleleg 
{
                private static final Pattern META_DIV_URL_PATTERN = Pattern.compile(".*\"ou\":\"(.+?)[?\"].*");
        	private static String IMAGE_DESTINATION_FOLDER = "C:/images";
		private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36";
		private List<String> title = new LinkedList<String>();
		private List<String> links = new LinkedList<String>();
                private List<String> desc = new LinkedList<String>();
                
		
		private Document htmlDocument;
		public List<String> getTitle() {return title;}
		public List<String> getLinks() {return links;}
                public List<String> getDesc() {return desc;}
                
		
		public boolean crawl(String url) // Give it a URL and it makes an HTTP request for a web page
		{
			try{
				Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
				
				Document htmlDocument = connection.get();
				
				this.htmlDocument = htmlDocument;
				
				if(connection.response().statusCode() == 200)
				{
					System.out.println("\nVisiting Received web page at " + url);
				}

				if(!connection.response().contentType().contains("text/html"))
				{
					System.out.println("Failure Retrieved something other than HTML");
					return false;
				}
				
                                String newLine = System.getProperty("line.separator");
				Elements linksOnPage = htmlDocument.select("h3.r > a");
				//Elements linksOnPage = htmlDocument.select("div.g");
	                       Elements bodies = htmlDocument.select("span[class=st]");
	                       // Elements bodies = htmlDocument.select("div[class=st]");
                                //Elements linksOnPage = htmlDocument.select("div.rg_meta");
				System.out.println("Find(" + linksOnPage.size() + ") links");
	
				for(Element link: linksOnPage)
				{
                                        this.title.add(link.text());
					this.links.add(link.absUrl("href"));
                                        for (int j=0;j<bodies.size();j++) {
                                            this.desc.add(bodies.get(j).text());
                                        }   
				}
                                
		for(int i= 0;i < links.size();i++)
                {
               	 System.out.println(title.get(i));
               	 System.out.println(links.get(i));
                 System.out.println(desc.get(i));
                 
                 
                }
				
				return true;
			}
			catch(IOException ioe){
				return false;
			}
		}

                public boolean crawlNews(String url) // Give it a URL and it makes an HTTP request for a web page
		{
			try{
				Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
				
				Document htmlDocument = connection.get();
				
				this.htmlDocument = htmlDocument;
				
				if(connection.response().statusCode() == 200)
				{
					System.out.println("\nVisiting Received web page at " + url);
				}

				if(!connection.response().contentType().contains("text/html"))
				{
					System.out.println("Failure Retrieved something other than HTML");
					return false;
				}
				
				Elements linksOnPage = htmlDocument.select("h3.r > a");
		                Elements bodies = htmlDocument.select("div[class=st]");                                
                                Elements linksImage = htmlDocument.select("div.rg_meta");
                               
				System.out.println("Find(" + linksOnPage.size() + ") links");
	
				for(Element link: linksOnPage)
				{
                                        this.title.add(link.text());
					this.links.add(link.absUrl("href"));
                                        for (int j=0;j<bodies.size();j++) {
                                            this.desc.add(bodies.get(j).text());
                                        }   
                                    
				}
                                
							
		for(int i= 0;i < links.size();i++)
                {
               	 System.out.println(title.get(i));
               	 System.out.println(links.get(i));
                 System.out.println(desc.get(i));      
                }
				
				return true;
			}
			catch(IOException ioe){
				return false;
			}
		}
                
		public boolean searchForWord(String searchWord)  // Tries to find a word on the page
		{
			if(htmlDocument == null)
			{
				System.out.println("Error!! Empty page");
				
				return false;
				
			}
			
			System.out.println("Searching for the word'" + searchWord + "' ....");
			
			String bodyText = htmlDocument.body().text();
			
			return bodyText.toLowerCase().contains(searchWord.toLowerCase());
		}
		
		
		public boolean crawlimg(String url) throws ParseException
		{
			try{
				Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
				
				Document htmlDocument = connection.get();
				
				this.htmlDocument = htmlDocument;
				
				if(connection.response().statusCode() == 200)
				{
					System.out.println("\nVisiting Received Image web page at " + url);
				}
				
				
				if(!connection.response().contentType().contains("text/html"))
				{
					System.out.println("Failure Retrieved something other than HTML");
					return false;
				}
				
		
                                Elements linksOnPage = htmlDocument.select("div.rg_meta");
                                JSONObject jsonObject;
                                
				for(Element link: linksOnPage)
				{
                                       jsonObject = (JSONObject) new JSONParser().parse(link.childNode(0).toString());
                                       this.links.add((String) jsonObject.get("ou"));
                                       String strImageURL = (String) jsonObject.get("ou");
                                       if (isValidURL(strImageURL)){
                                            downloadImage(strImageURL);
                                        }
				}
		
				return true;
			}
			catch(IOException ioe){
				return false;
			}
		}

public boolean isValidURL(String urlStr) {
    try {
      URL url = new URL(urlStr);
      return true;
    }
    catch (MalformedURLException e) {
        return false;
    }
}

private static void downloadImage(String strImageURL){
        
        //get file name from image path
                String strImageName = 
                strImageURL.substring( strImageURL.lastIndexOf("/")+1);
                
        System.out.println("Saving: " + strImageName + ", from: " + strImageURL);
        
        try {
             //open the stream from URL
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();
            
            byte[] buffer = new byte[4096];
            int n = -1;
            
            OutputStream os = 
                new FileOutputStream( IMAGE_DESTINATION_FOLDER + "/" + strImageName );
            
            //write bytes to the output stream
            while ( (n = in.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }
            
            //close the stream
            os.close();
            
            System.out.println("Image saved");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
