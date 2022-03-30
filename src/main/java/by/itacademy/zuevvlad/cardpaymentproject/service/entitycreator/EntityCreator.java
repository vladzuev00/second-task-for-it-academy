package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator;

import by.itacademy.zuevvlad.cardpaymentproject.entity.Entity;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface EntityCreator<TypeOfEntity extends Entity>
{
    public abstract TypeOfEntity createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException;
}
