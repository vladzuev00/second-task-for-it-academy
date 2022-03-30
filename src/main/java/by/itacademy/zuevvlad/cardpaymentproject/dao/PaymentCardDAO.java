package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.cryptographer.Cryptographer;
import by.itacademy.zuevvlad.cardpaymentproject.dao.cryptographer.StringToStringCryptographer;
import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.DataBaseConnectionPool;
import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.exception.DataBaseConnectionPoolAccessConnectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.FounderGeneratedIdByDataBase;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.exception.FindingGeneratedIdByDataBaseException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToCollection;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetMappingToCollectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetRowMappingToEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToPaymentCards;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Client;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;

import java.sql.*;
import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.PaymentCardSqlOperation.*;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.PaymentCardTableProperty.NAME_OF_COLUMN_OF_ID;

public final class PaymentCardDAO implements DAO<PaymentCard>
{
    private final DataBaseConnectionPool dataBaseConnectionPool;
    private final Cryptographer<String, String> cryptographer;
    private final ResultSetMapperToCollection<PaymentCard, Set<PaymentCard>> resultSetMapperToPaymentCards;
    private final ResultSetMapperToCollection.ResultSetRowMapperToEntity<PaymentCard> resultSetRowMapperToPaymentCard;
    private final FounderGeneratedIdByDataBase founderGeneratedIdByDataBase;

    public static PaymentCardDAO createPaymentCardDAO()
    {
        if(PaymentCardDAO.paymentCardDAO == null)
        {
            synchronized(PaymentCardDAO.class)
            {
                if(PaymentCardDAO.paymentCardDAO == null)
                {
                    PaymentCardDAO.paymentCardDAO = new PaymentCardDAO();
                }
            }
        }
        return PaymentCardDAO.paymentCardDAO;
    }

    private static PaymentCardDAO paymentCardDAO = null;

    private PaymentCardDAO()
    {
        super();
        this.dataBaseConnectionPool = DataBaseConnectionPool.createDataBaseConnectionPool();
        this.cryptographer = StringToStringCryptographer.createCryptographer();
        this.resultSetMapperToPaymentCards = ResultSetMapperToPaymentCards.createResultSetMapperToPaymentCards();
        this.resultSetRowMapperToPaymentCard = this.resultSetMapperToPaymentCards.getResultSetRowMapperToEntity();
        this.founderGeneratedIdByDataBase = FounderGeneratedIdByDataBase.createFounderGeneratedIdByDataBase();
    }

    @Override
    public final void addEntity(final PaymentCard addedPaymentCard)
            throws AddingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_INSERT_PAYMENT_CARD.getSqlQuery(), Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setString(PARAMETER_INDEX_OF_CARD_NUMBER_IN_PREPARED_STATEMENT_TO_INSERT,
                        addedPaymentCard.getCardNumber());

                final PaymentCard.ExpirationDate expirationDate = addedPaymentCard.getExpirationDate();
                final Month monthOfExpirationDate = expirationDate.getMonth();
                preparedStatement.setInt(
                        PARAMETER_INDEX_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE_IN_PREPARED_STATEMENT_TO_INSERT,
                        monthOfExpirationDate.getValue());

                final Year yearOfExpirationDate = expirationDate.getYear();
                preparedStatement.setInt(PARAMETER_INDEX_OF_YEAR_OF_EXPIRATION_DATE_IN_PREPARED_STATEMENT_TO_INSERT,
                        yearOfExpirationDate.getValue());

                preparedStatement.setString(PARAMETER_INDEX_OF_PAYMENT_SYSTEM_IN_PREPARED_STATEMENT_TO_INSERT,
                        addedPaymentCard.getPaymentSystem());

                final String encryptedCvc = this.cryptographer.encrypt(addedPaymentCard.getCvc());
                preparedStatement.setString(PARAMETER_INDEX_OF_ENCRYPTED_CVC_IN_PREPARED_STATEMENT_TO_INSERT,
                        encryptedCvc);

                final Client client = addedPaymentCard.getClient();
                preparedStatement.setLong(PARAMETER_INDEX_OF_CLIENT_ID_IN_PREPARED_STATEMENT_TO_INSERT,
                        client.getId());

                preparedStatement.setString(PARAMETER_INDEX_OF_NAME_OF_BANK_IN_PREPARED_STATEMENT_TO_INSERT,
                        addedPaymentCard.getNameOfBank());

                final String encryptedPassword = this.cryptographer.encrypt(addedPaymentCard.getPassword());
                preparedStatement.setString(PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_INSERT,
                        encryptedPassword);

                preparedStatement.executeUpdate();

                final long generatedId = this.founderGeneratedIdByDataBase.findGeneratedIdInLastInserting(
                        preparedStatement, NAME_OF_COLUMN_OF_ID.getValue());
                addedPaymentCard.setId(generatedId);
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException
                | FindingGeneratedIdByDataBaseException cause)
        {
            throw new AddingEntityException(cause);
        }
    }

    @Override
    public final Collection<PaymentCard> offloadEntities()
            throws OffloadingEntitiesException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final Statement statement = connectionToDataBase.createStatement())
            {
                final ResultSet resultSet = statement.executeQuery(OFFLOAD_ALL_PAYMENT_CARDS.getSqlQuery());
                return this.resultSetMapperToPaymentCards.map(resultSet);
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException
                | ResultSetMappingToCollectionException cause)
        {
            throw new OffloadingEntitiesException(cause);
        }
    }

    @Override
    public final Optional<PaymentCard> findEntityById(final long idOfFoundPaymentCard)
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_SELECT_PAYMENT_CARD_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID,
                        idOfFoundPaymentCard);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? Optional.of(this.resultSetRowMapperToPaymentCard.mapCurrentRow(resultSet))
                        : Optional.empty();
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException
                | ResultSetRowMappingToEntityException cause)
        {
            throw new FindingEntityException(cause);
        }
    }

    @Override
    public final void updateEntity(final PaymentCard updatedPaymentCard)
            throws UpdatingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_UPDATE_PAYMENT_CARD.getSqlQuery()))
            {
                preparedStatement.setString(PARAMETER_INDEX_OF_CARD_NUMBER_IN_PREPARED_STATEMENT_TO_UPDATE,
                        updatedPaymentCard.getCardNumber());

                final PaymentCard.ExpirationDate expirationDate = updatedPaymentCard.getExpirationDate();
                final Month monthOfExpirationDate = expirationDate.getMonth();
                preparedStatement.setInt(
                        PARAMETER_INDEX_OF_NUMBER_OF_MONTH_OF_EXPIRATION_DATE_IN_PREPARED_STATEMENT_TO_UPDATE,
                        monthOfExpirationDate.getValue());

                final Year yearOfExpirationDate = expirationDate.getYear();
                preparedStatement.setInt(PARAMETER_INDEX_OF_YEAR_OF_EXPIRATION_DATE_IN_PREPARED_STATEMENT_TO_UPDATE,
                        yearOfExpirationDate.getValue());

                preparedStatement.setString(PARAMETER_INDEX_OF_PAYMENT_SYSTEM_IN_PREPARED_STATEMENT_TO_UPDATE,
                        updatedPaymentCard.getPaymentSystem());

                final String encryptedCvc = this.cryptographer.encrypt(updatedPaymentCard.getCvc());
                preparedStatement.setString(PARAMETER_INDEX_OF_ENCRYPTED_CVC_IN_PREPARED_STATEMENT_TO_UPDATE,
                        encryptedCvc);

                final Client client = updatedPaymentCard.getClient();
                preparedStatement.setLong(PARAMETER_INDEX_OF_CLIENT_ID_IN_PREPARED_STATEMENT_TO_UPDATE, client.getId());

                preparedStatement.setString(PARAMETER_INDEX_OF_NAME_OF_BANK_IN_PREPARED_STATEMENT_TO_UPDATE,
                        updatedPaymentCard.getNameOfBank());

                final String encryptedPassword = this.cryptographer.encrypt(updatedPaymentCard.getPassword());
                preparedStatement.setString(PARAMETER_INDEX_OF_ENCRYPTED_PASSWORD_IN_PREPARED_STATEMENT_TO_UPDATE,
                        encryptedPassword);

                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE,
                        updatedPaymentCard.getId());

                preparedStatement.executeUpdate();
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException cause)
        {
            throw new UpdatingEntityException(cause);
        }
    }

    @Override
    public final void deleteEntityById(final long idOfDeletedPaymentCard)
            throws DeletingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_DELETE_PAYMENT_CARD_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DELETE,
                        idOfDeletedPaymentCard);
                preparedStatement.executeUpdate();
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException cause)
        {
            throw new DeletingEntityException(cause);
        }
    }

    @Override
    public final boolean isEntityWithGivenIdExisting(final long idOfResearchPaymentCard)
            throws DefiningExistingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_DEFINE_EXISTING_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING,
                        idOfResearchPaymentCard);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException cause)
        {
            throw new DefiningExistingEntityException(cause);
        }
    }

    public final Optional<PaymentCard> findPaymentCardByGivenCardNumber(final String cardNumberOfFoundPaymentCard)
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_SELECT_PAYMENT_CARD_BY_CARD_NUMBER.getSqlQuery()))
            {
                preparedStatement.setString(
                        PARAMETER_INDEX_OF_CARD_NUMBER_IN_PREPARED_STATEMENT_TO_SELECT_BY_CARD_NUMBER,
                        cardNumberOfFoundPaymentCard);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? Optional.of(this.resultSetRowMapperToPaymentCard.mapCurrentRow(resultSet))
                        : Optional.empty();
            }
            finally
            {
                this.dataBaseConnectionPool.returnConnectionToPool(connectionToDataBase);
            }
        }
        catch(final DataBaseConnectionPoolAccessConnectionException | SQLException
                | ResultSetRowMappingToEntityException cause)
        {
            throw new FindingEntityException(cause);
        }
    }
}
