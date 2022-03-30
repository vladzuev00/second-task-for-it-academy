package by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.service.BankAccountService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_BANK_ACCOUNTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_BANK_ACCOUNTS;

public final class ServletListingBankAccounts extends HttpServlet
{
    private BankAccountService bankAccountService;

    public ServletListingBankAccounts()
    {
        super();
        this.bankAccountService = null;
    }

    @Override
    public final void init()
    {
        this.bankAccountService = BankAccountService.createBankAccountService();
    }

    @Override
    public final void doGet(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse)
            throws ServletException, IOException
    {
        try
        {
            final Collection<BankAccount> listedBankAccounts = this.bankAccountService.findAllEntities();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_BANK_ACCOUNTS.getValue(),
                    listedBankAccounts);
            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_LISTED_BANK_ACCOUNTS.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
