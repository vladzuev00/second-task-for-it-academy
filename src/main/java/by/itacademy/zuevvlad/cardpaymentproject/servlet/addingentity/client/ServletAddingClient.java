package by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.client;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.AddingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.service.ClientService;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.client.PropertyOfAddingClientServlet.PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_CLIENT;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.*;

public final class ServletAddingClient extends HttpServlet
{
    private ClientService clientService;

    public ServletAddingClient()
    {
        super();
        this.clientService = null;
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
        final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_CLIENT.getValue());
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    public final void doPost(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final Client addedClient = this.clientService.createEntity(httpServletRequest);
            this.clientService.addEntityInDataBase(addedClient);

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
        catch(final EntityCreatingException | AddingEntityException | OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
