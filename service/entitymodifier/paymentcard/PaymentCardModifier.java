package by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.paymentcard;

import by.itacademy.zuevvlad.cardpaymentproject.dao.ClientDAO;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.FindingEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.EntityModifier;
import by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.exception.EntityModifyingException;
import by.itacademy.zuevvlad.cardpaymentproject.service.parser.paymentcardexpirationdate.ParserOfExpirationDateOfPaymentCard;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static by.itacademy.zuevvlad.cardpaymentproject.service.entitymodifier.paymentcard.NameOfRequestParameterToUpdatePaymentCard.*;

public final class PaymentCardModifier implements EntityModifier<PaymentCard>
{
    private final ParserOfExpirationDateOfPaymentCard parserOfExpirationDateOfPaymentCard;
    private final ClientDAO clientDAO;

    public static PaymentCardModifier createPaymentCardModifier()
    {
        if(PaymentCardModifier.paymentCardModifier == null)
        {
            synchronized(PaymentCardModifier.class)
            {
                if(PaymentCardModifier.paymentCardModifier == null)
                {
                    PaymentCardModifier.paymentCardModifier = new PaymentCardModifier();
                }
            }
        }
        return PaymentCardModifier.paymentCardModifier;
    }

    private static PaymentCardModifier paymentCardModifier = null;

    private PaymentCardModifier()
    {
        super();
        this.parserOfExpirationDateOfPaymentCard
                = ParserOfExpirationDateOfPaymentCard.createParserOfExpirationDateOfPaymentCard();
        this.clientDAO = ClientDAO.createClientDAO();
    }

    @Override
    public final void modify(final PaymentCard modifiedPaymentCard, final HttpServletRequest httpServletRequest)
            throws EntityModifyingException
    {
        try
        {
            final String newCardNumber = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_CARD_NUMBER_OF_UPDATED_PAYMENT_CARD.getValue());

            final String descriptionOfNewExpirationDate = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_EXPIRATION_DATE_OF_UPDATED_PAYMENT_CARD.getValue());
            final PaymentCard.ExpirationDate newExpirationDate = this.parserOfExpirationDateOfPaymentCard.parse(
                    descriptionOfNewExpirationDate);

            final String newPaymentSystem = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PAYMENT_SYSTEM_OF_UPDATED_PAYMENT_CARD.getValue());
            final String newCvc = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_CVC_OF_UPDATED_PAYMENT_CARD.getValue());

            final String phoneNumberOfNewClient = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PHONE_NUMBER_OF_CLIENT_OF_UPDATED_PAYMENT_CARD.getValue());
            final Optional<Client> optionalOfNewClient = this.clientDAO.findClientByGivenPhoneNumber(
                    phoneNumberOfNewClient);
            if(optionalOfNewClient.isEmpty())
            {
                throw new EntityModifyingException("Impossible to modify object '" + modifiedPaymentCard + "', because "
                        + "object of class '" + Client.class.getName() + "' with phone number '"
                        + phoneNumberOfNewClient + "' doesn't exist.");
            }
            final Client newClient = optionalOfNewClient.get();

            final String newNameOfBank = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_NAME_OF_BANK_OF_UPDATED_PAYMENT_CARD.getValue());
            final String newPassword = httpServletRequest.getParameter(
                    NAME_OF_REQUEST_PARAMETER_OF_PASSWORD_OF_UPDATED_PAYMENT_CARD.getValue());

            modifiedPaymentCard.setCardNumber(newCardNumber);
            modifiedPaymentCard.setExpirationDate(newExpirationDate);
            modifiedPaymentCard.setPaymentSystem(newPaymentSystem);
            modifiedPaymentCard.setCvc(newCvc);
            modifiedPaymentCard.setClient(newClient);
            modifiedPaymentCard.setNameOfBank(newNameOfBank);
            modifiedPaymentCard.setPassword(newPassword);
        }
        catch(final FindingEntityException cause)
        {
            throw new EntityModifyingException(cause);
        }
    }
}
