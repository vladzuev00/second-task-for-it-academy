package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.administrator;

import by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.administrator.NameOfRequestParameterToAddAdministrator.*;

public final class AdministratorCreator implements EntityCreator<Administrator>
{
    public static AdministratorCreator createAdministratorCreator()
    {
        if(AdministratorCreator.administratorCreator == null)
        {
            synchronized(AdministratorCreator.class)
            {
                if(AdministratorCreator.administratorCreator == null)
                {
                    AdministratorCreator.administratorCreator = new AdministratorCreator();
                }
            }
        }
        return AdministratorCreator.administratorCreator;
    }

    private static AdministratorCreator administratorCreator = null;

    private AdministratorCreator()
    {
        super();
    }

    @Override
    public final Administrator createEntity(final HttpServletRequest httpServletRequest)
    {
        final String email = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_ADDED_ADMINISTRATOR.getValue());
        final String password = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_ADMINISTRATOR.getValue());

        final String descriptionOfLevel = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_LEVEL_OF_ADDED_ADMINISTRATOR.getValue());
        final String descriptionOfLevelInUpperCase = descriptionOfLevel.toUpperCase(Locale.ROOT);
        final Administrator.Level level = Administrator.Level.valueOf(descriptionOfLevelInUpperCase);

        return new Administrator(email, password, level);
    }
}
