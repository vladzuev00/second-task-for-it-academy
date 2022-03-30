package by.itacademy.zuevvlad.cardpaymentproject.service;

import by.itacademy.zuevvlad.cardpaymentproject.dao.PaymentCardDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.paymentcard.PaymentCardCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.paymentcard.PaymentCardModifier;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

public final class PaymentCardService extends EntityService<PaymentCard>
{
    private final PaymentCardDAO paymentCardDAO;

    public static PaymentCardService createPaymentCardService()
    {
        if(PaymentCardService.paymentCardService == null)
        {
            synchronized(PaymentCardService.class)
            {
                if(PaymentCardService.paymentCardService == null)
                {
                    final EntityCreator<PaymentCard> paymentCardCreator = PaymentCardCreator.createPaymentCardCreator();
                    final EntityModifier<PaymentCard> paymentCardModifier
                            = PaymentCardModifier.createPaymentCardModifier();
                    PaymentCardService.paymentCardService = new PaymentCardService(
                            paymentCardCreator, paymentCardModifier);
                }
            }
        }
        return PaymentCardService.paymentCardService;
    }

    private static PaymentCardService paymentCardService = null;

    private PaymentCardService(final EntityCreator<PaymentCard> paymentCardCreator,
                               final EntityModifier<PaymentCard> paymentCardModifier)
    {
        super(paymentCardCreator, paymentCardModifier);
        this.paymentCardDAO = PaymentCardDAO.createPaymentCardDAO();
    }

    @Override
    public final Collection<PaymentCard> findAllEntities()
            throws OffloadingEntitiesException
    {
        return this.paymentCardDAO.offloadEntities();
    }

    @Override
    public final void addEntityInDataBase(final PaymentCard addedPaymentCard)
            throws AddingEntityException
    {
        this.paymentCardDAO.addEntity(addedPaymentCard);
    }

    @Override
    public final PaymentCard createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
        final EntityCreator<PaymentCard> paymentCardCreator = super.getEntityCreator();
        return paymentCardCreator.createEntity(httpServletRequest);
    }

    @Override
    public final Optional<PaymentCard> findEntityById(final long idOfFoundPaymentCard)
            throws FindingEntityException
    {
        return this.paymentCardDAO.findEntityById(idOfFoundPaymentCard);
    }

    @Override
    public final void modifyEntity(final PaymentCard modifiedPaymentCard, final HttpServletRequest httpServletRequest)
            throws EntityModifyingException
    {
        final EntityModifier<PaymentCard> paymentCardModifier = super.getEntityModifier();
        paymentCardModifier.modify(modifiedPaymentCard, httpServletRequest);
    }

    @Override
    public final void updateEntityInDataBase(final PaymentCard updatedPaymentCard)
            throws UpdatingEntityException
    {
        this.paymentCardDAO.updateEntity(updatedPaymentCard);
    }

    @Override
    public final void deleteEntityById(final long idOfDeletedPaymentCard)
            throws DeletingEntityException
    {
        this.paymentCardDAO.deleteEntityById(idOfDeletedPaymentCard);
    }
}
