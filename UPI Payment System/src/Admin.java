import java.util.*;

//ADMIN Class
class Admin {
    User[] users;
    int size;
    int count = 0;

    Admin(int size) {
        this.size = size;
        users = new User[size];
    }

    void createAccount(Scanner sc) {
        if (count == size) {
            System.out.println("Account limit reached!");
            return;
        }

        System.out.print("Name : ");
        String name = sc.nextLine();
        String mobile;
        boolean valid = true;
        do {
            System.out.print("Mobile Number : ");
            mobile = sc.nextLine();
            if(mobile.length() == 10) {
                for(int i=0; i<mobile.length(); i++) {
                    if(mobile.charAt(i) >= '0' && mobile.charAt(i) <= '9') {
                        valid = false;
                    } else {
                        valid = true;
                        break;
                    }
                }
                if(valid) {
                    System.out.println("Please enter a valid mobile number");
                }
            } else {
                System.out.println("Please enter a valid mobile number");
            }

            for(int i=0; i<users.length; i++) {
                if (users[i] != null) {
                    if (users[i].mobile.equals(mobile)) {
                        System.out.println("Account Number already exists with entered mobile number!");
                        valid = true;
                        break;
                    }

                }
            }

        } while(valid);

        BankAccount acc = new BankAccount("AC" + (1001 + count), 10000);
        users[count] = new User(name, mobile, acc);

        System.out.println("Account Created : " + acc.accNo);
        count++;
    }

    void generateUpi(Scanner sc) {
        System.out.print("Enter Account No : ");
        String accNo = sc.nextLine();

        boolean valid = true;
        for (int i = 0; i < count; i++) {
            if (users[i].account.accNo.equals(accNo)) {
                String pin;

                do {
                    System.out.print("Set 4 digit PIN : ");
                    pin = sc.nextLine();

                    if(pin.length() != 4) {
                        System.out.println("PIN must be of 4 digits!");
                    }

                    for(int j=0; j<pin.length(); j++) {
                        char ch = pin.charAt(j);
                        valid = true;
                        if(ch < '0' || ch > '9') {
                            valid = false;
                            break;
                        }
                    }

                    if(!valid) {
                        System.out.println("Please set a 4 digit numeric pin");
                    }
                } while(pin.length() != 4 || !valid);

                users[i].upi = new UPI(accNo + "@upi", pin);
                System.out.println("UPI Created : " + users[i].upi.upiId);
                return;
            }
        }
        System.out.println("Account not found");
    }

    void blockUnblock(Scanner sc) {
        System.out.print("Enter Account No : ");
        String acc = sc.nextLine();

        for (int i = 0; i < count; i++) {
            if (users[i].account.accNo.equals(acc)) {
                users[i].account.blocked = !users[i].account.blocked;
                users[i].upi.wrongAttempts = 0;
                System.out.println("Account Block Status : " + users[i].account.blocked);
                return;
            }
        }
        System.out.println("Account not found!");
    }
}