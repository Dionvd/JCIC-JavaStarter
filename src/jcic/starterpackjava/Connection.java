package jcic.starterpackjava;

import jcic.starterpackjava.entity.LoginCredentials;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcic.starterpackjava.entity.GameMap;
import jcic.starterpackjava.entity.Move;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dion
 */
public class Connection {

    final static String JCIC_URL = "http://localhost:8080/";
    static long MATCH_ID = 1;
    
    public static void login(LoginCredentials credentials) {
        //TODO
        System.out.println("--Received Map");

    }

    public static void joinQueue() {
        //TODO
        System.out.println("--Joined Queue");
    }

    public static void waitWhileInQueue() {
        //TODO
        System.out.println("--Queue Ended, Game started!");
    }

    public static void findGame() {
        //TODO
        System.out.println("--Connected to game!");
    }

    public static void waitForNewTurn() {
        //TODO
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static GameMap getMap() {
        try {
            URL url = new URL(JCIC_URL + "matches/" + MATCH_ID + "/map");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            String responseMessage = conn.getResponseMessage();
            int responseCode = conn.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            String gameMapText = new String(sb);
            JSONObject jsonMap = new JSONObject(gameMapText);
            GameMap gameMap = new GameMap(jsonMap);
            conn.disconnect();
            System.out.println("--Map Received");

            return gameMap;

        } catch (MalformedURLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void sendMoves(long myId, List<Move> moves) {

        if (moves.isEmpty()) return;
        
        try {

            URL url = new URL(JCIC_URL + "matches/" + MATCH_ID + "/players/" + myId + "/moves");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");

            JSONArray jsonObject = new JSONArray(moves);
            String input = jsonObject.toString();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.print(output);
            }

            conn.disconnect();
            System.out.println("--Sent " + moves.size() + " moves.");

        } catch (ProtocolException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
