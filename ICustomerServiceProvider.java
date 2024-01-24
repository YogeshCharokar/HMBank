package service;

import java.util.Date;
import java.util.List;

import bean.Account;
import bean.Transaction;

public interface ICustomerServiceProvider {
    float getAccountBalance(long accountNo);
    float deposit(long accountNo, float amount);
    float withdraw(long accountNo, float amount);
    void transfer(long fromAccountNumber, long toAccountNumber, float amount) throws IllegalArgumentException;
    void getAccountDetails(long accountNumber) throws IllegalArgumentException;
    void addAccountBalance(long accountNo, Account initialBalance);
    List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate);
}
