module com.example.snakegamefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakegamefx to javafx.fxml;
    exports com.example.snakegamefx;
}