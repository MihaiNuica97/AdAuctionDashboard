module segGroupCW {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.api;

    opens segGroupCW to javafx.fxml;
    exports segGroupCW;
}