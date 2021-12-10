
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.net.URL;

public class Parser {

  //  public String url;

    private static Document getPage(String url) throws IOException {
        Document page = Jsoup.parse(new URL(url),3000);
        return page;
    };

    public static void main (String[] args) throws IOException{

        Document page = getPage("https://finviz.com/screener.ashx?v=111&f=idx_sp500&o=-marketcap");
        String StringCorpInfo="";
        String StringTicker="";
        String StringPrice="";
        int i=0;
        int j=0;
        int FullLength=0;
        int FindingIndex=0;
        int FindingIndex2=0;

       while(i<200){

           if (i%10==0) {
               System.out.println("");
               Element tdTicker = page.select("a[class=screener-link-primary]").get(i/10);
               StringTicker=tdTicker.toString();
               FullLength = StringTicker.length();
               FindingIndex = StringTicker.indexOf(">", 2);
               StringTicker = StringTicker.substring(FindingIndex + 1, FullLength - 4);
               System.out.println(StringTicker);
               Document page2 = Jsoup.connect("https://query1.finance.yahoo.com/v8/finance/chart/"+StringTicker).ignoreContentType(true).get();
               Element tdPrice = page2.body();
               StringPrice=tdPrice.toString();
               FindingIndex=StringPrice.indexOf("regularMarketPrice");
               FindingIndex2=StringPrice.indexOf(",",FindingIndex);
               StringPrice=StringPrice.substring(FindingIndex+20,FindingIndex2);
               System.out.println(StringPrice);
           }
           Element tdCorpInfo=page.select("a[class=screener-link]").get(i);
           StringCorpInfo=tdCorpInfo.toString();
           FullLength = StringCorpInfo.length();
           FindingIndex=StringCorpInfo.indexOf(">",2);
           StringCorpInfo = StringCorpInfo.substring(FindingIndex+1,FullLength-4);
           if (StringCorpInfo.matches(".*\\d+.*")!=true)
           {
               System.out.println(StringCorpInfo);
           }
           i++;
       }

        String date="";
        System.out.println(date+"тикер компания сектор индастри");
        //System.out.println();

    }
}
