package lexer;

public class Word extends Token {
    public final String lexeme;
    public Word(int t,String lexeme) {
        super(t);
        this.lexeme = lexeme;
    }
}
