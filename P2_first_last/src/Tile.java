import java.util.ArrayList;

public class Tile {
	private int row, col, room;
	private Tile parent;
	public ArrayList<Tile> parents = new ArrayList<Tile>();
	public int getRoom() {
		return room;
	}
	
	public void setRoom(int room) {
		this.room = room;
	}
	private char type;
	
	public Tile(int row, int col, int room, char type) {
		super();
		this.row = row;
		this.col = col;
		this.room = room;
		this.type = type;
		parent = null;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
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
	public String toString() {
		return "(" + getRow() + "," + getCol()+")";
	}
	
	public void setParent(Tile padre) {
		parents.add(padre);
		System.out.println(parents.toString());
	}
	
	public ArrayList getParents() {
		return parents;
	}
	
}
