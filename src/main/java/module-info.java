module segGroupCW {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires com.jfoenix;

	opens segGroupCW to javafx.fxml;
    exports segGroupCW;
}