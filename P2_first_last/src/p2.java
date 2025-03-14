import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class p2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("test case 1");
		readMap("test case 1");
		
	}
	static int numRow = 0;
	static int numCol = 0;
	static int numRoom = 0;
	static Queue<Tile> queue = null;
	static Map map = null;
	public static void readMap(String filename) {
			
		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			queue = new Queue<Tile>();
			int numRows = scanner.nextInt();
			int numCols = scanner.nextInt();
			int numRooms = scanner.nextInt();
			numRow = numRows;
			numCol = numCols;
			numRoom = numRooms;
			map = new Map(new Tile[numRows][numCols][numRooms]);
			int rowIndex = 0;
			int roomNum = 0;
			int aRow=0;
			int aCol=0;
			Tile W = null;
			while(scanner.hasNextLine()) {
				String row = scanner.nextLine();
				if(row.length()>0) {
					for(int i = 0; i<numCols && i< row.length(); i++) {
						char el = row.charAt(i);
						Tile obj = new Tile(rowIndex, i, 0, el);
						//obj = map[rowIndex][i][1];
						//System.out.println(el);
						if(obj.getType()=='W') {
							aRow= obj.getRow();
							aCol = obj.getCol();
							 W = new Tile(aRow, aCol, 0,  'W');
							//queue.enqueue(W);
						}
						if(obj.getType()=='$') {
							aRow= obj.getRow();
							aCol = obj.getCol();
							Tile $ = new Tile(aRow, aCol, 0,  '$');
						}
//						if(obj.getType()=='.') {
//							queue.enqueue(obj);
//						}
						map.addItem(rowIndex, i, roomNum, obj);
					}
					rowIndex++;
				}
			}
			//System.out.println(aRow+ " "+ aCol);
			System.out.println(map.toString());
			//while(1<2) {
				Tile North = null;
				Tile South = null;
				Tile West = null;
				Tile East = null;
				if(W.getRow()>=1) {
					North = map.getTile(W.getRow()-1,W.getCol(),W.getRoom());
					System.out.println(North.getType());
				}
				if(W.getRow()< numRows) {
					South = map.getTile(W.getRow()+1,W.getCol(),W.getRoom());
					System.out.println(South.getType());
				}
				if(W.getCol() >= 1) {
					West = map.getTile(W.getRow(),W.getCol()-1,W.getRoom());
					System.out.println(West.getType());
				}
				if(W.getCol()<numCols) {
					East = map.getTile(W.getRow(),W.getCol()+1,W.getRoom());
					System.out.println(East.getType());
				}
				
				if(North.getType()=='.') {
					queue.enqueue(North);
				}else if(South.getType()=='.') {
					queue.enqueue(South);
				}else if(East.getType()=='.') {
					queue.enqueue(East);
				}else if(West.getType()=='.') {
					queue.enqueue(West);
				}
				queue.toString();
			//}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	
	
	
	public static Tile search(Tile temp) {
		Tile North = null;
		Tile South = null;
		Tile West = null;
		Tile East = null;
		if(temp.getRow()>=1) {
			North = map.getTile(temp.getRow()-1,temp.getCol(),temp.getRoom());
			System.out.println(North.getType());
		}
		if(temp.getRow()< numRow) {
			South = map.getTile(temp.getRow()+1,temp.getCol(),temp.getRoom());
			System.out.println(South.getType());
		}
		if(temp.getCol() >= 1) {
			West = map.getTile(temp.getRow(),temp.getCol()-1,temp.getRoom());
			System.out.println(West.getType());
		}
		if(temp.getCol()<numCol) {
			East = map.getTile(temp.getRow(),temp.getCol()+1,temp.getRoom());
			System.out.println(East.getType());
		}
		
		if(North.getType()=='.') {
			queue.enqueue(North);
		}else if(South.getType()=='.') {
			queue.enqueue(South);
		}else if(East.getType()=='.') {
			queue.enqueue(East);
		}else if(West.getType()=='.') {
			queue.enqueue(West);
		}
		queue.toString();
		return temp;
	}
	
	
	
	
	
	
	

}
