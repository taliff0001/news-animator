module com.tommyaliff.parsestuff {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    requires org.jsoup;

    requires com.google.gson;

    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires java.net.http;


    opens com.tommyaliff.livenews to javafx.fxml;
    exports com.tommyaliff.livenews;
}