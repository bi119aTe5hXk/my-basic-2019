package newlang4;

import java.util.*;
import newlang3.*;

public class SubstNode extends Node {
//	LexicalUnit first;
	Node handler;
	String left=null;
	static Set<LexicalType> firstSet = new HashSet<LexicalType>(Arrays.asList(LexicalType.NAME));

	private SubstNode(Environment env) {
        super(env,NodeType.ASSIGN_STMT);
//        first = lu;
    }
	
	public static boolean isFirst(LexicalUnit lu) {
		return firstSet.contains(lu.getType());
	}
	
	public static Node getHandler( Environment env) {
        return new SubstNode(env);
    }
	
	@Override
	public boolean parse() throws Exception{
		
//		first = env.getInput().get();
//		if(VariableNode.isFirst(first)) {
//			handler = VariableNode.getHandler(first,env);
//			handler.parse();
//		}
//		
//		first = env.getInput().get();
//		if(first.getType() != LexicalType.EQ) return false;
//		
//		first = env.getInput().get();
//		if(ExprNode.isFirst(first)) {
//			handler = ExprNode.getHandler(first, env);
//			System.out.println("expr");
//			handler.parse();
//		}
//		return false;
		
		if (env.getInput().expect(LexicalType.NAME)){
			left = env.getInput().get().getValue().getSValue();
		} else {
			throw new InternalError("incorrect var name");
		}
		
		if (env.getInput().expect(LexicalType.EQ)){
			env.getInput().get();
		} else {
			throw new InternalError("No = fond in here.");
		}
		
		if (ExprNode.isFirst(env.getInput().peek())){
			handler = ExprNode.getHandler(env);
			handler.parse();
		}else {
			throw new InternalError("The right side of = is incorrect");
		}
		return true;
		
		
		
	}
	
	@Override
    public String toString() {
        //return right.toString();
		return left + ":" + handler;
    }
	
	@Override
	public Value getValue() throws Exception{
		Value val = handler.getValue();
		env.getVariable(left).setValue(val);
//		left.setValue(val);
//		reutrn val;
		return null;
	}
}
