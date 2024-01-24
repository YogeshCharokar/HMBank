package bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNo;
    private String address;

    public Customer() {
        this.customerId = 0;
        this.firstName = "";
        this.lastName = "";
        this.emailAddress = "";
        this.phoneNo = "";
        this.address = "";
    }

    public Customer(int customerId, String firstName, String lastName, String emailAddress, String phoneNo, String address) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        setEmailAddress(emailAddress);
        setPhoneNo(phoneNo);
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailAddress);

        if (matcher.matches()) {
            this.emailAddress = emailAddress;
        } else {
            System.out.println("Invalid email address - Enter a valid email address.");
        }
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        String phoneRegex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNo);

        if (matcher.matches()) {
            this.phoneNo = phoneNo;
        } else {
            System.out.println("Invalid phone number - Enter a 10-digit phone number.");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void printCustomerInformation() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Phone Number: " + phoneNo);
        System.out.println("Address: " + address);
    }

    public void listAccount() {
        System.out.println("Name: " + firstName + " " + lastName);
    }
}
