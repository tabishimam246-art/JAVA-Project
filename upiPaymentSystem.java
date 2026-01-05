import java.util.*;
class UpiPaymentSystem {
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

    void checkBalance(UpiPaymentSystem[] records) {
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

    void sendMoneyToMobileNo(UpiPaymentSystem[] records) {
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

    void bankTransfer(UpiPaymentSystem[] records) {
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

    void generateUPI(UpiPaymentSystem[] records) {
        for(int i=0; i<records.length; i++) {
            records[i].upiID = records[i].mobileNo + "@abi";
        }
    }

    void login(UpiPaymentSystem[] records) {
        boolean login = true;
        do {
            System.out.println("Mobile No. : ");
            long mobileNo = sc.nextLong();
            for(int i=0; i<records.length; i++) {
                if(mobileNo == records[i].mobileNo) {
                    login = false;
                    return;
                }
            }
        } while(login);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter No. of Account Holders : ");
        int holders = sc.nextInt();
        UpiPaymentSystem[] details = new UpiPaymentSystem[holders];
        for(int i=0; i<details.length; i++) {
            details[i] = new UpiPaymentSystem();
            details[i].createAccount();
        }
        details[0].generateUPI(details);

        int choice;
        do {
            System.out.println("-------------UPI PAYMENT SYSTEM-------------");
            System.out.println("1. Send Money To Mobile Number\n2. Bank Transfer\n3. Check Balance\n4. Mobile Recharge\n5. Electricity Bill\n6. Log Out");
            choice = sc.nextInt();
            switch(choice) {
                case 1 -> {

                }
            }
        } while(choice != 6);

    }
}
