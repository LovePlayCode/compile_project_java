package lexer;

public class Float extends Token {
    public final double value;
    public Float(double value) {
        super(Tag.FLOAT);
        this.value = value;
    }
}
