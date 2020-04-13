package segGroupCW;

import java.math.BigDecimal;
import java.util.Date;

public class Click {
    private String id;
    private Date date;
    private BigDecimal cost;

    public Click(String id, String date, String cost) {
        this.id = id;
        this.date = DateFormat.convertDate(date);
        this.cost = new BigDecimal(cost);
    }

    public String getId(){ return id; }

    public Date getDate(){ return date; }

    public BigDecimal getCost(){ return cost; }

    public String setId(String id){
        this.id = id;
        return id;
    }

    public Date setDate(String date) {
        this.date = DateFormat.convertDate(date);
        return this.date;
    }

    public BigDecimal setCost(String cost) {
        this.cost = new BigDecimal(cost);
        return this.cost;
    }
}
