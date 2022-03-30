package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.payment;

import by.itacademy.zuevvlad.cardpaymentproject.dao.PaymentCardDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Payment;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import by.itacademy.zuevvlad.cardpaymentproject.service.datetransfomer.DateTransformer;
import by.itacademy.zuevvlad.cardpaymentproject.service.datetransfomer.exception.DateTransformingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.payment.NameOfRequestParameterToAddPayment.*;

public final class PaymentCreator implements EntityCreator<Payment>
{
    private final PaymentCardDAO paymentCardDAO;
    private final DateTransformer dateTransformer;

    public static PaymentCreator createPaymentCreator()
    {
        if(PaymentCreator.paymentCreator == null)
        {
            synchronized(PaymentCreator.class)
            {
                if(PaymentCreator.paymentCreator == null)
                {
                    PaymentCreator.paymentCreator = new PaymentCreator();
                }
            }
        }
        return PaymentCreator.paymentCreator;
    }

    private static PaymentCreator paymentCreator = null;

    private PaymentCreator()
    {
        super();
        this.paymentCardDAO = PaymentCardDAO.createPaymentCardDAO();
        this.dateTransformer = new DateTransformer();
    }


    @Override
    public final Payment createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
       try
       {
           final String cardNumberOfSender = httpServletRequest.getParameter(
                   NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_SENDER_OF_ADDED_PAYMENT.getValue());
           final Optional<PaymentCard> optionalOfPaymentCardOfSender
                   = this.paymentCardDAO.findPaymentCardByGivenCardNumber(cardNumberOfSender);
           if(optionalOfPaymentCardOfSender.isEmpty())
           {
               throw new EntityCreatingException("Impossible to create object of class '" + Payment.class
                       + "', because payment card with card number '" + cardNumberOfSender + "' doesn't exist.");
           }
           final PaymentCard paymentCardOfSender = optionalOfPaymentCardOfSender.get();

           final String cardNumberOfReceiver = httpServletRequest.getParameter(
                   NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_RECEIVER_OF_ADDED_PAYMENT.getValue());
           final Optional<PaymentCard> optionalOfPaymentCardOfReceiver
                   = this.paymentCardDAO.findPaymentCardByGivenCardNumber(cardNumberOfReceiver);
           if(optionalOfPaymentCardOfReceiver.isEmpty())
           {
               throw new EntityCreatingException("Impossible to create object of class '" + Payment.class
                       + "', because payment card with card number '" + cardNumberOfReceiver + "' doesn't exist.");
           }
           final PaymentCard paymentCardOfReceiver = optionalOfPaymentCardOfReceiver.get();

           final String descriptionOfMoney = httpServletRequest.getParameter(
                   NAME_OF_REQUEST_PARAMETER_OF_MONEY_OF_ADDED_PAYMENT.getValue());
           final BigDecimal money = new BigDecimal(descriptionOfMoney);

           final String descriptionOfDate = httpServletRequest.getParameter(
                   NAME_OF_REQUEST_PARAMETER_OF_DATE_OF_ADDED_PAYMENT.getValue());
           final Calendar date = this.dateTransformer.findDate(descriptionOfDate);

           return new Payment(paymentCardOfSender, paymentCardOfReceiver, money, date);
       }
       catch(final FindingEntityException | DateTransformingException cause)
       {
           throw new EntityCreatingException(cause);
       }
    }
}
