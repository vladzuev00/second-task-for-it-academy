package by.itacademy.zuevvlad.cardpaymentproject.service;

import by.itacademy.zuevvlad.cardpaymentproject.dao.UserDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.User;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.user.UserCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public final class UserService extends EntityService<User>
{
    private final UserDAO userDAO;

    public static UserService createUserService()
    {
        if(UserService.userService == null)
        {
            synchronized(UserService.class)
            {
                if(UserService.userService == null)
                {
                    final EntityCreator<User> userCreator = UserCreator.createUserCreator();
                    UserService.userService = new UserService(userCreator);
                }
            }
        }
        return UserService.userService;
    }

    private static UserService userService = null;

    private UserService(final EntityCreator<User> userCreator)
    {
        super(userCreator, null);
        this.userDAO = UserDAO.createUserDAO();
    }

    @Override
    public final Collection<User> findAllEntities()
            throws OffloadingEntitiesException
    {
        return this.userDAO.offloadEntities();
    }

    @Override
    public final void addEntityInDataBase(final User addedUser)
            throws AddingEntityException
    {
        this.userDAO.addEntity(addedUser);
    }

    @Override
    public final User createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
        final EntityCreator<User> userCreator = super.getEntityCreator();
        return userCreator.createEntity(httpServletRequest);
    }

    @Override
    public final Optional<User> findEntityById(final long idOfFoundUser)
            throws FindingEntityException
    {
        return this.userDAO.findEntityById(idOfFoundUser);
    }

    @Override
    public void modifyEntity(User modifiedEntity, HttpServletRequest httpServletRequest)
            throws EntityModifyingException
    {

    }

    @Override
    public void updateEntityInDataBase(User updatedEntity) throws UpdatingEntityException {

    }

    @Override
    public void deleteEntityById(long idOfDeletedEntity) throws DeletingEntityException {

    }

    public final boolean isUserWithGivenEmailExist(final String emailOfResearchUser)
            throws DefiningExistingEntityException
    {
        return this.userDAO.isUserWithGivenEmailExist(emailOfResearchUser);
    }

    public final boolean isCombinationOfEmailAndPasswordExist(final String researchEmail, final String researchPassword)
            throws FindingEntityException
    {
        final Optional<User> optionalOfFoundUser = this.userDAO.findUserByGivenEmail(researchEmail);
        if(optionalOfFoundUser.isEmpty())
        {
            return false;
        }
        final User foundUser = optionalOfFoundUser.get();
        final String passwordOfFoundUser = foundUser.getPassword();
        return Objects.equals(researchPassword, passwordOfFoundUser);
    }
}
