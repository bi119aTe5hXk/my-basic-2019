package newlang4;

import java.util.*;
import newlang3.*;

public class ExprListNode extends Node {
	List<Node> list = new ArrayList<>();
	static Set<LexicalType> first = new HashSet<>(Arrays.asList(
			LexicalType.NAME,
			LexicalType.SUB,
			LexicalType.LP,
			LexicalType.INTVAL,
			LexicalType.DOUBLEVAL,
			LexicalType.LITERAL
		));
	
	public static boolean isFirst(LexicalUnit lu){
		return first.contains(lu.getType());
	}

	private ExprListNode(Environment env){
		super(env,NodeType.EXPR_LIST);
	}
	public static ExprListNode getHandler(Environment env){
		return new ExprListNode(env);
	}
	@Override
	public boolean parse() throws Exception{
		if (ExprNode.isFirst(env.getInput().peek(1))){
			Node handler=ExprNode.getHandler(env);
			handler.parse();
			list.add(handler);
		} else {
			throw new InternalError("incorrect arguments");
		}

		while(true){
			if (env.getInput().expect(LexicalType.COMMA)){
				env.getInput().get();
			} else {
				break;
			}

			if (ExprNode.isFirst(env.getInput().peek(1))){
				Node handler=ExprNode.getHandler(env);
				handler.parse();
				list.add(handler);
			} else {
				throw new InternalError("function syntax error");
			}
		}
		return true;
	}
	public int size(){
		return list.size();
	}
	public Value get(int i) throws Exception{
		if (list.size()>i){
			return list.get(i).getValue();
		}else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		String str = "ExprList:";
        for (int i = 0; i < list.size(); i++) {
        	str += list.get(i) + " ";
        }
//        str += "]";
        return str;
		
	}
}
