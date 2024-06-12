package controller;

import exception.LoginException;
import model.User;
import utils.Format;

import java.util.Objects;
import java.util.logging.Logger;

import java.sql.*;

public class AuthController extends BaseController {
    private static Logger LOGGER = utils.Format.getLogger(PlaceOrderController.class.getName());

    public void login(String email, String password) throws Exception {
        try {
            User user = new User().authenticate(email, Format.md5(password));
            if (Objects.isNull(user)) throw new LoginException();
        } catch (SQLException ex) {
            throw new LoginException();
        }
    }
}
