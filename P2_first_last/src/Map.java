
public class Map {
	private Tile[][][] map;

	public Map(Tile[][][] map) {
		super();
		this.map = map;
	}

	public Tile[][][] getMap() {
		return map;
	}
	public void addItem(int row, int col, int room, Tile temp) {
		map[row][col][room]= temp; 
	}

	public void setMap(Tile[][][] map) {
		this.map = map;
	}
	
	public Tile getTile(int row, int col, int room) {
		return map[row][col][room];
	}
	
	public String toString() {
		String result = "";
		for(int i = 0; i<map[0][0].length; i++) {
			for(int k = 0; k<map.length; k++) {
				for(int j = 0; j<map[0].length; j++) {
					result+= map[k][j][i].getType() + " ";
				}
				result+= "\n";
			}
		}
		return result;
	}
	
	
	
	
	
}
