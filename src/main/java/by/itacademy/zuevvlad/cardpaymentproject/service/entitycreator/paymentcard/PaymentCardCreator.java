package by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.paymentcard;

import by.itacademy.zuevvlad.cardpaymentproject.dao.ClientDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.EntityCreator;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.exception.EntityCreatingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.parser.paymentcardexpirationdate.ParserOfExpirationDateOfPaymentCard;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitycreator.paymentcard.NameOfRequestParameterToAddPaymentCard.*;

public final class PaymentCardCreator implements EntityCreator<PaymentCard>
{
    private final ParserOfExpirationDateOfPaymentCard parserOfExpirationDateOfPaymentCard;
    private final ClientDAO clientDAO;

    public static PaymentCardCreator createPaymentCardCreator()
    {
        if(PaymentCardCreator.paymentCardCreator == null)
        {
            synchronized(PaymentCardCreator.class)
            {
                if(PaymentCardCreator.paymentCardCreator == null)
                {
                    PaymentCardCreator.paymentCardCreator = new PaymentCardCreator();
                }
            }
        }
        return PaymentCardCreator.paymentCardCreator;
    }

    private static PaymentCardCreator paymentCardCreator = null;

    private PaymentCardCreator()
    {
        super();
        this.parserOfExpirationDateOfPaymentCard
                = ParserOfExpirationDateOfPaymentCard.createParserOfExpirationDateOfPaymentCard();
        this.clientDAO = ClientDAO.createClientDAO();
    }

    @Override
    public final PaymentCard createEntity(final HttpServletRequest httpServletRequest)
            throws EntityCreatingException
    {
        try
        {
            final String cardNumber = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_ADDED_PAYMENT_CARD.getValue());

            final String descriptionOfExpirationDate = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_EXPIRATION_DATE_OF_ADDED_PAYMENT_CARD.getValue());
            final PaymentCard.ExpirationDate expirationDate = this.parserOfExpirationDateOfPaymentCard.parse(
                    descriptionOfExpirationDate);

            final String paymentSystem = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PAYMENT_SYSTEM_OF_ADDED_PAYMENT_CARD.getValue());
            final String cvc = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_CVC_OF_ADDED_PAYMENT_CARD.getValue());

            final String phoneNumberOfClient = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_CLIENT_OF_ADDED_PAYMENT_CARD.getValue());
            final Optional<Client> optionalOfClient = this.clientDAO.findClientByGivenPhoneNumber(phoneNumberOfClient);
            if(optionalOfClient.isEmpty())
            {
                throw new EntityCreatingException("Impossible to find object of class '" + Client.class.getName()
                    + "' by not existing number of phone number '" + phoneNumberOfClient + "'.");
            }
            final Client client = optionalOfClient.get();

            final String nameOfBank = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_BANK_OF_ADDED_PAYMENT_CARD.getValue());
            final String password = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_ADDED_PAYMENT_CARD.getValue());

            return new PaymentCard(cardNumber, expirationDate, paymentSystem, cvc, client, nameOfBank, password);
        }
        catch(final FindingEntityException cause)
        {
            throw new EntityCreatingException(cause);
        }
    }
}
