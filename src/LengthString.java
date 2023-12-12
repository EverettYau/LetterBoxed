public class LengthString implements Comparable<LengthString> {
    String label;
    int length;

    public LengthString(String label) {
        this.label = label;
        this.length = label.length();
    }

    @Override
    public int compareTo(LengthString s) {
        return this.length - s.length;
    }
}
