package segGroupCW;

import java.math.BigDecimal;
import java.util.Date;

public class Impression {
    private String id;
    private Date date;
    private String context;
    private BigDecimal cost;

    public Impression(String id, String date, String context, String cost) {
        this.id = id;
        this.date = DateFormat.convertDate(date);
        this.context = context;
        this.cost = new BigDecimal(cost);
    }

    public String getId(){ return id; }

    public Date getDate(){ return date; }

    public String getContext(){ return context; }

    public BigDecimal getCost(){ return cost; }

    public String setId(String id){
        this.id = id;
        return id;
    }

    public Date setDate(String date) {
        this.date = DateFormat.convertDate(date);
        return this.date;
    }

    public String setContext(String context){
        this.context = context;
        return context;
    }

    public BigDecimal setCost(String cost) {
        this.cost = new BigDecimal(cost);
        return this.cost;
    }
}
