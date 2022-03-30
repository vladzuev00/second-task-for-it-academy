package by.itacademy.zuevvlad.cardpaymentproject.service;

import by.itacademy.zuevvlad.cardpaymentproject.dao.BankAccountDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.bankaccount.BankAccountCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.bankaccount.BankAccountModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

public final class BankAccountService extends EntityService<BankAccount>
{
    private final BankAccountDAO bankAccountDAO;

    public static BankAccountService createBankAccountService()
    {
        if(BankAccountService.bankAccountService == null)
        {
            synchronized(BankAccountService.class)
            {
                if(BankAccountService.bankAccountService == null)
                {
                    final EntityCreator<BankAccount> bankAccountCreator = BankAccountCreator.createBankAccountCreator();
                    final EntityModifier<BankAccount> bankAccountModifier
                            = BankAccountModifier.createBankAccountModifier();
                    BankAccountService.bankAccountService = new BankAccountService(
                            bankAccountCreator, bankAccountModifier);
                }
            }
        }
        return BankAccountService.bankAccountService;
    }

    private static BankAccountService bankAccountService = null;

    private BankAccountService(final EntityCreator<BankAccount> bankAccountCreator,
                               final EntityModifier<BankAccount> bankAccountModifier)
    {
        super(bankAccountCreator, bankAccountModifier);
        this.bankAccountDAO = BankAccountDAO.createBankAccountDAO();
    }

    @Override
    public final Collection<BankAccount> findAllEntities()
            throws OffloadingEntitiesException
    {
        return this.bankAccountDAO.offloadEntities();
    }

    @Override
    public final void addEntityInDataBase(final BankAccount addedBankAccount)
            throws AddingEntityException
    {
        this.bankAccountDAO.addEntity(addedBankAccount);
    }

    @Override
    public final BankAccount createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
        final EntityCreator<BankAccount> bankAccountCreator = super.getEntityCreator();
        return bankAccountCreator.createEntity(httpServletRequest);
    }

    @Override
    public final Optional<BankAccount> findEntityById(final long idOfFoundBankAccount)
            throws FindingEntityException
    {
        return this.bankAccountDAO.findEntityById(idOfFoundBankAccount);
    }

    @Override
    public final void modifyEntity(final BankAccount modifiedBankAccount, final HttpServletRequest httpServletRequest)
            throws EntityModifyingException
    {
        final EntityModifier<BankAccount> bankAccountModifier = super.getEntityModifier();
        bankAccountModifier.modify(modifiedBankAccount, httpServletRequest);
    }

    @Override
    public final void updateEntityInDataBase(final BankAccount updatedBankAccount)
            throws UpdatingEntityException
    {
        this.bankAccountDAO.updateEntity(updatedBankAccount);
    }

    @Override
    public final void deleteEntityById(final long idOfDeletedBankAccount)
            throws DeletingEntityException
    {
        this.bankAccountDAO.deleteEntityById(idOfDeletedBankAccount);
    }
}
