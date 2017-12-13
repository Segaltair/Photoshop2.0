package Controller.Coding;

public class Encryption {
    private String lexicon;
    private String phrase;
    private Interval coding;
    private String decoding;
    private Integer N;
    private Table[] table;
    private StringBuilder tableReply;
    private StringBuilder codingReply;
    private StringBuilder logDecoding;

    public Encryption(String lexicon, String phrase) {
        this.lexicon = lexicon;
        this.phrase = phrase;
        tableReply = new StringBuilder();
        codingReply = new StringBuilder();
        logDecoding = new StringBuilder();
    }

    public StringBuilder getTableReply() {
        return tableReply;
    }

    public String getLexicon() {
        return lexicon;
    }

    public String getPhrase() {
        return phrase;
    }

    public Interval getCoding() {
        return coding;
    }

    public String getDecoding() {
        return decoding;
    }

    public Integer getN() {
        return N;
    }

    public Table[] getTable() {
        return table;
    }

    public StringBuilder getCodingReply() {
        return codingReply;
    }

    public StringBuilder getLogDecoding() {
        return logDecoding;
    }

    public void coding(){
        String[] charsLexicon = lexicon.split("");
        N = charsLexicon.length;

        coding = new Interval(N);

        table = new Table[N];
        for (int i = 0; i < N; i++) {
            table[i] = new Table(charsLexicon[i].charAt(0),
                    new Interval(coding.getLengthInterval()*i, coding.getLengthInterval() * (i + 1)));
        }
        outputTable(table);

        System.out.println("CODING START!");
        System.out.println();
        System.out.println("PHRASE: \"" + phrase + "\"");
        System.out.println("INTERVAL: " + coding.toString() + " LENGTH = " + (coding.getRightInterval() - coding.getLeftInterval()));
        System.out.println();

        codingReply.append("CODING START!\n\nPHRASE: \"")
                .append(phrase)
                .append("\"\n\n");

        for (int i = 0; i < phrase.length(); i++){
            for (int j = 0; j < N; j++)
                if (charsLexicon[j].charAt(0) == phrase.charAt(i)){
                    coding.setLeftInterval(coding.getLeftInterval() + coding.getLengthInterval()*j);
                    coding.setRightInterval(coding.getLeftInterval() + coding.getLengthInterval());
                    coding.calculateLengthInterval();

                    outputCoding(charsLexicon, i + 1, j, coding);
            }
        }
    }

    public void decoding(Interval interval, Table[] table, double code) {
        decoding = "";
        //code = interval.getLeftInterval() + (interval.getRightInterval() - interval.getLeftInterval()) / 2;

        System.out.println("DECODING START!");
        System.out.println("CODE = " + code);
        logDecoding.append("DECODING START!\n\n");

        int j = 1;
        while (true){
            for (int i = 0; i < getN(); i++) {
                if ((table[i].interval.getRightInterval() > code)
                        && (table[i].interval.getLeftInterval() < code)){

                    decoding += table[i].aChar;
                    logDecoding.append("CODE = ")
                            .append(code)
                            .append("\nDECODING: ")
                            .append(decoding)
                            .append("\n\n");

                    code = (code - table[i].interval.getLeftInterval())
                            / (table[i].interval.getRightInterval() - table[i].interval.getLeftInterval());

                    System.out.println("decoding = " + decoding);
                    System.out.println("CODE = " + code);

                    break;
                }
            }
            if (decoding.charAt(decoding.length() - 1) == '!') {
                break;
            }
            if (j > 30)
                break;
            j++;
        }
    }

    public void outputTable(Table[] table){
        System.out.println();
        System.out.println("TABLE");
        tableReply.append("TABLE\n\nLENGTH INTERVAL: ")
                .append(table[0].chance)
                .append("\n\n");

        for (int i = 0; i < N; i++) {
            System.out.print(table[i].aChar + "   " + table[i].chance + "    [");
            System.out.println((table[i].chance*i) + "; " + (table[i].chance * (i + 1)) + "]");
            tableReply.append(table[i].aChar)
                    .append("    [")
                    .append(table[i].chance * i)
                    .append("; ")
                    .append(table[i].chance * (i + 1))
                    .append("]\n");
        }
        System.out.println();
        System.out.println();
        tableReply.append("\n");
        tableReply.append("\n");
    }

    public void outputCoding(String[] charsLexicon, int i, int j, Interval coding){
        System.out.println("STEP " + i + " CHAR: \""+ charsLexicon[j].charAt(0) + "\"");
        System.out.println("INTERVAL: " + coding.toString() + " LENGTH = " + (coding.getRightInterval() - coding.getLeftInterval()));
        System.out.println();
        codingReply.append("STEP ")
                .append(i)
                .append("\nCHAR: \"")
                .append(charsLexicon[j].charAt(0))
                .append("\"\n")
                .append("INTERVAL: ")
                .append(coding.toString())
                .append("\nLENGTH = ")
                .append(coding.getRightInterval() - coding.getLeftInterval())
                .append("\n\n");
    }


}

class Interval{
    private double lengthInterval;
    private double leftInterval;
    private double rightInterval;
    private Integer N;

    public Interval(Integer N) {
        this.N = N;
        leftInterval = 0;
        rightInterval = 1.0;
        lengthInterval = 1.0 / N;
    }

    public Interval(double leftInterval, double rightInterval) {
        this.leftInterval = leftInterval;
        this.rightInterval = rightInterval;
        lengthInterval = rightInterval - leftInterval;
    }

    public double getLengthInterval() {
        return lengthInterval;
    }

    public double getLeftInterval() {
        return leftInterval;
    }

    public double getRightInterval() {
        return rightInterval;
    }

    public void calculateLengthInterval() {
        lengthInterval = (rightInterval - leftInterval)/N;
    }

    public void setLeftInterval(double leftInterval) {
        this.leftInterval = leftInterval;
    }

    public void setRightInterval(double rightInterval) {
        this.rightInterval = rightInterval;
    }

    @Override
    public String toString() {
        return "[" + leftInterval + ", " + rightInterval + "]";
    }
}

class Table{
    char aChar;
    double chance;
    Interval interval;

    public Table(char aChar, Interval interval) {
        this.aChar = aChar;
        chance = interval.getLengthInterval();
        this.interval = interval;
    }

    public char getaChar() {
        return aChar;
    }

    public Interval getInterval() {
        return interval;
    }
}

class TestCoding {
    public static void main(String[] args) {
//        String lexicon = "aeiou!";
//        String phrase = "eaii!";
//        String lexicon = "q,w,e,r,t,y,!";
//        String phrase = "qw!";
//        Encryption encryption = new Encryption(lexicon, phrase);
//        encryption.coding();
//        encryption.decoding(encryption.getCoding() ,encryption.getTable());
        int[][] arr = new int[1][2];
        System.out.println(arr[0].length);


    }
}