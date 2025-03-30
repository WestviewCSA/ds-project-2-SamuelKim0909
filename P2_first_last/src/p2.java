import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class p2 {
	static int roomCount;
	static int rowNum;
    static Map map = null;
    static boolean useStack = false;
    static boolean useQueue = false;
    static boolean useOpt = false;
    static boolean printTime = false;
    static boolean inputCoordinate = false;
    static boolean outputCoordinate = false;
    static boolean extraCredit = false;
    
    public static void main(String[] args) {
        try {
            processCommandLineArgs(args);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        } 
    }
//arguration
    public static void processCommandLineArgs(String[] args) throws IllegalArgumentException{
        String filename = null;
        for (String arg : args) {
            switch (arg) {
                case "--Stack": useStack = true; break;
                case "--Queue": useQueue = true; break;
                case "--Opt": useOpt = true; break;
                case "--Time": printTime = true; break;
                case "--Incoordinate": inputCoordinate = true; break;
                case "--Outcoordinate": outputCoordinate = true; break;
                case "--Help": printHelp(); System.exit(0); break;
                case "--EC": extraCredit = true; break;
                default:
                    if (arg.startsWith("--")) {
                        throw new IllegalArgumentException("Invalid command line argument: " + arg);
                    }
                    filename = arg;
                    break;
            }
        }

        if (filename == null) {
            throw new IllegalArgumentException("Missing filename");
        }

        int algorithmCount = 0;
        if (useStack) algorithmCount++;
        if (useQueue) algorithmCount++;
        if (useOpt) algorithmCount++;

        if (algorithmCount != 1) {
            throw new IllegalArgumentException("Exactly one of --Stack, --Queue, or --Opt must be specified");
        }
        readMap(filename);
    }
// map input
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

            if (inputCoordinate == true) {
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
        if (printTime) {
            System.out.println("Total Runtime: " + (time2 - time1) / 1e9 + " seconds");
        }        
    }
// all '=' to '.'
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
//North South East West to Stack
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
    
    // North South East West into queue;
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

// optimal (same with queue)
    public static void findPathOpt() {
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
  //ArrayList<Tile> temp = new ArrayList<Tile>();
    
   // prints '+'s / coornidates
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
        if (outputCoordinate== true) {
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
        System.out.println("(" + (end.getRow()+ (rowNum*roomCount)) + "," + end.getCol() + ")");
    }
//record path- same code with printPath but no System.out.println()
    public static void recordPath(Tile end) {
    	//int multiple = roomCount;
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
        if (outputCoordinate== true) {
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
    
    public static void printHelp() {
        System.out.println("Usage: java p2 [options] filename");
        System.out.println("Options:");
        System.out.println("  --Stack        Use stack-based algorithm");
        System.out.println("  --Queue        Use queue-based algorithm");
        System.out.println("  --Opt          Use optimal path algorithm");
        System.out.println("  --Time         Print runtime");
        System.out.println("  --Incoordinate  Input is in coordinate format");
        System.out.println("  --Outcoordinate Output is in coordinate format");
        System.out.println("  --Help         Print this help message");
        System.out.println("  --EC           Enable extra credit mode");
    }
}