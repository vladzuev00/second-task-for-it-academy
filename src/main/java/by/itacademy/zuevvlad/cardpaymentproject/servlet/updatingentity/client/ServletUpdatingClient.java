package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.client;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.UpdatingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.service.ClientService;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.client.PropertyOfUpdatingClientServlet.*;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_CLIENTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_CLIENTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_CLIENTS_AND_ASSOCIATED_NUMBERS_OF_PAYMENT_CARDS;

public final class ServletUpdatingClient extends HttpServlet
{
    private ClientService clientService;

    public ServletUpdatingClient()
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
        try
        {
            final String descriptionOfIdOfUpdatedClient = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_CLIENT.getValue());
            final long idOfUpdatedClient = Long.parseLong(descriptionOfIdOfUpdatedClient);
            final Optional<Client> optionalOfUpdatedClient = this.clientService.findEntityById(idOfUpdatedClient);
            if(optionalOfUpdatedClient.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + Client.class.getName()
                        + "', because object with id '" + idOfUpdatedClient + "' doesn't exist.");
            }
            final Client updatedClient = optionalOfUpdatedClient.get();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_CLIENT.getValue(), updatedClient);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_CLIENT.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final FindingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }

    @Override
    public final void doPost(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final String descriptionOfIdOfUpdatedClient = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_CLIENT.getValue());
            final long idOfUpdatedClient = Long.parseLong(descriptionOfIdOfUpdatedClient);
            final Optional<Client> optionalOfUpdatedClient = this.clientService.findEntityById(idOfUpdatedClient);
            if(optionalOfUpdatedClient.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + Client.class.getName()
                        + "', because object with id '" + idOfUpdatedClient + "' doesn't exist.");
            }
            final Client updatedClient = optionalOfUpdatedClient.get();

            this.clientService.modifyEntity(updatedClient, httpServletRequest);
            this.clientService.updateEntityInDataBase(updatedClient);

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
        catch(final FindingEntityException | EntityModifyingException | UpdatingEntityException
                | OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
