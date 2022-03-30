package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Optional;

public final class UserDAOTest
{
    private final UserDAO userDAO;

    public UserDAOTest()
    {
        super();

        this.userDAO = UserDAO.createUserDAO();
    }

    @Test
    public final void userShouldBeAdded()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final User addedUser = new User(email, password);

        final long idOfAddedUserBeforeAdding = addedUser.getId();
        this.userDAO.addEntity(addedUser);
        try
        {
            final long idOfAddedUserAfterAdding = addedUser.getId();

            final Optional<User> optionalOfAddedUserFromDataBase
                    = this.userDAO.findEntityById(idOfAddedUserAfterAdding);
            if(optionalOfAddedUserFromDataBase.isEmpty())
            {
                Assert.fail();
            }
            final User addedUserFromDataBase = optionalOfAddedUserFromDataBase.get();

            final boolean testSuccess = addedUser.equals(addedUserFromDataBase)
                    && idOfAddedUserBeforeAdding != idOfAddedUserAfterAdding;
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.userDAO.deleteEntity(addedUser);
        }
    }

    @Test(expectedExceptions = {AddingEntityException.class})
    public final void userWithAlreadyExistingEmailShouldNotBeAdded()
            throws AddingEntityException, DeletingEntityException
    {
        final String emailOfFirstUser = "not_existing_email@mail.ru";
        final String passwordOfFirstUser = "password";
        final User firstUser = new User(emailOfFirstUser, passwordOfFirstUser);

        final String passwordOfSecondUser = "password";
        final User secondUser = new User(emailOfFirstUser, passwordOfSecondUser);

        try
        {
            this.userDAO.addEntity(firstUser);
        }
        catch(final AddingEntityException addingEntityException)
        {
            Assert.fail();
        }
        try
        {
            this.userDAO.addEntity(secondUser);
            try
            {
                Assert.fail();
            }
            finally
            {
                this.userDAO.deleteEntity(secondUser);
            }
        }
        finally
        {
            this.userDAO.deleteEntity(firstUser);
        }
    }

    @Test(expectedExceptions = {AddingEntityException.class})
    public final void userWithNotValidEmailShouldNotBeAdded()
            throws AddingEntityException, DeletingEntityException
    {
        final String email = "not_valid_email";
        final String password = "password";
        final User addedUser = new User(email, password);
        this.userDAO.addEntity(addedUser);
        try
        {
            Assert.fail();
        }
        finally
        {
            this.userDAO.deleteEntity(addedUser);
        }
    }

    @Test(dependsOnMethods = {"userShouldBeAdded", "userShouldBeDeleted"})
    public final void allUsersShouldBeOffloaded()
            throws AddingEntityException, OffloadingEntitiesException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password6";
        final User addedUser = new User(email, password);

        this.userDAO.addEntity(addedUser);
        try
        {
            final Collection<User> offloadedUsers = this.userDAO.offloadEntities();
            final boolean testSuccess = offloadedUsers != null && !offloadedUsers.isEmpty()
                    && offloadedUsers.contains(addedUser);
            Assert.assertTrue(testSuccess);
        }
        finally
        {
            this.userDAO.deleteEntity(addedUser);
        }
    }

    @Test(dependsOnMethods = {"userShouldBeAdded"})
    public final void userShouldBeFoundById()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password6";
        final User user = new User(email, password);

        this.userDAO.addEntity(user);
        try
        {
            final Optional<User> optionalOfUserFoundInDataBase = this.userDAO.findEntityById(user.getId());
            if(optionalOfUserFoundInDataBase.isEmpty())
            {
                Assert.fail();
            }
            final User userFoundInDataBase = optionalOfUserFoundInDataBase.get();
            Assert.assertEquals(user, userFoundInDataBase);
        }
        finally
        {
            this.userDAO.deleteEntity(user);
        }
    }

    @Test
    public final void userShouldNotBeFoundByNotValidId()
            throws FindingEntityException
    {
        final long idOfFoundUser = -1;
        final Optional<User> optionalOfFoundUser = this.userDAO.findEntityById(idOfFoundUser);
        Assert.assertFalse(optionalOfFoundUser.isPresent());
    }

    @Test(dependsOnMethods = {"userShouldBeAdded", "userShouldBeFoundById", "userShouldBeDeleted"})
    public final void userShouldBeUpdated()
            throws AddingEntityException, UpdatingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password6";
        final User user = new User(email, password);

        this.userDAO.addEntity(user);
        try
        {
            final String newEmail = "new_not_existing_email@mail.ru";
            user.setEmail(newEmail);
            this.userDAO.updateEntity(user);

            final Optional<User> optionalOfUpdatedUserFromDataBase = this.userDAO.findEntityById(user.getId());
            if(optionalOfUpdatedUserFromDataBase.isEmpty())
            {
                Assert.fail();
            }

            final User updatedUserFromDataBase = optionalOfUpdatedUserFromDataBase.get();
            Assert.assertEquals(updatedUserFromDataBase, user);
        }
        finally
        {
            this.userDAO.deleteEntity(user);
        }
    }

    @Test(dependsOnMethods = {"userShouldBeAdded", "userShouldBeDeleted"},
            expectedExceptions = {UpdatingEntityException.class})
    public final void userShouldNotBeUpdatedByNotValidProperties()
            throws AddingEntityException, UpdatingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password6";
        final User user = new User(email, password);

        this.userDAO.addEntity(user);
        try
        {
            final String newEmail = "new_not_valid_email";
            user.setEmail(newEmail);
            this.userDAO.updateEntity(user);
        }
        finally
        {
            this.userDAO.deleteEntity(user);
        }
    }

    @Test(dependsOnMethods = {"userShouldBeAdded", "userShouldBeFoundById"})
    public final void userShouldBeDeletedById()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password6";
        final User user = new User(email, password);
        this.userDAO.addEntity(user);

        final long idOfDeletedUser = user.getId();
        this.userDAO.deleteEntityById(idOfDeletedUser);

        final Optional<User> optionalOfFoundUser = this.userDAO.findEntityById(idOfDeletedUser);
        Assert.assertFalse(optionalOfFoundUser.isPresent());
    }

    @Test(dependsOnMethods = {"allUsersShouldBeOffloaded"})
    public final void userShouldNotBeDeletedById()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password6";
        final User user = new User(email, password);    //user with not valid id

        final Collection<User> usersBeforeDeleting = this.userDAO.offloadEntities();
        this.userDAO.deleteEntityById(user.getId());
        final Collection<User> usersAfterDeleting = this.userDAO.offloadEntities();

        Assert.assertEquals(usersBeforeDeleting, usersAfterDeleting);
    }

    @Test(dependsOnMethods = {"userShouldBeAdded", "userShouldBeFoundById"})
    public final void userShouldBeDeleted()
            throws AddingEntityException, DeletingEntityException, FindingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final User user = new User(email, password);
        this.userDAO.addEntity(user);
        this.userDAO.deleteEntity(user);

        final long idOfDeletedUser = user.getId();
        final Optional<User> optionalOfFoundUser = this.userDAO.findEntityById(idOfDeletedUser);
        Assert.assertFalse(optionalOfFoundUser.isPresent());
    }

    @Test(dependsOnMethods = {"allUsersShouldBeOffloaded"})
    public final void userShouldNotBeDeleted()
            throws OffloadingEntitiesException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final User user = new User(email, password);     //user with not valid id

        final Collection<User> usersBeforeDeleting = this.userDAO.offloadEntities();
        this.userDAO.deleteEntity(user);
        final Collection<User> usersAfterDeleting = this.userDAO.offloadEntities();

        Assert.assertEquals(usersBeforeDeleting, usersAfterDeleting);
    }

    @Test(dependsOnMethods = {"userShouldBeAdded", "userShouldBeDeleted"})
    public final void userWithGivenIdShouldBeExisted()
            throws AddingEntityException, DefiningExistingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password6";
        final User user = new User(email, password);

        this.userDAO.addEntity(user);
        try
        {
            final long idOfResearchUser = user.getId();
            final boolean userExists = this.userDAO.isEntityWithGivenIdExisting(idOfResearchUser);
            Assert.assertTrue(userExists);
        }
        finally
        {
            this.userDAO.deleteEntity(user);
        }
    }

    @Test
    public final void userWithGivenIdShouldNotBeExisted()
            throws DefiningExistingEntityException
    {
        final long idOfResearchUser = -1;        //not valid id
        final boolean userExists = this.userDAO.isEntityWithGivenIdExisting(idOfResearchUser);
        Assert.assertFalse(userExists);
    }

    @Test(dependsOnMethods = {"userShouldBeAdded", "userShouldBeDeleted"})
    public final void userWithGivenEmailShouldBeExisted()
            throws AddingEntityException, DefiningExistingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password6";
        final User user = new User(email, password);

        this.userDAO.addEntity(user);
        try
        {
            final boolean userExists = this.userDAO.isUserWithGivenEmailExist(user.getEmail());
            Assert.assertTrue(userExists);
        }
        finally
        {
            this.userDAO.deleteEntity(user);
        }
    }

    @Test
    public final void userWithGivenNotValidEmailShouldNotBeExisted()
            throws DefiningExistingEntityException
    {
        final String email = "not_valid_email";
        final boolean userExists = this.userDAO.isUserWithGivenEmailExist(email);
        Assert.assertFalse(userExists);
    }

    @Test(dependsOnMethods = {"userShouldBeAdded", "userShouldBeDeleted"})
    public final void userShouldBeFoundByGivenEmail()
            throws AddingEntityException, FindingEntityException, DeletingEntityException
    {
        final String email = "not_existing_email@mail.ru";
        final String password = "password";
        final User user = new User(email, password);
        this.userDAO.addEntity(user);
        try
        {
            final Optional<User> optionalOfFoundUser = this.userDAO.findUserByGivenEmail(user.getEmail());
            if(optionalOfFoundUser.isEmpty())
            {
                Assert.fail();
            }
            final User foundUser = optionalOfFoundUser.get();
            Assert.assertEquals(user, foundUser);
        }
        finally
        {
            this.userDAO.deleteEntity(user);
        }
    }

    @Test
    public final void userShouldNotBeFoundByNotValidGivenEmail()
            throws FindingEntityException
    {
        final String email = "not_valid_email";
        final Optional<User> optionalOfFoundUser = this.userDAO.findUserByGivenEmail(email);
        Assert.assertTrue(optionalOfFoundUser.isEmpty());
    }
}
