package Pb1;

public record Carte(String titlul, String autorul, int anul) {
    @Override
    public String toString() {
        return "Carte{" + "titlul='" + titlul + '\'' + ", autorul='" + autorul + '\'' + ", anul=" + anul + '}';
    }
}
