package newlang4;
import java.util.*;
import newlang3.*;

public class EndNode extends Node {
	
	static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(LexicalType.END));
	
	private EndNode(Environment env) {
        super(env,NodeType.END);
    }
public static boolean isFirst(LexicalUnit lu) {
		return first.contains(lu.getType());
	}
	
	public static Node getHandler(Environment env) {
        return new EndNode(env);
    }
	
	@Override
	public boolean parse() throws Exception {
		LexicalUnit first = env.getInput().get();
		if(!EndNode.isFirst(first)) {
			return false;
		}
		return true;
	}
	
	@Override
    public String toString() {
        return "END";
    }
	
	@Override
	public Value getValue(){
		System.exit(0);
		return null;
	}
	
}
