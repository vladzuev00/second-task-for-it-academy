package by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.service.ClientService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_CLIENTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_CLIENTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_CLIENTS_AND_ASSOCIATED_NUMBERS_OF_PAYMENT_CARDS;

public final class ServletListingClients extends HttpServlet
{
    private ClientService clientService;

    public ServletListingClients()
    {
        super();
    }

    @Override
    public final void init()
    {
        this.clientService = ClientService.createClientService();
    }

    @Override
    public final void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final Collection<Client> listedClients = this.clientService.findAllEntities();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_CLIENTS.getValue(), listedClients);

            final Map<Client, List<String>> clientsAndAssociatedNumbersOfPaymentCards
                    = this.clientService.findClientsAndAssociatedNumbersOfPaymentCards();
            httpServletRequest.setAttribute(
                    NAME_OF_REQUEST_ATTRIBUTE_OF_CLIENTS_AND_ASSOCIATED_NUMBERS_OF_PAYMENT_CARDS.getValue(),
                    clientsAndAssociatedNumbersOfPaymentCards);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_LISTED_CLIENTS.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
