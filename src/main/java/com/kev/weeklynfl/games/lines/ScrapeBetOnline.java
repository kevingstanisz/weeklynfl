package com.kev.weeklynfl.games.lines;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.kev.weeklynfl.games.GameLine;
import com.kev.weeklynfl.games.WeekNumber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrapeBetOnline {

    public ScrapeBetOnline() {
    }

    private String getHtmlElementChild(HtmlElement htmlElement) {
        if(htmlElement == null || !htmlElement.hasChildNodes()) {
            return " ";
        }

        return htmlElement.getFirstChild().asText();
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
                    // System.out.println(tdTeam1);
                    HtmlElement tdTeam2 = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='col_teamname bdevtt']");
                    // System.out.println(tdTeam2);
                    HtmlElement tdSp1 = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='hdcp bdevtt ']");
                    HtmlElement tdSp2 = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='hdcp bdevtt ']");
                    HtmlElement tdSp1Odds = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='odds bdevtt displayOdds']");
                    HtmlElement tdSp2Odds = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='odds bdevtt displayOdds']");
                    HtmlElement tdMl1 = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='odds bdevtt moneylineodds displayOdds']");
                    HtmlElement tdMl2 = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='odds bdevtt moneylineodds displayOdds']");
                    HtmlElement tdOver = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='hdcp bdevtt '][2]");
                    HtmlElement tdUnder = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='hdcp bdevtt '][2]");
                    HtmlElement tdOverOdds = htmlElement.getFirstByXPath("tr[@class='h2hSeq firstline']/td[@class='odds bdevtt displayOdds'][2]");
                    HtmlElement tdUnderOdds = htmlElement.getFirstByXPath("tr[@class='otherline']/td[@class='odds bdevtt displayOdds'][2]");

                    if(tdTeam1 != null) {
                        gameLines.add(new GameLine(
                                getHtmlElementChild(tdTeam1),
                                getHtmlElementChild(tdTeam2),
                                getHtmlElementChild(tdSp1),
                                getHtmlElementChild(tdSp2),
                                getHtmlElementChild(tdSp1Odds),
                                getHtmlElementChild(tdSp2Odds),
                                getHtmlElementChild(tdMl1),
                                getHtmlElementChild(tdMl2),
                                getHtmlElementChild(tdOver),
                                getHtmlElementChild(tdUnder),
                                getHtmlElementChild(tdOverOdds),
                                getHtmlElementChild(tdUnderOdds)
                        ));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameLines;
    }

    public static void main(String[] args) {
        ScrapeBetOnline scrapeBetOnline = new ScrapeBetOnline();
        List<GameLine> gameLines = scrapeBetOnline.getLines();

        for(GameLine gameLine : gameLines) {
            System.out.println(gameLine.toString());
        }

        WeekNumber weekNumber = new WeekNumber();

    }
}
