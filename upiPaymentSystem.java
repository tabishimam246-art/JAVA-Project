import java.util.*;
public class upiPaymentSystem {
    String name, accNo, upiID;
    long mobileNo;
    double balance;
    int pin;

    Scanner sc = new Scanner(System.in);

    void setDetails(String name, long mobileNo) {
        this.name = name;
        this.mobileNo = mobileNo;
    }

    void createAccount() {
        System.out.println("Enter Name : ");
        String name = sc.nextLine();
        System.out.println("Enter Mobile No. : ");
        long mobileNo = sc.nextLong();
        setDetails(name, mobileNo);
    }

    void showBalance() {
        System.out.println("Balance Check Successful!!");
        System.out.println("Available Balance : ₹"+this.balance);
    }

    void checkBalance(upiPaymentSystem[] records) {
        System.out.println("Enter Account No. : ");
        String accNo = sc.nextLine().trim();
        System.out.println("Enter pin : ");
        int pin = sc.nextInt();
        boolean accFound = true, validPin = true;
        for(int i=0; i<records.length; i++) {
            accFound = false;
            if (records[i].accNo.equals(accNo)) {
                if (records[i].pin == pin) {
                    records[i].showBalance();
                    validPin = false;
                    break;
                }
            }
        }
        if(validPin) {
            System.out.println("Incorrect Pin Entered!!");
        }
        if(accFound) {
            System.out.println("Account Number not found!!");
        }
    }

    void sendMoneyToMobileNo(upiPaymentSystem[] records) {
        System.out.println("Mobile No. : ");
        long mobileNo = sc.nextLong();
        boolean found = true;
        for(int i=0; i<records.length; i++) {
            if(records[i].mobileNo == mobileNo) {
                System.out.println("Enter Amount : ");
                double amount = sc.nextDouble();
                records[i].balance += amount;
                found = false;
                break;
            }
        }
        if(found) {
            System.out.println("Mobile Number doesn't have UPI!!");
        }
    }

    void bankTransfer(upiPaymentSystem[] records) {
        sc.nextLine();
        System.out.println("Account Number : ");
        String accNo = sc.nextLine();
        boolean found = true;
        for(int i=0; i<records.length; i++) {
            if(records[i].accNo.equals(accNo)) {
                System.out.println("Amount : ");
                double amount = sc.nextDouble();
                records[i].balance += amount;
                System.out.println("Transaction is Successful!!");
                found = false;
                break;
            }
        }
        if(found) {
            System.out.println("Bank Account is not available!!");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------UPI PAYMENT SYSTEM-------------");
        
    }
}
