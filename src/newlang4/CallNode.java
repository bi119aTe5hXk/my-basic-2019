package newlang4;
import java.util.*;
import newlang3.*;
public class CallNode extends Node {
	String funcname;
	ExprListNode args;
	static Set<LexicalType> first = new HashSet<LexicalType>(Arrays.asList(
			LexicalType.NAME
			));
	
	private CallNode(Environment env){
		super(env,NodeType.FUNCTION_CALL);
	}
	
	public static boolean isFirst(LexicalType type){
		return first.contains(type);
	}
	
	public static Node getHandler(Environment env) {
        return new CallNode(env);
    }
	
	
	
	@Override
	public boolean parse() throws Exception {
		boolean bracket = false;
		LexicalUnit lu = env.getInput().peek();
		
		funcname = env.getInput().get().getValue().getSValue();
		
		if (lu.getType() == LexicalType.LP) {
			bracket = true;
            env.getInput().get();
        }
		
		if (ExprListNode.isFirst(lu)) {
			args = ExprListNode.getHandler(env);
			args.parse();
        }
		if (bracket) {
            if(env.getInput().get().getType() != LexicalType.RP) 
            	throw new Exception("syntax error. missing ')' in function.");
        }
		
		return true;
	}
	
	@Override
	public Value getValue() throws Exception {
        return env.getFunction(funcname).invoke(args);
    }
	@Override
	public String toString() {
        return "(func)" + funcname + "{" + args + "}";
    }
	
}
