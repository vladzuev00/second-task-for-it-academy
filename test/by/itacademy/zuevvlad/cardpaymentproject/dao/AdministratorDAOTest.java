package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Optional;

public final class AdministratorDAOTest
{
    private final AdministratorDAO administratorDAO;

    public AdministratorDAOTest()
    {
        super();

        this.administratorDAO = AdministratorDAO.createAdministratorDAO();
    }

    @Test
    public final void administratorShouldBeAdded()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator addedAdministrator = new Administrator(email, password, level);

        final long idOfAddedAdministratorBeforeAdding = addedAdministrator.getId();
        this.administratorDAO.addEntity(addedAdministrator);
        try
        {
            final long idOfAddedAdministratorAfterAdding = addedAdministrator.getId();

            final Optional<Administrator> optionalOfAddedAdministratorFromDataBase
                    = this.administratorDAO.findEntityById(idOfAddedAdministratorAfterAdding);
            if(optionalOfAddedAdministratorFromDataBase.isEmpty())
            {
                Assert.fail();
            }
            final Administrator addedAdministratorFromDataBase = optionalOfAddedAdministratorFromDataBase.get();

            final boolean testSuccess = addedAdministrator.equals(addedAdministratorFromDataBase)
                    && idOfAddedAdministratorBeforeAdding != idOfAddedAdministratorAfterAdding;
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.administratorDAO.deleteEntity(addedAdministrator);
        }
    }

    @Test(expectedExceptions = {AddingEntityException.class})
    public final void administratorWithNotDefinedLevelShouldNotBeAdded()
            throws AddingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.NOT_DEFINED;
        final Administrator addedAdministrator = new Administrator(email, password, level);

        this.administratorDAO.addEntity(addedAdministrator);
        try
        {
            Assert.fail();
        }
        finally
        {
            this.administratorDAO.deleteEntity(addedAdministrator);
        }
    }

    @Test(dependsOnMethods = {"administratorShouldBeAdded", "administratorShouldBeDeleted"})
    public final void allAdministratorsShouldBeOffloaded()
            throws AddingEntityException, OffloadingEntitiesException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator addedAdministrator = new Administrator(email, password, level);

        this.administratorDAO.addEntity(addedAdministrator);
        try
        {
            final Collection<Administrator> offloadedAdministrators = this.administratorDAO.offloadEntities();
            final boolean testSuccess = offloadedAdministrators != null && !offloadedAdministrators.isEmpty()
                    && offloadedAdministrators.contains(addedAdministrator);
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.administratorDAO.deleteEntity(addedAdministrator);
        }
    }

    @Test(dependsOnMethods = {"administratorShouldBeAdded"})
    public final void administratorShouldBeFoundById()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator addedAdministrator = new Administrator(email, password, level);

        this.administratorDAO.addEntity(addedAdministrator);
        try
        {
            final Optional<Administrator> optionalOfAdministratorFoundInDataBase = this.administratorDAO.findEntityById(
                    addedAdministrator.getId());
            if(optionalOfAdministratorFoundInDataBase.isEmpty())
            {
                Assert.fail();
            }
            final Administrator administratorFoundInDataBase = optionalOfAdministratorFoundInDataBase.get();
            Assert.assertEquals(addedAdministrator, administratorFoundInDataBase);
        }
        finally
        {
            this.administratorDAO.deleteEntity(addedAdministrator);
        }
    }

    @Test
    public final void administratorShouldNotBeFoundByNotValidId()
            throws FindingEntityException
    {
        final long idOfFoundAdministrator = -1;
        final Optional<Administrator> optionalOfFoundAdministrator = this.administratorDAO.findEntityById(
                idOfFoundAdministrator);
        Assert.assertFalse(optionalOfFoundAdministrator.isPresent());
    }

    @Test(dependsOnMethods = {"administratorShouldBeAdded", "administratorShouldBeFoundById",
            "administratorShouldBeDeleted"})
    public final void administratorShouldBeUpdated()
            throws AddingEntityException, UpdatingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator administrator = new Administrator(email, password, level);

        this.administratorDAO.addEntity(administrator);
        try
        {
            final String newPassword = "new_password";
            final Administrator.Level newLevel = Administrator.Level.MODIFIER;
            administrator.setPassword(newPassword);
            administrator.setLevel(newLevel);
            this.administratorDAO.updateEntity(administrator);

            final Optional<Administrator> optionalOfUpdatedAdministratorFromDataBase
                    = this.administratorDAO.findEntityById(administrator.getId());
            if(optionalOfUpdatedAdministratorFromDataBase.isEmpty())
            {
                Assert.fail();
            }

            final Administrator updatedAdministratorFromDataBase = optionalOfUpdatedAdministratorFromDataBase.get();
            Assert.assertEquals(administrator, updatedAdministratorFromDataBase);
        }
        finally
        {
            this.administratorDAO.deleteEntity(administrator);
        }
    }

    @Test(dependsOnMethods = {"administratorShouldBeAdded", "administratorShouldBeDeleted"},
            expectedExceptions = {UpdatingEntityException.class})
    public final void administratorShouldNotBeUpdatedByNotValidProperties()
            throws AddingEntityException, UpdatingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator administrator = new Administrator(email, password, level);

        this.administratorDAO.addEntity(administrator);
        try
        {
            final Administrator.Level newLevel = Administrator.Level.NOT_DEFINED;
            administrator.setLevel(newLevel);
            this.administratorDAO.updateEntity(administrator);
        }
        finally
        {
            this.administratorDAO.deleteEntity(administrator);
        }
    }

    @Test(dependsOnMethods = {"administratorShouldBeAdded", "administratorShouldBeFoundById"})
    public final void administratorShouldBeDeletedById()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator administrator = new Administrator(email, password, level);
        this.administratorDAO.addEntity(administrator);

        final long idOfDeletedAdministrator = administrator.getId();
        this.administratorDAO.deleteEntityById(idOfDeletedAdministrator);

        final Optional<Administrator> optionalOfFoundAdministrator = this.administratorDAO.findEntityById(
                idOfDeletedAdministrator);
        Assert.assertFalse(optionalOfFoundAdministrator.isPresent());
    }

    @Test(dependsOnMethods = {"allAdministratorsShouldBeOffloaded"})
    public final void administratorShouldNotBeDeletedById()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator administrator = new Administrator(email, password, level);

        final Collection<Administrator> administratorsBeforeDeleting = this.administratorDAO.offloadEntities();
        this.administratorDAO.deleteEntityById(administrator.getId());
        final Collection<Administrator> administratorsAfterDeleting = this.administratorDAO.offloadEntities();

        Assert.assertEquals(administratorsBeforeDeleting, administratorsAfterDeleting);
    }

    @Test(dependsOnMethods = {"administratorShouldBeAdded", "administratorShouldBeFoundById"})
    public final void administratorShouldBeDeleted()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator administrator = new Administrator(email, password, level);

        this.administratorDAO.addEntity(administrator);
        this.administratorDAO.deleteEntity(administrator);

        final long idOfDeletedAdministrator = administrator.getId();
        final Optional<Administrator> optionalOfFoundAdministrator = this.administratorDAO.findEntityById(
                idOfDeletedAdministrator);
        Assert.assertFalse(optionalOfFoundAdministrator.isPresent());
    }

    @Test(dependsOnMethods = {"allAdministratorsShouldBeOffloaded"})
    public final void administratorShouldNotBeDeleted()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator administrator = new Administrator(email, password, level);

        final Collection<Administrator> administratorsBeforeDeleting = this.administratorDAO.offloadEntities();
        this.administratorDAO.deleteEntity(administrator);
        final Collection<Administrator> administratorsAfterDeleting = this.administratorDAO.offloadEntities();

        Assert.assertEquals(administratorsBeforeDeleting, administratorsAfterDeleting);
    }

    @Test(dependsOnMethods = {"administratorShouldBeAdded", "administratorShouldBeDeleted"})
    public final void administratorWithGivenIdShouldBeExisted()
            throws AddingEntityException, DefiningExistingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator administrator = new Administrator(email, password, level);

        this.administratorDAO.addEntity(administrator);
        try
        {
            final long idOfResearchAdministrator = administrator.getId();
            final boolean administratorExists = this.administratorDAO.isEntityWithGivenIdExisting(
                    idOfResearchAdministrator);
            Assert.assertTrue(administratorExists);
        }
        finally
        {
            this.administratorDAO.deleteEntity(administrator);
        }
    }

    @Test
    public final void administratorWithGivenIdShouldNotBeExisted()
            throws DefiningExistingEntityException
    {
        final long idOfResearchAdministrator = -1;        //not valid id
        final boolean administratorExists = this.administratorDAO.isEntityWithGivenIdExisting(
                idOfResearchAdministrator);
        Assert.assertFalse(administratorExists);
    }

    @Test(dependsOnMethods = {"administratorShouldBeAdded", "administratorShouldBeDeleted"})
    public final void administratorShouldBeFoundByEmail()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final Administrator.Level level = Administrator.Level.SUPPORTER;
        final Administrator administrator = new Administrator(email, password, level);

        this.administratorDAO.addEntity(administrator);
        try
        {
            final Optional<Administrator> optionalOfFoundAdministrator = this.administratorDAO
                    .findAdministratorByGivenEmail(administrator.getEmail());
            if(optionalOfFoundAdministrator.isEmpty())
            {
                Assert.fail();
            }
            final Administrator foundAdministrator = optionalOfFoundAdministrator.get();
            Assert.assertEquals(administrator, foundAdministrator);
        }
        finally
        {
            this.administratorDAO.deleteEntity(administrator);
        }
    }

    @Test
    public final void administratorShouldNotBeFoundByNotValidEmail()
            throws FindingEntityException
    {
        final String email = "not_valid_email";
        final Optional<Administrator> optionalOfFoundAdministrator = this.administratorDAO
                .findAdministratorByGivenEmail(email);
        Assert.assertTrue(optionalOfFoundAdministrator.isEmpty());
    }
}
