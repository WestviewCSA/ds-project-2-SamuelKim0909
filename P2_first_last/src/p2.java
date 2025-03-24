import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class p2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("test case 2");
		readMap("test case 2");
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
						map.addItem(rowIndex, i, roomNum, obj);
					}
					rowIndex++;
				}
			}
			//System.out.println(map.toString());
				Tile North = null;
				Tile South = null;
				Tile West = null;
				Tile East = null;
				if(W.getRow()>=1) {
					North = map.getTile(W.getRow()-1,W.getCol(),W.getRoom());
					//System.out.println("North "+North.getType());
				}
				if(W.getRow()< numRows) {
					South = map.getTile(W.getRow()+1,W.getCol(),W.getRoom());
					//System.out.println("South "+South.getType());
				}
				if(W.getCol() >= 1) {
					West = map.getTile(W.getRow(),W.getCol()-1,W.getRoom());
					//System.out.println("West "+West.getType());
				}
				if(W.getCol()<numCols) {
					East = map.getTile(W.getRow(),W.getCol()+1,W.getRoom());
					//System.out.println("East "+East.getType());
				}
				
				
				if(North!= null&& North.getType()=='.') {
					queue.enqueue(North);
				}
				if(South!= null&& South.getType()=='.') {
					queue.enqueue(South);
				}
				if(East!= null&& East.getType()=='.') {
					queue.enqueue(East);
				}
				if(West!= null&& West.getType()=='.') {
					queue.enqueue(West);
				}
				//System.out.println("queue tostring " + queue.toString());
				System.out.println("search " +search(queue.dequeue()));
			//}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	
	
	public static Tile search(Tile start) {
		Tile north = null;
		Tile south = null;
		Tile east = null;
		Tile west = null;
		// assign tiles to north south east and west
		if(start.getRow()>=1) {
			north = map.getTile(start.getRow()-1, start.getCol(), start.getRoom());
			//System.out.println("second North "+north.getType());
		}
		if(start.getRow()< map.getRow()) {
			south = map.getTile(start.getRow()+1,start.getCol(),start.getRoom());
			//System.out.println("second South "+south.getType());
		}
		if(start.getCol() >= 1) {
			west = map.getTile(start.getRow(),start.getCol()-1,start.getRoom());
			//System.out.println("second West "+west.getType());
		}
		if(start.getCol()<map.getCol()) {
			east = map.getTile(start.getRow(),start.getCol()+1,start.getRoom());
			//System.out.println("second East "+east.getType());
		}
		//enqueue
		if(north != null&& north.getType()=='.'){
			north.setType('+');
			queue.enqueue(north);
			if(!north.parents.contains(start)) {
				north.setParent(start);
			}
		}
		if(south != null&&south.getType()=='.'){
			south.setType('+');
			queue.enqueue(south);
			if(!south.parents.contains(start)) {
				south.setParent(start);
			}
		}
		if(east != null&&east.getType()=='.'){
			east.setType('+');
			queue.enqueue(east);
			if(!east.parents.contains(start)) {
				east.setParent(start);
			}
		}
		if(west != null&&west.getType()=='.'){
			west.setType('+');
			queue.enqueue(west);
			if(!west.parents.contains(start)) {
				west.setParent(start);
			}
		}
		//identifies which one is $ or |
		if(north != null&&north.getType()==('$')||north.getType()==('|')){
			System.out.println(map.toString());
			System.out.println(north.getParents());
			return north;
		}else if (south != null&&south.getType()==('$')||south.getType()==('|')){
			System.out.println(map.toString());
			System.out.println(south.getParents());
			return south;
		}else if (east != null&&east.getType()==('$')||east.getType()==('|')){
			System.out.println(map.toString());
			System.out.println(east.getParents());
			return east;
		}else if (west != null&&west.getType()==('$')||west.getType()==('|')){
			System.out.println(map.toString());
			System.out.println(west.getParents());
			return west;
		}else {
			return search(queue.dequeue());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
