package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.DataBaseConnectionPool;
import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.exception.DataBaseConnectionPoolAccessConnectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.FounderGeneratedIdByDataBase;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.exception.FindingGeneratedIdByDataBaseException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToCollection;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToPayments;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetMappingToCollectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetRowMappingToEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.entity.Payment;
import by.itacademy.zuevvlad.cardpaymentproject.entity.PaymentCard;

import java.sql.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.PaymentSqlOperation.*;
import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.PaymentTableProperty.NAME_OF_COLUMN_OF_ID;

public final class PaymentDAO implements DAO<Payment>
{
    private final DataBaseConnectionPool dataBaseConnectionPool;
    private final ResultSetMapperToCollection<Payment, Set<Payment>> resultSetMapperToPayments;
    private final ResultSetMapperToCollection.ResultSetRowMapperToEntity<Payment> resultSetRowMapperToPayment;
    private final FounderGeneratedIdByDataBase founderGeneratedIdByDataBase;

    public static PaymentDAO createPaymentDAO()
    {
        if(PaymentDAO.paymentDAO == null)
        {
            synchronized(PaymentDAO.class)
            {
                if(PaymentDAO.paymentDAO == null)
                {
                    PaymentDAO.paymentDAO = new PaymentDAO();
                }
            }
        }
        return PaymentDAO.paymentDAO;
    }

    private static PaymentDAO paymentDAO = null;

    private PaymentDAO()
    {
        super();
        this.dataBaseConnectionPool = DataBaseConnectionPool.createDataBaseConnectionPool();
        this.resultSetMapperToPayments = ResultSetMapperToPayments.createResultSetMapperToPayments();
        this.resultSetRowMapperToPayment = this.resultSetMapperToPayments.getResultSetRowMapperToEntity();
        this.founderGeneratedIdByDataBase = FounderGeneratedIdByDataBase.createFounderGeneratedIdByDataBase();
    }

    @Override
    public final void addEntity(final Payment addedPayment)
            throws AddingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_INSERT_PAYMENT.getSqlQuery(), Statement.RETURN_GENERATED_KEYS))
            {
                final PaymentCard paymentCardOfSender = addedPayment.getCardOfSender();
                preparedStatement.setLong(PARAMETER_INDEX_OF_CARD_ID_OF_SENDER_IN_PREPARED_STATEMENT_TO_INSERT,
                        paymentCardOfSender.getId());

                final PaymentCard paymentCardOfReceiver = addedPayment.getCardOfReceiver();
                preparedStatement.setLong(PARAMETER_INDEX_OF_CARD_ID_OF_RECEIVER_IN_PREPARED_STATEMENT_TO_INSERT,
                        paymentCardOfReceiver.getId());

                preparedStatement.setBigDecimal(PARAMETER_INDEX_OF_MONEY_IN_PREPARED_STATEMENT_TO_INSERT,
                        addedPayment.getMoney());

                final Calendar date = addedPayment.getDate();
                final Timestamp timestamp = new Timestamp(date.getTimeInMillis());
                preparedStatement.setTimestamp(PARAMETER_INDEX_OF_DATE_IN_PREPARED_STATEMENT_TO_INSERT, timestamp);

                preparedStatement.executeUpdate();

                final long generatedId = this.founderGeneratedIdByDataBase.findGeneratedIdInLastInserting(
                        preparedStatement, NAME_OF_COLUMN_OF_ID.getValue());
                addedPayment.setId(generatedId);
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
    public final Collection<Payment> offloadEntities()
            throws OffloadingEntitiesException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final Statement statement = connectionToDataBase.createStatement())
            {
                final ResultSet resultSet = statement.executeQuery(OFFLOAD_ALL_PAYMENTS.getSqlQuery());
                return this.resultSetMapperToPayments.map(resultSet);
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
    public final Optional<Payment> findEntityById(final long idOfFoundPayment)
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_SELECT_PAYMENT_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID, idOfFoundPayment);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? Optional.of(this.resultSetRowMapperToPayment.mapCurrentRow(resultSet))
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
    public final void updateEntity(final Payment updatedPayment)
            throws UpdatingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_UPDATE_PAYMENT.getSqlQuery()))
            {
                final PaymentCard paymentCardOfSender = updatedPayment.getCardOfSender();
                preparedStatement.setLong(PARAMETER_INDEX_OF_CARD_ID_OF_SENDER_IN_PREPARED_STATEMENT_TO_UPDATE,
                        paymentCardOfSender.getId());

                final PaymentCard paymentCardOfReceiver = updatedPayment.getCardOfReceiver();
                preparedStatement.setLong(PARAMETER_INDEX_OF_CARD_ID_OF_RECEIVER_IN_PREPARED_STATEMENT_TO_UPDATE,
                        paymentCardOfReceiver.getId());

                preparedStatement.setBigDecimal(PARAMETER_INDEX_OF_MONEY_IN_PREPARED_STATEMENT_TO_UPDATE,
                        updatedPayment.getMoney());

                final Calendar date = updatedPayment.getDate();
                final Timestamp timestamp = new Timestamp(date.getTimeInMillis());
                preparedStatement.setTimestamp(PARAMETER_INDEX_OF_DATE_IN_PREPARED_STATEMENT_TO_UPDATE, timestamp);

                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE, updatedPayment.getId());

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
    public final void deleteEntityById(final long idOfDeletedPayment)
            throws DeletingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_DELETE_PAYMENT_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DELETE, idOfDeletedPayment);
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
    public final boolean isEntityWithGivenIdExisting(final long idOfResearchEntity)
            throws DefiningExistingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    PREPARED_STATEMENT_TO_DEFINE_EXISTING_PAYMENT_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING,
                        idOfResearchEntity);
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
}
