package by.itacademy.zuevvlad.cardpaymentproject.servlet.logon;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.entity.User;
import by.itacademy.zuevvlad.cardpaymentproject.service.AdministratorService;
import by.itacademy.zuevvlad.cardpaymentproject.service.ClientService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.logon.LogOnServletProperty.NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_EMAIL;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.ServletProperty.PATH_OF_MAIN_PAGE_OF_USER;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.ServletProperty.PATH_OF_MAIN_PAGE_OF_ADMINISTRATOR;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.ServletProperty.NAME_OF_SESSION_ATTRIBUTE_OF_LOGGED_ON_USER;

public final class LogOnServlet extends HttpServlet
{
    private ClientService clientService;
    private AdministratorService administratorService;

    @Override
    public final void init()
    {
        this.clientService = ClientService.createClientService();
        this.administratorService = AdministratorService.createAdministratorService();
    }

    @Override
    public final void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final String inputtedEmail = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_INPUTTED_USER_EMAIL.getValue());
            String pathOfResultPage;
            User loggedOnUser;
            final Optional<Client> optionalOfLoggedOnClient = this.clientService.findClientByGivenEmail(
                    inputtedEmail);
            if(optionalOfLoggedOnClient.isPresent())
            {
                pathOfResultPage = PATH_OF_MAIN_PAGE_OF_USER.getValue();
                loggedOnUser = optionalOfLoggedOnClient.get();
            }
            else
            {
                final Optional<Administrator> optionalOfLoggedOnAdministrator = this.administratorService
                        .findAdministratorByGivenEmail(inputtedEmail);
                if(optionalOfLoggedOnAdministrator.isEmpty())
                {
                    throw new ServletException("User with given email '" + inputtedEmail
                            + "' is impossible to identify.");
                }
                pathOfResultPage = PATH_OF_MAIN_PAGE_OF_ADMINISTRATOR.getValue();
                loggedOnUser = optionalOfLoggedOnAdministrator.get();
            }
            final HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute(NAME_OF_SESSION_ATTRIBUTE_OF_LOGGED_ON_USER.getValue(), loggedOnUser);
            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(pathOfResultPage);
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final FindingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }
}
