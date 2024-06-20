package controller;

import model.media.Media;
import model.media.Book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class MediaController extends BaseController {
    Media media;

    public MediaController() {
        media = new Media();
    }

    public MediaController(Media media) {
        this.media = media;
    }

    public ObservableList<Media> getAllMedia() throws SQLException {
        return FXCollections.observableArrayList(media.getAllMedia());
    }

    public Media getMediaById(int id) throws SQLException {
        return media.getMediaById(id);
    }

    public Book getBookMediaById(int id) throws SQLException {
        return new Book().getMediaById(id);
    }

    public void saveMedia(Media media) throws SQLException {
        media.save();
    }

    public void deleteMediaById(int id) throws SQLException {
        media.delete(id);
    }
}