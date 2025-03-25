import java.util.ArrayList;

public class Tile {
	private int row, col, room;
	private Tile parent;
	public ArrayList<Tile> parents;
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
		parents = new ArrayList<Tile>();
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
		for(int i = 0; i<padre.getParents().size(); i++) {
			parents.add(padre.getParents().get(i));
		}
		parents.add(padre);
		System.out.println("setParent" +parents.toString());
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
	
}
