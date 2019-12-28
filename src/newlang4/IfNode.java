package newlang4;
import java.util.*;
import newlang3.*;

public class IfNode extends Node {
	private Node condition;
	private Node op;
	private Node elseop;
	
	static  Set<LexicalType> first = EnumSet.of(
			LexicalType.IF
			);
	
	public static boolean isFirst(LexicalUnit lu) {
		return first.contains(lu.getType());
	}
	public static Node getHandler(Environment env) {
		return new IfNode(env);
	}

	private IfNode(Environment env) {
		super(env, NodeType.IF_BLOCK);
	}
	
	@Override
	public boolean parse() throws Exception {
		boolean elseifF = false;
		LexicalUnit lu = env.getInput().peek();
		
		//elseif
		if (lu.getType() == LexicalType.ELSEIF){
			elseifF = true;
        }
		env.getInput().get();
		
		if (CondNode.isFirst(env.getInput().peek())){
            condition = CondNode.getHandler(env);
            condition.parse();
        } else {
        	throw new Exception("missing condition.");
        }
		
		check(LexicalType.THEN);
		
		//
		if (StmtNode.isFirst(env.getInput().peek())) {
            op = StmtNode.getHandler(env);
            op.parse();

            if (env.getInput().expect(LexicalType.ELSE)) {
                env.getInput().get();

                if (StmtNode.isFirst(env.getInput().peek())){
                	elseop = StmtNode.getHandler(env);
                	elseop.parse();
                } else {
                	throw new Exception("syntax error in ELSE");
                }
            }

        } else if (env.getInput().expect(LexicalType.NL)) {
            env.getInput().get();//skip NL

            if (StmtListNode.isFirst(env.getInput().peek())) {
                op = StmtListNode.getHandler(env);
                op.parse();
            }else {
            	throw new Exception("operation is null");
            }
            
            check(LexicalType.NL);

            if (env.getInput().expect(LexicalType.ELSEIF)) {
            	elseop = IfNode.getHandler(env);
            	elseop.parse();

            } else if (env.getInput().expect(LexicalType.ELSE)) {
                env.getInput().get();//skip ELSE

                if (env.getInput().expect(LexicalType.NL)) {
                	env.getInput().get();//skip NL
                	
                    if (StmtListNode.isFirst(env.getInput().peek())){
                    	elseop = StmtListNode.getHandler(env);
                    	elseop.parse();
                    } else {
                    	throw new Exception("syntax error.");
                    }
                    
                    check(LexicalType.NL);
                } 
            }
            
            if (!elseifF){
				if (env.getInput().expect(LexicalType.ENDIF)){
					env.getInput().get();
				} else {
					throw new Exception("missing ENDIF");
				}
			}
        }
		
		return true;
	
	}
	private void check(LexicalType type) throws Exception{
		if (env.getInput().expect(type)){
			env.getInput().get();
		} else {
			throw new Exception("missing " + type);
		}
	}
	@Override
	public Value getValue() throws Exception {
        if (condition.getValue().getBValue()) {
        	op.getValue();
        } else if (elseop != null) {
        	elseop.getValue();
        }
        return null;
    }
	@Override
	public String toString() {
		String str="IF(" + condition + "){"+op+"} ";
		if (elseop != null){
			str += "ELSE { " + elseop +" }";
		}
		return str;
	}
}
