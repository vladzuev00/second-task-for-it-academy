package by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.bankaccount;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.DeletingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.service.BankAccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.itacademy.zuevvlad.cardpaymentproject.servlet.deletingentity.bankaccount.PropertyOfDeletingBankAccountServlet.*;

public final class ServletDeletingBankAccount extends HttpServlet
{
    private BankAccountService bankAccountService;

    public ServletDeletingBankAccount()
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
            final String descriptionOfIdOfDeletedBankAccount = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_ID_OF_DELETED_BANK_ACCOUNT.getValue());
            final long idOfDeletedBankAccount = Long.parseLong(descriptionOfIdOfDeletedBankAccount);
            this.bankAccountService.deleteEntityById(idOfDeletedBankAccount);
            httpServletResponse.sendRedirect(URL_TO_LIST_ALL_BANK_ACCOUNTS.getValue());
        }
        catch(final DeletingEntityException cause)
        {
            throw new ServletException(cause);
        }
    }
}
