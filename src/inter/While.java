package inter;

public class While extends Stmt{
    Expr expr;
    Stmt stmt;
    public While(){
        expr = null;
        stmt = null;
    }
    public void gen(int b,int a){
        after = a;
        // 条件为真执行循环体(直接继续到后续代码)，条件为假跳出循环(跳到 after)
        // 生成代码时，只需要"假时跳转到a(after)，真时不需要跳转，直接顺序落到循环体代码即可"
        expr.jumping(0, a);
        int label = newLabel();
        emitlabel(label);
        stmt.gen(label, b);
        emit("goto L" + b);
    }
}
