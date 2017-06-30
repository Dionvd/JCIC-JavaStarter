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

    final static String JCIC_URL = "http://172.16.32.50:8080/";
    static long MATCH_ID = 1;
    
    private static JSONObject sendMessage(String urlString, String requestMethod, Object objectToSend)
    {
        //TODO send objectToSend
        
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
              
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Accept", "application/json");
           
            if ("POST".equals(requestMethod))
            {
                conn.setDoOutput(true);

                String input = objectToSend.toString();

                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();
            }
            
            conn.connect();
            String responseMessage = conn.getResponseMessage();
            int responseCode = conn.getResponseCode();
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            String data = new String(sb);
            JSONObject jsonData = new JSONObject(data);
            conn.disconnect();

            return jsonData;

        } catch (MalformedURLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    
    public static long login(LoginCredentials credentials) {
        
        System.out.println("--Logging in");
        JSONObject receivedMessage = sendMessage(JCIC_URL + "players/login", "POST", credentials);
        if (receivedMessage.has("error"))
        {
            System.out.println("--Login failed");
            throw new Error("Login failed, Please try different email and password values.");
        }
        else
        {
            System.out.println("--Login succeeded");
            return receivedMessage.getLong("value");        
        }
    }

    public static int joinQueue(long playerId, long sessionToken) {
        JSONObject receivedMessage = sendMessage(JCIC_URL + "queue/" + playerId + "/" + sessionToken, "GET", null);
        int pos = receivedMessage.getInt("value");
        System.out.println("--Joined Queue : Pos in Queue : " + pos);
        return pos;
 }

    public static void waitWhileInQueue(long playerId) {
        
        int pos = 999;
        
        while(true)
        {
            try {
                JSONObject receivedMessage = sendMessage(JCIC_URL + "queue/" + playerId, "GET", null);
                pos = receivedMessage.getInt("value");
                
                if (pos < 1) break;
                Thread.sleep(1000);
                System.out.println("-- pos : " + pos);
            } catch (InterruptedException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("--Queue Ended, Game started!");
        
    }

    public static long findGame(long playerId) {
        //TODO
        JSONObject receivedMessage = sendMessage(JCIC_URL + "rounds/withPlayer/" + playerId, "GET", null);
        JSONArray receivedMessages = new JSONArray(receivedMessage);
        if (receivedMessages.toList().size() > 0)
        {
            JSONObject obj = receivedMessages.getJSONObject(0);
            System.out.println("--match found!");
            return obj.getLong("roundId");
        }
        else
        {
            throw new Error("No game was found");
        }
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
            
        JSONObject jsonMap = sendMessage(JCIC_URL + "rounds/" + MATCH_ID + "/map", "GET", null);
        GameMap gameMap = new GameMap(jsonMap);
        System.out.println("--Map Received");
        return gameMap;
    }

    public static void sendMoves(long myId, List<Move> moves) {

        if (moves.isEmpty()) return;
        
        JSONArray jsonArray = new JSONArray(moves);
        JSONObject receivedMessage = sendMessage(JCIC_URL + "rounds/" + MATCH_ID + "/players/" + myId + "/moves", "POST", jsonArray);
        System.out.println("--Sent " + moves.size() + " moves.");

    }

}
