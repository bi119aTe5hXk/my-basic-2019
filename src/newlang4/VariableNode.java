package newlang4;
import java.util.*;
import newlang3.*;
public class VariableNode extends Node {
	   String var_name;
	    Value v;
	    static Set<LexicalType> first=new HashSet<>(Arrays.asList(
	    		LexicalType.NAME
	    	));
	    /** Creates a new instance of variable */
	    public VariableNode(String name) {
	        var_name = name;
	    }
	    public VariableNode(LexicalUnit u) {
	        var_name = u.getValue().getSValue();
	    }
	    public static boolean isFirst(LexicalUnit lu) {
			return first.contains(lu.getType());
		}
	    public static boolean isMatch(LexicalType first) {
	        return (first == LexicalType.NAME);
	    }
	    
	    public static Node getHandler(Environment my_env,LexicalUnit first) {
	    	return getHandler(first,my_env);
	    }
	    
	    public static Node getHandler(LexicalUnit first, Environment my_env) {
	        if (first.getType() == LexicalType.NAME) {
	        	VariableNode v;

//	        	try {
//		        	LexicalUnit lu = my_env.getInput().get();
//		        	my_env.getInput().unget(lu);
//		            String s = lu.getValue().getSValue();//this return null will cause exception
//		            v = my_env.getVariable(s);
//		            return v;
		        	return new VariableNode(first.getValue().getSValue());
//	        	}
//	        	catch(Exception e) {}
	        }
	        return null;
	    }
	    
	    public void setValue(Value my_v) {
	        v = my_v;
	    }
	    
	    public Value getValue() {
	        return v;
	    }
	    
	    public String toString() {
	    	return "(var)" + var_name;
	    }
	    
}
