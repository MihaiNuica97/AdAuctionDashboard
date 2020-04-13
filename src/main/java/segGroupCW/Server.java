package segGroupCW;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    private SimpleDateFormat parser = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private String id;
    private Date entryDate;
    private Date exitDate;
    private int pages;
    private Boolean conversion;

    public Server(String id, String entryDate, String exitDate, String pages, String conversion) {
        this.id = id;
        this.entryDate = convertDate(entryDate);
        this.exitDate = convertDate(exitDate);
        this.pages = Integer.parseInt(pages);
        this.conversion = Boolean.parseBoolean(conversion);
    }

    private Date convertDate(String time) {
        try {
            return new Date(parser.parse(time).getTime());
        } catch (ParseException e) {
            System.out.println("Date converter failed");
            e.printStackTrace();
            return null;
        }
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
        this.entryDate = convertDate(date);
        return this.entryDate;
    }

    public Date setExitDate(String date) {
        this.exitDate = convertDate(date);
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
