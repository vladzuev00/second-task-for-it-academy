package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.user;

import by.itacademy.zuevvlad.cardpaymentproject.entity.User;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;

import javax.servlet.http.HttpServletRequest;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.user.NameOfRequestParameterToAddUser.*;

public final class UserCreator implements EntityCreator<User>
{
    public static UserCreator createUserCreator()
    {
        if(UserCreator.userCreator == null)
        {
            synchronized(UserCreator.class)
            {
                if(UserCreator.userCreator == null)
                {
                    UserCreator.userCreator = new UserCreator();
                }
            }
        }
        return UserCreator.userCreator;
    }

    private static UserCreator userCreator = null;

    private UserCreator()
    {
        super();
    }

    @Override
    public final User createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
        final String email = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_ADDED_USER.getValue());
        final String password = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_USER.getValue());
        return new User(email, password);
    }
}
