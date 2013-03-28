import java.io.IOException;
import java.io.InputStreamReader;

public class Task1 {


}

enum Lexem {
    LEFT_P, //<
    LEFT_S, ID, NUM, QUOTE, ARROW, ST_LN, ///

}
class LexScanner {
    //private Scanner scanner;
    private InputStreamReader reader;
    enum state {H, IDENT, NUMB, DELIM, HYPHEN, }
    Character[] buf = new Character[80];
    int buf_top;
    state CS;
    Character c;

    LexScanner() {
        //scanner = new Scanner(new InputStreamReader(System.in));
        reader = new InputStreamReader(System.in);
    }

    private void clear(){
        buf_top = 0;
        for (int i = 0; i < 80; i++)
            buf[i] = null;
    }

    private void add(){
        buf [buf_top ++] = c;
    }

    private void gc(){
        try {
            int r = reader.read();
            if (r != -1)
                c = (char) r;
            else
                c = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Lexem getLexem(){
        int d, j;

        CS = state.H;
        do {
            switch (CS){
                case H:
                    if ( c == ' ' || c == '\n' || c == '\r' || c == '\t')
                        gc();
                    else if (Character.isAlphabetic(c))
                    {
                        clear();
                        add();
                        gc();
                        CS = state.IDENT;
                    }
                    else if (Character.isDigit(c))
                    {
                        d = c - '0';
                        gc();
                        CS = state.NUMB;
                    }
                    else if (c == '<')
                    {
                        gc();
                        return Lexem.LEFT_P;
                    }
                    else if ( c == '/')
                    {
                        gc();
                        return Lexem.LEFT_S;
                    }
                    else if (c == '\"')
                    {
                        gc();
                        return Lexem.QUOTE;
                    }
                    else if (c == '-')
                    {
                        clear();
                        add();
                        gc();
                        CS = state.HYPHEN;
                    }
                    else if (c == '|')
                    {
                        gc();
                        return Lexem.ST_LN;
                    }
                    else
                        CS = state.DELIM;
                    break;
                case IDENT:
                    if (Character.isAlphabetic(c) || Character.isDigit(c)){
                        add();
                        gc();
                    }
                    else
                        return Lexem.ID;
                    break;
                case NUMB:
                    if (Character.isDigit(c))
                    {
                        gc();
                    }
                    else
                        return Lexem.NUM;
                    break;
                case HYPHEN:
                    if (c == '>'){
                        gc();
                        return Lexem.ARROW;
                    }

                    break;
            }
        } while (true);
    }

}

class SemParser {
    private LexScanner scanner;

}
