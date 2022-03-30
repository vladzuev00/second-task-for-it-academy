package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public final class BankAccountDAOTest
{
    private final BankAccountDAO bankAccountDAO;

    public BankAccountDAOTest()
    {
        super();

        this.bankAccountDAO = BankAccountDAO.createBankAccountDAO();
    }

    @Test
    public final void bankAccountShouldBeAdded()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.ZERO;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);

        final long idOfBankAccountBeforeAdding = bankAccount.getId();
        this.bankAccountDAO.addEntity(bankAccount);
        try
        {
            final long idOfBankAccountAfterAdding = bankAccount.getId();

            final Optional<BankAccount> optionalOfAddedBankAccountFromDataBase = this.bankAccountDAO.findEntityById(
                    idOfBankAccountAfterAdding);
            if(optionalOfAddedBankAccountFromDataBase.isEmpty())
            {
                Assert.fail();
            }
            final BankAccount addedBankAccountFromDataBase = optionalOfAddedBankAccountFromDataBase.get();

            final boolean testSuccess = bankAccount.equals(addedBankAccountFromDataBase)
                    && idOfBankAccountBeforeAdding != idOfBankAccountAfterAdding;
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.bankAccountDAO.deleteEntity(bankAccount);
        }
    }

    @Test(expectedExceptions = {AddingEntityException.class})
    public final void bankAccountWithNotPositiveMoneyShouldNotBeAdded()
            throws AddingEntityException, DeletingEntityException
    {
        final BigDecimal money = new BigDecimal("-1");
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);

        this.bankAccountDAO.addEntity(bankAccount);
        try
        {
            Assert.fail();
        }
        finally
        {
            this.bankAccountDAO.deleteEntity(bankAccount);
        }
    }

    @Test(dependsOnMethods = {"bankAccountShouldBeAdded", "bankAccountShouldBeDeleted"})
    public final void allBankAccountsShouldBeOffloaded()
            throws AddingEntityException, OffloadingEntitiesException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);

        this.bankAccountDAO.addEntity(bankAccount);
        try
        {
            final Collection<BankAccount> offloadedBankAccounts = this.bankAccountDAO.offloadEntities();
            final boolean testSuccess = offloadedBankAccounts != null && !offloadedBankAccounts.isEmpty()
                    && offloadedBankAccounts.contains(bankAccount);
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.bankAccountDAO.deleteEntity(bankAccount);
        }
    }

    @Test(dependsOnMethods = {"bankAccountShouldBeAdded"})
    public final void bankAccountShouldBeFoundById()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);

        this.bankAccountDAO.addEntity(bankAccount);
        try
        {
            final Optional<BankAccount> optionalOfBankAccountFoundInDataBase = this.bankAccountDAO.findEntityById(
                    bankAccount.getId());
            if(optionalOfBankAccountFoundInDataBase.isEmpty())
            {
                Assert.fail();
            }
            final BankAccount bankAccountFoundInDataBase = optionalOfBankAccountFoundInDataBase.get();
            Assert.assertEquals(bankAccount, bankAccountFoundInDataBase);
        }
        finally
        {
            this.bankAccountDAO.deleteEntity(bankAccount);
        }
    }

    @Test
    public final void bankAccountShouldNotBeFoundByNotValidId()
            throws FindingEntityException
    {
        final long idOfFoundBankAccount = -1;
        final Optional<BankAccount> optionalOfFoundBankAccount = this.bankAccountDAO.findEntityById(
                idOfFoundBankAccount);
        Assert.assertFalse(optionalOfFoundBankAccount.isPresent());
    }

    @Test(dependsOnMethods = {"bankAccountShouldBeAdded", "bankAccountShouldBeFoundById", "bankAccountShouldBeDeleted"})
    public final void bankAccountShouldBeUpdated()
            throws AddingEntityException, UpdatingEntityException, FindingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);

        this.bankAccountDAO.addEntity(bankAccount);
        try
        {
            final BigDecimal newMoney = BigDecimal.ONE;
            final boolean newBlocked = true;
            bankAccount.setMoney(newMoney);
            bankAccount.setBlocked(newBlocked);
            this.bankAccountDAO.updateEntity(bankAccount);

            final Optional<BankAccount> optionalOfUpdatedBankAccountFromDataBase = this.bankAccountDAO.findEntityById(
                    bankAccount.getId());
            if(optionalOfUpdatedBankAccountFromDataBase.isEmpty())
            {
                Assert.fail();
            }

            final BankAccount updatedBankAccountFromDataBase = optionalOfUpdatedBankAccountFromDataBase.get();
            Assert.assertEquals(bankAccount, updatedBankAccountFromDataBase);
        }
        finally
        {
            this.bankAccountDAO.deleteEntity(bankAccount);
        }
    }

    @Test(dependsOnMethods = {"bankAccountShouldBeAdded", "bankAccountShouldBeDeleted"},
            expectedExceptions = {UpdatingEntityException.class})
    public final void bankAccountShouldNotBeUpdatedByNegativeMoney()
            throws AddingEntityException, UpdatingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);

        this.bankAccountDAO.addEntity(bankAccount);
        try
        {
            final BigDecimal newMoney = new BigDecimal("-1");
            bankAccount.setMoney(newMoney);
            this.bankAccountDAO.updateEntity(bankAccount);
        }
        finally
        {
            this.bankAccountDAO.deleteEntity(bankAccount);
        }
    }

    @Test(dependsOnMethods = {"bankAccountShouldBeAdded", "bankAccountShouldBeFoundById"})
    public final void bankAccountShouldBeDeletedById()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);
        this.bankAccountDAO.addEntity(bankAccount);

        this.bankAccountDAO.deleteEntityById(bankAccount.getId());

        final Optional<BankAccount> optionalOfFoundBankAccount = this.bankAccountDAO.findEntityById(
                bankAccount.getId());
        Assert.assertFalse(optionalOfFoundBankAccount.isPresent());
    }

    @Test(dependsOnMethods = {"allBankAccountsShouldBeOffloaded"})
    public final void bankAccountShouldNotBeDeletedByNotValidId()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final long idOfDeletedBankAccount = -1;

        final Collection<BankAccount> bankAccountsBeforeDeleting = this.bankAccountDAO.offloadEntities();
        this.bankAccountDAO.deleteEntityById(idOfDeletedBankAccount);
        final Collection<BankAccount> bankAccountsAfterDeleting = this.bankAccountDAO.offloadEntities();

        Assert.assertEquals(bankAccountsBeforeDeleting, bankAccountsAfterDeleting);
    }

    @Test(dependsOnMethods = {"bankAccountShouldBeAdded", "bankAccountShouldBeFoundById"})
    public final void bankAccountShouldBeDeleted()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);
        this.bankAccountDAO.addEntity(bankAccount);

        this.bankAccountDAO.deleteEntity(bankAccount);

        final Optional<BankAccount> optionalOfFoundBankAccount = this.bankAccountDAO.findEntityById(
                bankAccount.getId());
        Assert.assertFalse(optionalOfFoundBankAccount.isPresent());
    }

    @Test(dependsOnMethods = {"allBankAccountsShouldBeOffloaded"})
    public final void bankAccountShouldNotBeDeleted()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);   //bank account with not valid id

        final Collection<BankAccount> bankAccountsBeforeDeleting = this.bankAccountDAO.offloadEntities();
        this.bankAccountDAO.deleteEntity(bankAccount);
        final Collection<BankAccount> bankAccountsAfterDeleting = this.bankAccountDAO.offloadEntities();

        Assert.assertEquals(bankAccountsBeforeDeleting, bankAccountsAfterDeleting);
    }

    @Test(dependsOnMethods = {"bankAccountShouldBeAdded", "bankAccountShouldBeDeleted"})
    public final void bankAccountWithGivenIdShouldBeExisted()
            throws AddingEntityException, DefiningExistingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);

        this.bankAccountDAO.addEntity(bankAccount);
        try
        {
            final boolean bankAccountExists = this.bankAccountDAO.isEntityWithGivenIdExisting(bankAccount.getId());
            Assert.assertTrue(bankAccountExists);
        }
        finally
        {
            this.bankAccountDAO.deleteEntity(bankAccount);
        }
    }

    @Test
    public final void bankAccountWithNotValidIdShouldNotBeExisted()
            throws DefiningExistingEntityException
    {
        final long idOfResearchBankAccount = -1;
        final boolean bankAccountExists = this.bankAccountDAO.isEntityWithGivenIdExisting(idOfResearchBankAccount);
        Assert.assertFalse(bankAccountExists);
    }

    @Test(dependsOnMethods = {"bankAccountShouldBeAdded", "bankAccountShouldBeDeleted"})
    public final void bankAccountShouldBeFoundByGivenNumber()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);

        this.bankAccountDAO.addEntity(bankAccount);
        try
        {
            final Optional<BankAccount> optionalOfFoundBankAccount = this.bankAccountDAO.findBankAccountByNumber(
                    bankAccount.getNumber());
            if(optionalOfFoundBankAccount.isEmpty())
            {
                Assert.fail();
            }
            final BankAccount foundBankAccount = optionalOfFoundBankAccount.get();
            Assert.assertEquals(bankAccount, foundBankAccount);
        }
        finally
        {
            this.bankAccountDAO.deleteEntity(bankAccount);
        }
    }

    @Test
    public final void bankAccountShouldNotBeFoundByNotValidNumber()
            throws FindingEntityException
    {
        final String number = "9999999999999999999";    //short number
        final Optional<BankAccount> optionalOfBankAccount = this.bankAccountDAO.findBankAccountByNumber(number);
        Assert.assertTrue(optionalOfBankAccount.isEmpty());
    }
}
