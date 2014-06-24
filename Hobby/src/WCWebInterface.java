/**
 * Created by GleasonK on 6/21/14.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;

public class WCWebInterface extends HttpServlet {
    private static WorldCup wcInfo;
    static {
        WorldCup tmp = null;
        try {
            tmp = new WorldCup(new URL("http://live.mobileapp.fifa.com/api/wc/matches"));
//            tmp = new WorldCup(new File("/usr/local/apache-tomcat-7.0.54/webapps/WorldCup/WEB-INF/context/FIFA API.json"));
        }
        catch (Exception e) { e.printStackTrace(); }
        wcInfo = tmp;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // Set the response MIME type of the response message
        response.setContentType("text/html");
        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();

        // Write the response message, in an HTML page
        try {
            out.println("<html>");
            out.println("<head>"+ getHeaders() +"<title>Simply World Cup</title></head>");
            out.println("<body><div id='top-bar'>Simply world cup. Nothing more.</div>");
            out.println("<div id='page-container'>");  // says Hello

            //Game Info
            try {
                out.println(liveGames());
                out.println(todaysGames());
                out.println(recentGames());
            }
            catch (Exception e) { e.printStackTrace(); }

            out.println("</div></body></html>");
        } finally {
            out.close();  // Always close the output writer
        }
    }

    public String liveGames() throws JSONException {
        JSONArray liveGames = wcInfo.getLiveGames();
        if (liveGames.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("<div class='game-header'>Live Games</div>");
            sb.append("<div class = 'game'>");
            for (int i = 0; i < liveGames.length(); i++) {
                JSONObject game = liveGames.getJSONObject(i);
                String[] teams = wcInfo.getTeams(game);
                String[] gameInfo = wcInfo.getGameInfo(game);
                String[] teamInfo = wcInfo.getTeamInfo(game);
                sb.append("<p id='teamNames'>" + teams[0] + " vs " + teams[1] + "</p>");
                sb.append("<br />");
                sb.append("<img class = 'teamImg' src = '" + teamInfo[0] + "'/>");
                sb.append("<img class = 'teamImg' src = '" + teamInfo[1] + "'/>");
                sb.append("<p><span id='header'>Score:</span><br/><span id = 'score'>" + fixScore(gameInfo[1]) + "</span><br/>");
                sb.append("<span id='time-left'>" + gameInfo[0] + "</span></p>");
                if (i + 1 < liveGames.length())
                    sb.append("<hr/>");
            }
            sb.append("</div><br/><br/>");
            return sb.toString();
        }
        return "";
    }

    public String todaysGames() throws JSONException{
        JSONArray todayGames = wcInfo.getTodaysGames();
        if (todayGames.length() > 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("<div class='game-header'>Todays Games</div>");
            sb.append("<div class = 'game'>");
            for (int i = 0; i < todayGames.length(); i++) {
                JSONObject game = todayGames.getJSONObject(i);
                Date matchDate = getDate(game);
                String[] teams = wcInfo.getTeams(game);
                String[] teamInfo = wcInfo.getTeamInfo(game);
                sb.append("<p id='teamNames'>" + teams[0] + " vs " + teams[1] + "</p>");
                sb.append("<br />");
                sb.append("<img class = 'teamImg' src = '" + teamInfo[0] + "'/>");
                sb.append("<img class = 'teamImg' src = '" + teamInfo[1] + "'/>");
                sb.append("<p><span id='time-left'>" + matchDate.toString() + "</span></p>");
                if (i + 1 < todayGames.length())
                    sb.append("<hr/>");
            }
            sb.append("</div><br/><br/>");
            return sb.toString();
        }
        return "";
    }

    public String recentGames() throws JSONException {
        JSONArray recentGames = wcInfo.getRecentGames();
        if (recentGames.length() > 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("<div class='game-header'>Recent Games</div>");
            sb.append("<div class = 'game'>");
            for (int i = 0; i < recentGames.length(); i++) {
                JSONObject game = recentGames.getJSONObject(i);
                String[] teams = wcInfo.getTeams(game);
                String[] gameInfo = wcInfo.getGameInfo(game);
                String[] teamInfo = wcInfo.getTeamInfo(game);
                sb.append("<p id='teamNames'>" + teams[0] + " vs " + teams[1] + "</p>");
                sb.append("<br />");
                sb.append("<img class = 'teamImg' src = '" + teamInfo[0] + "'/>");
                sb.append("<img class = 'teamImg' src = '" + teamInfo[1] + "'/>");
                sb.append("<p><span id='header'>Score:</span><br/><span id = 'score'>" + fixScore(gameInfo[1]) + "</span><br/>");
                sb.append("<span id='time-left'>Full time</span></p>");
                if (i + 1 < recentGames.length())
                    sb.append("<hr/>");
            }
            sb.append("</div>");
            return sb.toString();
        }
        return "";
    }

    private String fixScore(String score){
        score = score.replaceAll("\\(.*?\\)","");
        String[] scores = score.split(":");
        return scores[0] + " - " + scores[1];
    }

    private Date getDate(JSONObject game) throws JSONException{
        Date matchDate = new Date(Long.valueOf(game.get("d_Date").toString()));
        return matchDate;
    }

    private String getHeaders(){
        return "<link rel=\"stylesheet\" type=\"text/css\" href=\"/WorldCup/WebContent/stylesheet.css\">";
    }

}
