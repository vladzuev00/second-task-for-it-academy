package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.administrator;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.DeletingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.service.AdministratorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.administrator.PropertyOfDeletingAdministratorServlet.*;

public final class ServletDeletingAdministrator extends HttpServlet
{
    private AdministratorService administratorService;

    public ServletDeletingAdministrator()
    {
        super();
        this.administratorService = null;
    }

    @Override
    public final void init()
    {
        this.administratorService = AdministratorService.createAdministratorService();
    }

    @Override
    public final void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final String descriptionOfIdOfDeletedAdministrator = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_ADMINISTRATOR.getValue());
            final long idOfDeletedAdministrator = Long.parseLong(descriptionOfIdOfDeletedAdministrator);
            this.administratorService.deleteEntityById(idOfDeletedAdministrator);
            httpServletResponse.sendRedirect(URL_TO_LIST_ALL_ADMINISTRATORS.getValue());
        }
        catch(final DeletingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }
}
