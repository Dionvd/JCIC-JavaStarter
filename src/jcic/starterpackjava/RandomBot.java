package jcic.starterpackjava;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import jcic.starterpackjava.entity.Action;
import jcic.starterpackjava.entity.GameMap;
import jcic.starterpackjava.entity.Move;
import jcic.starterpackjava.entity.MoveDirection;
import jcic.starterpackjava.entity.Node;

/**
 *
 * @author dion
 */
public class RandomBot {

    private long myId;
    
    public RandomBot(Long myId) {
        this.myId = myId;
    }

    public List<Move> doTurn(GameMap gameMap) {
        
        List<Move> moves = new ArrayList<>();
        
        for (int x = 0; x < gameMap.getWidth(); x++) {
            for (int y = 0; y < gameMap.getHeight(); y++) {
                Node node = gameMap.getNode(x, y);
                Point location = new Point(x,y);
                if (node.getOwnerId() == myId && node.getPower() > 30)
                {
                    MoveDirection moveDirection = MoveDirection.values()[(int)(Math.random()*7)];
                    Point location2 = moveDirection.getLocation(location);
                    Node node2 = gameMap.getNode(location2.x,location2.y);
                    
                    if (node2 != null && node2.getOwnerId() != myId)
                    moves.add(new Move(location, Action.SPREAD, moveDirection));
                }
            }
        }
        
        return moves;
    }
    
    
   
    
}
