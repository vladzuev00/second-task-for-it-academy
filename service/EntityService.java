package by.itacademy.zuevvlad.cardpaymentproject.service;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Entity;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

public abstract class EntityService<TypeOfEntity extends Entity>
{
    private final EntityCreator<TypeOfEntity> entityCreator;
    private final EntityModifier<TypeOfEntity> entityModifier;

    protected EntityService(final EntityCreator<TypeOfEntity> entityCreator,
                            final EntityModifier<TypeOfEntity> entityModifier)
    {
        super();
        this.entityCreator = entityCreator;
        this.entityModifier = entityModifier;
    }

    public final EntityCreator<TypeOfEntity> getEntityCreator()
    {
        return this.entityCreator;
    }
    public final EntityModifier<TypeOfEntity> getEntityModifier()
    {
        return this.entityModifier;
    }

    public abstract Collection<TypeOfEntity> findAllEntities()
            throws OffloadingEntitiesException;
    public abstract void addEntityInDataBase(final TypeOfEntity addedEntity)
            throws AddingEntityException;
    public abstract TypeOfEntity createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException;
    public abstract Optional<TypeOfEntity> findEntityById(final long idOfFoundEntity)
            throws FindingEntityException;
    public abstract void modifyEntity(final TypeOfEntity modifiedEntity, final HttpServletRequest httpServletRequest)
            throws EntityModifyingException;
    public abstract void updateEntityInDataBase(final TypeOfEntity updatedEntity)
            throws UpdatingEntityException;
    public abstract void deleteEntityById(final long idOfDeletedEntity)
            throws DeletingEntityException;
}
