package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.client;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.DeletingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.client.PropertyOfDeletingClientServlet.*;

public final class ServletDeletingClient extends HttpServlet
{
    private ClientService clientService;

    public ServletDeletingClient()
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
            final String descriptionOfIdOfDeletedClient = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_CLIENT.getValue());
            final long idOfDeletedClient = Long.parseLong(descriptionOfIdOfDeletedClient);
            this.clientService.deleteEntityById(idOfDeletedClient);
            httpServletResponse.sendRedirect(URL_TO_LIST_ALL_CLIENTS.getValue());
        }
        catch(final DeletingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }
}
