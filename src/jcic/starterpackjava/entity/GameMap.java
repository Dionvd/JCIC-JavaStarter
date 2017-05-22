package jcic.starterpackjava.entity;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dion
 */
public class GameMap {

    private long id;
    private List<Node> nodes;

    private int width;
    private int height;

    public GameMap(JSONObject jsonMap) {

        this.id = jsonMap.getLong("id");
        nodes = new ArrayList<>();
        JSONArray jsonArray = jsonMap.getJSONArray("nodes");

        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonNode = jsonArray.getJSONObject(i);
            long id = jsonNode.getLong("id");
            int power = jsonNode.getInt("power");
            long ownerId = jsonNode.getLong("ownerId");
            int type = jsonNode.getInt("type");
            nodes.add(new Node(id, power, ownerId, type));
        }
        
        this.width = 10;
        this.height = 10;
    }

    /**
     * @param x coordinate as integer.
     * @param y coordinate as integer.
     * @return Node located at coordinates x and y.
     */
    public Node getNode(int x, int y) {

        int i = x + y * width;
        if (x < 0 || x >= width || i < 0 || i >= nodes.size()) return null;
        return nodes.get(i);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
