package com.google.sps.servlets.auth;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that handles generation of links to login/logout users.
 */
@WebServlet("/auth/generate-login-link")
public class AuthLinkServlet extends HttpServlet {
    private static final String urlToRedirectOnLogin = Constants.LINK_HOME;
    private static final String urlToRedirectOnLogout = Constants.LINK_HOME;

    private final UserService svc = UserServiceFactory.getUserService();

    /**
     * Returns a hyperlink to login/logout if user is unauthenticated/authenticated, respectively.
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType(Constants.TYPE_HTML);
        if (svc.isUserLoggedIn()) {
            res.getWriter().printf("<a href='%s'>Logout</a>", svc.createLogoutURL(urlToRedirectOnLogout));
        } else {
            res.getWriter().printf("<a href='%s'>Login</a>", svc.createLoginURL(urlToRedirectOnLogin));
        }
    }
}
