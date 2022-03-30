package by.itacademy.zuevvlad.cardpaymentproject.servlet.logon.filter;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.logon.LogOnServletProperty.NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_EMAIL;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.logon.LogOnServletProperty.NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_PASSWORD;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.logon.LogOnServletProperty.NAME_OF_ATTRIBUTE_OF_REQUEST_OF_ERROR_MESSAGE;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.ServletProperty.PATH_OF_LOG_ON_PAGE;

public final class LoggedOnCorrectPasswordOfUserFilter implements Filter
{
    private UserService userService;

    public LoggedOnCorrectPasswordOfUserFilter()
    {
        super();
    }

    @Override
    public final void init(final FilterConfig filterConfig)
    {
        this.userService = UserService.createUserService();
    }

    @Override
    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                               final FilterChain filterChain)
            throws IOException, ServletException
    {
        try
        {
            final String inputtedEmail = servletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_EMAIL.getValue());
            final String inputtedPassword = servletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_PASSWORD.getValue());
            if(!this.userService.isCombinationOfEmailAndPasswordExist(inputtedEmail, inputtedPassword))
            {
                servletRequest.setAttribute(NAME_OF_ATTRIBUTE_OF_REQUEST_OF_ERROR_MESSAGE.getValue(),
                        LoggedOnCorrectPasswordOfUserFilter.MESSAGE_OF_ERROR_OF_WRONG_PASSWORD_OF_USER);
                final RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher(
                        PATH_OF_LOG_ON_PAGE.getValue());
                requestDispatcher.forward(servletRequest, servletResponse);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch(final FindingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }

    private static final String MESSAGE_OF_ERROR_OF_WRONG_PASSWORD_OF_USER = "wrong password";
}
