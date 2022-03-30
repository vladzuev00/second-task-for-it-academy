package by.itacademy.zuevvlad.cardpaymentproject.servlet.logon.filter;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.DefiningExistingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.service.UserService;

import javax.servlet.*;
import java.io.IOException;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.logon.LogOnServletProperty.NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_EMAIL;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.logon.LogOnServletProperty.NAME_OF_ATTRIBUTE_OF_REQUEST_OF_ERROR_MESSAGE;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.ServletProperty.PATH_OF_LOG_ON_PAGE;

public final class LoggedOnUserExistingFilter implements Filter
{
    private UserService userService;

    public LoggedOnUserExistingFilter()
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
            throws ServletException, IOException
    {
        try
        {
            final String inputtedEmail = servletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_EMAIL.getValue());
            if(!this.userService.isUserWithGivenEmailExist(inputtedEmail))
            {
                servletRequest.setAttribute(NAME_OF_ATTRIBUTE_OF_REQUEST_OF_ERROR_MESSAGE.getValue(),
                        LoggedOnUserExistingFilter.MESSAGE_OF_ERROR_OF_NOT_EXISTING_USER_WITH_GIVEN_EMAIL);
                final RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher(
                        PATH_OF_LOG_ON_PAGE.getValue());
                requestDispatcher.forward(servletRequest, servletResponse);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch(final DefiningExistingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }

    private static final String MESSAGE_OF_ERROR_OF_NOT_EXISTING_USER_WITH_GIVEN_EMAIL
            = "user with inputted email doesn't exist";
}
