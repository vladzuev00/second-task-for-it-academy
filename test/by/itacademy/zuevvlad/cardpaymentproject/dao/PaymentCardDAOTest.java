package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.Optional;

public final class PaymentCardDAOTest
{
    private final PaymentCardDAO paymentCardDAO;
    private BankAccount insertedBankAccount;
    private Client insertedClient;

    public PaymentCardDAOTest()
    {
        super();
        this.paymentCardDAO = PaymentCardDAO.createPaymentCardDAO();
        this.insertedBankAccount = null;
        this.insertedClient = null;
    }

    @BeforeClass
    public final void insertBankAccount()
            throws AddingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final boolean blocked = false;
        final String number = "11112222333344445555";
        final BankAccount bankAccount = new BankAccount(money, blocked, number);
        final DAO<BankAccount> bankAccountDAO = BankAccountDAO.createBankAccountDAO();
        bankAccountDAO.addEntity(bankAccount);
        this.insertedBankAccount = bankAccount;
    }

    @BeforeClass(dependsOnMethods = {"insertBankAccount"})
    public final void insertClient()
            throws AddingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";
        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);
        final DAO<Client> clientDAO = ClientDAO.createClientDAO();
        clientDAO.addEntity(client);
        this.insertedClient = client;
    }

    @AfterClass(dependsOnMethods = {"deleteInsertedClient"})
    public final void deleteInsertedBankAccount()
            throws DeletingEntityException
    {
        final DAO<BankAccount> bankAccountDAO = BankAccountDAO.createBankAccountDAO();
        bankAccountDAO.deleteEntity(this.insertedBankAccount);
    }

    @AfterClass
    public final void deleteInsertedClient()
            throws DeletingEntityException
    {
        final DAO<Client> clientDAO = ClientDAO.createClientDAO();
        clientDAO.deleteEntity(this.insertedClient);
    }

    @Test
    public final void paymentCardShouldBeAdded()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        final long idOfAddedPaymentCardBeforeAdding = paymentCard.getId();
        this.paymentCardDAO.addEntity(paymentCard);
        try
        {
            final long idOfAddedPaymentCardAfterAdding = paymentCard.getId();
            final Optional<PaymentCard> optionalOfAddedPaymentCardFromDataBase = this.paymentCardDAO.findEntityById(
                    idOfAddedPaymentCardAfterAdding);
            if(optionalOfAddedPaymentCardFromDataBase.isEmpty())
            {
                Assert.fail();
            }
            final PaymentCard addedPaymentCardFromDataBase = optionalOfAddedPaymentCardFromDataBase.get();

            final boolean testSuccess = paymentCard.equals(addedPaymentCardFromDataBase)
                    && idOfAddedPaymentCardBeforeAdding != idOfAddedPaymentCardAfterAdding;
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.paymentCardDAO.deleteEntity(paymentCard);
        }
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeDeleted"}, expectedExceptions = {AddingEntityException.class})
    public final void paymentCardWithAlreadyExistingCardNumberShouldNotBeAdded()
            throws AddingEntityException, DeletingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard firstPaymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        try
        {
            this.paymentCardDAO.addEntity(firstPaymentCard);
        }
        catch(final AddingEntityException cause)
        {
            Assert.fail();
        }
        try
        {
            final PaymentCard secondPaymentCard = new PaymentCard(cardNumber, expirationDate,
                    paymentSystem, cvc, this.insertedClient, nameOfBank, password);
            this.paymentCardDAO.addEntity(secondPaymentCard);
            try
            {
                Assert.fail();
            }
            finally
            {
                this.paymentCardDAO.deleteEntity(secondPaymentCard);
            }
        }
        finally
        {
            this.paymentCardDAO.deleteEntity(firstPaymentCard);
        }
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeDeleted"}, expectedExceptions = {AddingEntityException.class})
    public final void paymentCardWithNotValidCardNumberShouldNotBeAdded()
            throws AddingEntityException, DeletingEntityException
    {
        final String cardNumber = "1111-11111111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        this.paymentCardDAO.addEntity(paymentCard);
        try
        {
            Assert.fail();
        }
        finally
        {
            this.paymentCardDAO.deleteEntity(paymentCard);
        }
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeAdded", "paymentCardShouldBeDeleted"})
    public final void allPaymentCardsShouldBeOffloaded()
            throws AddingEntityException, OffloadingEntitiesException, DeletingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        this.paymentCardDAO.addEntity(paymentCard);
        try
        {
            final Collection<PaymentCard> offloadedPaymentCards = this.paymentCardDAO.offloadEntities();
            final boolean testIsSuccess = offloadedPaymentCards != null && !offloadedPaymentCards.isEmpty()
                    && offloadedPaymentCards.contains(paymentCard);
            Assert.assertTrue(testIsSuccess);
        }
        finally
        {
            this.paymentCardDAO.deleteEntity(paymentCard);
        }
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeAdded"})
    public final void paymentCardShouldBeFoundById()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        this.paymentCardDAO.addEntity(paymentCard);
        try
        {
            final Optional<PaymentCard> optionalOfPaymentCardFoundInDataBase = this.paymentCardDAO.findEntityById(
                    paymentCard.getId());
            if(optionalOfPaymentCardFoundInDataBase.isEmpty())
            {
                Assert.fail();
            }
            final PaymentCard paymentCardFoundInDataBase = optionalOfPaymentCardFoundInDataBase.get();
            Assert.assertEquals(paymentCard, paymentCardFoundInDataBase);
        }
        finally
        {
            this.paymentCardDAO.deleteEntity(paymentCard);
        }
    }

    @Test
    public final void paymentCardShouldNotBeFoundByNotExistingId()
            throws FindingEntityException
    {
        final long idOfFoundPaymentCard = -1;
        final Optional<PaymentCard> optionalOfFoundPaymentCard = this.paymentCardDAO.findEntityById
                (idOfFoundPaymentCard);
        Assert.assertTrue(optionalOfFoundPaymentCard.isEmpty());
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeAdded", "paymentCardShouldBeFoundById", "paymentCardShouldBeDeleted"})
    public final void paymentCardShouldBeUpdated()
            throws AddingEntityException, UpdatingEntityException, FindingEntityException, DeletingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        this.paymentCardDAO.addEntity(paymentCard);
        try
        {
            final String newCardNumber = "2222-2222-2222-2222";
            paymentCard.setCardNumber(newCardNumber);
            this.paymentCardDAO.updateEntity(paymentCard);

            final Optional<PaymentCard> optionalOfUpdatedPaymentCardFromDataBase = this.paymentCardDAO.findEntityById(
                    paymentCard.getId());
            if(optionalOfUpdatedPaymentCardFromDataBase.isEmpty())
            {
                Assert.fail();
            }

            final PaymentCard updatedPaymentCardFromDataBase = optionalOfUpdatedPaymentCardFromDataBase.get();
            Assert.assertEquals(paymentCard, updatedPaymentCardFromDataBase);
        }
        finally
        {
            this.paymentCardDAO.deleteEntity(paymentCard);
        }
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeAdded", "paymentCardShouldBeDeleted"},
            expectedExceptions = {UpdatingEntityException.class})
    public final void paymentCardShouldNotBeUpdatedByNotValidCardNumber()
            throws AddingEntityException, UpdatingEntityException, DeletingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        this.paymentCardDAO.addEntity(paymentCard);
        try
        {
            final String newCardNumber = "1111-1111-11111-1111";
            paymentCard.setCardNumber(newCardNumber);
            this.paymentCardDAO.updateEntity(paymentCard);
        }
        finally
        {
            this.paymentCardDAO.deleteEntity(paymentCard);
        }
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeAdded", "paymentCardShouldBeFoundById"})
    public final void paymentCardShouldBeDeletedById()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);
        this.paymentCardDAO.addEntity(paymentCard);

        this.paymentCardDAO.deleteEntityById(paymentCard.getId());
        final Optional<PaymentCard> optionalOfFoundPaymentCard = this.paymentCardDAO.findEntityById(paymentCard.getId());
        Assert.assertTrue(optionalOfFoundPaymentCard.isEmpty());
    }

    @Test(dependsOnMethods = {"allPaymentCardsShouldBeOffloaded"})
    public final void paymentCardShouldNotBeDeletedByNotExistingId()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final long idOfDeletedPaymentCard = -1;
        final Collection<PaymentCard> paymentCardsBeforeDeleting = this.paymentCardDAO.offloadEntities();
        this.paymentCardDAO.deleteEntityById(idOfDeletedPaymentCard);
        final Collection<PaymentCard> paymentCardsAfterDeleting = this.paymentCardDAO.offloadEntities();
        Assert.assertEquals(paymentCardsBeforeDeleting, paymentCardsAfterDeleting);
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeAdded", "paymentCardShouldBeFoundById"})
    public final void paymentCardShouldBeDeleted()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);
        this.paymentCardDAO.addEntity(paymentCard);
        this.paymentCardDAO.deleteEntity(paymentCard);

        final Optional<PaymentCard> optionalOfFoundPaymentCard = this.paymentCardDAO.findEntityById(paymentCard.getId());
        Assert.assertTrue(optionalOfFoundPaymentCard.isEmpty());
    }

    @Test(dependsOnMethods = {"allPaymentCardsShouldBeOffloaded"})
    public final void paymentCardWithNotExistingIdShouldNotBeDeleted()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        final Collection<PaymentCard> paymentCardsBeforeDeleting = this.paymentCardDAO.offloadEntities();
        this.paymentCardDAO.deleteEntity(paymentCard);
        final Collection<PaymentCard> paymentCardsAfterDeleting = this.paymentCardDAO.offloadEntities();

        Assert.assertEquals(paymentCardsBeforeDeleting, paymentCardsAfterDeleting);
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeAdded", "paymentCardShouldBeDeleted"})
    public final void paymentCardWithGivenIdShouldBeExisted()
            throws AddingEntityException, DefiningExistingEntityException, DeletingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        this.paymentCardDAO.addEntity(paymentCard);
        try
        {
            final boolean paymentCardExists = this.paymentCardDAO.isEntityWithGivenIdExisting(paymentCard.getId());
            Assert.assertTrue(paymentCardExists);
        }
        finally
        {
            this.paymentCardDAO.deleteEntity(paymentCard);
        }
    }

    @Test
    public final void paymentCardWithGivenNotExistingIdShouldNotBeExisted()
            throws DefiningExistingEntityException
    {
        final long idOfResearchPaymentCard = -1;
        final boolean paymentCardExists = this.paymentCardDAO.isEntityWithGivenIdExisting(idOfResearchPaymentCard);
        Assert.assertFalse(paymentCardExists);
    }

    @Test(dependsOnMethods = {"paymentCardShouldBeAdded", "paymentCardShouldBeDeleted"})
    public final void paymentCardShouldBeFoundByGivenCardNumber()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String cardNumber = "1111-1111-1111-1111";
        final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
        final String paymentSystem = "visa";
        final String cvc = "111";
        final String nameOfBank = "the best bank";
        final String password = "1111";
        final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                this.insertedClient, nameOfBank, password);

        this.paymentCardDAO.addEntity(paymentCard);
        try
        {
            final Optional<PaymentCard> optionalOfFoundPaymentCard
                    = this.paymentCardDAO.findPaymentCardByGivenCardNumber(paymentCard.getCardNumber());
            if(optionalOfFoundPaymentCard.isEmpty())
            {
                Assert.fail();
            }
            final PaymentCard foundPaymentCard = optionalOfFoundPaymentCard.get();
            Assert.assertEquals(paymentCard, foundPaymentCard);
        }
        finally
        {
            this.paymentCardDAO.deleteEntity(paymentCard);
        }
    }

    @Test
    public final void paymentCardShouldNotBeFoundByNotValidCardNumber()
            throws FindingEntityException
    {
        final String cardNumber = "1111-1111-1111-11111";
        final Optional<PaymentCard> optionalOfFoundPaymentCard = this.paymentCardDAO.findPaymentCardByGivenCardNumber(
                cardNumber);
        Assert.assertTrue(optionalOfFoundPaymentCard.isEmpty());
    }
}
