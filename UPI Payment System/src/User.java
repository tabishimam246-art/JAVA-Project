//USER Class
class User extends Person {
    BankAccount account;
    UPI upi;
    Transaction[] history = new Transaction[20];
    int txnCount = 0;

    User(String name, String mobile, BankAccount acc) {
        super(name, mobile);
        account = acc;
    }
}
