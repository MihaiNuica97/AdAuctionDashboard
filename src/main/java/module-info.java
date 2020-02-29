module segGroupCW {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens segGroupCW to javafx.fxml;
    exports segGroupCW;
}