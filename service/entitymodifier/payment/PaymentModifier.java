package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.payment;

import by.itacademy.zuevvlad.cardpaymentproject.dao.PaymentCardDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Payment;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import by.itacademy.zuevvlad.cardpaymentproject.service.datetransfomer.DateTransformer;
import by.itacademy.zuevvlad.cardpaymentproject.service.datetransfomer.exception.DateTransformingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.payment.NameOfRequestParameterToUpdatePayment.*;

public final class PaymentModifier implements EntityModifier<Payment>
{
    private final PaymentCardDAO paymentCardDAO;
    private final DateTransformer dateTransformer;

    public static PaymentModifier createPaymentModifier()
    {
        if(PaymentModifier.paymentModifier == null)
        {
            synchronized(PaymentModifier.class)
            {
                if(PaymentModifier.paymentModifier == null)
                {
                    PaymentModifier.paymentModifier = new PaymentModifier();
                }
            }
        }
        return PaymentModifier.paymentModifier;
    }

    private static PaymentModifier paymentModifier = null;

    private PaymentModifier()
    {
        super();
        this.paymentCardDAO = PaymentCardDAO.createPaymentCardDAO();
        this.dateTransformer = new DateTransformer();
    }

    @Override
    public final void modify(final Payment modifiedPayment, final HttpServletRequest httpServletRequest)
            throws EntityModifyingException
    {
        try
        {
            final String cardNumberOfNewSender = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_SENDER_OF_UPDATED_PAYMENT.getValue());
            final Optional<PaymentCard> optionalOfPaymentCardOfNewSender
                    = this.paymentCardDAO.findPaymentCardByGivenCardNumber(cardNumberOfNewSender);
            if(optionalOfPaymentCardOfNewSender.isEmpty())
            {
                throw new EntityModifyingException("Impossible to modify object '" + modifiedPayment + "', because "
                    + "object of class '" + PaymentCard.class.getName() + "' with card number '"
                    + cardNumberOfNewSender + "' doesn't exist.");
            }
            final PaymentCard paymentCardOfNewSender = optionalOfPaymentCardOfNewSender.get();

            final String cardNumberOfNewReceiver = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_RECEIVER_OF_UPDATED_PAYMENT.getValue());
            final Optional<PaymentCard> optionalOfPaymentCardOfReceiver
                    = this.paymentCardDAO.findPaymentCardByGivenCardNumber(cardNumberOfNewReceiver);
            if(optionalOfPaymentCardOfReceiver.isEmpty())
            {
                throw new EntityModifyingException("Impossible to modify object '" + modifiedPayment + "', because "
                        + "object of class '" + PaymentCard.class.getName() + "' with card number '"
                        + cardNumberOfNewReceiver + "' doesn't exist.");
            }
            final PaymentCard paymentCardOfNewReceiver = optionalOfPaymentCardOfReceiver.get();

            final String descriptionOfNewMoney = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_UPDATED_PAYMENT.getValue());
            final BigDecimal newMoney = new BigDecimal(descriptionOfNewMoney);

            final String descriptionOfNewDate = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_DATE_OF_UPDATED_PAYMENT.getValue());
            final Calendar newDate = this.dateTransformer.findDate(descriptionOfNewDate);

            modifiedPayment.setCardOfSender(paymentCardOfNewSender);
            modifiedPayment.setCardOfReceiver(paymentCardOfNewReceiver);
            modifiedPayment.setMoney(newMoney);
            modifiedPayment.setDate(newDate);
        }
        catch(final FindingEntityException | DateTransformingException cause)
        {
            throw new EntityModifyingException(cause);
        }
    }
}
