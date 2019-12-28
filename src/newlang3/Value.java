package newlang3;

/**
 * Value interface
 */
public interface Value {
	// conststra
	
//    public Value(String s) {
//    };
//    public Value(int i) {
//    };
//    public Value(double d) {
//    };
//    public Value(boolean b) {
//    };
//    public Value(String s, ValueType t) {
//    };

	public abstract String getSValue();

	// Output as String, convert if nessnary
	public abstract int getIValue();

	// out as Int
	public abstract double getDValue();

	// Out as double
	public abstract boolean getBValue();

	// out as bool
	public abstract ValueType getType();
}
