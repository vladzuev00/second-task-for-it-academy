package by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.bankaccount;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.OffloadingEntitiesException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.UpdatingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.service.BankAccountService;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.updatingentity.bankaccount.PropertyOfUpdatingBankAccountServlet.*;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_BANK_ACCOUNTS;
import static by.itacademy.zuevvlad.cardpaymentproject.servlet.administrator.listingentities.PropertyOfListingEntitiesServlet.PATH_OF_PAGE_WITH_LISTED_BANK_ACCOUNTS;

public final class ServletUpdatingBankAccount extends HttpServlet
{
    private BankAccountService bankAccountService;

    public ServletUpdatingBankAccount()
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
            final String descriptionOfIdOfUpdatedBankAccount = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_BANK_ACCOUNT.getValue());
            final long idOfUpdatedBankAccount = Long.parseLong(descriptionOfIdOfUpdatedBankAccount);
            final Optional<BankAccount> optionalOfUpdatedBankAccount
                    = this.bankAccountService.findEntityById(idOfUpdatedBankAccount);
            if(optionalOfUpdatedBankAccount.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + BankAccount.class.getName()
                        + "', because object with id '" + idOfUpdatedBankAccount + "' doesn't exist.");
            }
            final BankAccount updatedBankAccount = optionalOfUpdatedBankAccount.get();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_UPDATED_BANK_ACCOUNT.getValue(),
                    updatedBankAccount);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_FORM_TO_UPDATE_BANK_ACCOUNT.getValue());
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
            final String descriptionOfIdOfUpdatedBankAccount = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_UPDATED_BANK_ACCOUNT.getValue());
            final long idOfUpdatedBankAccount = Long.parseLong(descriptionOfIdOfUpdatedBankAccount);
            final Optional<BankAccount> optionalOfUpdatedBankAccount
                    = this.bankAccountService.findEntityById(idOfUpdatedBankAccount);
            if(optionalOfUpdatedBankAccount.isEmpty())
            {
                throw new ServletException("Impossible to update object of class '" + BankAccount.class.getName()
                        + "', because object with id '" + idOfUpdatedBankAccount + "' doesn't exist.");
            }
            final BankAccount updatedBankAccount = optionalOfUpdatedBankAccount.get();

            this.bankAccountService.modifyEntity(updatedBankAccount, httpServletRequest);
            this.bankAccountService.updateEntityInDataBase(updatedBankAccount);

            final Collection<BankAccount> listedBankAccounts = this.bankAccountService.findAllEntities();
            httpServletRequest.setAttribute(NAME_OF_REQUEST_ATTRIBUTE_OF_LISTED_BANK_ACCOUNTS.getValue(),
                    listedBankAccounts);

            final RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(
                    PATH_OF_PAGE_WITH_LISTED_BANK_ACCOUNTS.getValue());
            requestDispatcher.forward(httpServletRequest, httpServletResponse);
        }
        catch(final FindingEntityException | EntityModifyingException | UpdatingEntityException
                | OffloadingEntitiesException cause)
        {
            throw new ServletException(cause);
        }
    }
}
