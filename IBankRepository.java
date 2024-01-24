package service;

import java.util.Date;
import java.util.List;

import bean.Account;
import bean.Customer;
import bean.Transaction;

public interface IBankRepository {

    void createAccount(Customer customer, long accNo, String accType, float balance);

    List<Account> listAccounts();

    void calculateInterest(long accNo);

    float getAccountBalance(long accountNumber);

    float deposit(long accountNumber, float amount);

    float withdraw(long accountNumber, float amount);

    void transfer(long fromAccountNumber, long toAccountNumber, float amount);

    void getAccountDetails(long accountNumber);

    List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate);
}
