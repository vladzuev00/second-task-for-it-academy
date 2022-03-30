package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.client;

import by.itacademy.zuevvlad.cardpaymentproject.dao.BankAccountDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.client.NameOfRequestParameterToAddClient.*;

public final class ClientCreator implements EntityCreator<Client>
{
    private final BankAccountDAO bankAccountDAO;

    public static ClientCreator createClientCreator()
    {
        if(ClientCreator.clientCreator == null)
        {
            synchronized(ClientCreator.class)
            {
                if(ClientCreator.clientCreator == null)
                {
                    ClientCreator.clientCreator = new ClientCreator();
                }
            }
        }
        return ClientCreator.clientCreator;
    }

    private static ClientCreator clientCreator = null;

    private ClientCreator()
    {
        super();
        this.bankAccountDAO = BankAccountDAO.createBankAccountDAO();
    }

    @Override
    public final Client createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
        try
        {
            final String email = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_ADDED_CLIENT.getValue());
            final String password = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_CLIENT.getValue());
            final String name = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_ADDED_CLIENT.getValue());
            final String surname = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_SURNAME_OF_ADDED_CLIENT.getValue());
            final String patronymic = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PATRONYMIC_OF_ADDED_CLIENT.getValue());
            final String phoneNumber = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_ADDED_CLIENT.getValue());

            final String numberOfBankAccounts = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_BANK_ACCOUNT_NUMBER_OF_ADDED_CLIENT.getValue());
            final Optional<BankAccount> optionalOfBankAccount
                    = this.bankAccountDAO.findBankAccountByNumber(numberOfBankAccounts);
            if(optionalOfBankAccount.isEmpty())
            {
                throw new EntityCreatingException("Impossible to find object of class '" + Client.class.getName()
                        + "' by not existing number of bank account '" + numberOfBankAccounts + "'.");
            }
            final BankAccount bankAccount = optionalOfBankAccount.get();

            return new Client(email, password, name, surname, patronymic, phoneNumber, bankAccount);
        }
        catch(final FindingEntityException cause)
        {
            throw new EntityCreatingException(cause);
        }
    }
}

