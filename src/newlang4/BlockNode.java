package newlang4;
import java.util.*;
import newlang3.*;
public class BlockNode extends Node {
		static Set<LexicalType> first=new HashSet<LexicalType>(Arrays.asList(
			LexicalType.DO,
			LexicalType.WHILE,
			LexicalType.IF
		));
		public static boolean isFirst(LexicalUnit lu) {
			
			return first.contains(lu.getType());
		}
		public static Node getHandler(LexicalUnit lu, Environment env) {
			return new BlockNode(env);
		}
		private BlockNode(Environment env){
			super(env,NodeType.BLOCK);
		}
		
		public static Node getHandler(Environment env) throws Exception{
			LexicalUnit lu = env.getInput().peek();
	        if (IfNode.isFirst(lu)) 
	        	return IfNode.getHandler(env);
	        if (LoopNode.isFirst(lu)) 
	        	return LoopNode.getHandler(env);
	        throw new InternalError("make block error");
			
		}
		
		public String toString() {
			return "Block:";
		}
}
