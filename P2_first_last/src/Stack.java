import java.util.ArrayList;

public class Stack<z>{
	
	private ArrayList<z> data;
	
	//add a constructor
	public Stack(){
		data = new ArrayList<z>();
	
	}
	
	public ArrayList<z> getData(){
		return data;
	}
	
	public void push(z el) {
		data.add(el);
	}
	
	public z pop() {
		z temp = data.get(data.size()-1);
		data.remove(data.size()-1);
		return temp;
	}
	public z peek() {
		z temp = data.get(data.size()-1);
		return temp;
	}
	
	public int size() {
		return data.size();
	}
	
	public boolean isEmpty() {
		return data.isEmpty();
	}
	
	public String toString() {
		return data.toString();
	}
	
}