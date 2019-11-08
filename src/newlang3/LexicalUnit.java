package newlang3;

public class LexicalUnit {
    LexicalType type;
    Value value;
    LexicalUnit link;

    public LexicalUnit(LexicalType this_type) {
        type = this_type;
    }

    public LexicalUnit(LexicalType this_type, Value this_value) {
        type = this_type;
        value = this_value;
    }

    public Value getValue() {
        return value;
    }

    public LexicalType getType() {
        return type;
    }

    public String toString() {
        switch (type) {
        case LITERAL:
            return "LITERAL:\t" + value.getSValue();
        case NAME:
            return "NAME:\t" + value.getSValue();
        case DOUBLEVAL:
            return "DOUBLEVAL:\t" + value.getSValue();
        case INTVAL:
            return "INTVAL:\t" + value.getSValue();
        default:
            return type.toString();
        }
    }
}
