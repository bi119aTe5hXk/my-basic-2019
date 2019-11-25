package newlang3;

public enum LexicalType {
	LITERAL, // String const
	INTVAL, // Int const
	DOUBLEVAL, // Double const
	NAME, // value
	IF, // IF
	THEN, // THEN
	ELSE, // ELSE
	ELSEIF, // ELSEIF
	ENDIF, // ENDIF
	FOR, // FOR
	FORALL, // FORALL
	NEXT, // NEXT
	EQ, // =
	LT, // <
	GT, // >
	LE, // <=, =<
	GE, // >=, =>
	NE, // <>
	FUNC, // SUB
	DIM, // DIM
	AS, // AS
	END, // END
	NL, // Next Line
	DOT, // .
	WHILE, // WHILE
	DO, // DO
	UNTIL, // UNTIL
	ADD, // +
	SUB, // -
	MUL, // *
	DIV, // /
	LP, // )
	RP, // (
	COMMA, // ,
	LOOP, // LOOP
	TO, // TO
	WEND, // WEND
	EOF, // end of file
}
