package jcic.starterpackjava;

import jcic.starterpackjava.entity.LoginCredentials;
import jcic.starterpackjava.entity.GameMap;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;
import jcic.starterpackjava.entity.Move;

/**
 *
 * @author dion
 */
public class Application {

    private final static String MY_EMAIL = "John@test.uk";
    private final static String MY_PASSWORD = "password";
    private final static Long MY_PLAYER_ID = 7L;
    
    
    public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException, InterruptedException {

        LoginCredentials credentials = new LoginCredentials(MY_EMAIL, MY_PASSWORD);
        
        long sessionToken = Connection.login(credentials);
        
        Connection.joinQueue(MY_PLAYER_ID, sessionToken);
        
        Connection.waitWhileInQueue(MY_PLAYER_ID);
        
        long GAME_ID = Connection.findGame(MY_PLAYER_ID);
        
        RandomBot bot = new RandomBot(MY_PLAYER_ID);

        //GAME START
        while (true)
        {
            Connection.waitForNewTurn();
            
            GameMap gameMap = Connection.getMap();

            List<Move> moves = bot.doTurn(gameMap);
            
            Connection.sendMoves(MY_PLAYER_ID, moves);
        }
        
        //When game ends, application will throw exception for HTTP response code 400.
        
    }

}
