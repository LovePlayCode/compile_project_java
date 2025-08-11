package lexer;

public class Real  extends Token{
    public final float value;
    public Real(float tag) {
        super(Tag.REAL);
        this.value = tag;
        
    }
    public String toString(){
        return "" + value;
    }
}
