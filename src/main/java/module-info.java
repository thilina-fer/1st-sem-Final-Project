module lk.ijse.alphamodifications {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires java.desktop;


    opens lk.ijse.alphamodifications.contraller to javafx.fxml;
    opens lk.ijse.alphamodifications.dto.tm to javafx.base;
    exports lk.ijse.alphamodifications;

   //opens lk.ijse.alphamodifications.contraller.ItemPageContraller to javafx.fxml;
}