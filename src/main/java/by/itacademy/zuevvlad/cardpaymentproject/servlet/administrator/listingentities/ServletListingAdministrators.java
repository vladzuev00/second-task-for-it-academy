package by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator;
import by.itacademy.zuevvlad.cardpaymentproject.service.AdministratorService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_ADMINISTRATORS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_ADMINISTRATORS;

public final class ServletListingAdministrators extends HttpServlet
{
    private AdministratorService administratorService;

    public ServletListingAdministrators()
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
            final Collection<Administrator> listedAdministrators = this.administratorService.findAllEntities();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_ADMINISTRATORS.getValue(),
                    listedAdministrators);
            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_LISTED_ADMINISTRATORS.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}

