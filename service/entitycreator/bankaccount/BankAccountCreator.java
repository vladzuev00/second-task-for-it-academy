package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.bankaccount;

import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.bankaccount.NameOfRequestParameterToAddBankAccount.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public final class BankAccountCreator implements EntityCreator<BankAccount>
{
    public static BankAccountCreator createBankAccountCreator()
    {
        if(BankAccountCreator.bankAccountCreator == null)
        {
            synchronized(BankAccountCreator.class)
            {
                if(BankAccountCreator.bankAccountCreator == null)
                {
                    BankAccountCreator.bankAccountCreator = new BankAccountCreator();
                }
            }
        }
        return BankAccountCreator.bankAccountCreator;
    }

    private static BankAccountCreator bankAccountCreator = null;

    private BankAccountCreator()
    {
        super();
    }

    @Override
    public final BankAccount createEntity(final HttpServletRequest httpServletRequest)
    {
        final String descriptionOfMoney = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_ADDED_BANK_ACCOUNT.getValue());
        final BigDecimal money = new BigDecimal(descriptionOfMoney);

        final String descriptionOfBlocked = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_BLOCKED_OF_ADDED_BANK_ACCOUNT.getValue());
        final boolean blocked = Boolean.parseBoolean(descriptionOfBlocked);

        final String number = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_NUMBER_OF_ADDED_BANK_ACCOUNT.getValue());

        return new BankAccount(money, blocked, number);
    }
}

