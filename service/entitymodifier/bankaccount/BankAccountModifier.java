package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.bankaccount;

import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.bankaccount.NameOfRequestParameterToUpdateBankAccount.*;

public final class BankAccountModifier implements EntityModifier<BankAccount>
{
    public static BankAccountModifier createBankAccountModifier()
    {
        if(BankAccountModifier.bankAccountModifier == null)
        {
            synchronized(BankAccountModifier.class)
            {
                if(BankAccountModifier.bankAccountModifier == null)
                {
                    BankAccountModifier.bankAccountModifier = new BankAccountModifier();
                }
            }
        }
        return BankAccountModifier.bankAccountModifier;
    }

    private static BankAccountModifier bankAccountModifier = null;

    private BankAccountModifier()
    {
        super();
    }

    @Override
    public final void modify(final BankAccount modifiedBankAccount, final HttpServletRequest httpServletRequest)
    {
        final String descriptionOfNewMoney = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_UPDATED_BANK_ACCOUNT.getValue());
        final BigDecimal newMoney = new BigDecimal(descriptionOfNewMoney);

        final String descriptionOfNewBlocked = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_BLOCKED_OF_UPDATED_BANK_ACCOUNT.getValue());
        final boolean newBlocked = Boolean.parseBoolean(descriptionOfNewBlocked);

        final String newNumber = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_NUMBER_OF_UPDATED_BANK_ACCOUNT.getValue());

        modifiedBankAccount.setMoney(newMoney);
        modifiedBankAccount.setBlocked(newBlocked);
        modifiedBankAccount.setNumber(newNumber);
    }
}
