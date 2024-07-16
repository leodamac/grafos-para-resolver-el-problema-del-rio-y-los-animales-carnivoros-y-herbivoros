module com.mycompany.decision {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.abego.treelayout.core;
    requires java.desktop;

    opens com.mycompany.decision to javafx.fxml, org.abego.treelayout.core;
    exports com.mycompany.decision;
}
