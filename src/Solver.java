import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.PriorityQueue;

public class Solver {
//    private HashMap letters;
    private HashSet<String> dictionary;
    private HashMap<Character, LinkedList<String>> betterDictionary;
    private HashMap<Character, PriorityQueue<LengthString>> lengthDictionary;
    private Trie words;
    private char[] left;
    private char[] right;
    private char[] up;
    private char[] down;

    public Solver() {
        String alphabet="abcdefghijklmnopqrstuvwxyz";
        //default dictionary
        dictionary=new HashSet<>();
        //dictionary for betterBruteForceSolve()
        betterDictionary=new HashMap<>();
        for (int i=0; i<alphabet.length(); i++) {
            betterDictionary.put(alphabet.charAt(i), new LinkedList<>());
        }
        //dictionary for lengthSolve()
        lengthDictionary=new HashMap<>();
        for (int i=0; i<alphabet.length(); i++) {
            lengthDictionary.put(alphabet.charAt(i), new PriorityQueue<>());
        }

        //letters
        words = new Trie();
        left = new char[3];
        right = new char[3];
        up = new char[3];
        down = new char[3];
//        left = new char[] {'p', 'h', 'r'};
//        right = new char[] {'a', 'c', 'k'};
//        up = new char[] {'g', 'm', 't'};
//        down = new char[] {'e', 'i', 'o'};
        //phr ack gmt eio
        //dcm ano ive prl
        //wbi pkh tra eon
//        left = new char[] {'w', 'b', 'i'};
//        right = new char[] {'p', 'k', 'h'};
//        up = new char[] {'t', 'r', 'a'};
//        down = new char[] {'e', 'o', 'n'};
        //gut ami xnr elv
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
        command = scan.nextLine().toLowerCase();
        while (!command.matches("^[A-Za-z]{3}$")) {
            System.out.println("Entries must be 3 letters. Try again.");
            command = scan.nextLine().toLowerCase();
        }
        for (int i=0; i<3; i++) {
            left[i]=command.charAt(i);
        }

        System.out.println("Right:");
        command = scan.nextLine().toLowerCase();
        while (!command.matches("^[A-Za-z]{3}$")) {
            System.out.println("Entries must be 3 letters. Try again.");
            command = scan.nextLine().toLowerCase();
        }
        for (int i=0; i<3; i++) {
            right[i]=command.charAt(i);
        }

        System.out.println("Up:");
        command = scan.nextLine().toLowerCase();
        while (!command.matches("^[A-Za-z]{3}$")) {
            System.out.println("Entries must be 3 letters. Try again.");
            command = scan.nextLine().toLowerCase();
        }
        for (int i=0; i<3; i++) {
            up[i]=command.charAt(i);
        }

        System.out.println("Down:");
        command = scan.nextLine().toLowerCase();
        while (!command.matches("^[A-Za-z]{3}$")) {
            System.out.println("Entries must be 3 letters. Try again.");
            command = scan.nextLine().toLowerCase();
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
            betterDictionary.get(str.charAt(0)).add(str);
            lengthDictionary.get(str.charAt(0)).add(new LengthString(str));
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
            HashSet<Character> firstLetters=(HashSet<Character>) letters.clone();
            for (int i=0; i<a.length(); i++) {
                firstLetters.remove(a.charAt(i));
            }
            HashSet<String> secondDictionary=(HashSet<String>) dictionary.clone();
            secondDictionary.remove(a);
            for (String b: secondDictionary) {
                String secondAnswer=a+", "+b;
                HashSet<Character> secondLetters=(HashSet<Character>) firstLetters.clone();
                if (b.charAt(0)==a.charAt(a.length()-1)) {
                    for (int i=0; i<b.length(); i++) {
                        secondLetters.remove(b.charAt(i));
                    }
                    HashSet<String> thirdDictionary=(HashSet<String>) secondDictionary.clone();
                    thirdDictionary.remove(b);
                    for (String c: thirdDictionary) {
                        String thirdAnswer=secondAnswer+", "+c;
                        HashSet<Character> thirdLetters=(HashSet<Character>) secondLetters.clone();
                        if (c.charAt(0)==b.charAt(b.length()-1)) {
                            for (int i=0; i<c.length(); i++) {
                                thirdLetters.remove(c.charAt(i));
                            }
                            HashSet<String> fourthDictionary=(HashSet<String>) thirdDictionary.clone();
                            fourthDictionary.remove(c);
                            for (String d: fourthDictionary) {
                                String fourthAnswer=thirdAnswer+", "+d;
                                HashSet<Character> fourthLetters=(HashSet<Character>) thirdLetters.clone();
                                if (d.charAt(0)==c.charAt(c.length()-1)) {
                                    for (int i=0; i<d.length(); i++) {
                                        fourthLetters.remove(d.charAt(i));
                                    }
                                    if (fourthLetters.isEmpty()) {
                                        return fourthAnswer;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "No Solutions.";
    }

    public String betterBruteForceSolve () {
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
            HashSet<Character> firstLetters=(HashSet<Character>) letters.clone();
            for (int i=0; i<a.length(); i++) {
                firstLetters.remove(a.charAt(i));
            }
            HashMap<Character, LinkedList<String>> secondDictionary=(HashMap<Character, LinkedList<String>>) betterDictionary.clone();
            secondDictionary.replaceAll((k, v) -> new LinkedList<>(v));
            secondDictionary.get(a.charAt(0)).remove(a);
            Queue<String> firstValues=secondDictionary.get(a.charAt(a.length()-1));
            while (firstValues.peek()!=null) {
                String b=firstValues.poll();
                String secondAnswer=a+", "+b;
                HashSet<Character> secondLetters=(HashSet<Character>) firstLetters.clone();
                for (int i=0; i<b.length(); i++) {
                    secondLetters.remove(b.charAt(i));
                }
                HashMap<Character, LinkedList<String>> thirdDictionary=(HashMap<Character, LinkedList<String>>) secondDictionary.clone();
                thirdDictionary.replaceAll((k, v) -> new LinkedList<>(v));
                thirdDictionary.get(b.charAt(0)).remove(b);
                Queue<String> secondValues=thirdDictionary.get(b.charAt(b.length()-1));
                while (secondValues.peek()!=null) {
                    String c=secondValues.poll();
                    String thirdAnswer=secondAnswer+", "+c;
                    HashSet<Character> thirdLetters=(HashSet<Character>) secondLetters.clone();
                    for (int i=0; i<c.length(); i++) {
                        thirdLetters.remove(c.charAt(i));
                    }
                    HashMap<Character, LinkedList<String>> fourthDictionary=(HashMap<Character, LinkedList<String>>) thirdDictionary.clone();
                    fourthDictionary.replaceAll((k, v) -> new LinkedList<>(v));
                    fourthDictionary.get(c.charAt(0)).remove(c);
                    Queue<String> thirdValues=fourthDictionary.get(c.charAt(c.length()-1));
                    while (thirdValues.peek()!=null) {
                        String d=thirdValues.poll();
                        String fourthAnswer=thirdAnswer+", "+d;
                        HashSet<Character> fourthLetters=(HashSet<Character>) thirdLetters.clone();
                        for (int i=0; i<d.length(); i++) {
                            fourthLetters.remove(d.charAt(i));
                        }
                        if (fourthLetters.isEmpty()) {
                            return fourthAnswer;
                        }
                    }
                }
            }
        }
        return "No Solutions.";
    }

    public String lengthSolve() {
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
        for (Character z: letters) {
            //first word
            PriorityQueue<LengthString> firstValues=lengthDictionary.get(z);
            while (firstValues.peek()!=null) {
                LengthString aNode=firstValues.poll();
                String a=aNode.label;
                System.out.println("First word: "+a);
                HashSet<Character> firstLetters=(HashSet<Character>) letters.clone();
                for (int i=0; i<a.length(); i++) {
                    firstLetters.remove(a.charAt(i));
                }
//                HashMap<Character, PriorityQueue<LengthString>> secondDictionary=(HashMap<Character, PriorityQueue<LengthString>>) lengthDictionary.clone();
//                for (Character k: secondDictionary.keySet()) {
//                    secondDictionary.put(k, new PriorityQueue<>(secondDictionary.get(k)));
//                }
//                secondDictionary.get(a.charAt(0)).remove(aNode);

                //second word
                PriorityQueue<LengthString> secondValues=new PriorityQueue<> (lengthDictionary.get(a.charAt(a.length()-1)));
                while (secondValues.peek()!=null) {
                    LengthString bNode=secondValues.poll();
                    String b=bNode.label;
                    System.out.println("Second word: "+b);
                    String secondAnswer=a+", "+b;
                    HashSet<Character> secondLetters=(HashSet<Character>) firstLetters.clone();
                    for (int i=0; i<b.length(); i++) {
                        secondLetters.remove(b.charAt(i));
                    }
//                    HashMap<Character, PriorityQueue<LengthString>> thirdDictionary=(HashMap<Character, PriorityQueue<LengthString>>) secondDictionary.clone();
//                    for (Character k: thirdDictionary.keySet()) {
//                        thirdDictionary.put(k, new PriorityQueue<>(thirdDictionary.get(k)));
//                    }
//                    thirdDictionary.get(b.charAt(0)).remove(bNode);
                    //third word
                    PriorityQueue<LengthString> thirdValues=new PriorityQueue<> (lengthDictionary.get(b.charAt(b.length()-1)));
                    while (thirdValues.peek()!=null) {
                        LengthString cNode=thirdValues.poll();
                        String c=cNode.label;
                        System.out.println("Third word: "+c);
                        String thirdAnswer=secondAnswer+", "+c;
                        HashSet<Character> thirdLetters=(HashSet<Character>) secondLetters.clone();
                        for (int i=0; i<c.length(); i++) {
                            thirdLetters.remove(c.charAt(i));
                        }
//                        HashMap<Character, PriorityQueue<LengthString>> fourthDictionary= (HashMap<Character, PriorityQueue<LengthString>>) thirdDictionary.clone();
//                        for (Character k: fourthDictionary.keySet()) {
//                            fourthDictionary.put(k, new PriorityQueue<>(fourthDictionary.get(k)));
//                        }
//                        fourthDictionary.get(c.charAt(0)).remove(cNode);
                        //fourth word
                        PriorityQueue<LengthString> fourthValues=new PriorityQueue<> (lengthDictionary.get(c.charAt(c.length()-1)));
                        while (fourthValues.peek()!=null) {
                            String d=fourthValues.poll().label;
                            System.out.println("Fourth word: "+d);
                            String fourthAnswer=thirdAnswer+", "+d;
                            HashSet<Character> fourthLetters=(HashSet<Character>) thirdLetters.clone();
                            for (int i=0; i<d.length(); i++) {
                                fourthLetters.remove(d.charAt(i));
                            }
                            if (fourthLetters.isEmpty()) {
                                return fourthAnswer;
                            }
                        }
                    }
                }
            }
        }
        return "No Solutions.";
    }

    public void debug() {
        System.out.println("Better Dictionary.");
        for (Character k: betterDictionary.keySet()) {
            LinkedList<String> a=betterDictionary.get(k);
            while (a.peek()!=null) {
                System.out.println(a.poll());
            }
        }
        System.out.println("Length Dictionary.");
        for (Character k: lengthDictionary.keySet()) {
            PriorityQueue<LengthString> a=lengthDictionary.get(k);
            while (a.peek()!=null) {
                System.out.println(a.poll().label);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Solver solver = new Solver();
        solver.enterLetters();
        solver.load();
        solver.findWords();
//        System.out.println(solver.betterBruteForceSolve());
        System.out.println(solver.lengthSolve());
//        solver.debug();
    }
}

