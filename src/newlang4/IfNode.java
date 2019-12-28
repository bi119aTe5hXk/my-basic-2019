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
		
		if (lu.getType() == LexicalType.ELSEIF){
			elseifF = true;
        }
		env.getInput().get();
		
		if (CondNode.isFirst(env.getInput().peek())){
            condition = CondNode.getHandler(env);
            condition.parse();
        } else {
        	throw new Exception("syntax error. missing condition.");
        }
		
		if (env.getInput().get().getType() != LexicalType.THEN) 
			throw new Exception("syntax error. missing then.");
		
		if (StmtNode.isFirst(env.getInput().peek())) {
            op = StmtNode.getHandler(env);
            op.parse();

            if (env.getInput().peek().getType() == LexicalType.ELSE) {
                env.getInput().get();

                // check process when ELSE
                if (StmtNode.isFirst(env.getInput().peek())){
                	elseop = StmtNode.getHandler(env);
                	elseop.parse();
                } else {
                	throw new Exception("syntax error. missing operation.");
                }
            }

        } else if (env.getInput().peek().getType() == LexicalType.NL) {
            env.getInput().get();

            if (StmtListNode.isFirst(env.getInput().peek())) {
                op = StmtListNode.getHandler(env);
                op.parse();
            }

            if (env.getInput().peek().getType() == LexicalType.ELSEIF) {
            	elseop = IfNode.getHandler(env);
            	elseop.parse();

            } else if (env.getInput().peek().getType() == LexicalType.ELSE) {
                env.getInput().get();

                if (env.getInput().get().getType() == LexicalType.NL) {

                    if (StmtListNode.isFirst(env.getInput().peek())){
                    	elseop = StmtListNode.getHandler(env);
                    	elseop.parse();
                    } else throw new Exception("syntax error.");

                    if (env.getInput().get().getType() != LexicalType.NL) 
                    	throw new Exception("syntax error. missing NL.");
                } else 
                	throw new Exception("syntax error. missing NL.");
            } else 
            	throw new Exception("syntax error. it need ELSEIF or ELSE.");

            if (!elseifF) {
                if (env.getInput().get().getType() != LexicalType.NL) 
                	throw new Exception("syntax error. missing NL.");
            }

            if (env.getInput().get().getType() != LexicalType.ENDIF) 
            	throw new Exception("syntax error. missing ENDIF.");
        }
		
		return true;
	
	}
	@Override
	public Value getValue() throws Exception {
        if (condition.getValue().getBValue()) 
        	op.getValue();
        else if (elseop != null) 
        	elseop.getValue();
        return null;
    }
	
}
