package newlang4;
import newlang3.*;

public class Node {
    NodeType type;
    Environment env;

    /** Creates a new instance of Node */
    public Node() {
    }
    public Node(NodeType type) {
        this.type = type;
    }
    public Node(Environment env) {
        this.env = env;
    }
    public Node(NodeType type, Environment env) {
		this.type = type;
		this.env = env;
	}
    public Node(Environment env, NodeType type) {
		this.type = type;
		this.env = env;
	}
    
    public NodeType getType() {
        return type;
    }
    
//    public abstract Value eval();
    
    public boolean parse() throws Exception {
        return true;
    }
    
    public Value getValue() throws Exception {
        return null;
    }
 
    public String toString() {
    	if (type == NodeType.END) 
    		return "END";
    	else 
    		return "Node";        
    }

}
