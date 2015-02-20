//import java.io.*;
//import java.util.Scanner;
//
//public class Task1 {
//
//    public static void main(String[] args) {
//        InputStreamReader isr =  new InputStreamReader(System.in);
//        //CharArrayReader cr = new CharArrayReader() ;
//        Scanner scanner = new Scanner(isr);
//        String in = "";
//        while (scanner.hasNextLine())
//        {
//            in += scanner.nextLine();
//            //char[] chars = scanner.next().toCharArray();
///*        SemParser sp = new SemParser(chars);
//        sp.analyze();*/
//        }
//        SemParser sp = new SemParser(in);
//
//        try {
//            sp.analyze();
//        } catch (IllegalSymbolException e) {
//            System.out.println("incorrect " + e.getMessage());
//        }
//    }
//
//}
//
//enum Lexem {
//    LEFT_P, //<
//    LEFT_S, ID, NUM, QUOTE, ARROW, ST_LN, DOT, COLON, RIGHT_P, END, STRING, ///
//
//}
//class LexScanner {
//    //private Scanner scanner;
//    private Reader reader;
//
//    public LexScanner(final String s) {
//        /*reader = new InputStreamReader(new InputStream() {
//            private int i = -1;
//            @Override
//            public int read() throws IOException {
//                i++;
//                return s.charAt(i);
//            }
//        });*/
//        reader = new StringReader(s);
//
//        CS = state.H;
//        clear();
//        gc();
//    }
//
//    public LexScanner(InputStreamReader isr) {
//        reader = isr;
//        CS = state.H;
//        clear();
//        gc();
//    }
//
//    public LexScanner(final char[] chars) {
//        reader = new InputStreamReader(new InputStream() {
//            private int i = -1;
//            @Override
//            public int read() throws IOException {
//                i++;
//                return chars[i];
//            }
//        });
//
//        CS = state.H;
//        clear();
//        gc();
//    }
//
//    enum state {H, IDENT, NUMB, DELIM, HYPHEN, STRING, }
//    Character[] buf = new Character[80];
//    int buf_top;
//    state CS;
//    Character c;
//
//    LexScanner() {
//        //scanner = new Scanner(new InputStreamReader(System.in));
//        reader = new InputStreamReader(System.in);
//    }
//
//    private void clear(){
//        buf_top = 0;
//        for (int i = 0; i < 80; i++)
//            buf[i] = null;
//    }
//
//    private void add(){
//        buf [buf_top ++] = c;
//    }
//
//    private void gc(){
//        try {
//            int r = reader.read();
//            if (r != -1)
//                c = (char) r;
//            else
//                c = null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public Lexem getLexem() throws IllegalSymbolException {
//        int d, j;
//
//        CS = state.H;
//        clear();
//        do {
//            switch (CS){
//                case H:
//                    if (c == null)
//                        return Lexem.END;
//                    if ( c == ' ' || c == '\n' || c == '\r' || c == '\t')
//                        gc();
//                    else if (Character.isAlphabetic(c))
//                    {
//                        clear();
//                        add();
//                        gc();
//                        CS = state.IDENT;
//                    }
//                    else if (Character.isDigit(c))
//                    {
//                        d = c - '0';
//                        gc();
//                        CS = state.NUMB;
//                    }
//                    else if (c == '<')
//                    {
//                        gc();
//                        return Lexem.LEFT_P;
//                    }
//                    else if (c == '>')
//                    {
//                        gc();
//                        return Lexem.RIGHT_P;
//                    }
//                    else if ( c == '/')
//                    {
//                        gc();
//                        return Lexem.LEFT_S;
//                    }
//                    else if (c == '\"')
//                    {
//                        clear();
//                        gc();
//                        CS = state.STRING;
//                        //return Lexem.QUOTE;
//                    }
//                    else if (c == '-')
//                    {
//                        clear();
//                        add();
//                        gc();
//                        CS = state.HYPHEN;
//                    }
//                    else if (c == '|')
//                    {
//                        gc();
//                        return Lexem.ST_LN;
//                    }
//                    else if (c == '.')
//                    {
//                        gc();
//                        return Lexem.DOT;
//                    }
//                    else if (c == ':')
//                    {
//                        gc();
//                        return Lexem.COLON;
//                    }
//                    else if (c == null)
//                    {
//                        return Lexem.END;
//                    }
//                    else
//                        CS = state.DELIM;
//                    break;
//                case IDENT:
//                    if (c == null){
//                        if (buf_top == 0)
//                            return Lexem.END;
//                        else {
//                            gc();
//                            return Lexem.ID;
//                        }
//                    }
//                    else if (Character.isAlphabetic(c) || Character.isDigit(c)){
//                        add();
//                        gc();
//                    }
//                    else
//                        return Lexem.ID;
//                    break;
//                case NUMB:
//                    if ( c == null ){
//                        if (buf_top == 0)
//                            return Lexem.END;
//                        else {
//                            gc();
//                            return Lexem.NUM;
//                        }
//                    }
//                    if (Character.isDigit(c))
//                    {
//                        gc();
//                    }
//                    else
//                        return Lexem.NUM;
//                    break;
//                case STRING:
//                    if (c == null)
//                        throw new IllegalSymbolException("expected string");
//                    else if (c == '\"'){
//                        gc();
//                        return Lexem.STRING;
//                    }
//                    else {
//                        add();
//                        gc();
//                    }
//                    break;
//                case HYPHEN:
//                    if (c == null){
//                        return Lexem.END;
//                    }
//                    else if (c == '>'){
//                        gc();
//                        return Lexem.ARROW;
//                    }
//                    else
//                    if (Character.isDigit(c)){
//                        //d =
//                        gc();
//                        CS = state.NUMB;
//                    }
//                    else
//                        throw new IllegalSymbolException("Expected > or number");
//                    break;
//                case DELIM:
//                    clear();
//                    add();
//                    break;
//            }
//        } while (true);
//    }
//
//}
//
//class SemParser {
//    private LexScanner scanner ;//= new LexScanner();
//    private Lexem curLex;
//    private int spaceCount = 0;
//
//    public SemParser(String s) {
//        scanner = new LexScanner(s);
//    }
//
//    public SemParser(InputStreamReader isr) {
//        scanner = new LexScanner(isr);
//    }
//
//    public SemParser(char[] chars) {
//        scanner = new LexScanner(chars);
//    }
//
//    private void gl() throws IllegalSymbolException{
//        curLex = scanner.getLexem();
//
//    }
//
//    public void analyze() throws IllegalSymbolException {
//        gl();
//        P();
//        if (curLex != Lexem.END) {
//            throw new IllegalSymbolException("P: Expected end but was " + curLex);
//        }
//        System.out.println("OK");
//    }
//
//    private void P() throws IllegalSymbolException {
//        step();
//        while (curLex == Lexem.ARROW){
//            gl();
//            step();
//        }
//
//    }
//
//    private void step() throws IllegalSymbolException {
//        if (curLex == Lexem.ID || curLex == Lexem.STRING || curLex == Lexem.NUM){
//            gl();
//            call();
//        }
//        else if (curLex == Lexem.LEFT_S){
//            gl();
//            protectedProg();
//        }
//        else if (curLex == Lexem.LEFT_P){
//            gl();
//            methodDecl();
//        }
//        else
//            throw new IllegalSymbolException("step: Expected call, protected program or method declaration but was: " + curLex);
//
//    }
//
//    private void methodDecl() throws IllegalSymbolException {
//        P();
//        if (curLex == Lexem.RIGHT_P){
//            gl();
//            if (curLex == Lexem.ARROW){
//                gl();
//                if (curLex == Lexem.ID){
//                    gl();
//                    call();
//                }
//                else
//                    throw new IllegalSymbolException("methodDecl: Expected ID but was: " + curLex);
//            }
//            else
//                throw new IllegalSymbolException("methodDecl: Expected ->  but was: " + curLex);
//        }
//        else
//            throw new IllegalSymbolException("methodDecl: Expected >  but was: " + curLex);
//    }
//
//    private void protectedProg() throws IllegalSymbolException {
//        P();
//        if (curLex == Lexem.ST_LN){
//            gl();
//            P();
//        }
//        else
//            throw new IllegalSymbolException("protectedProg : expected | " + curLex);
//
//        if (curLex == Lexem.LEFT_S){
//            gl();
//        }
//        else
//            throw new IllegalSymbolException("protectedProg : expected / " + curLex);
//    }
//
//    private void call() throws IllegalSymbolException {
//        if (curLex == Lexem.DOT)
//        {
//            gl();
//            if (curLex == Lexem.ID){
//                gl();
//                if (curLex == Lexem.COLON){
//                    gl();
//                    //constant();
//                    object();
//                }
//            }
//            else
//                throw new IllegalSymbolException("call: expected method " + curLex);
//        }
//        else
//            throw new IllegalSymbolException("call: Expected . but was: " + curLex);
//
//    }
//
//    private void object() throws IllegalSymbolException {
//        if (curLex == Lexem.ID){
//            gl();
//        }
//        else {
//            constant();
//        }
//    }
//
//    private void constant() throws IllegalSymbolException {
//        if (curLex == Lexem.NUM){
//            gl();
//
//        }
//        else if (curLex == Lexem.STRING){
//            gl();
//
//        }
//    }
//
//}
//
//class IllegalSymbolException extends Exception{
//
//    IllegalSymbolException(String message) {
//        super(message);
//    }
//}
