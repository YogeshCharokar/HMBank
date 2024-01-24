package bean;

public class CurrentAccount extends Account {
    private float overdraftLimit;

    public CurrentAccount(Customer customer, float overdraftLimit) {
        super("Current", 0, customer);
        this.overdraftLimit = overdraftLimit;
    }
    public float withdraw(float amount) {
        if (amount <= (accountBalance + overdraftLimit)) {
            accountBalance -= amount;
            return accountBalance;
        } else {
            throw new RuntimeException("Withdrawal failed - Exceeds overdraft limit.");
        }
    }
    

    public float getOverdraftLimit() {
        return overdraftLimit;
    }
}