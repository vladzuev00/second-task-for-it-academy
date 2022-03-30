package by.itacademy.zuevvlad.cardpaymentproject.service;

import by.itacademy.zuevvlad.cardpaymentproject.dao.AdministratorDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Administrator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.administrator.AdministratorCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.administrator.AdministratorModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

public final class AdministratorService extends EntityService<Administrator>
{
    private final AdministratorDAO administratorDAO;

    public static AdministratorService createAdministratorService()
    {
        if(AdministratorService.administratorService == null)
        {
            synchronized(AdministratorService.class)
            {
                if(AdministratorService.administratorService == null)
                {
                    final EntityCreator<Administrator> administratorCreator
                            = AdministratorCreator.createAdministratorCreator();
                    final EntityModifier<Administrator> administratorModifier
                            = AdministratorModifier.createAdministratorModifier();
                    AdministratorService.administratorService
                            = new AdministratorService(administratorCreator, administratorModifier);
                }
            }
        }
        return AdministratorService.administratorService;
    }

    private static AdministratorService administratorService = null;

    private AdministratorService(final EntityCreator<Administrator> administratorCreator,
                                 final EntityModifier<Administrator> administratorModifier)
    {
        super(administratorCreator, administratorModifier);
        this.administratorDAO = AdministratorDAO.createAdministratorDAO();
    }

    @Override
    public final Collection<Administrator> findAllEntities()
            throws OffloadingEntitiesException
    {
        return this.administratorDAO.offloadEntities();
    }

    @Override
    public final void addEntityInDataBase(final Administrator addedAdministrator)
            throws AddingEntityException
    {
        this.administratorDAO.addEntity(addedAdministrator);
    }

    @Override
    public final Administrator createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
        final EntityCreator<Administrator> administratorCreator = super.getEntityCreator();
        return administratorCreator.createEntity(httpServletRequest);
    }

    @Override
    public final Optional<Administrator> findEntityById(final long idOfFoundAdministrator)
            throws FindingEntityException
    {
        return this.administratorDAO.findEntityById(idOfFoundAdministrator);
    }

    @Override
    public final void modifyEntity(final Administrator modifiedAdministrator,
                                   final HttpServletRequest httpServletRequest)
            throws EntityModifyingException
    {
        final EntityModifier<Administrator> administratorModifier = super.getEntityModifier();
        administratorModifier.modify(modifiedAdministrator, httpServletRequest);
    }

    @Override
    public final void updateEntityInDataBase(final Administrator updatedAdministrator)
            throws UpdatingEntityException
    {
        this.administratorDAO.updateEntity(updatedAdministrator);
    }

    @Override
    public final void deleteEntityById(final long idOfDeletedAdministrator)
            throws DeletingEntityException
    {
        this.administratorDAO.deleteEntityById(idOfDeletedAdministrator);
    }

    public final Optional<Administrator> findAdministratorByGivenEmail(final String emailOfFoundAdministrator)
            throws FindingEntityException
    {
        return this.administratorDAO.findAdministratorByGivenEmail(emailOfFoundAdministrator);
    }
}
