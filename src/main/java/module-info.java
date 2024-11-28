module a6project {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    opens org.a6 to javafx.fxml;
    exports org.a6;
}
