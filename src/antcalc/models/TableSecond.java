package antcalc.models;


public class TableSecond {
    private String descr;
    private Integer c03;
    private Integer c025;
    private Integer c02;
    private Integer c018;
    private Integer c016;
    private Integer c014;
    private Integer c012;
    private Integer c01;
    private Integer c008;
    private Integer c006;

    public TableSecond(String descr, Integer c03, Integer c025, Integer c02, Integer c018, Integer c016, Integer c014, Integer c012, Integer c01, Integer c008, Integer c006) {
        this.descr = descr;
        this.c03   = c03;
        this.c025  = c025;
        this.c02   = c02;
        this.c018  = c018;
        this.c016  = c016;
        this.c014  = c014;
        this.c012  = c012;
        this.c01   = c01;
        this.c008  = c008;
        this.c006  = c006;
    }

    public TableSecond() {
    }

    public String getDescr() {
        return descr;
    }

    public Integer getC03() {
        return c03;
    }

    public Integer getC025() {
        return c025;
    }

    public Integer getC02() {
        return c02;
    }

    public Integer getC018() {
        return c018;
    }

    public Integer getC016() {
        return c016;
    }

    public Integer getC014() {
        return c014;
    }

    public Integer getC012() {
        return c012;
    }

    public Integer getC01() {
        return c01;
    }

    public Integer getC008() {
        return c008;
    }

    public Integer getC006() {
        return c006;
    }
}
