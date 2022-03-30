package by.itacademy.zuevvlad.cardpaymentproject.dao;

import by.itacademy.zuevvlad.cardpaymentproject.dao.exception.*;
import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.DataBaseConnectionPool;
import by.itacademy.zuevvlad.cardpaymentproject.dao.databaseconnectionpool.exception.DataBaseConnectionPoolAccessConnectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.FounderGeneratedIdByDataBase;
import by.itacademy.zuevvlad.cardpaymentproject.dao.foundergeneratedidbydatabase.exception.FindingGeneratedIdByDataBaseException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToCollection;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.ResultSetMapperToBankAccounts;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetMappingToCollectionException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.resultsetmappertocollection.exception.ResultSetRowMappingToEntityException;
import by.itacademy.zuevvlad.cardpaymentproject.dao.sqloperation.BankAccountSqlOperation;
import by.itacademy.zuevvlad.cardpaymentproject.entity.BankAccount;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static by.itacademy.zuevvlad.cardpaymentproject.dao.tableproperty.BankAccountTableProperty.*;

public final class BankAccountDAO implements DAO<BankAccount>
{
    private final DataBaseConnectionPool dataBaseConnectionPool;
    private final ResultSetMapperToCollection<BankAccount, Set<BankAccount>> resultSetMapperToBankAccounts;
    private final ResultSetMapperToCollection.ResultSetRowMapperToEntity<BankAccount> resultSetRowMapperToBankAccount;
    private final FounderGeneratedIdByDataBase founderGeneratedIdByDataBase;

    public static BankAccountDAO createBankAccountDAO()
    {
        if(BankAccountDAO.bankAccountDAO == null)
        {
            synchronized(BankAccountDAO.class)
            {
                if(BankAccountDAO.bankAccountDAO == null)
                {
                    BankAccountDAO.bankAccountDAO = new BankAccountDAO();
                }
            }
        }
        return BankAccountDAO.bankAccountDAO;
    }

    private static BankAccountDAO bankAccountDAO = null;

    private BankAccountDAO()
    {
        super();

        this.dataBaseConnectionPool = DataBaseConnectionPool.createDataBaseConnectionPool();
        this.resultSetMapperToBankAccounts = ResultSetMapperToBankAccounts.createResultSetMapperToBankAccounts();
        this.resultSetRowMapperToBankAccount = this.resultSetMapperToBankAccounts.getResultSetRowMapperToEntity();
        this.founderGeneratedIdByDataBase = FounderGeneratedIdByDataBase.createFounderGeneratedIdByDataBase();
    }

    @Override
    public final void addEntity(final BankAccount addedBankAccount)
            throws AddingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    BankAccountSqlOperation.PREPARED_STATEMENT_TO_INSERT_BANK_ACCOUNT.getSqlQuery(),
                    Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setBigDecimal(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_MONEY_IN_PREPARED_STATEMENT_TO_INSERT,
                        addedBankAccount.getMoney());
                preparedStatement.setBoolean(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_BLOCKED_IN_PREPARED_STATEMENT_TO_INSERT,
                        addedBankAccount.isBlocked());
                preparedStatement.setString(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_NUMBER_IN_PREPARED_STATEMENT_TO_INSERT,
                        addedBankAccount.getNumber());

                preparedStatement.executeUpdate();

                final long generatedId = this.founderGeneratedIdByDataBase.findGeneratedIdInLastInserting(
                        preparedStatement, NAME_OF_COLUMN_OF_ID.getValue());
                addedBankAccount.setId(generatedId);
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
    public final Collection<BankAccount> offloadEntities()
            throws OffloadingEntitiesException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final Statement statement = connectionToDataBase.createStatement())
            {
                final ResultSet resultSet = statement.executeQuery(
                        BankAccountSqlOperation.OFFLOAD_ALL_BANK_ACCOUNTS.getSqlQuery());
                return this.resultSetMapperToBankAccounts.map(resultSet);
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
    public final Optional<BankAccount> findEntityById(final long idOfFoundBankAccount)
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    BankAccountSqlOperation.PREPARED_STATEMENT_TO_SELECT_BANK_ACCOUNT_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_SELECT_BY_ID,
                        idOfFoundBankAccount);

                final ResultSet resultSet = preparedStatement.executeQuery();

                return resultSet.next() ? Optional.of(this.resultSetRowMapperToBankAccount.mapCurrentRow(resultSet))
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
    public final void updateEntity(final BankAccount updatedBankAccount)
            throws UpdatingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    BankAccountSqlOperation.PREPARED_STATEMENT_TO_UPDATE_BANK_ACCOUNT.getSqlQuery()))
            {
                preparedStatement.setBigDecimal(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_MONEY_IN_PREPARED_STATEMENT_TO_UPDATE,
                        updatedBankAccount.getMoney());
                preparedStatement.setBoolean(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_BLOCKED_IN_PREPARED_STATEMENT_TO_UPDATE,
                        updatedBankAccount.isBlocked());
                preparedStatement.setString(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_NUMBER_IN_PREPARED_STATEMENT_TO_UPDATE,
                        updatedBankAccount.getNumber());
                preparedStatement.setLong(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_UPDATE,
                        updatedBankAccount.getId());

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
    public final void deleteEntityById(final long idOfDeletedBankAccount)
            throws DeletingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    BankAccountSqlOperation.PREPARED_STATEMENT_TO_DELETE_BANK_ACCOUNT_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DELETE_BY_ID,
                        idOfDeletedBankAccount);

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
    public final boolean isEntityWithGivenIdExisting(final long idOfResearchBankAccount)
            throws DefiningExistingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    BankAccountSqlOperation.PREPARED_STATEMENT_TO_DEFINE_EXISTING_BANK_ACCOUNT_BY_ID.getSqlQuery()))
            {
                preparedStatement.setLong(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_ID_IN_PREPARED_STATEMENT_TO_DEFINE_EXISTING,
                        idOfResearchBankAccount);
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

    public final Optional<BankAccount> findBankAccountByNumber(final String numberOfFoundBankAccount)      //TODO: test method
            throws FindingEntityException
    {
        try
        {
            final Connection connectionToDataBase = this.dataBaseConnectionPool.findAvailableConnection();
            try(final PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(
                    BankAccountSqlOperation.PREPARED_STATEMENT_TO_FIND_BANK_ACCOUNT_BY_NUMBER.getSqlQuery()))
            {
                preparedStatement.setString(
                        BankAccountSqlOperation.PARAMETER_INDEX_OF_NUMBER_IN_PREPARED_STATEMENT_TO_SELECT_BY_NUMBER,
                        numberOfFoundBankAccount);
                final ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() ? Optional.of(this.resultSetRowMapperToBankAccount.mapCurrentRow(resultSet))
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
