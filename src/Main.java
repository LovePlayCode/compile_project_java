import lexer.Lexer;
import lexer.Num;
import lexer.Word;
import lexer.Tag;

import java.io.IOException;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) throws IOException {
        var lexer = new Lexer();
        var res = lexer.scan();
 
        // 改进打印方式，显示token的详细信息
        if (res instanceof Num) {
            Num num = (Num) res;
            System.out.println("数字: " + num.value);
        } else if (res instanceof Word) {
            Word word = (Word) res;
            String type = "";
            switch (word.tag) {
                case Tag.ID:
                    type = "标识符";
                    break;
                case Tag.TRUE:
                    type = "关键字";
                    break;
                case Tag.FALSE:
                    type = "关键字";
                    break;
                default:
                    type = "未知";
            }
            System.out.println(type + ": " + word.lexeme);
        } else {
            // 处理关系运算符和单字符运算符的输出
            String op;
            if (res.tag == Tag.LE) {
                op = "<=";
            } else if (res.tag == Tag.GE) {
                op = ">=";
            } else if (res.tag == Tag.EQ) {
                op = "==";
            } else if (res.tag == Tag.NE) {
                op = "!=";
            } else if (res.tag == '<' || res.tag == '>' || res.tag == '=' || res.tag == '!') {
                op = String.valueOf((char) res.tag);
            } else {
                op = String.valueOf((char) res.tag);
            }
            System.out.println("运算符/符号: " + op);
        }
    }
}