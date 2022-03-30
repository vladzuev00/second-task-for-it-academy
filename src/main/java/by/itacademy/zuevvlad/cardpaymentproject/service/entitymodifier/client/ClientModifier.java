package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.client;

import by.itacademy.zuevvlad.cardpaymentproject.dao.BankAccountDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.client.NameOfRequestParameterToUpdateClient.*;

public final class ClientModifier implements EntityModifier<Client>
{
    private final BankAccountDAO bankAccountDAO;

    public static ClientModifier createClientModifier()
    {
        if(ClientModifier.clientModifier == null)
        {
            synchronized(ClientModifier.class)
            {
                if(ClientModifier.clientModifier == null)
                {
                    ClientModifier.clientModifier = new ClientModifier();
                }
            }
        }
        return ClientModifier.clientModifier;
    }

    private static ClientModifier clientModifier = null;

    private ClientModifier()
    {
        super();
        this.bankAccountDAO = BankAccountDAO.createBankAccountDAO();
    }

    @Override
    public final void modify(final Client modifiedClient, final HttpServletRequest httpServletRequest)
            throws EntityModifyingException
    {
        try
        {
            final String newEmail = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_UPDATED_CLIENT.getValue());
            final String newPassword = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_UPDATED_CLIENT.getValue());
            final String newName = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_UPDATED_CLIENT.getValue());
            final String newSurname = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_SURNAME_OF_UPDATED_CLIENT.getValue());
            final String newPatronymic = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PATRONYMIC_OF_UPDATED_CLIENT.getValue());
            final String newPhoneNumber = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_UPDATED_CLIENT.getValue());

            final String numberOfNewBankAccount = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_BANK_ACCOUNT_NUMBER_OF_UPDATED_CLIENT.getValue());
            final Optional<BankAccount> optionalOfNewBankAccount
                    = this.bankAccountDAO.findBankAccountByNumber(numberOfNewBankAccount);
            if(optionalOfNewBankAccount.isEmpty())
            {
                throw new EntityModifyingException("Impossible to modify object '" + modifiedClient + "', because "
                        + "object of class '" + BankAccount.class.getName() + "' with number '"
                        + numberOfNewBankAccount + "' doesn't exist.");
            }
            final BankAccount newBankAccount = optionalOfNewBankAccount.get();

            modifiedClient.setEmail(newEmail);
            modifiedClient.setPassword(newPassword);
            modifiedClient.setName(newName);
            modifiedClient.setSurname(newSurname);
            modifiedClient.setPatronymic(newPatronymic);
            modifiedClient.setPhoneNumber(newPhoneNumber);
            modifiedClient.setBankAccount(newBankAccount);
        }
        catch(final FindingEntityException cause)
        {
            throw new EntityModifyingException(cause);
        }
    }
}
