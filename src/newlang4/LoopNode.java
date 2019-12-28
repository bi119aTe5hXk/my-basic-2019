package newlang4;

import java.util.*;
import newlang3.*;

public class LoopNode extends Node {
	private Node condition;
	private Node op;
	private boolean doF = false;
	private boolean untilF = false;

	static Set<LexicalType> first = new HashSet<>(Arrays.asList(
			LexicalType.WHILE, 
			LexicalType.DO
			));

	public static boolean isFirst(LexicalUnit lu) {
		return first.contains(lu.getType());
	}

	private LoopNode(Environment env) {
		super(env, NodeType.LOOP_BLOCK);
	}

	public static Node getHandler(Environment env) {
		return new LoopNode(env);
	}

	@Override
	public boolean parse() throws Exception {
		if (env.getInput().peek().getType() == LexicalType.WHILE) {
			env.getInput().get();

			if (CondNode.isFirst(env.getInput().peek())) {
				condition = CondNode.getHandler(env);
				condition.parse();
			} else {
				throw new Exception("missing condition.");
				}

			if (env.getInput().get().getType() != LexicalType.NL) {
				throw new Exception("missing NL.");
			}

			if (StmtListNode.isFirst(env.getInput().peek())) {
				op = StmtListNode.getHandler(env);
				op.parse();
			} else {
				throw new Exception("missing condition");
			}

			if (env.getInput().get().getType() != LexicalType.NL) {
				throw new Exception("missing NL.");
			}

			if (env.getInput().expect(LexicalType.WEND)) {
				env.getInput().get();
			} else {
				throw new Exception("missing WEND.");
			}

		} else if (env.getInput().expect(LexicalType.DO)) {
			doF = true;
			
			env.getInput().get();
			doCond();

			while (env.getInput().expect(LexicalType.NL)) {
				env.getInput().get();
			}

			if (StmtListNode.isFirst(env.getInput().peek())) {
				op = StmtListNode.getHandler(env);
				op.parse();
			} else {
				throw new Exception("condition syntax error");
			}
			
			while(env.getInput().expect(LexicalType.NL)) {//skip all NL before LOOP
				env.getInput().get();
			}
			
			if (env.getInput().expect(LexicalType.LOOP)) {
				env.getInput().get();
			} else {
				throw new Exception("syntax error. missing loop.");
			}
			
			if (condition == null) {
				if (!doCond()) {
					throw new Exception("syntax error. missing condition");
				}
			}
		} else {
			throw new Exception("syntax error. missing operator.");
		}
		if (!env.getInput().expect(LexicalType.NL)) {
			throw new Exception("syntax error. missing new line.");
		}
		return true;
	}

	private boolean doCond() throws Exception {
		switch (env.getInput().peek().getType()) {
		case UNTIL:
			untilF = true;
			env.getInput().get();

			if (CondNode.isFirst(env.getInput().peek())) {
				condition = CondNode.getHandler(env);
				condition.parse();
			} else {
				throw new Exception("syntax error. missing condition.");
			}
			break;

		case WHILE:
			env.getInput().get();

			if (CondNode.isFirst(env.getInput().peek())) {
				condition = CondNode.getHandler(env);
				condition.parse();
			} else {
				throw new Exception("syntax error. missing condition.");
			}
			break;
		default:
			return false;
		}
		return true;
	}
	
	private boolean isEnd() throws Exception {
		return ((!condition.getValue().getBValue() && untilF) || 
				(condition.getValue().getBValue() && !untilF));
	}
	
	@Override
	public Value getValue() throws Exception {
        if (doF) 
        	op.getValue();
        
        while (true) {
            if(!isEnd()) {
            	return null;
            }
            op.getValue();
        }
    }
	
	@Override
	public String toString() {
		String str = "";
		if (doF) {
			str += "DO(";
		}else {
			str += "LOOP(";
		}
		if (untilF) {
			str += "UNTIL ";
		}
		str += condition + "){";

		str += op;
		
		return str+="}";
	}
}
