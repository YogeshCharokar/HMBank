package bean;

// import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaction {
    public enum TransactionType {
        WITHDRAW,
        DEPOSIT,
        TRANSFER
    }
    private Account account;
    private String description;
    private LocalDateTime dateTime;
    private TransactionType transactionType;
    private float transactionAmount;

    public Transaction(Account account, String description, LocalDateTime dateTime, TransactionType transactionType, float transactionAmount) {
        this.account = account;
        this.description = description;
        this.dateTime = LocalDateTime.now();
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    public Transaction(String description, LocalDateTime dateTime, TransactionType transactionType, float transactionAmount) {
        // this.account = account;
        this.description = description;
        this.dateTime = LocalDateTime.now();
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    
    // public static List<Transaction> getTransactionsBetweenDates(List<Transaction> allTransactions, Date fromDate, Date toDate) {
    //     List<Transaction> transactionsBetweenDates = new ArrayList<>();

    //     for (Transaction transaction : allTransactions) {
    //         LocalDateTime transactionDateTime = transaction.getDateTime();
    //         if (!transactionDateTime.toLocalDate().isBefore(fromDate.toLocalDate()) &&
    //             !transactionDateTime.toLocalDate().isAfter(toDate.toLocalDate())) {
    //             transactionsBetweenDates.add(transaction);
    //         }
    //     }

    //     return transactionsBetweenDates;
    // }
    public static List<Transaction> getTransactionsBetweenDates(List<Transaction> allTransactions, Date fromDate, Date toDate) {
    List<Transaction> transactionsBetweenDates = new ArrayList<>();

    LocalDate fromLocalDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate toLocalDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    for (Transaction transaction : allTransactions) {
        LocalDateTime transactionDateTime = transaction.getDateTime();
        LocalDate transactionLocalDate = transactionDateTime.toLocalDate();

        if (!transactionLocalDate.isBefore(fromLocalDate) && !transactionLocalDate.isAfter(toLocalDate)) {
            transactionsBetweenDates.add(transaction);
        }
    }

    return transactionsBetweenDates;
}



    @Override
    public String toString() {
        return "Transaction{" +
                "account=" + account +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", transactionType=" + transactionType +
                ", transactionAmount=" + transactionAmount +
                '}';
    }

}
