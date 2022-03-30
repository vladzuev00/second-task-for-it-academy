package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier;

import by.itacademy.zuevvlad.cardpaymentproject.entity.Entity;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface EntityModifier<TypeOfEntity extends Entity>
{
    public abstract void modify(final TypeOfEntity modifiedEntity, final HttpServletRequest httpServletRequest)
            throws EntityModifyingException;
}
