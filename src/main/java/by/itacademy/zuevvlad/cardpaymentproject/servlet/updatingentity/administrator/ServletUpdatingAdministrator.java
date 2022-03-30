package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.administrator;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.UpdatingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator;
import by.itacademy.zuevvlad.cardpaymentproject.service.AdministratorService;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.administrator.PropertyOfUpdatingAdministratorServlet.*;

public final class ServletUpdatingAdministrator extends HttpServlet
{
    private AdministratorService administratorService;

    public ServletUpdatingAdministrator()
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
            final String descriptionOfIdOfUpdatedAdministrator = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_ADMINISTRATOR.getValue());
            final long idOfUpdatedAdministrator = Long.parseLong(descriptionOfIdOfUpdatedAdministrator);
            final Optional<Administrator> optionalOfUpdatedAdministrator = this.administratorService.findEntityById(
                    idOfUpdatedAdministrator);
            if(optionalOfUpdatedAdministrator.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + Administrator.class.getName()
                        + "', because object with id '" + idOfUpdatedAdministrator + "' doesn't exist.");
            }
            final Administrator updatedAdministrator = optionalOfUpdatedAdministrator.get();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_ADMINISTRATOR.getValue(),
                    updatedAdministrator);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_ADMINISTRATOR.getValue());
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
            final String descriptionOfIdOfUpdatedAdministrator = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_ADMINISTRATOR.getValue());
            final long idOfUpdatedAdministrator = Long.parseLong(descriptionOfIdOfUpdatedAdministrator);
            final Optional<Administrator> optionalOfUpdatedAdministrator = this.administratorService.findEntityById(
                    idOfUpdatedAdministrator);
            if(optionalOfUpdatedAdministrator.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + Administrator.class.getName()
                        + "', because object with id '" + idOfUpdatedAdministrator + "' doesn't exist.");
            }
            final Administrator updatedAdministrator = optionalOfUpdatedAdministrator.get();

            this.administratorService.modifyEntity(updatedAdministrator, httpServletRequest);
            this.administratorService.updateEntityInDataBase(updatedAdministrator);

            httpServletResponse.sendRedirect("/admin/list_administrators");
        }
        catch(final FindingEntityException | EntityModifyingException | UpdatingEntityException
                 cause)
        {
            throw new ServletException(cause);
        }
    }
}
