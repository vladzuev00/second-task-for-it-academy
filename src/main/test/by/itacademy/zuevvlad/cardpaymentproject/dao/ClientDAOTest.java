package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

public final class ClientDAOTest
{
    private final ClientDAO clientDAO;
    private BankAccount insertedBankAccount;

    public ClientDAOTest()
    {
        super();
        this.clientDAO = ClientDAO.createClientDAO();
        this.insertedBankAccount = null;
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

    @AfterClass
    public final void deleteBankAccount()
            throws DeletingEntityException
    {
        final DAO<BankAccount> bankAccountDAO = BankAccountDAO.createBankAccountDAO();
        bankAccountDAO.deleteEntity(this.insertedBankAccount);
    }

    @Test
    public final void clientShouldBeAdded()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);
        final long idOfClientBeforeAdding = client.getId();
        this.clientDAO.addEntity(client);
        try
        {
            final long idOfClientAfterAdding = client.getId();
            final Optional<Client> optionalOfAddedClientFromDataBase = this.clientDAO.findEntityById(
                    idOfClientAfterAdding);
            if(optionalOfAddedClientFromDataBase.isEmpty())
            {
                Assert.fail();
            }
            final Client addedClientFromDataBase = optionalOfAddedClientFromDataBase.get();

            final boolean testSuccess = client.equals(addedClientFromDataBase)
                    && idOfClientBeforeAdding != idOfClientAfterAdding;
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.clientDAO.deleteEntity(client);
        }
    }

    @Test(expectedExceptions = {AddingEntityException.class})
    public final void clientWithNotValidNumberShouldNotBeAdded()
            throws AddingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-1111";

        Client client = new Client(email, password, name, surname, patronymic, phoneNumber, insertedBankAccount);
        this.clientDAO.addEntity(client);
        try
        {
            Assert.fail();
        }
        finally
        {
            this.clientDAO.deleteEntity(client);
        }
    }

    @Test
    public final void allClientsShouldBeOffloaded()
            throws AddingEntityException, OffloadingEntitiesException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);
        this.clientDAO.addEntity(client);
        try
        {
            final Collection<Client> offloadedClients = this.clientDAO.offloadEntities();
            final boolean testSuccess = offloadedClients != null && !offloadedClients.isEmpty()
                    && offloadedClients.contains(client);
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.clientDAO.deleteEntity(client);
        }
    }

    @Test
    public final void clientShouldBeFoundById()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);
        this.clientDAO.addEntity(client);
        try
        {
            final Optional<Client> optionalOfClientFoundInDataBase = this.clientDAO.findEntityById(client.getId());
            if(optionalOfClientFoundInDataBase.isEmpty())
            {
                Assert.fail();
            }
            final Client clientFoundInDataBase = optionalOfClientFoundInDataBase.get();
            Assert.assertEquals(client, clientFoundInDataBase);
        }
        finally
        {
            this.clientDAO.deleteEntity(client);
        }
    }

    @Test
    public final void clientShouldNotBeFoundByNotValidId()
            throws FindingEntityException
    {
        final long idOfFoundClient = -1;
        final Optional<Client> optionalOfFoundClient = this.clientDAO.findEntityById(idOfFoundClient);
        Assert.assertFalse(optionalOfFoundClient.isPresent());
    }

    @Test(dependsOnMethods = {"clientShouldBeAdded", "clientShouldBeFoundById", "clientShouldBeDeleted"})
    public final void clientShouldBeUpdated()
            throws AddingEntityException, UpdatingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);
        this.clientDAO.addEntity(client);
        try
        {
            final String newPhoneNumber = "+375-44-222-22-22";
            client.setPhoneNumber(newPhoneNumber);
            this.clientDAO.updateEntity(client);

            final Optional<Client> optionalOfUpdatedClientFromDataBase = this.clientDAO.findEntityById(
                    client.getId());
            if(optionalOfUpdatedClientFromDataBase.isEmpty())
            {
                Assert.fail();
            }

            final Client updatedClientFromDataBase = optionalOfUpdatedClientFromDataBase.get();
            Assert.assertEquals(client, updatedClientFromDataBase);
        }
        finally
        {
            this.clientDAO.deleteEntity(client);
        }
    }

    @Test(dependsOnMethods = {"clientShouldBeAdded", "clientShouldBeDeleted"},
            expectedExceptions = {UpdatingEntityException.class})
    public final void clientShouldNotBeUpdatedByNotValidPhoneNumber()
            throws AddingEntityException, UpdatingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);
        this.clientDAO.addEntity(client);
        try
        {
            final String newPhoneNumber = "+375-44-11111-11";
            client.setPhoneNumber(newPhoneNumber);
            this.clientDAO.updateEntity(client);
        }
        finally
        {
            this.clientDAO.deleteEntity(client);
        }
    }

    @Test(dependsOnMethods = {"clientShouldBeAdded", "clientShouldBeFoundById"})
    public final void clientShouldBeDeletedById()
            throws AddingEntityException, DeletingEntityException, FindingEntityException

    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);
        this.clientDAO.addEntity(client);

        this.clientDAO.deleteEntityById(client.getId());

        final Optional<Client> optionalOfFoundClient = this.clientDAO.findEntityById(client.getId());
        Assert.assertFalse(optionalOfFoundClient.isPresent());
    }

    @Test(dependsOnMethods = {"allClientsShouldBeOffloaded"})
    public final void clientShouldNotBeDeletedByNotExistingId()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final long idOfDeletedClient = -1;
        final Collection<Client> clientsBeforeDeleting = this.clientDAO.offloadEntities();
        this.clientDAO.deleteEntityById(idOfDeletedClient);
        final Collection<Client> clientsAfterDeleting = this.clientDAO.offloadEntities();
        Assert.assertEquals(clientsBeforeDeleting, clientsAfterDeleting);
    }

    @Test(dependsOnMethods = {"clientShouldBeAdded", "clientShouldBeFoundById"})
    public final void clientShouldBeDeleted()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);
        this.clientDAO.addEntity(client);
        this.clientDAO.deleteEntity(client);

        final Optional<Client> optionalOfFoundClient = this.clientDAO.findEntityById(client.getId());
        Assert.assertFalse(optionalOfFoundClient.isPresent());
    }

    @Test(dependsOnMethods = {"allClientsShouldBeOffloaded"})
    public final void clientWithNotExistingIdShouldNotBeDeleted()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final Client client = new Client();
        final Collection<Client> clientsBeforeDeleting = this.clientDAO.offloadEntities();
        this.clientDAO.deleteEntity(client);
        final Collection<Client> clientsAfterDeleting = this.clientDAO.offloadEntities();
        Assert.assertEquals(clientsBeforeDeleting, clientsAfterDeleting);
    }

    @Test(dependsOnMethods = {"clientShouldBeAdded", "clientShouldBeDeleted"})
    public final void clientWithGivenIdShouldBeExisted()
            throws AddingEntityException, DefiningExistingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);
        this.clientDAO.addEntity(client);
        try
        {
            final boolean clientExists = this.clientDAO.isEntityWithGivenIdExisting(client.getId());
            Assert.assertTrue(clientExists);
        }
        finally
        {
            this.clientDAO.deleteEntity(client);
        }
    }

    @Test
    public final void clientWithGivenNotValidIdShouldNotBeExisted()
            throws DefiningExistingEntityException
    {
        final long idOfResearchClient = -1;
        final boolean clientExists = this.clientDAO.isEntityWithGivenIdExisting(idOfResearchClient);
        Assert.assertFalse(clientExists);
    }

    @Test(dependsOnMethods = {"clientShouldBeAdded", "clientShouldBeDeleted"})
    public final void clientWithGivenEmailShouldBeFound()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);

        this.clientDAO.addEntity(client);
        try
        {
            final Optional<Client> optionalOfFoundClient = this.clientDAO.findClientByGivenEmail(client.getEmail());
            if(optionalOfFoundClient.isEmpty())
            {
                Assert.fail();
            }
            final Client foundClient = optionalOfFoundClient.get();
            Assert.assertEquals(client, foundClient);
        }
        finally
        {
            this.clientDAO.deleteEntity(client);
        }
    }

    @Test
    public final void clientWithGivenNotValidEmailShouldNotBeFound()
            throws FindingEntityException
    {
        final String email = "not_valid_email";
        final Optional<Client> optionalOfFoundClient = this.clientDAO.findClientByGivenEmail(email);
        Assert.assertTrue(optionalOfFoundClient.isEmpty());
    }

    @Test(dependsOnMethods = {"clientShouldBeAdded", "clientShouldBeDeleted"})
    public final void clientShouldBeFoundByGivenPhoneNumber()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final String name = "vlad";
        final String surname = "zuev";
        final String patronymic = "sergeevich";
        final String phoneNumber = "+375-44-111-11-11";

        final Client client = new Client(email, password, name, surname, patronymic, phoneNumber,
                this.insertedBankAccount);

        this.clientDAO.addEntity(client);
        try
        {
            final Optional<Client> optionalOfFoundClient = this.clientDAO.findClientByGivenPhoneNumber(
                    client.getPhoneNumber());
            if(optionalOfFoundClient.isEmpty())
            {
                Assert.fail();
            }
            final Client foundClient = optionalOfFoundClient.get();
            Assert.assertEquals(client, foundClient);
        }
        finally
        {
            this.clientDAO.deleteEntity(client);
        }
    }

    @Test
    public final void clientShouldNotBeFoundByNotValidPhoneNumber()
            throws FindingEntityException
    {
        final String phoneNumber = "1111";
        final Optional<Client> optionalOfFoundClient = this.clientDAO.findClientByGivenPhoneNumber(phoneNumber);
        Assert.assertTrue(optionalOfFoundClient.isEmpty());
    }
}
