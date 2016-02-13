package antcalc.models;


public class TableFirst {
    private int iter;
    private Float first;
    private Float six;
    private Float oreol;
    private String fmax;

    public TableFirst(int iter, Float first, Float six, Float oreol, String fmax) {
        this.iter   = iter;
        this.first  = first;
        this.six    = six;
        this.oreol  = oreol;
        this.fmax  = fmax;
    }

    public TableFirst() {
    }

    public int getIter() {
        return iter;
    }

    public Float getFirst() {
        return first;
    }

    public Float getSix() {
        return six;
    }

    public Float getOreol() {
        return oreol;
    }

    public String getFmax() {
        return fmax;
    }
}
