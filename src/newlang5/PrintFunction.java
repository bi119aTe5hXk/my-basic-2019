package newlang5;
import newlang3.*;
import newlang4.*;
public class PrintFunction extends Function{
	@Override
	public Value invoke(ExprListNode arg) throws Exception{
		if (arg==null || arg.size()==0){
			throw new Exception("parameter is null");
		}
		System.out.print(arg.get(0).getSValue());
		return null;        
    }
}
