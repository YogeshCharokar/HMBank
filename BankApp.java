package app;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import bean.Account;
import bean.Customer;
import bean.Transaction;
import service.BankServiceProviderImpl;
import service.CustomerServiceProviderImpl;
import service.DateUtil;
import service.IBankServiceProvider;
import service.ICustomerServiceProvider;

public class BankApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ICustomerServiceProvider customerService = new CustomerServiceProviderImpl();
        IBankServiceProvider bankService = new BankServiceProviderImpl(null, null, customerService);

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Get Account Balance");
            System.out.println("6. Get Account Details");
            System.out.println("7. List Accounts");
            System.out.println("8. Transactions");
            System.out.println("9. Exit");
            System.out.print("Enter your choice (1-9): ");
            int choice = sc.nextInt();

            try{
            switch (choice) {
                case 1:
                    createAccountSubMenu(bankService, sc);
                    break;

                case 2:
                    depositOperation(customerService, sc);
                    break;

                case 3:
                    withdrawOperation(customerService, sc);
                    break;

                case 4:
                    transferOperation(customerService, sc);
                    break;

                case 5:
                    getBalanceOperation(customerService, sc);
                    break;

                case 6:
                    getAccountDetailsOperation(customerService, sc);
                    break;

                case 7:
                    listAccountsOperation(bankService);
                    break;

                case 8:
                    getTransactionsOperation(customerService, sc);
                    break;
                    
                case 9:
                    System.out.println("Exiting the Bank App - Thank you!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Choose a valid option (1-8).");
            }
        } catch (NullPointerException e) {
            System.err.println("NullPointerException occurred in the main method: " + e.getMessage());
            e.printStackTrace();
        }
         finally {
            // sc.close();
        }
    }
}

    private static void createAccountSubMenu(IBankServiceProvider bankService, Scanner sc) {
        System.out.println("\nSub Menu - Choose Type of Account:");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.println("3. Zero Balance Account");
        System.out.print("Enter your choice (1, 2, or 3): ");
        int accountChoice = sc.nextInt();

        System.out.print("Enter customer ID: ");
        int customerId = sc.nextInt();

        System.out.print("Enter first name: ");
        String firstName = sc.next();

        System.out.print("Enter last name: ");
        String lastName = sc.next();

        System.out.print("Enter email address: ");
        String email = sc.next();

        System.out.print("Enter phone number: ");
        String phoneNo = sc.next();

        System.out.print("Enter address: ");
        String address = sc.next();

        Customer customer = new Customer(customerId, firstName, lastName, email, phoneNo, address);

        float initialBalance;
        do {
            System.out.print("Enter initial balance (must be greater than or equal to 0): ");
            initialBalance = sc.nextFloat();
        } while (initialBalance < 0);

        switch (accountChoice) {
            case 1:
                bankService.createAccount(customer, 0, "Savings", initialBalance);
                break;
            case 2:
                bankService.createAccount(customer, 0, "Current", initialBalance);
                break;
            case 3:
                bankService.createAccount(customer, 0, "ZeroBalance", initialBalance);
                break;
            default:
                System.out.println("Invalid choice. Account creation failed.");
        }
    }

    private static void depositOperation(ICustomerServiceProvider customerService, Scanner sc) {
        long accountNo = getAccountNo(sc);
        float amount;
        do {
            System.out.print("Enter deposit amount (must be greater than 0): ");
            amount = sc.nextFloat();
            sc.nextLine();
        } while (amount <= 0);
    
        try {
            customerService.deposit(accountNo, amount);
        } catch (RuntimeException e) {
            System.err.println("RuntimeException occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    private static void withdrawOperation(ICustomerServiceProvider customer, Scanner sc) {
        long accountNo = getAccountNo(sc);
        float amount;
        do {
            System.out.print("Enter withdrawal amount (must be greater than 0): ");
            amount = sc.nextFloat();
            sc.nextLine();
        } while (amount <= 0);

        customer.withdraw(accountNo, amount);
    }

    private static void transferOperation(ICustomerServiceProvider customer, Scanner sc) {
        long fromAccountNo = getAccountNo(sc);
        long toAccountNo;
        do {
            System.out.print("Enter recipient account number: ");
            toAccountNo = sc.nextLong();
            sc.nextLine();
        } while (toAccountNo <= 0);

        float amount;
        do {
            System.out.print("Enter transfer amount (must be greater than 0): ");
            amount = sc.nextFloat();
            sc.nextLine();
        } while (amount <= 0);

        customer.transfer(fromAccountNo, toAccountNo, amount);
    }

    private static void getBalanceOperation(ICustomerServiceProvider customer, Scanner sc) {
        long accountNo = getAccountNo(sc);
        float balance = customer.getAccountBalance(accountNo);
        if (balance >= 0) {
            System.out.println("Balance: " + balance);
        }
    }

    private static void getAccountDetailsOperation(ICustomerServiceProvider customer, Scanner sc) {
        long accountNo = getAccountNo(sc);
        customer.getAccountDetails(accountNo);
    }

    private static long getAccountNo(Scanner sc) {
        long accountNo;
        do {
            System.out.print("Enter account number: ");
            accountNo = sc.nextLong();
            sc.nextLine();
        } while (accountNo <= 0);

        return accountNo;
    }

    private static void listAccountsOperation(IBankServiceProvider bankService) {
        Account[] accounts = bankService.listAccounts();
        System.out.println("\nList of Accounts:");
        System.out.println("----------------------");
        for (Account account : accounts) {
            account.listAccount();
            System.out.println("----------------------");
        }
    }

    private static void getTransactionsOperation(ICustomerServiceProvider customerService, Scanner sc) {
        long accountNo = getAccountNo(sc);
        System.out.print("Enter start date (in yyyy-mm-dd format): ");
        String startDateString = sc.next();
        Date fromDate = DateUtil.parseDate(startDateString);

        System.out.print("Enter end date (in yyyy-mm-dd format): ");
        String endDateString = sc.next();
        Date toDate = DateUtil.parseDate(endDateString);

        List<Transaction> transactions = customerService.getTransactions(accountNo, fromDate, toDate);

        if (transactions != null && !transactions.isEmpty()) {
            System.out.println("\nList of Transactions:");
            System.out.println("----------------------");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
                System.out.println("----------------------");
            }
        } else {
            System.out.println("No transactions found for the specified date range.");
        }
    }
}
