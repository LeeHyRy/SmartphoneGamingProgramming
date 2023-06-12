package framework.tiled;

public class TiledLayer {
    private final TiledMap map;
    ////////////////////////////////////////////////////////////
    // from tmj
    public int x, y, width, height;
    public int[] data;
    ////////////////////////////////////////////////////////////

    public TiledLayer(TiledMap map) {
        this.map = map;
    }

    public int tileAt(int x, int y) {
        return data[y * width + x];
    }
}
