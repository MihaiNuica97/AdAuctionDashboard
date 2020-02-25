module segGroupCW {
    requires javafx.controls;
    requires javafx.fxml;

    opens segGroupCW to javafx.fxml;
    exports segGroupCW;
}