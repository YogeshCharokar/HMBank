package service;

import bean.Account;
import bean.CurrentAccount;
import bean.Customer;
import bean.SavingsAccount;
import bean.ZeroBalanceAccount;
import java.util.ArrayList;
import java.util.List;

public class BankServiceProviderImpl extends CustomerServiceProviderImpl implements IBankServiceProvider {
    private List<Account> accountList;
    private ICustomerServiceProvider customerService;
    private String branchName;
    private String branchAddress;

    public BankServiceProviderImpl(String branchName, String branchAddress, ICustomerServiceProvider customerService) {
        super();
        this.accountList = new ArrayList<>();
        this.customerService = customerService;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
    }

    @Override
    public Account[] listAccounts() {
        return accountList.toArray(new Account[0]);
    }

    @Override
    public void calculateInterest() {
        for (Account account : accountList) {
            if (account instanceof SavingsAccount) {
                SavingsAccount savingsAccount = (SavingsAccount) account;
                float interest = savingsAccount.getInterest() * savingsAccount.getAccountBalance();
                System.out.println("Interest for account " + account.getAccountNo() + ": " + interest);
            }
        }
    }

    public void printBranchDetails() {
        System.out.println("Branch Name: " + branchName);
        System.out.println("Branch Address: " + branchAddress);
    }

    @Override
    public void createAccount(Customer customer, long accNo, String accType, float balance) {
        if (customer != null) {
            Account account;
            switch (accType) {
                case "Savings":
                    account = new SavingsAccount(customer, 0.0f);
                    break;
                case "Current":
                    account = new CurrentAccount(customer, 0.0f);
                    break;
                case "ZeroBalance":
                    account = new ZeroBalanceAccount(customer);
                    break;
                default:
                    System.out.println("Invalid account type. Cannot create the account.");
                    return;
            }
            account.setAccountBalance(balance);
            accountList.add(account);
            customerService.addAccountBalance(account.getAccountNo(), account);
            System.out.println("Account created successfully - Account Number: " + account.getAccountNo());
        } else {
            System.out.println("Customer not found. Cannot create the account.");
        }
    }
}
