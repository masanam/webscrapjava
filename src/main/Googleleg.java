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
import java.net.URL;

public class Googleleg 
{
        	private static String IMAGE_DESTINATION_FOLDER = "C:/images";
		private static final String USER_AGENT = "Chrome/56.0.2924.87";
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
				
				Elements linksOnPage = htmlDocument.select("h3.r a");
	                        Elements bodies = htmlDocument.select("span[class=st]");

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
               	 System.out.print(title.get(i));
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
				
				Elements linksOnPage = htmlDocument.select("img");
				//Elements imageElements = document.select("img");
				System.out.println("Find Image(" + linksOnPage.size() + ") links");
				
				for(Element link: linksOnPage)
				{
					this.links.add(link.attr("abs:src"));
                                        String strImageURL = link.attr("abs:src");
                                        //downloadImage(strImageURL);
				}

//    Elements elements = htmlDocument.select("div.rg_meta");
//
//    JSONObject obj;
//    for (Element result : htmlDocument.select("div.rg_meta")) {
//
//        // div.rg_meta contains a JSON object, which also holds the image url
//        obj = (JSONObject) new JSONParser().parse(result.text());
//
//        String imageUrl = (String) obj.get("ou");
////this.links.add(imageUrl);
//        // just printing out the url to demonstate the approach
//        System.out.println("imageUrl: " + imageUrl);    
//    } 
			
				return true;
			}
			catch(IOException ioe){
				return false;
			}
		}
                
private static void downloadImage(String strImageURL){
        
        //get file name from image path
        String strImageName = 
                strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );
        
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
