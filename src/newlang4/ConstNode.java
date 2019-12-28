package newlang4;
import java.util.*;

import newlang3.*;
public class ConstNode extends Node{
	Value val;
	static Set<LexicalType> FIRST=new HashSet<>(Arrays.asList(
			LexicalType.SUB,
			LexicalType.INTVAL,
			LexicalType.DOUBLEVAL,
			LexicalType.LITERAL
		));
	
	private ConstNode(Environment env,Value v){
		super(env);
		switch (v.getType()){
			case INTEGER:
				type = NodeType.INT_CONSTANT;
				break;
			case DOUBLE:
				type = NodeType.DOUBLE_CONSTANT;
				break;
			case STRING:
				type = NodeType.STRING_CONSTANT;
				break;
			default:
				throw new InternalError("Unknown value's type");
		}
		val = v;
	}

	public static Node getHandler(Environment env,Value v){
		return new ConstNode(env,v);
	}
	public static Node getHandler(Value v,Environment env){
		return new ConstNode(env,v);
	}
	public String toString() {
        String str = "(const)";
        switch (type) {
            case INT_CONSTANT:
            	str += val.getIValue();
                break;
            case DOUBLE_CONSTANT:
            	str += val.getDValue();
                break;
            case STRING_CONSTANT:
            	str += val.getSValue();
                break;
        }
        return str;
    }
}
