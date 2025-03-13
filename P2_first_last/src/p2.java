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
	
	public static void readMap(String filename) {
			
		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			
			int numRows = scanner.nextInt();
			int numCols = scanner.nextInt();
			int numRooms = scanner.nextInt();
			Map map = new Map(new Tile[numRows][numCols][numRooms]);
			int rowIndex = 0;
			int roomNum = 0;
			int aRow=0;
			int aCol=0;
			while(scanner.hasNextLine()) {
				String row = scanner.nextLine();
				if(row.length()>0) {
					for(int i = 0; i<numCols && i< row.length(); i++) {
						char el = row.charAt(i);
						Tile obj = new Tile(rowIndex, i, el);
						//obj = map[rowIndex][i][1];
						//System.out.println(el);
						if(obj.getType()=='W') {
							aRow= obj.getRow();
							aCol = obj.getCol();
							Tile W = new Tile(aRow, aCol, 'W');
						}
						if(obj.getType()=='$') {
							aRow= obj.getRow();
							aCol = obj.getCol();
							Tile $ = new Tile(aRow, aCol, '$');
						}
						map.addItem(rowIndex, i, roomNum, obj);
					}
					rowIndex++;
				}
			}
			//System.out.println(aRow+ " "+ aCol);
			System.out.println(map.toString());
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
