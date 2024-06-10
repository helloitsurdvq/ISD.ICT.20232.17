package controller;

import model.media.Media;

import java.sql.SQLException;
import java.util.List;

public class HomeController extends BaseController {
    /**
     * this method gets all Media in database and return back to home to display
     */
    public List getAllMedia() throws SQLException {
        return new Media().getAllMedia();
    }
}