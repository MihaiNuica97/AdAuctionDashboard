package segGroupCW;

import java.util.Date;

public class Server {
    private String id;
    private Date entryDate;
    private Date exitDate;
    private int pages;
    private Boolean conversion;

    public Server(String id, String entryDate, String exitDate, String pages, String conversion) {
        this.id = id;
        this.entryDate = DateFormat.convertDate(entryDate);
        this.exitDate = DateFormat.convertDate(exitDate);
        this.pages = Integer.parseInt(pages);
        this.conversion = Boolean.parseBoolean(conversion);
    }

    public String getId(){ return id; }

    public Date getEntryDate(){ return entryDate; }

    public Date getExitDate(){ return exitDate; }

    public int getPages(){ return pages; }

    public Boolean getConversion(){ return conversion; }

    public String setId(String id){
        this.id = id;
        return id;
    }

    public Date setEntryDate(String date) {
        this.entryDate = DateFormat.convertDate(date);
        return this.entryDate;
    }

    public Date setExitDate(String date) {
        this.exitDate = DateFormat.convertDate(date);
        return this.exitDate;
    }

    public String setPages(String pages){
        this.pages = Integer.parseInt(pages);
        return pages;
    }

    public Boolean setConversion(String conversion) {
        this.conversion = Boolean.parseBoolean(conversion);
        return this.conversion;
    }
}
