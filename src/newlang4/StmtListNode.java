package newlang4;

import java.util.*;
import newlang3.*;

public class StmtListNode extends Node{
	List<Node> stmt_list = new ArrayList<>();
	
	static Set<LexicalType> first = EnumSet.of(
			LexicalType.NAME,
			LexicalType.FOR,
			LexicalType.END,
			LexicalType.IF,
			LexicalType.WHILE,
			LexicalType.DO,
			LexicalType.NL
			);
	
	static boolean isFirst(LexicalUnit lu) {
		return first.contains(lu.getType());
	}
	
	
	
	private StmtListNode(Environment env) {
		super(env,NodeType.STMT_LIST);
	}
	
	public static Node getHandler(Environment env) {
		return new StmtListNode(env);
	}
	
	
	public Value getValue() throws Exception {
		for(int i=0;i<stmt_list.size();i++){
			stmt_list.get(i).getValue();
		}
//	    for (Node stmt : stmt_list) {
//	      stmt.getValue();
//	    }
        return null;
    }
	
	@Override
	public boolean parse() throws Exception {

		
		while(true){
			//skip NL
			while(env.getInput().peek().getType() == LexicalType.NL && 
					StmtListNode.isFirst(env.getInput().peek(2))){
				env.getInput().get();
			}

			Node handler=null;
			LexicalUnit lu = env.getInput().peek();
			//System.out.println(lu.getType());
			if (StmtNode.isFirst(lu)){
				
				handler = StmtNode.getHandler(env);
				
			} else if (BlockNode.isFirst(lu)){
				
				handler = BlockNode.getHandler(env);
				
			} else {
				return true;
			}
			handler.parse();
			stmt_list.add(handler);
		}
    }
	
	@Override
	public String toString() {
		return "stmt_list:" + stmt_list.toString();
//		String str = "stmt_list(";
//	    for (Node stmt : stmt_list) {
//	      str += stmt.toString() + ",\n";
//	    }
//	    return str + ")";

	}
	
	
}
