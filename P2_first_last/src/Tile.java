import java.util.ArrayList;

public class Tile {
	private int row, col, room;
	private Tile parent;
	public ArrayList<Tile> parents;
	private char type;
	
	public Tile(int row, int col, int room, char type) {
		this.row = row;
		this.col = col;
		this.room = room;
		this.type = type;
		this.parent = null;
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	
	public void setParent(Tile parent) {
		this.parent = parent;
	}
	
	public ArrayList<Tile> getParents() {
		return parents;
	}
	
	public String ParentsToString() {
		String result = "";
		for(int i = 0; i<parents.size(); i++) {
			result+= "[" + parents.get(i)+"] ";
		}
		if(result.length() == 0) {
			return "nothing";
		}else {
			return result;
		}
	}
	public int getRoom() {
		return room;
	}
	
	public void setRoom() {
		this.room = room;
	}
	
	public void serParent(Tile parent) {
		this.parent = parent;
	}
	
	public Tile getParent() {
		return parent;
	}
	
	@Override
	public String toString() {
		return "(" + row+"," + col+","+type +")";
	}
}
