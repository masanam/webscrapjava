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

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Google {
    private static final int MAX_PAGES_TO_SEARCH = 10;
    private List<String> pagesVisited = new LinkedList<String>();
    private List<String> pagesToVisit = new LinkedList<String>();
    private List<String> pageTitle = new LinkedList<String>();
    private List<String> pageDesc = new LinkedList<String>();
    
    public List<String> getTitle() {return pageTitle;}
    public List<String> getLinks() {return pagesToVisit;}
    public List<String> getDesc() {return pageDesc;}

    
    public void search(String url, String searchWord, String browser)  {
        while(pagesVisited.size() < MAX_PAGES_TO_SEARCH)   {
        	String currentUrl;
            
        	  Googleleg leg = new Googleleg();
            
            
            if(this.pagesToVisit.isEmpty())  {
                currentUrl = url;
                
            }
            else  {  currentUrl = nextUrl();   }
            
            System.out.println(currentUrl);
            leg.crawl(currentUrl); 
        Runtime runtime = Runtime.getRuntime();
        try {
            Runtime.getRuntime().exec("cmd.exe /c start "+browser+" \"" + currentUrl + "\""); // TODO Auto-generated catch block
        } catch (IOException ex) {
           ex.printStackTrace();
        }

            
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
    
    public void news(String url, String searchWord, String browser)  {
        while(pagesVisited.size() < MAX_PAGES_TO_SEARCH)   {
        	String currentUrl;
            
        	  Googleleg leg = new Googleleg();
            
            
            if(this.pagesToVisit.isEmpty())  {
                currentUrl = url;
                
            }
            else  {  currentUrl = nextUrl();   }
            
            System.out.println(currentUrl);
            leg.crawlNews(currentUrl); 

        Runtime runtime = Runtime.getRuntime();
        try {
            Runtime.getRuntime().exec("cmd.exe /c start "+browser+" \"" + currentUrl + "\""); // TODO Auto-generated catch block
        } catch (IOException ex) {
           ex.printStackTrace();
        }

            
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
    
    public static void openWebpage(String urlString) {
    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        try {
            desktop.browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    
   public static void openBrowser(String urlString) {
    if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URL(urlString).toURI());
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
        }else{
            Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("firefox " + urlString); // TODO Auto-generated catch block
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        }
}
    
    public void img(String url, String searchWord, String browser) throws ParseException  {
        while(pagesVisited.size() < MAX_PAGES_TO_SEARCH)   {
        	  String currentUrl;
            
        	  Googleleg leg = new Googleleg();

            if(this.pagesToVisit.isEmpty())  {
                currentUrl = url;
                
            }
            else  {  currentUrl = nextUrl();   }
            
            
            
            Runtime runtime = Runtime.getRuntime();
        try {
            Runtime.getRuntime().exec("cmd.exe /c start "+browser+" \"" + currentUrl + "\""); // TODO Auto-generated catch block
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        
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

