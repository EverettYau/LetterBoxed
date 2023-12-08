import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

public class Solver {
//    private HashMap letters;
    private HashSet<String> dictionary;
    private Trie words;
    private char[] left;
    private char[] right;
    private char[] up;
    private char[] down;

    public Solver() {
//make a hashmap with all the letters and their frequencies
//        String alphabet="abcdefghijklmnopqrstuzwxyz";
//        letters=new HashMap();
//        for (int i=0; i<alphabet.length(); i++) {
//            letters.put(alphabet.charAt(i), i);
//        }
        dictionary=new HashSet();
        words = new Trie();
//        left = new char[3];
//        right = new char[3];
//        up = new char[3];
//        down = new char[3];
        left = new char[] {'c', 'i', 'l'};
        right = new char[] {'k', 'f', 'r'};;
        up = new char[] {'g', 'p', 't'};;
        down = new char[] {'n', 'u', 'e'};;
    }

    public void load() throws FileNotFoundException {
        // YOUR CODE HERE
        Scanner scan = new Scanner(new File("dictionaries/words.txt"));
        while (scan.hasNext()) {
            String word=scan.next().toLowerCase();
//            dictionary.add(word);
            if (word.matches("^[a-z]*$")) {
                words.add(word);
            }
        }
    }
    public void enterLetters() {
        Scanner scan = new Scanner(System.in);
        String command = "";

        System.out.println("Left:");
        command = scan.nextLine();
        while (!command.matches("^[A-Za-z]{3}$")) {
            System.out.println("Entries must be 3 letters. Try again.");
            command = scan.nextLine();
        }
        for (int i=0; i<3; i++) {
            left[i]=command.charAt(i);
        }

        System.out.println("Right:");
        command = scan.nextLine();
        while (!command.matches("^[A-Za-z]{3}$")) {
            System.out.println("Entries must be 3 letters. Try again.");
            command = scan.nextLine();
        }
        for (int i=0; i<3; i++) {
            right[i]=command.charAt(i);
        }

        System.out.println("Up:");
        command = scan.nextLine();
        while (!command.matches("^[A-Za-z]{3}$")) {
            System.out.println("Entries must be 3 letters. Try again.");
            command = scan.nextLine();
        }
        for (int i=0; i<3; i++) {
            up[i]=command.charAt(i);
        }

        System.out.println("Down:");
        command = scan.nextLine();
        while (!command.matches("^[A-Za-z]{3}$")) {
            System.out.println("Entries must be 3 letters. Try again.");
            command = scan.nextLine();
        }
        for (int i=0; i<3; i++) {
            down[i]=command.charAt(i);
        }
    }

    public void findWords() {
        findWordsRec("", "");
    }

    public String findWordsRec (String str, String arr) {
        if (words.isWord(str)==-1) {
            return null;
        }
        if (words.isWord(str)==1) {
            dictionary.add(str);
        }

        if (!arr.equals("left")) {
            for (int i=0; i<3; i++) {
                findWordsRec(str+left[i], "left");
            }
        }
        if (!arr.equals("right")) {
            for (int i=0; i<3; i++) {
                findWordsRec(str+right[i], "right");
            }
        }
        if (!arr.equals("up")) {
            for (int i=0; i<3; i++) {
                findWordsRec(str+up[i], "up");
            }
        }
        if (!arr.equals("down")) {
            for (int i=0; i<3; i++) {
                findWordsRec(str+down[i], "down");
            }
        }
        return null;
    }

    public String bruteForceSolve () {
        HashSet<Character> letters=new HashSet<>();
        for (int i=0; i<3; i++) {
            letters.add(left[i]);
        }
        for (int i=0; i<3; i++) {
            letters.add(right[i]);
        }
        for (int i=0; i<3; i++) {
            letters.add(up[i]);
        }
        for (int i=0; i<3; i++) {
            letters.add(down[i]);
        }

        for (String a: dictionary) {
            String firstAnswer=a;
            HashSet<Character> firstLetters=letters;
            for (int i=0; i<a.length(); i++) {
                firstLetters.remove(a.charAt(i));
            }
            for (String b: dictionary) {
                String secondAnswer=firstAnswer+", "+b;
                HashSet<Character> secondLetters=firstLetters;
                if (b.charAt(0)==a.charAt(a.length()-1)) {
                    for (int i=0; i<b.length(); i++) {
                        secondLetters.remove(b.charAt(i));
                    }
                }
                for (String c: dictionary) {
                    String thirdAnswer=secondAnswer+", "+c;
                    HashSet<Character> thirdLetters=secondLetters;
                    if (c.charAt(0)==b.charAt(b.length()-1)) {
                        for (int i=0; i<c.length(); i++) {
                            thirdLetters.remove(c.charAt(i));
                        }
                    }
                    for (String d: dictionary) {
                        String fourthAnswer=thirdAnswer+", "+d;
                        HashSet<Character> fourthLetters=thirdLetters;
                        if (d.charAt(0)==c.charAt(c.length()-1)) {
                            for (int i=0; i<d.length(); i++) {
                                fourthLetters.remove(d.charAt(i));
                            }
                            if (fourthLetters.isEmpty()) {
                                System.out.println("test");
                                return fourthAnswer;
                            }
                        }
                    }
                }
            }
        }
        return "No Solutions.";
    }

    public String lengthSolve() {
        return null;
    }
    public void frequencySolve() {

    }
    public void betterFrequencySolve() {

    }

    public static void main(String[] args) throws FileNotFoundException {
        Solver solver = new Solver();
//        solver.enterLetters();
        solver.load();
//        System.out.println(solver.words.isWord("crl"));
//        solver.words.print();
        solver.findWords();
        solver.bruteForceSolve();
    }
}

