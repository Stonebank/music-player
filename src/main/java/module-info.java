module com.hk.musicplayer {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.hk.musicplayer to javafx.fxml;
    exports com.hk.musicplayer;
}