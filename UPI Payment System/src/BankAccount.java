//BANK ACCOUNT Class
class BankAccount {
    String accNo;
    double balance;
    boolean blocked = false;

    BankAccount(String accNo, double balance) {
        this.accNo = accNo;
        this.balance = balance;
    }
}