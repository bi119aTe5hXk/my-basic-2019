package newlang3;

/**
 * ValueImpl
 */
public class ValueImpl extends Value {

    private ValueType type;
	private String val;
	
	public ValueImpl(String s, ValueType t) {
        super(s, t);
        this.val = s;
        this.type = t;
    }

	public ValueImpl(String s) {
		super(s);
        this.val = s;
	}
	
	public ValueImpl(int i) {
		super(i);
	}
	
	public ValueImpl(double d) {
		super(d);
	}
	
	public ValueImpl(boolean b) {
		super(b);
	}

    @Override
    public boolean getBValue() {

        return Boolean.parseBoolean(this.val);
    }

    @Override
    public int getIValue() {

        return Integer.parseInt(this.val);
    }

    @Override
    public double getDValue() {

        return Double.parseDouble(this.val);
    }

    @Override
    public String getSValue() {
    	
    	return this.val;
    }

    @Override
    public ValueType getType() {

        return this.type;
    }

}