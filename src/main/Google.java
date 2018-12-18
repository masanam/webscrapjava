import java.io.*;
import java.util.*;
import org.json.simple.parser.ParseException;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Google {
    private static final int MAX_PAGES_TO_SEARCH = 5;
    private List<String> pagesVisited = new LinkedList<String>();
    private List<String> pagesToVisit = new LinkedList<String>();
    private List<String> pageTitle = new LinkedList<String>();
    private List<String> pageDesc = new LinkedList<String>();
    
    public List<String> getTitle() {return pageTitle;}
    public List<String> getLinks() {return pagesToVisit;}
    public List<String> getDesc() {return pageDesc;}

    
    public void search(String url, String searchWord)  {
        while(pagesVisited.size() < MAX_PAGES_TO_SEARCH)   {
        	String currentUrl;
            
        	  Googleleg leg = new Googleleg();
            
            
            if(this.pagesToVisit.isEmpty())  {
                currentUrl = url;
                
            }
            else  {  currentUrl = nextUrl();   }
            
            
            leg.crawl(currentUrl); 
            
            
            
            boolean success = leg.searchForWord(searchWord);

            pageTitle.addAll(leg.getTitle());
            pagesToVisit.addAll(leg.getLinks());
            pageDesc.addAll(leg.getDesc());
            
            if(success)  {
                System.out.println(String.format("*Success*  Word %s found at %s", searchWord, currentUrl));
                pagesVisited.add(currentUrl);
                break;
             }
            
            
            
        }
        
        System.out.println("\n*Done* Visited " + pagesVisited.size() + " web page(s)");
    }
    
    
    private String nextUrl()   {
        String nextUrl;
        
        do {
            nextUrl = pagesToVisit.remove(0);
        } while(pagesVisited.contains(nextUrl));
        
        pagesVisited.add(nextUrl);
        
        return nextUrl;
    }
    
    
    public void img(String url, String searchWord) throws ParseException  {
        while(pagesVisited.size() < MAX_PAGES_TO_SEARCH)   {
        	  String currentUrl;
            
        	  Googleleg leg = new Googleleg();

            if(this.pagesToVisit.isEmpty())  {
                currentUrl = url;
                
            }
            else  {  currentUrl = nextUrl();   }
            
            
            leg.crawlimg(currentUrl); 

            boolean success = leg.searchForWord(searchWord);
            
            
            if(success)  {
                System.out.println(String.format("*Success*  Word %s found at %s", searchWord, currentUrl));
                pagesVisited.add(currentUrl);
             }
            
            
            pagesToVisit.addAll(leg.getLinks());

        }
        
        System.out.println("\n*Done* Visited " + pagesVisited.size() + " web page(s)");
    }
}
