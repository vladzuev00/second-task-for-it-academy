package by.itacademy.zuevvlad.cardpaymentproject.servlet.welcome;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.welcome.WelcomeServletProperty.PATH_OF_WELCOME_PAGE;

public final class WelcomeServlet extends HttpServlet
{
    @Override
    public final void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                PATH_OF_WELCOME_PAGE.getValue());
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }
}
