package segGroupCW;

import java.util.Date;

public class User {
    private String id;
    private String gender;
    private String age;
    private String income;

    public User(String id, String gender, String age, String income) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.income = income;
    }

    public String getId(){ return id; }

    public String getGender(){ return gender; }

    public String getAge(){ return age; }

    public String getIncome() { return income; }

    public String setId(String id){
        this.id = id;
        return id;
    }

    public String setGender(String gender){
        this.gender = gender;
        return gender;
    }

    public String setAge(String age){
        this.age = age;
        return age;
    }

    public String setIncome(String income) {
        this.income = income;
        return income;
    }
}
