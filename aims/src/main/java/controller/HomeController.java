package controller;

import model.media.Media;

import java.sql.SQLException;
import java.util.List;

public class HomeController extends BaseController {
    public List getAllMedia() throws SQLException {
        return new Media().getAllMedia();
    }
}