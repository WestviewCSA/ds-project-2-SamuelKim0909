
public class Map {
	private Tile[][][] map;

	public Map(Tile[][][] map) {
		super();
		this.map = map;
	}

	public Tile[][][] getMap() {
		return map;
	}
	public void addItem(int row, int col, int room) {
		//map[row][col][room]; start from here (identify how to add 
		//an item to this 3D array
	}

	public void setMap(Tile[][][] map) {
		this.map = map;
	}
	
	
	
	
	
	
}
