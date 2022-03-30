package by.itacademy.zuevvlad.cardpaymentproject.service.parser.paymentcardexpirationdate;

import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;

import java.time.Month;
import java.time.Year;

public final class ParserOfExpirationDateOfPaymentCard
{
    public static ParserOfExpirationDateOfPaymentCard createParserOfExpirationDateOfPaymentCard()
    {
        if(ParserOfExpirationDateOfPaymentCard.parserOfExpirationDateOfPaymentCard == null)
        {
            synchronized(ParserOfExpirationDateOfPaymentCard.class)
            {
                if(ParserOfExpirationDateOfPaymentCard.parserOfExpirationDateOfPaymentCard == null)
                {
                    ParserOfExpirationDateOfPaymentCard.parserOfExpirationDateOfPaymentCard
                            = new ParserOfExpirationDateOfPaymentCard();
                }
            }
        }
        return ParserOfExpirationDateOfPaymentCard.parserOfExpirationDateOfPaymentCard;
    }

    private static ParserOfExpirationDateOfPaymentCard parserOfExpirationDateOfPaymentCard = null;

    private ParserOfExpirationDateOfPaymentCard()
    {
        super();
    }

    public final PaymentCard.ExpirationDate parse(final String descriptionOfExpirationDate)
    {
        final String[] componentsOfDate = descriptionOfExpirationDate.split(
                ParserOfExpirationDateOfPaymentCard.DELIMITER_OF_MONTH_AND_YEAR);

        final String descriptionOfNumberOfMonth = componentsOfDate[
                ParserOfExpirationDateOfPaymentCard.INDEX_OF_COMPONENT_OF_MONTH];
        final int numberOfMonth = Integer.parseInt(descriptionOfNumberOfMonth);
        final Month month = Month.of(numberOfMonth);

        final String descriptionOfNumberOfYear = componentsOfDate[
                ParserOfExpirationDateOfPaymentCard.INDEX_OF_COMPONENT_OF_YEAR];
        final int numberOfYear = Integer.parseInt(descriptionOfNumberOfYear);
        final Year year = Year.of(numberOfYear);

        return new PaymentCard.ExpirationDate(month, year);
    }

    public static final String DELIMITER_OF_MONTH_AND_YEAR = "/";
    private static final int INDEX_OF_COMPONENT_OF_MONTH = 0;
    private static final int INDEX_OF_COMPONENT_OF_YEAR = 1;
}
