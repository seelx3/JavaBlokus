module javablokus.javablokus {
    requires javafx.controls;
    requires javafx.fxml;


    opens javablokus.blokus to javafx.fxml;
    exports javablokus.blokus;
}