
public class Queue<S> {
	//data   must only use stack(s)
	
	private Stack<S> stackS;
	private Stack<S> stackK;
	
	
	public Queue() {
		stackS = new Stack<S>();
		stackK = new Stack<S>();
		
	}
	//add to the queue
	
	public void enqueue(S el) {
		stackS.push(el);
	}
	// remove from the queue
	//return null if empty
	
	public S dequeue() {
		//if(queue is empty){
		// don't try to access stuff;
		//}
		int size = stackS.size();
		for(int i = 0; i<size; i++) {
			stackK.push(stackS.pop());
		}
		S temp = stackK.pop();
		for(int i = 0; i<size-1; i++) {
			stackS.push(stackK.pop());
		}
		return temp;
	}

	public S peek() {
		int size = stackS.size();
		for(int i = 0; i<size; i++) {
			stackK.push(stackS.pop());
		}
		S temp = stackK.peek();
		for(int i = 0; i<size; i++) {
			stackS.push(stackK.pop());
		}
		return temp;
	}
	
	public int size() {
		return stackS.size();
	}
	
	public String toString() {
		String result = "[";
		result += dequeue()+"";
		for(int i = 0; i<stackS.size(); i++) {
			result += ", ";
			result +=dequeue()+"";
		}
		result+= "]";
		return result;
	}
	
	public boolean empty() {
		if(stackS.size() == 0) {
			return true;
		}else {
			return false;
		}
	}
}
