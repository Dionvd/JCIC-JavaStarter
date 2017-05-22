package jcic.starterpackjava.entity;

/**
 *
 * @author dion
 */
public class Node {
    
    //MAPID XX YY
    private long id; 
    
    //0-100
    private int power = 0;
    private long ownerId = 0;

    //-1 = empty, 0 = normal, 1 = powerline, 2 = overclocked, 3 = guarded, 4 = storage
    private int type = 0;
    
    public Node(long id, int power, long ownerId, int type)
    {
        this.id = id;
        this.power = power;
        this.ownerId = ownerId;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
}
