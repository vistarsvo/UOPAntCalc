package antcalc.models;

public class TableResults {
    private int alpha;
    private Float Dg;
    private Float Ra;
    private Float Rna;

    public TableResults(int alpha, Float Dg, Float Ra, Float Rna) {
        this.alpha  = alpha;
        this.Dg     = Dg;
        this.Ra     = Ra;
        this.Rna    = Rna;
    }

    public TableResults() {
    }

    public int getAlpha() {
        return alpha;
    }

    public Float getDg() {
        return Dg;
    }

    public Float getRa() {
        return Ra;
    }

    public Float getRna() {
        return Rna;
    }
}
