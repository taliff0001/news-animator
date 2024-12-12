module com.tommyaliff.newsanimator {
    requires javafx.controls;
    requires javafx.fxml;

//    requires org.jsoup;
//
//    requires com.google.gson;
//
//    requires org.apache.httpcomponents.httpclient;
//    requires org.apache.httpcomponents.httpcore;
    requires java.net.http;
    requires com.google.gson;


    opens com.tommyaliff.newsanimator to javafx.fxml;
    exports com.tommyaliff.newsanimator;
}