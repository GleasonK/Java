import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by GleasonK on 6/21/14.
 */
public class WorldCup {
    private JSONObject fifaAPI;
    private JSONObject data;

    public WorldCup(File wcFile) throws Exception {
        String fileIn = new Scanner(wcFile).useDelimiter("//A").next();
        this.fifaAPI =  new JSONObject(fileIn);
        this.data = this.fifaAPI.getJSONObject("data");
    }

    public WorldCup(URL wcFile) throws Exception {
        this.fifaAPI = JSONReader.readJsonFromUrl(wcFile.toString());
        this.data = this.fifaAPI.getJSONObject("data");
    }

    public void runApp() throws JSONException{
        this.viewDataFields(data);
        JSONArray groupData = getGroup();
        for (int i=0; i<groupData.length(); i++) {
            JSONObject game = groupData.getJSONObject(i);
            System.out.println(getTeams(game));
            if (game.get("b_Live").toString().equals("true")) {
                System.out.println("PLAYING NOW");
                System.out.println(getGameInfo(game));
            }
        }
        viewDataFields(groupData.getJSONObject(0));
        String currentDay = getCurrentDay();
        System.out.println(groupData.toString());
    }

    private void viewDataFields(JSONObject json){
        try {
            Iterator keys = json.keys();
            while (keys.hasNext())
                System.out.println(keys.next().toString());
        }
        catch (Exception e) {e.printStackTrace(); }
    }

    public String getCurrentDay(){
        try { return data.get("currentDay").toString(); }
        catch (Exception e) { e.printStackTrace(); }
        throw new RuntimeException("ERROR - Couldn't get current day");
    }

    public JSONArray getGroup(){
        try { return data.getJSONArray("group"); }
        catch (Exception e) { e.printStackTrace(); }
        throw new RuntimeException("ERROR - Couldn't get group");
    }

    public String[] getTeams(JSONObject game){
        try { return new String[] { game.get("c_HomeTeam_en").toString(), game.get("c_AwayTeam_en").toString() }; }
        catch (Exception e) { e.printStackTrace(); }
        throw new RuntimeException("ERROR - Couldn't get team names");
    }

    public String[] getGameInfo(JSONObject game){
        try { return new String[] { game.get("c_Minute").toString(), game.get("c_Score").toString()}; }
        catch (Exception e) { e.printStackTrace(); }
        throw new RuntimeException("ERROR - Couldn't get game info");
    }

    public String[] getTeamInfo(JSONObject game) {
        try { return new String[] { game.get("c_HomeLogoImage").toString(), game.get("c_AwayLogoImage").toString()}; }
        catch (Exception e) { e.printStackTrace(); }
        throw new RuntimeException("ERROR - Couldn't get team info");
    }

    public JSONObject getData(){
        return this.data;
    }

    public JSONArray getLiveGames(){
        JSONArray liveGames = new JSONArray();
        try {
            JSONArray groupData = getGroup();
            for (int i = 0; i < groupData.length(); i++) {
                JSONObject game = groupData.getJSONObject(i);
                if (game.get("b_Live").toString().equals("true")) {
                    liveGames.put(game);
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        return liveGames;
    }

    public JSONArray getTodaysGames() {
        JSONArray recentGames = new JSONArray();
        try {
            JSONArray groupData = getGroup();
            for (int i = 0; i < groupData.length(); i++) {
                JSONObject game = groupData.getJSONObject(i);
                if (Integer.valueOf(game.get("n_MatchDay").toString()).equals(Integer.valueOf(this.data.get("currentDay").toString())) &&
                        game.get("c_Score").toString().equals("null")) {
                    recentGames.put(game);
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        return recentGames;
    }

    public JSONArray getRecentGames() {
        JSONArray recentGames = new JSONArray();
        try {
            JSONArray groupData = getGroup();
            for (int i = 0; i < groupData.length(); i++) {
                JSONObject game = groupData.getJSONObject(i);
                if (Integer.valueOf(game.get("n_MatchDay").toString()) + 1 >= Integer.valueOf(this.data.get("currentDay").toString()) &&
                        game.get("c_Score").toString() != "null" && game.get("c_Minute").toString() == "null") {
                    recentGames.put(game);
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        return recentGames;
    }

    public static void main(String[] args) throws Exception {
        File wcInfo = new File("/usr/local/apache-tomcat-7.0.54/webapps/WorldCup/WEB-INF/context/FIFA API.json");

//        URL wcInfo = new URL("http://live.mobileapp.fifa.com/api/wc/matches");
        WorldCup app = new WorldCup(wcInfo);
        app.runApp();
    }

}
