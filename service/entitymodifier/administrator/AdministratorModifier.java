package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.administrator;

import by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;

import javax.servlet.http.HttpServletRequest;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.administrator.NameOfRequestParameterToUpdateAdministrator.*;

public final class AdministratorModifier implements EntityModifier<Administrator>
{
    public static AdministratorModifier createAdministratorModifier()
    {
        if(AdministratorModifier.administratorModifier == null)
        {
            synchronized(AdministratorModifier.class)
            {
                if(AdministratorModifier.administratorModifier == null)
                {
                    AdministratorModifier.administratorModifier = new AdministratorModifier();
                }
            }
        }
        return AdministratorModifier.administratorModifier;
    }

    private static AdministratorModifier administratorModifier = null;

    private AdministratorModifier()
    {
        super();
    }

    @Override
    public final void modify(final Administrator modifiedAdministrator, final HttpServletRequest httpServletRequest)
    {
        final String newEmail = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_EMAIL_OF_UPDATED_ADMINISTRATOR.getValue());
        final String newPassword = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_UPDATED_ADMINISTRATOR.getValue());

        final String descriptionOfNewLevel = httpServletRequest.getParameter(
                NAME_OF_REQUEST_PARAMETER_OF_LEVEL_OF_UPDATED_ADMINISTRATOR.getValue());
        final Administrator.Level newLevel = Administrator.Level.valueOf(descriptionOfNewLevel);

        modifiedAdministrator.setEmail(newEmail);
        modifiedAdministrator.setPassword(newPassword);
        modifiedAdministrator.setLevel(newLevel);
    }
}
