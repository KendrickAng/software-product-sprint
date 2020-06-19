package com.google.sps.servlets.auth;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private final UserService svc = UserServiceFactory.getUserService();

    /**
     * Returns whether or not a user is authenticated. // TODO: replace once cookies are implemented.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType(Constants.TYPE_TEXT);
        res.getWriter().print(svc.isUserLoggedIn());
    }
}
