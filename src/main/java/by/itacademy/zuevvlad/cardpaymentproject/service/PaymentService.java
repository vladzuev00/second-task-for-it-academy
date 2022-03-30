package by.itacademy.zuevvlad.cardpaymentproject.service;

import by.itacademy.zuevvlad.cardpaymentproject.dao.PaymentDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Payment;
import by.itacademy.zuevvlad.cardpaymentproject.service.datetransfomer.DateTransformer;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.payment.PaymentCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.payment.PaymentModifier;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PaymentService extends EntityService<Payment>
{
    private final PaymentDAO paymentDAO;
    private final DateTransformer dateTransformer;

    public static PaymentService createPaymentService()
    {
        if(PaymentService.paymentService == null)
        {
            synchronized(PaymentService.class)
            {
                if(PaymentService.paymentService == null)
                {
                    final EntityCreator<Payment> paymentCreator = PaymentCreator.createPaymentCreator();
                    final EntityModifier<Payment> paymentModifier = PaymentModifier.createPaymentModifier();
                    PaymentService.paymentService = new PaymentService(paymentCreator, paymentModifier);
                }
            }
        }
        return PaymentService.paymentService;
    }

    private static PaymentService paymentService = null;

    private PaymentService(final EntityCreator<Payment> paymentCreator, final EntityModifier<Payment> paymentModifier)
    {
        super(paymentCreator, paymentModifier);
        this.paymentDAO = PaymentDAO.createPaymentDAO();
        this.dateTransformer = new DateTransformer();
    }

    @Override
    public final Collection<Payment> findAllEntities()
            throws OffloadingEntitiesException
    {
        return this.paymentDAO.offloadEntities();
    }

    @Override
    public final void addEntityInDataBase(final Payment addedPayment)
            throws AddingEntityException
    {
        this.paymentDAO.addEntity(addedPayment);
    }

    @Override
    public final Payment createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
        final EntityCreator<Payment> paymentCreator = super.getEntityCreator();
        return paymentCreator.createEntity(httpServletRequest);
    }

    @Override
    public final Optional<Payment> findEntityById(final long idOfFoundPayment)
            throws FindingEntityException
    {
        return this.paymentDAO.findEntityById(idOfFoundPayment);
    }

    @Override
    public final void modifyEntity(final Payment modifiedPayment, final HttpServletRequest httpServletRequest)
            throws EntityModifyingException
    {
        final EntityModifier<Payment> paymentModifier = super.getEntityModifier();
        paymentModifier.modify(modifiedPayment, httpServletRequest);
    }

    @Override
    public final void updateEntityInDataBase(final Payment updatedPayment)
            throws UpdatingEntityException
    {
        this.paymentDAO.updateEntity(updatedPayment);
    }

    @Override
    public final void deleteEntityById(final long idOfDeletedPayment)
            throws DeletingEntityException
    {
        this.paymentDAO.deleteEntityById(idOfDeletedPayment);
    }

    public final Map<Payment, String> findPaymentsAndAssociatedDescriptionOfDates()
            throws OffloadingEntitiesException
    {
        final Collection<Payment> payments = this.paymentDAO.offloadEntities();
        return payments.stream().collect(Collectors.toMap(Function.identity(), (final Payment payment) ->
        {
            final Calendar date = payment.getDate();
            return PaymentService.this.dateTransformer.findDescription(date);
        }));
    }
}
