package newlang4;

import java.util.*;

import newlang3.*;

public class ProgramNode extends Node {
	Node list;
	static Set<LexicalType> first=new HashSet<LexicalType>(Arrays.asList(
			LexicalType.IF,
			LexicalType.WHILE,
			LexicalType.DO,
			LexicalType.NAME,
			LexicalType.FOR,
			LexicalType.END,
			LexicalType.NL
		));
	public static boolean isFirst(LexicalUnit lu) {
		return StmtListNode.isFirst(lu);
	}
	private ProgramNode(LexicalUnit first, Environment env) {
		super(env);
	}
	private ProgramNode(Environment env) {
		super(env,NodeType.PROGRAM);
	}
	
	public static Node getHandler(LexicalUnit lu, Environment env) {
		return StmtListNode.getHandler(env);
		//return new ProgramNode(env);
	}
	

	public Value getValue() throws Exception{
		if (list!=null){
			return list.getValue();
		}
		return null;
	}
	
	public String toString() {
        if (type == NodeType.PROGRAM) 
        	return "PROGRAM";
        else 
        	return "Node";
    }
	
	@Override
	  public boolean parse() throws Exception {
		LexicalUnit first = env.getInput().get();

        if (StmtListNode.isFirst(first)) {
        	Node handler = StmtListNode.getHandler(env);
        	handler.parse();
        }

		return true;
	  }
}
