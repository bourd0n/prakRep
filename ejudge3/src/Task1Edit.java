import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Task1Edit {

    public static void main(String[] args) {
        InputStreamReader isr =  new InputStreamReader(System.in);
        Scanner scanner = new Scanner(isr);
        String in = "";
        while (scanner.hasNextLine())
        {
            in += scanner.nextLine();
        }
        SemParser sp = new SemParser(in);

        try {
            sp.analyze();
        } catch (IllegalSymbolException e) {
            System.out.println("incorrect");
        }
    }

}

enum LexemType {
    LEFT_P, //<
    LEFT_S, ID, NUM, ARROW, ST_LN, DOT, COLON, RIGHT_P, END, STRING, ///
}

class Lexem {
    private LexemType type;
    private String value;

    Lexem(LexemType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Lexem(LexemType type) {
        this.type = type;
    }

    public LexemType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}

class LexScanner {
    private Reader reader;

    public LexScanner(final String s) {
        reader = new StringReader(s);

        CS = state.H;
        clear();
        gc();
    }

    public LexScanner(InputStreamReader isr) {
        reader = isr;
        CS = state.H;
        clear();
        gc();
    }

    public LexScanner(final char[] chars) {
        reader = new InputStreamReader(new InputStream() {
            private int i = -1;
            @Override
            public int read() throws IOException {
                i++;
                return chars[i];
            }
        });

        CS = state.H;
        clear();
        gc();
    }

    enum state {H, IDENT, NUMB, DELIM, HYPHEN, STRING, }
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

    private String getBufString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf_top; i++){
            sb.append(buf[i]);
        }
        return sb.toString();
    }

    public Lexem getLexem() throws IllegalSymbolException {
        int d, j;

        CS = state.H;
        clear();
        do {
            switch (CS){
                case H:
                    if (c == null)
                        return new Lexem(LexemType.END);
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
                        add();
                        gc();
                        CS = state.NUMB;
                    }
                    else if (c == '<')
                    {
                        gc();
                        return new Lexem (LexemType.LEFT_P, "<");
                    }
                    else if (c == '>')
                    {
                        gc();
                        return new Lexem(LexemType.RIGHT_P, ">");
                    }
                    else if ( c == '/')
                    {
                        gc();
                        return new Lexem(LexemType.LEFT_S, "/");
                    }
                    else if (c == '\"')
                    {
                        clear();
                        add();
                        gc();
                        CS = state.STRING;
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
                        return new Lexem(LexemType.ST_LN, "|");
                    }
                    else if (c == '.')
                    {
                        gc();
                        return new Lexem(LexemType.DOT, ".");
                    }
                    else if (c == ':')
                    {
                        gc();
                        return new Lexem(LexemType.COLON, ":");
                    }
                    else if (c == null)
                    {
                        return new Lexem(LexemType.END);
                    }
                    else
                        CS = state.DELIM;
                    break;
                case IDENT:
                    if (c == null){
                        if (buf_top == 0)
                            return new Lexem(LexemType.END);
                        else {
                            gc();
                            return new Lexem(LexemType.ID, getBufString());
                        }
                    }
                    else if (Character.isAlphabetic(c) || Character.isDigit(c)){
                        add();
                        gc();
                    }
                    else
                        return new Lexem(LexemType.ID, getBufString());;
                    break;
                case NUMB:
                    if ( c == null ){
                        if (buf_top == 0)
                            return new Lexem(LexemType.END);
                        else {
                            gc();
                            return new Lexem(LexemType.NUM, getBufString());
                        }
                    }
                    if (Character.isDigit(c))
                    {
                        add();
                        gc();
                    }
                    else
                        return new Lexem(LexemType.NUM, getBufString());
                    break;
                case STRING:
                    if (c == null)
                        throw new IllegalSymbolException("expected string");
                    else if (c == '\"'){
                        add();
                        gc();
                        return new Lexem(LexemType.STRING, getBufString());
                    }
                    else {
                        add();
                        gc();
                    }
                    break;
                case HYPHEN:
                    if (c == null){
                        return new Lexem(LexemType.END);
                    }
                    else if (c == '>'){
                        gc();
                        return new Lexem(LexemType.ARROW, "->");
                    }
                    else
                    if (Character.isDigit(c)){
                        //d =
                        add();
                        gc();
                        CS = state.NUMB;
                    }
                    else
                        throw new IllegalSymbolException("Expected > or number");
                    break;
                case DELIM:
                    clear();
                    //add();
                    break;
            }
        } while (true);
    }

}

class SemParser {
    private LexScanner scanner ;//= new LexScanner();
    private Lexem curLex;
    private int spaceCount = 0;
    private StringBuilder sb;

    public SemParser(String s) {
        scanner = new LexScanner(s);
        sb = new StringBuilder();
    }

    public SemParser(InputStreamReader isr) {
        scanner = new LexScanner(isr);
    }

    public SemParser(char[] chars) {
        scanner = new LexScanner(chars);
    }

    private void gl() throws IllegalSymbolException{
        curLex = scanner.getLexem();

    }

    public void analyze() throws IllegalSymbolException {
        gl();
        P();
        if (curLex.getType() != LexemType.END) {
            throw new IllegalSymbolException("P: Expected end but was " + curLex);
        }
        System.out.println(sb.toString());
    }

    private void P() throws IllegalSymbolException {
        step();
        while (curLex.getType() == LexemType.ARROW){
            sb.append(" ->\n");
            gl();
            step();
        }

    }

    private void step() throws IllegalSymbolException {
        if (curLex.getType() == LexemType.ID || curLex.getType() == LexemType.STRING || curLex.getType() == LexemType.NUM){
            append(curLex.getValue());
            gl();
            call();
        }
        else if (curLex.getType() == LexemType.LEFT_S){
            append(curLex.getValue() + "\n");
            spaceCount += 4;
            gl();
            protectedProg();
        }
        else if (curLex.getType() == LexemType.LEFT_P){
            append(curLex.getValue() + "\n");
            spaceCount += 4;
            gl();
            methodDecl();
            sb.append("\n");
            append("");
        }
        else
            throw new IllegalSymbolException("step: Expected call, protected program or method declaration but was: " + curLex);

    }

    private void append(String s) {
        for (int i = 0; i < spaceCount; i++){
            sb.append(" ");
        }
        if (s != null && !s.isEmpty())
            sb.append(s);
    }

    private void methodDecl() throws IllegalSymbolException {
        P();
        //spaceCount -= 4;
        sb.append("\n");
        if (curLex.getType() == LexemType.RIGHT_P){
            spaceCount -= 4;
            append(curLex.getValue() + " ");
            gl();
            if (curLex.getType() == LexemType.ARROW){
                sb.append(curLex.getValue() + " ");
                gl();
                if (curLex.getType() == LexemType.ID){
                    sb.append(curLex.getValue());
                    gl();
                    call();
                }
                else
                    throw new IllegalSymbolException("methodDecl: Expected ID but was: " + curLex);
            }
            else
                throw new IllegalSymbolException("methodDecl: Expected ->  but was: " + curLex);
        }
        else
            throw new IllegalSymbolException("methodDecl: Expected >  but was: " + curLex);
    }

    private void protectedProg() throws IllegalSymbolException {
        P();
        if (curLex.getType() == LexemType.ST_LN){
            sb.append("\n");
            spaceCount -= 4;
            append(curLex.getValue() + "\n");
            spaceCount += 4;
            gl();
            P();
        }
        else
            throw new IllegalSymbolException("protectedProg : expected | " + curLex);

        if (curLex.getType() == LexemType.LEFT_S){
            spaceCount -= 4;
            sb.append("\n");
            append(curLex.getValue());
            gl();
        }
        else
            throw new IllegalSymbolException("protectedProg : expected / " + curLex);
    }

    private void call() throws IllegalSymbolException {
        if (curLex.getType() == LexemType.DOT)
        {
            sb.append(curLex.getValue());
            gl();
            if (curLex.getType() == LexemType.ID){
                sb.append(curLex.getValue());
                gl();
                if (curLex.getType() == LexemType.COLON){
                    sb.append(curLex.getValue());
                    gl();
                    //constant();
                    object();
                }
            }
            else
                throw new IllegalSymbolException("call: expected method " + curLex);
        }
        else
            throw new IllegalSymbolException("call: Expected . but was: " + curLex);

    }

    private void object() throws IllegalSymbolException {
        if (curLex.getType() == LexemType.ID){
            sb.append(curLex.getValue());
            gl();
        }
        else {
            constant();
        }
    }

    private void constant() throws IllegalSymbolException {
        if (curLex.getType() == LexemType.NUM){
            sb.append(curLex.getValue());
            gl();
        }
        else if (curLex.getType() == LexemType.STRING){
            sb.append(curLex.getValue());
            gl();
        }
    }

}

class IllegalSymbolException extends Exception{

    IllegalSymbolException(String message) {
        super(message);
    }
}
