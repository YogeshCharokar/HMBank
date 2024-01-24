package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Account;
import bean.Customer;
import bean.Transaction;
import bean.Transaction.TransactionType;

public class BankRepositoryImpl implements IBankRepository {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/HMBank";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "yogesh";

    @Override
    public void createAccount(Customer customer, long accNo, String accType, float balance) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO accounts (account_number, customer_id, account_type, balance) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, accNo);
                preparedStatement.setInt(2, customer.getCustomerId());
                preparedStatement.setString(3, accType);
                preparedStatement.setFloat(4, balance);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public List<Account> listAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM accounts";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Account account = new Account();
                    account.setAccountNo(resultSet.getLong("account_number"));
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // @Override
    // public void calculateInterest() {

    // }
    @Override
    public void calculateInterest(long accountNumber) {
        try (Connection connection = DBUtil.getDBConn();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT balance FROM accounts WHERE account_number = ?")) {

            preparedStatement.setLong(1, accountNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    float balance = resultSet.getFloat("balance");

                    double interestRate = 0.07;
                    double interest = balance * interestRate;

                    updateAccountBalance(connection, accountNumber, (float) interest);

                    System.out.println("Interest calculated and added to account " + accountNumber +
                            ". Interest Amount: " + interest);
                } else {
                    System.out.println("Account not found with account number: " + accountNumber);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to calculate interest. Please try again later.");
        }
    }

    @Override
    public float getAccountBalance(long account_number) {
        float balance = -1;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT balance FROM accounts WHERE account_number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, account_number);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        balance = resultSet.getFloat("balance");
                    } else {
                        System.out.println("Account not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    @Override
    public float deposit(long account_number, float amount) {
        float newBalance = -1;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setFloat(1, amount);
                preparedStatement.setLong(2, account_number);
                preparedStatement.executeUpdate();
                newBalance = getAccountBalance(account_number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newBalance;
    }

    @Override
    public float withdraw(long account_number, float amount) {
        float newBalance = -1;
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newBalance;
    }


    // @Override
    // public List<Transaction> getTransations(long account_number, Date fromDate, Date toDate) {
    //     List<Transaction> transactions = new ArrayList<>();
    //     try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return transactions;
    // }
    // @Override
    // public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) {
    //     List<Transaction> transactions = new ArrayList<>();

    //     try (Connection connection = DBUtil.getDBConn();
    //          PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE account_number = ? AND transaction_date BETWEEN ? AND ?")) {

    //         preparedStatement.setLong(1, accountNumber);
    //         preparedStatement.setDate(2, new java.sql.Date(fromDate.getTime()));
    //         preparedStatement.setDate(3, new java.sql.Date(toDate.getTime()));

    //         try (ResultSet resultSet = preparedStatement.executeQuery()) {
    //             while (resultSet.next()) {
    //                 Transaction transaction = new Transaction(resultSet.getAccount(),
    //                         resultSet.getString("description"),
    //                         resultSet.getTimestamp("transaction_date"),
    //                         TransactionType.valueOf(resultSet.getString("transaction_type")),
    //                         resultSet.getFloat("transaction_amount"));

    //                         // Transaction transaction = new Transaction(resultSet.getLong("account_number"),
    //                         // resultSet.getString("description"),
    //                         // resultSet.getTimestamp("transaction_date"),
    //                         // TransactionType.valueOf(resultSet.getString("transaction_type")),
    //                         // resultSet.getFloat("transaction_amount"));

    //                 transactions.add(transaction);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         System.err.println("SQLException occurred: " + e.getMessage());
    //         e.printStackTrace();
    //         throw new RuntimeException("Failed to get transactions. Please try again later.");
    //     }

    //     return transactions;
    // }

    @Override
    public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) {
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DBUtil.getDBConn();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE account_number = ? AND transaction_date BETWEEN ? AND ?")) {

            preparedStatement.setLong(1, accountNumber);
            preparedStatement.setDate(2, new java.sql.Date(fromDate.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(toDate.getTime()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction(
                            resultSet.getString("description"),
                            resultSet.getTimestamp("transaction_date").toLocalDateTime(),
                            TransactionType.valueOf(resultSet.getString("transaction_type")),
                            resultSet.getFloat("transaction_amount"));

                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to get transactions. Please try again later.");
        }

        return transactions;
    }

    @Override
    public void transfer(long fromAccountNumber, long toAccountNumber, float amount) {
        try (Connection connection = DBUtil.getDBConn()) {
            connection.setAutoCommit(false);
            updateAccountBalance(connection, fromAccountNumber, -amount);
            updateAccountBalance(connection, toAccountNumber, amount);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to perform the transfer. Please try again later.");
        }
    }

    private void updateAccountBalance(Connection connection, long accountNumber, float amount) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE accounts SET balance = balance + ? WHERE account_number = ?")) {

            preparedStatement.setFloat(1, amount);
            preparedStatement.setLong(2, accountNumber);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void getAccountDetails(long accountNumber) {
        try (Connection connection = DBUtil.getDBConn();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM accounts WHERE account_number = ?")) {

            preparedStatement.setLong(1, accountNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long accountId = resultSet.getLong("account_id");
                    String accountHolderName = resultSet.getString("account_holder_name");
                    float balance = resultSet.getFloat("balance");
                    System.out.println("Account Details:");
                    System.out.println("Account ID: " + accountId);
                    System.out.println("Account Holder Name: " + accountHolderName);
                    System.out.println("Balance: " + balance);
                } else {
                    System.out.println("Account not found with account number: " + accountNumber);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to get account details. Please try again later.");
        }
    }

}