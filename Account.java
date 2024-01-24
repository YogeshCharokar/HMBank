package bean;

public class Account {
    private static int lastAccNo = 1000;

    protected long accountNo;
    protected String accountType;
    protected float accountBalance;
    protected Customer customer;

    public Account() {
        this.accountNo = getNextAccountNo();
        this.accountType = "";
        this.accountBalance = 0.0f;
        this.customer = new Customer();
    }

    public Account(String accountType, float accountBalance, Customer customer) {
        this.accountNo = getNextAccountNo();
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.customer = customer;
    }

    public long getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }    

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void printAccountInformation() {
        System.out.println("Account Number: " + accountNo);
        System.out.println("Account Type: " + accountType);
        System.out.println("Account Balance: " + accountBalance);
        System.out.println("Customer Information:");
        customer.printCustomerInformation();
    }

    protected static int getNextAccountNo() {
        return ++lastAccNo;
    }

    public float deposit(float amount) {
        accountBalance = amount+accountBalance;
        return accountBalance;
    }

    public float withdraw(float amount) {
        if (amount <= accountBalance) {
            accountBalance -= amount;
            return accountBalance;
        } else {
                throw new RuntimeException("Insufficient funds for withdrawal.");
        }
    }
    public void transfer(Account toAccount, float amount) {
        if (withdraw(amount) >= 0) {
            toAccount.deposit(amount);
            System.out.println("Transfer successful - Balance: "+accountBalance);
        } else {
            throw new RuntimeException("Transfer failed - Insufficient funds in the source account.");
        }
    }

    public void listAccount() {
        System.out.println("Account Number: "+accountNo);
        System.out.println("Account Type: "+accountType);
        customer.listAccount();
    }
}
