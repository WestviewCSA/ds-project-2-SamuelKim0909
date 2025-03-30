import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class p2 {
	static int roomCount;
	static int rowNum;
    static Map map = null;
    static boolean useStack;
    static boolean useQueue;
    static boolean useOpt;
    static boolean coordinateIn;
    static boolean coordinateOut;
    public static void main(String[] args) {
        useStack = false;
        useQueue = true;
        useOpt = false;
        coordinateIn = true;
        coordinateOut = false;
        System.out.println("test case 1");
        readMap("test case 10");
    }
    public static void readMap(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            int numRows = scanner.nextInt();
            int numCols = scanner.nextInt();
            int numRooms = scanner.nextInt();
            rowNum = numRows;
            roomCount = 0;
            map = new Map(new Tile[numRows][numCols][numRooms]);
            scanner.nextLine();

            if (coordinateIn == true) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(" ");
                    char type = parts[0].charAt(0);
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    int room = Integer.parseInt(parts[3]);
                    map.addItem(row, col, room, new Tile(row, col, room, type));
                }
            } else {
                for (int room = 0; room < numRooms; room++) {
                    for (int row = 0; row < numRows; row++) {
                        String line = scanner.nextLine();
                        for (int col = 0; col < numCols; col++) {
                            char type = line.charAt(col);
                            map.addItem(row, col, room, new Tile(row, col, room, type));
                        }
                    }
                }
            }
            findPath();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (Exception e) {
            System.err.println("Map File Format Error");
        }
    }
    //router that assigns what to do for each type + keep track of time
    public static void findPath() {
        double time1 = System.nanoTime();
        if (useStack) {
            findPathStack();
        } else if (useQueue) {
            findPathQueue();
        } else if (useOpt) {
            findPathOpt();
        }
        double time2 = System.nanoTime();
        System.out.println("Total Runtime: " + (time2 - time1) / 1e9 + " seconds");
        
    }

    public static void resetMap() {
        for (int room = 0; room < map.getRoom(); room++) {
            for (int row = 0; row < map.getRow(); row++) {
                for (int col = 0; col < map.getCol(); col++) {
                    if (map.getTile(row, col, room).getType() == '=') {
                        map.getTile(row, col, room).setType('.');
                    }
                }
            }
        }
    }
// stack
    public static void findPathStack() {
        boolean found = false;
        for (int i = 0; i < map.getRoom(); i++) {
            Stack<Tile> stack = new Stack<>();
            Tile start = findW(i);
            stack.push(start);

            while (!stack.isEmpty() && !found) {
                Tile current = stack.pop();
                if (current.getType() == '$' || current.getType() == '|') {
                    if (current.getType() == '$') {
                        found = true;
                        resetMap();
                        printPath(current);
                    }else {
                    recordPath(current);
                    break;
                    }
                }
                addNeighborsToStack(stack, current);
            }
        }

        if (!found) {
            System.out.println("The Wolverine Store is closed.");
        }
    }

    public static void addNeighborsToStack(Stack<Tile> stack, Tile current) {
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] direction : directions) {
            int newRow = current.getRow() + direction[0];
            int newCol = current.getCol() + direction[1];
            if (newRow >= 0 && newRow < map.getRow() && newCol >= 0 && newCol < map.getCol()) {
                Tile neighbor = map.getTile(newRow, newCol, current.getRoom());
                if (neighbor.getType() == '.') {
                    neighbor.setType('=');
                    neighbor.setParent(current);
                    stack.push(neighbor);
                }
                if (neighbor.getType() == '$' || neighbor.getType() == '|') {
                    neighbor.setParent(current);
                    stack.push(neighbor);
                }
            }
        }
    }
//queue
    public static void findPathQueue() {
        boolean found = false;
        for (int i = 0; i < map.getRoom(); i++) {
            Queue<Tile> queue = new Queue<Tile>();
            Tile start = findW(i);
            queue.enqueue(start);

            while (!queue.isEmpty() && !found) {
                Tile current = queue.dequeue();

                if (current.getType() == '$' || current.getType() == '|') {
                    if (current.getType() == '$') {
                        found = true;
                        resetMap();
                        printPath(current);
                    }else {
                    recordPath(current);
                    break;
                    }
                }
                addNeighborsToQueue(queue, current);
            }
        }

        if (!found) {
            System.out.println("The Wolverine Store is closed.");
        }
    }
    
    public static void addNeighborsToQueue(Queue<Tile> queue, Tile current) {
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] direction : directions) {
            int newRow = current.getRow() + direction[0];
            int newCol = current.getCol() + direction[1];
            if (newRow >= 0 && newRow < map.getRow() && newCol >= 0 && newCol < map.getCol()) {
                Tile neighbor = map.getTile(newRow, newCol, current.getRoom());
                if (neighbor.getType() == '.') {
                    neighbor.setType('=');
                    neighbor.setParent(current);
                    queue.enqueue(neighbor);
                }
                if (neighbor.getType() == '$' || neighbor.getType() == '|') {
                    neighbor.setParent(current);
                    queue.enqueue(neighbor);
                }
            }
        }
    }

// optimal (same with stack)
    public static void findPathOpt() {
        boolean found = false;
        for (int i = 0; i < map.getRoom(); i++) {
            Stack<Tile> stack = new Stack<>();
            Tile start = findW(i);
            stack.push(start);

            while (!stack.isEmpty() && !found) {
                Tile current = stack.pop();

                if (current.getType() == '$' || current.getType() == '|') {
                    if (current.getType() == '$') {
                        found = true;
                        resetMap();
                        printPath(current);
                    }else {
                    recordPath(current);
                    break;
                    }
                }
                addNeighborsToStack(stack, current);
            }
        }

        if (!found) {
            System.out.println("The Wolverine Store is closed.");
        }
    }

   
    public static Tile findW() {
        for (int room = 0; room < map.getRoom(); room++) {
            for (int row = 0; row < map.getRow(); row++) {
                for (int col = 0; col < map.getCol(); col++) {
                    if (map.getTile(row, col, room).getType() == 'W') {
                        return map.getTile(row, col, room);
                    }
                }
            }
        }
        return null;
    }

    public static Tile findW(int room) {
        for (int row = 0; row < map.getRow(); row++) {
            for (int col = 0; col < map.getCol(); col++) {
                if (map.getTile(row, col, room).getType() == 'W') {
                    return map.getTile(row, col, room);
                }
            }
        }
        return null;
    }
  ArrayList<Tile> temp = new ArrayList<Tile>();
   
    public static void printPath(Tile end) {
    	ArrayList<Tile> temp = new ArrayList<Tile>();
        ArrayList<Tile> list = new ArrayList<Tile>();
        Tile current = end;
        while (current != null) {
        	temp.add(current);
            current = current.getParent();
        }
        for(int i = temp.size()-2; i>0; i--) {
        	list.add(temp.get(i));
        }
        if (coordinateOut== true) {
            for (Tile tile : list) {
 //           	System.out.println(tile.getType() + " " + tile.getRow()  + " " +tile.getCol()  + " " +tile.getRoom());
                //if (tile.getType() == '+') {
            	if (tile.getType() != 'W' && tile.getType() != '$' && tile.getType() != '|') {
                    System.out.println("+ " + (tile.getRow()+ (rowNum*roomCount)) +" " + tile.getCol() + " " + tile.getRoom());
                }
            }
        } else { //non=coordinate output
            for (Tile tile : list) {
                if (tile.getType() != 'W' && tile.getType() != '$' && tile.getType() != '|') {
                    map.setTile(tile.getRow(), tile.getCol(), tile.getRoom(), new Tile(tile.getRow(), tile.getCol(), tile.getRoom(), '+'));
                    //System.out.println(map.toString());
                }
            }
            System.out.println(map.toString());
        }
        System.out.println("(" + end.getRow() + "," + end.getCol() + ")");
    }
//record path- same code with printPath but no System.out.println()
    public static void recordPath(Tile end) {
    	int multiple = roomCount;
    	ArrayList<Tile> temp = new ArrayList<Tile>();
        ArrayList<Tile> list = new ArrayList<Tile>();
        Tile current = end;
        while (current != null) {
        	temp.add(current);
            current = current.getParent();
        }
        for(int i = temp.size()-2; i>0; i--) {
        	list.add(temp.get(i));
        }
        if (coordinateOut== true) {
            for (Tile tile : list) {
 //           	System.out.println(tile.getType() + " " + tile.getRow()  + " " +tile.getCol()  + " " +tile.getRoom());
                //if (tile.getType() == '+') {
            	if (tile.getType() != 'W' && tile.getType() != '$' && tile.getType() != '|') {
                    System.out.println("+ " + (tile.getRow()+ (rowNum*roomCount))+ " " + tile.getCol() + " " + tile.getRoom());
                }
            }
        } else { //non=coordinate output
            for (Tile tile : list) {
                if (tile.getType() != 'W' && tile.getType() != '$' && tile.getType() != '|') {
                    map.setTile(tile.getRow(), tile.getCol(), tile.getRoom(), new Tile(tile.getRow(), tile.getCol(), tile.getRoom(), '+'));
                    //System.out.println(map.toString());
                }
            }
        }
        roomCount++;
    }
}