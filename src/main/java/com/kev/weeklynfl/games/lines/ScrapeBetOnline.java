package com.kev.weeklynfl.games.lines;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.kev.weeklynfl.games.GameLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrapeBetOnline {

    public ScrapeBetOnline() {
    }

    public List<GameLine> getLines() {
        List<GameLine> gameLines = new ArrayList<GameLine>();

        String baseUrl = "https://www.betonline.ag/sportsbook/football/nfl";

        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);

        try {
            HtmlPage page = webClient.getPage(baseUrl);
            List<HtmlElement> htmlElements = page.getByXPath("//tbody[@class='event']");
            if(htmlElements.isEmpty()) {
                System.out.println("No items found");
            }
            else {
                for(HtmlElement htmlElement : htmlElements) {
                    HtmlElement tdTeam1 = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='col_teamname bdevtt']");
                    HtmlElement tdTeam2 = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='col_teamname bdevtt']");
                    HtmlElement tdSp1 = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='hdcp bdevtt ']");
                    HtmlElement tdSp2 = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='hdcp bdevtt ']");
                    HtmlElement tdSp1Odds = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='odds bdevtt displayOdds']");
                    HtmlElement tdSp2Odds = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='odds bdevtt displayOdds']");
                    HtmlElement tdMl1 = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='odds bdevtt moneylineodds displayOdds']");
                    HtmlElement tdMl2 = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='odds bdevtt moneylineodds displayOdds']");
                    HtmlElement tdOver = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='hdcp bdevtt ']");
                    HtmlElement tdUnder = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='hdcp bdevtt ']");
                    HtmlElement tdOverOdds = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='odds bdevtt displayOdds']");
                    HtmlElement tdUnderOdds = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='odds bdevtt displayOdds']");


//                    if(tdTeam1 != null) {
//                        gameLines.add(new GameLine(tdTeam1, tdTeam2, tdSp1, tdSp2, tdSp1Odds, tdSp2Odds, tdMl1, tdMl2, tdOver, tdUnder, tdOverOdds, tdUnderOdds));
//                    }
                }
            }

//            System.out.println(page.asXml());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameLines;
    }

    public static void main(String[] args) {
        ScrapeBetOnline scrapeBetOnline = new ScrapeBetOnline();
        scrapeBetOnline.getLines();
    }
}
