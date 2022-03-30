package by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.administrator;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.AddingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator;
import by.itacademy.zuevvlad.cardpaymentproject.service.AdministratorService;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.addingentity.administrator.PropertyOfAddingAdministratorServlet.PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_ADMINISTRATOR;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_ADMINISTRATORS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_ADMINISTRATORS;

public final class ServletAddingAdministrator extends HttpServlet
{
    private AdministratorService administratorService;

    public ServletAddingAdministrator()
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
        final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                PATH_OF_PAGE_WITH_FORM_TO_ADD_NEW_ADMINISTRATOR.getValue());
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    public final void doPost(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final Administrator addedAdministrator = this.administratorService.createEntity(httpServletRequest);
            this.administratorService.addEntityInDataBase(addedAdministrator);

            final Collection<Administrator> listedAdministrators = this.administratorService.findAllEntities();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_ADMINISTRATORS.getValue(),
                    listedAdministrators);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_LISTED_ADMINISTRATORS.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final EntityCreatingException | AddingEntityException | OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
