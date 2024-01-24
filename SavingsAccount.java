package bean;

public class SavingsAccount extends Account {
    private float interest;

    public SavingsAccount(Customer customer, float interest) {
        super("Savings", 500, customer);
        this.interest = interest;
    }

    public float getInterest() {
        return interest;
    }
}
