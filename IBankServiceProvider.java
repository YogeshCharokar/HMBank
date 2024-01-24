package service;

import bean.Account;
import bean.Customer;

public interface IBankServiceProvider {
    void createAccount(Customer customer, long accNo, String accType, float balance);

    Account[] listAccounts();

    void calculateInterest();
}
