package lexer;

import java.io.IOException;
import java.util.Hashtable;

public class Lexer {
    public int line = 1;
    private char peek = ' ';
    private Hashtable<String, Word> words = new Hashtable<>();
    void reserve(Word word) {
        words.put(word.lexeme, word);
    }
    public Lexer() {
        reserve(new Word(Tag.TRUE,"true"));
        reserve(new Word(Tag.FALSE,"false"));
    }
    public Token scan() throws IOException {
        while (true) {
            // 跳过空白字符
            for(;;peek = (char) System.in.read()) {
                if(peek == ' ' || peek=='\t') continue;
                else if (peek == '\n') {
                    line = line + 1;
                }else{
                    break;
                }
            }
            
            // 处理单行注释 //
            if(peek == '/') {
                peek = (char) System.in.read();
                if(peek == '/') {
                    // 跳过单行注释
                    do {
                        peek = (char) System.in.read();
                    } while(peek != '\n' && peek != '\0');
                    if(peek == '\n') {
                        line = line + 1;
                    }
                    // 继续循环，跳过注释后重新开始
                    continue;
                } else if(peek == '*') {
                    // 处理多行注释 /* */
                    do {
                        peek = (char) System.in.read();
                        if(peek == '\n') {
                            line = line + 1;
                        }
                        if(peek == '*') {
                            peek = (char) System.in.read();
                            if(peek == '/') {
                                peek = (char) System.in.read();
                                break;
                            }
                        }
                    } while(peek != '\0');
                    // 继续循环，跳过注释后重新开始
                    continue;
                } else {
                    // 这是一个除法运算符 /
                    return new Token('/');
                }
            }

            // 识别关系运算符: <, <=, >, >=, ==, !=
            if (peek == '<') {
                char next = (char) System.in.read();
                if (next == '=') {
                    peek = ' ';
                    return new Token(Tag.LE);
                } else {
                    // 单个 '<'，保留读到的下一个字符
                    Token t = new Token('<');
                    peek = ' ';
                    return t;
                }
            }
            if (peek == '>') {
                char next = (char) System.in.read();
                if (next == '=') {
                    peek = ' ';
                    return new Token(Tag.GE);
                } else {
                    Token t = new Token('>');
                    peek = ' ';
                    return t;
                }
            }
            if (peek == '=') {
                char next = (char) System.in.read();
                if (next == '=') {
                    peek = ' ';
                    return new Token(Tag.EQ);
                } else {
                    Token t = new Token('=');
                    peek = ' ';
                    return t;
                }
            }
            if (peek == '!') {
                char next = (char) System.in.read();
                if (next == '=') {
                    peek = ' ';
                    return new Token(Tag.NE);
                } else {
                    Token t = new Token('!');
                    peek = ' ';
                    return t;
                }
            }

            // 如果到这里，说明不是注释，跳出循环处理其他token
            break;
        }

        if(Character.isDigit(peek)) {
            int v = 0;
            do{
                v = v * 10 + Character.digit(peek, 10);
                peek = (char) System.in.read();
            }while (Character.isDigit(peek));
            
            // 检查是否是浮点数
            if(peek == '.') {
                double floatValue = v;  // 整数部分
                double decimal = 0.1;   // 小数位权重
                peek = (char) System.in.read();
                
                // 读取小数部分
                while(Character.isDigit(peek)) {
                    floatValue += Character.digit(peek, 10) * decimal;
                    decimal *= 0.1;
                    peek = (char) System.in.read();
                }
                return new lexer.Float(floatValue);
            }
            
            return new Num(v);
        }
        if(Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do{
                b.append(peek);
                peek = (char) System.in.read();
            }while (Character.isLetter(peek));
            String s = b.toString();
            Word word = words.get(s);
            if(word != null) {
                return word;
            }
            word = new Word(Tag.ID,s);
            words.put(s, word);
            return word;
        }
        Token t = new Token(peek);
        peek = ' ';
        return t;
    }
}
