package antcalc.models;


public class TableRandoms {
    private int nc;
    private Float sigma;
    private Float delta;

    public TableRandoms(int nc, Float sigma, Float delta) {
        this.nc     = nc;
        this.sigma  = sigma;
        this.delta  = delta;
    }

    public TableRandoms() {
    }

    public int getNc() {
        return nc;
    }

    public Float getSigma() {
        return sigma;
    }

    public Float getDelta() {
        return delta;
    }
}
