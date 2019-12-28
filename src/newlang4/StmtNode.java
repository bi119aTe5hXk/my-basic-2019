package newlang4;

import java.util.*;
import newlang3.*;

public class StmtNode extends Node{
	private static Set<LexicalType> firstSet = new HashSet<LexicalType>();
	static {
		firstSet.add(LexicalType.NAME);
		firstSet.add(LexicalType.FOR);
		firstSet.add(LexicalType.END);
	}
	//private Node body;
	
	private StmtNode(Environment env){
		super(env,NodeType.STMT);
	}


	
	public static boolean isFirst(LexicalUnit lu) {
		return firstSet.contains(lu.getType());
	}
	
	
	public static Node getHandler(Environment env) throws Exception{
//		return new StmtNode(env);
//		if (!isMatch(type)) 
//			return null;
//		
//		switch(type) {
//		case NAME:
//			if (type == LexicalType.EQ){
//				
//			}
//			return null;
//		case FOR:
//			
//			return ForNode.getHandler(env);
//		case END:
//			return EndNode.getHandler(env);
//		default:
//			
//			return null;
//		}
		switch (env.getInput().peek().getType()){
		case NAME:
			LexicalUnit lu = env.getInput().peek(2);
			//System.out.println(lu.getType());
			if (lu.getType() == LexicalType.EQ){
				return SubstNode.getHandler(env);
				
			} else if (ExprListNode.isFirst(lu)){
				return CallNode.getHandler(env);
				
			} else {
				throw new InternalError("Syntax error.");
			}
		case FOR:
			return ForNode.getHandler(env);
		case END:
			return EndNode.getHandler(env);
		default:
			throw new InternalError("A type which is not fit in StmtNode.");
	}
	}
	
	@Override
	public boolean parse() throws Exception {
//		LexicalUnit lu = env.getInput().get();
//		env.getInput().unget(lu);
//		
//		body = SubstNode.getHandler(lu,env);
//		if(body != null){
//			return body.parse();
//		}
//		body = CallNode.getHandler(lu,env);
//		if(body != null){
//			return body.parse();
//		}
//		if (lu.getType() == LexicalType.END) {
//			super.type = NodeType.END;
//			return true;
//		}
		return false;//no parse here

	}
	public String toString() {
        return "StmtNode";
    }
}
