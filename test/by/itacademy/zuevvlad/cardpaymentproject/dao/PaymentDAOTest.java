package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Payment;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

public final class PaymentDAOTest
{
    private final DAO<Payment> paymentDAO;
    private BankAccount firstInsertedBankAccount;
    private BankAccount secondInsertedBankAccount;
    private Client firstInsertedClient;
    private Client secondInsertedClient;
    private PaymentCard firstInsertedPaymentCard;
    private PaymentCard secondInsertedPaymentCard;

    public PaymentDAOTest()
    {
        super();
        this.paymentDAO = PaymentDAO.createPaymentDAO();
        this.firstInsertedBankAccount = null;
        this.secondInsertedBankAccount = null;
        this.firstInsertedClient = null;
        this.secondInsertedClient = null;
        this.firstInsertedPaymentCard = null;
        this.secondInsertedPaymentCard = null;
    }

    @BeforeClass
    public final void insertBankAccounts()
            throws AddingEntityException
    {
        final DAO<BankAccount> bankAccountDAO = BankAccountDAO.createBankAccountDAO();
        {
            final BigDecimal money = BigDecimal.TEN;
            final boolean blocked = false;
            final String number = "11112222333344445555";
            final BankAccount bankAccount = new BankAccount(money, blocked, number);
            bankAccountDAO.addEntity(bankAccount);
            this.firstInsertedBankAccount = bankAccount;
        }
        {
            final BigDecimal money = BigDecimal.TEN;
            final boolean blocked = false;
            final String number = "11112222333344445556";
            final BankAccount bankAccount = new BankAccount(money, blocked, number);
            bankAccountDAO.addEntity(bankAccount);
            this.secondInsertedBankAccount = bankAccount;
        }
    }

    @BeforeClass(dependsOnMethods = {"insertBankAccounts"})
    public final void insertClients()
            throws AddingEntityException
    {
        final DAO<Client> clientDAO = ClientDAO.createClientDAO();
        {
            final String email = "first_not_existing_email@mail.ru";
            final String password = "password";
            final String name = "vlad";
            final String surname = "zuev";
            final String patronymic = "sergeevich";
            final String phoneNumber = "+375-44-111-11-11";
            final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                    this.firstInsertedBankAccount);
            clientDAO.addEntity(client);
            this.firstInsertedClient = client;
        }
        {
            final String email = "second_not_existing_email@mail.ru";
            final String password = "password";
            final String name = "sergey";
            final String surname = "zuev";
            final String patronymic = "vyacheslavovich";
            final String phoneNumber = "+375-44-222-22-22";
            final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                    this.secondInsertedBankAccount);
            clientDAO.addEntity(client);
            this.secondInsertedClient = client;
        }
    }

    @BeforeClass(dependsOnMethods = {"insertClients"})
    public final void insertPaymentCards()
            throws AddingEntityException
    {
        final DAO<PaymentCard> paymentCardDAO = PaymentCardDAO.createPaymentCardDAO();
        {
            final String cardNumber = "1111-1111-1111-1111";
            final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.DECEMBER, Year.now());
            final String paymentSystem = "visa";
            final String cvc = "111";
            final String nameOfBank = "the best bank";
            final String password = "1111";
            final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                    this.firstInsertedClient, nameOfBank, password);
            paymentCardDAO.addEntity(paymentCard);
            this.firstInsertedPaymentCard = paymentCard;
        }
        {
            final String cardNumber = "2222-2222-2222-2222";
            final PaymentCard.ExpirationDate expirationDate = new PaymentCard.ExpirationDate(Month.JANUARY, Year.now());
            final String paymentSystem = "visa";
            final String cvc = "111";
            final String nameOfBank = "the best bank";
            final String password = "1111";
            final PaymentCard paymentCard = new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc,
                    this.secondInsertedClient, nameOfBank, password);
            paymentCardDAO.addEntity(paymentCard);
            this.secondInsertedPaymentCard = paymentCard;
        }
    }

    @AfterClass
    public final void deletePaymentCards()
            throws DeletingEntityException
    {
        final DAO<PaymentCard> paymentCardDAO = PaymentCardDAO.createPaymentCardDAO();
        paymentCardDAO.deleteEntity(this.firstInsertedPaymentCard);
        paymentCardDAO.deleteEntity(this.secondInsertedPaymentCard);
    }

    @AfterClass(dependsOnMethods = {"deletePaymentCards"})
    public final void deleteClients()
            throws DeletingEntityException
    {
        final DAO<Client> clientDAO = ClientDAO.createClientDAO();
        clientDAO.deleteEntity(this.firstInsertedClient);
        clientDAO.deleteEntity(this.secondInsertedClient);
    }

    @AfterClass(dependsOnMethods = {"deleteClients"})
    public final void deleteBankAccounts()
            throws DeletingEntityException
    {
        final DAO<BankAccount> bankAccountDAO = BankAccountDAO.createBankAccountDAO();
        bankAccountDAO.deleteEntity(this.firstInsertedBankAccount);
        bankAccountDAO.deleteEntity(this.secondInsertedBankAccount);
    }

    @Test
    public final void paymentShouldBeAdded()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.secondInsertedPaymentCard, money, date);

        final long idOfPaymentBeforeAdding = payment.getId();
        this.paymentDAO.addEntity(payment);
        try
        {
            final long idOfPaymentAfterAdding = payment.getId();
            final Optional<Payment> optionalOfAddedPaymentFromDataBase = this.paymentDAO.findEntityById(
                    idOfPaymentAfterAdding);
            if(optionalOfAddedPaymentFromDataBase.isEmpty())
            {
                Assert.fail();
            }
            final Payment addedPaymentFromDataBase = optionalOfAddedPaymentFromDataBase.get();
            final boolean testSuccess = idOfPaymentBeforeAdding != idOfPaymentAfterAdding
                    && payment.equals(addedPaymentFromDataBase);
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.paymentDAO.deleteEntity(payment);
        }
    }

    @Test(dependsOnMethods = {"paymentShouldBeDeleted"}, expectedExceptions = {AddingEntityException.class})
    public final void paymentWithSameCardsOfSenderAndReceiverShouldNotBeAdded()
            throws AddingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.firstInsertedPaymentCard, money, date);
        this.paymentDAO.addEntity(payment);
        try
        {
            Assert.fail();
        }
        finally
        {
            this.paymentDAO.deleteEntity(payment);
        }
    }

    @Test(dependsOnMethods = {"paymentShouldBeAdded", "paymentShouldBeDeleted"})
    public final void allPaymentsShouldBeOffloaded()
            throws AddingEntityException, OffloadingEntitiesException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.secondInsertedPaymentCard, money, date);

        this.paymentDAO.addEntity(payment);
        try
        {
            final Collection<Payment> offloadedPayments = this.paymentDAO.offloadEntities();
            final boolean testSuccess = offloadedPayments != null && !offloadedPayments.isEmpty()
                    && offloadedPayments.contains(payment);
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.paymentDAO.deleteEntity(payment);
        }
    }

    @Test(dependsOnMethods = {"paymentShouldBeAdded"})
    public final void paymentShouldBeFoundById()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.secondInsertedPaymentCard, money, date);

        this.paymentDAO.addEntity(payment);
        try
        {
            final Optional<Payment> optionalOfPaymentFoundInDataBase = this.paymentDAO.findEntityById(payment.getId());
            if(optionalOfPaymentFoundInDataBase.isEmpty())
            {
                Assert.fail();
            }
            final Payment paymentFoundInDataBase = optionalOfPaymentFoundInDataBase.get();
            Assert.assertEquals(payment, paymentFoundInDataBase);
        }
        finally
        {
            this.paymentDAO.deleteEntity(payment);
        }
    }

    @Test
    public final void paymentShouldNotBeFoundByNotExistingId()
            throws FindingEntityException
    {
        final long idOfFoundPayment = -1;
        final Optional<Payment> optionalOfFoundPayment = this.paymentDAO.findEntityById(idOfFoundPayment);
        Assert.assertTrue(optionalOfFoundPayment.isEmpty());
    }

    @Test(dependsOnMethods = {"paymentShouldBeAdded", "paymentShouldBeFoundById", "paymentShouldBeDeleted"})
    public final void paymentShouldBeUpdated()
            throws AddingEntityException, UpdatingEntityException, FindingEntityException, DeletingEntityException

    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.secondInsertedPaymentCard, money, date);

        this.paymentDAO.addEntity(payment);
        try
        {
            final BigDecimal newMoney = BigDecimal.ONE;
            payment.setMoney(newMoney);
            this.paymentDAO.updateEntity(payment);

            final Optional<Payment> optionalOfUpdatedPaymentFromDataBase = this.paymentDAO.findEntityById(
                    payment.getId());
            if(optionalOfUpdatedPaymentFromDataBase.isEmpty())
            {
                Assert.fail();
            }
            final Payment updatedPaymentFromDataBase = optionalOfUpdatedPaymentFromDataBase.get();
            Assert.assertEquals(payment, updatedPaymentFromDataBase);
        }
        finally
        {
            this.paymentDAO.deleteEntity(payment);
        }
    }

    @Test(dependsOnMethods = {"paymentShouldBeAdded", "paymentShouldBeDeleted"},
            expectedExceptions = {UpdatingEntityException.class})
    public final void paymentShouldNotBeUpdatedByNegativeMoney()
            throws AddingEntityException, UpdatingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.secondInsertedPaymentCard, money, date);

        this.paymentDAO.addEntity(payment);
        try
        {
            final BigDecimal newMoney = new BigDecimal("-1");
            payment.setMoney(newMoney);
            this.paymentDAO.updateEntity(payment);
        }
        finally
        {
            this.paymentDAO.deleteEntity(payment);
        }
    }

    @Test(dependsOnMethods = {"paymentShouldBeAdded", "paymentShouldBeFoundById"})
    public final void paymentShouldBeDeletedById()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.secondInsertedPaymentCard, money, date);
        this.paymentDAO.addEntity(payment);
        this.paymentDAO.deleteEntityById(payment.getId());
        final Optional<Payment> optionalOfFoundPayment = this.paymentDAO.findEntityById(payment.getId());
        Assert.assertTrue(optionalOfFoundPayment.isEmpty());
    }

    @Test
    public final void paymentShouldNotBeDeletedByNotExistingId()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final long idOfDeletedPayment = -1;
        final Collection<Payment> paymentsBeforeDeleting = this.paymentDAO.offloadEntities();
        this.paymentDAO.deleteEntityById(idOfDeletedPayment);
        final Collection<Payment> paymentsAfterDeleting = this.paymentDAO.offloadEntities();
        Assert.assertEquals(paymentsBeforeDeleting, paymentsAfterDeleting);
    }

    @Test(dependsOnMethods = {"paymentShouldBeAdded", "paymentShouldBeFoundById"})
    public final void paymentShouldBeDeleted()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.secondInsertedPaymentCard, money, date);
        this.paymentDAO.addEntity(payment);
        this.paymentDAO.deleteEntity(payment);
        final Optional<Payment> optionalOfFoundPayment = this.paymentDAO.findEntityById(payment.getId());
        Assert.assertTrue(optionalOfFoundPayment.isEmpty());
    }

    @Test
    public final void paymentWithNotExistingIdShouldNotBeDeleted()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.secondInsertedPaymentCard, money, date);
        final Collection<Payment> paymentsBeforeDeleting = this.paymentDAO.offloadEntities();
        this.paymentDAO.deleteEntity(payment);
        final Collection<Payment> paymentsAfterDeleting = this.paymentDAO.offloadEntities();
        Assert.assertEquals(paymentsBeforeDeleting, paymentsAfterDeleting);
    }

    @Test(dependsOnMethods = {"paymentShouldBeAdded"})
    public final void paymentWithGivenIdShouldBeExisted()
            throws AddingEntityException, DefiningExistingEntityException, DeletingEntityException
    {
        final BigDecimal money = BigDecimal.TEN;
        final Calendar date = Calendar.getInstance();
        final Payment payment = new Payment(this.firstInsertedPaymentCard, this.secondInsertedPaymentCard, money, date);

        this.paymentDAO.addEntity(payment);
        try
        {
            final boolean paymentExists = this.paymentDAO.isEntityWithGivenIdExisting(payment.getId());
            Assert.assertTrue(paymentExists);
        }
        finally
        {
            this.paymentDAO.deleteEntity(payment);
        }
    }

    @Test
    public final void paymentWithGivenIdShouldNotBeExisted()
            throws DefiningExistingEntityException
    {
        final long idOfResearchPayment = -1;
        final boolean paymentExists = this.paymentDAO.isEntityWithGivenIdExisting(idOfResearchPayment);
        Assert.assertFalse(paymentExists);
    }
}
