module javablokus.javablokus {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens javablokus.blokus to javafx.fxml;
    exports javablokus.blokus;
    exports javablokus.blokus.pieces;
    opens javablokus.blokus.pieces to javafx.fxml;
}