package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bean.Account;
import bean.Customer;
import bean.Transaction;


public class CustomerServiceProviderImpl implements ICustomerServiceProvider {
    private static final Customer[] customers = null;
    private Map<Long, Account> accounts;

    public CustomerServiceProviderImpl() {
        this.accounts = new HashMap<>();
    }

    public float getAccountBalance(long accountNo) {
        if (accounts.containsKey(accountNo)) {
            return accounts.get(accountNo).getAccountBalance();
        } else {
            System.out.println("Account not found.");
            return -1;
        }
    }

    @Override
    public float deposit(long accountNo, float amount) {
        System.out.println();
        if (accounts.containsKey(accountNo)) {
            Account account = accounts.get(accountNo);
            float balance = account.deposit(amount);
            System.out.println("Deposit successful - Balance: " + balance);
            return balance;
        } else {
            System.out.println("Account not found. Deposit failed.");
            return -1;
        }
    }

    @Override
    public float withdraw(long accountNo, float amount) {
        System.out.println();
        if (accounts.containsKey(accountNo)) {
            Account account = accounts.get(accountNo);
            float currentBalance = account.withdraw(amount);
            if (currentBalance >= 0) {
                System.out.println("Withdrawal successful - Balance: " + currentBalance);
            }
            return currentBalance;
        } else {
            System.out.println("Account not found.");
            return -1;
        }
    }

    public void transfer(long fromAccountNo, long toAccountNo, float amount) throws IllegalArgumentException{
        System.out.println();
        if (accounts.containsKey(fromAccountNo) && accounts.containsKey(toAccountNo)) {
            Account fromAccount = accounts.get(fromAccountNo);
            Account toAccount = accounts.get(toAccountNo);
            fromAccount.transfer(toAccount, amount);
            // System.out.println("Transfer successful - Balance: "+accountBalance);
        } else {
            throw new IllegalArgumentException("One or both accounts not found.");
        }
    }

    public void getAccountDetails(long accountNo) throws IllegalArgumentException {
        if (accounts.containsKey(accountNo)) {
            Account account = accounts.get(accountNo);
            account.printAccountInformation();
        } else {
            throw new IllegalArgumentException("Account not found.");
        }
    }

    public void addAccountBalance(long accountNo, Account initialBalance) {
        accounts.put(accountNo, initialBalance);
    }
    
    public Customer getCustomerById(long accNo) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == accNo) {
                return customer;
            }
        }
        return null;
    }

    // public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) {
    //     List<Transaction> transactions = new ArrayList<>();

    //     if (accounts.containsKey(accountNumber)) {
    //         Account account = accounts.get(accountNumber);
    //         transactions = Transaction.getTransactionsBetweenDates(getTransactions(), fromDate, toDate);
    //     } else {
    //         System.out.println("Account not found.");
    //     }

    //     return transactions;
    // }
    @Override
    public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) {
        List<Transaction> transactions = new ArrayList<>();

        if (accounts.containsKey(accountNumber)) {
            transactions = Transaction.getTransactionsBetweenDates(transactions, fromDate, toDate);
        } else {
            System.out.println("Account not found.");
        }

        return transactions;
    }
}