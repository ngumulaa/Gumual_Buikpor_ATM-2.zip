package atm;

import java.util.Scanner;
import static javax.swing.text.html.HTML.Attribute.ID;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ATM {

    public static void main(String[] args) {

        // init Scanner
        Scanner sc = new Scanner(System.in);

        // init Bank
        Bank theBank = new Bank("Bank of America");

        // add a user, which also creates a savings account ///////
        User aUser = theBank.addUser("Robert", "Whitnell", "1234");
        //User aUser1 = theBank.addUser("Nicole", "Whitnell", "12341");
        // add a checking account for one user 
        Account newAccount = new Account("Checking ", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            // stay in the login prompt until successful login 
            curUser = ATM.mainMenuPrompt(theBank, sc);

            // stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
            SwingDemo endWindow = new SwingDemo();
            
        }

    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        // inits
        String userID;
        String pin;
        User authUser;

        // prompt the user for user ID/pin combo until a correct one is reached 
        do {

            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter User ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the ID and pin combo 
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect User/pin combination. "
                        + "Please try again. ");
            }

        } while (authUser == null); // continue looping until successful login 

        return authUser;

    }

    public static void printUserMenu(User theUser, Scanner sc) {

        // print a summary of the user's accounts 
        theUser.printAccountsSummary();

        // init
        int choice;

        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n",
                    theUser.getFirstName());
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdraw  ");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");

            }

        } while (choice < 1 || choice > 5);

        // process the choice 
        switch (choice) {

            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                // gobble up rest of previous input 
                sc.nextLine();

                break;
        }
        //this is the recursion the method calling itself until the condition is met
        // redisplay this menu unless the user wants to quit 
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);

        }
    }

    /**
     * Show the transaction history for an account
     *
     * @param theUser the logged-in User object
     * @param sc the Scanner object used for user input
     */
    public static void showTransHistory(User theUser, Scanner sc) {

        int theAcct;

        // get account whose transaction history to look at 
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "whose transactions you want to see: ",
                    theUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }

        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        // print the transaction history 
        theUser.printAcctTransHistory(theAcct);

    }

    /**
     * Processing transferring funds one from account to another
     *
     * @param theUser the logged-in User object
     * @param sc the Scanner object used for the user input
     */
    public static void transferFunds(User theUser, Scanner sc) {
        //ini
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        // get the account to transfer from 
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the account to transfer to 
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        // get the amount to transfer 
        do {
            System.out.printf("Enter the amount to transfer(max $%.02f) : $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than \n"
                        + "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        // finally, do the transfer 
        theUser.addAcctTransaction(fromAcct, -1 * amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(fromAcct)));
    }

    /**
     * Process a fund withdraw from an account
     *
     * @param theUser the logged-in User object
     * @param sc the scanner object user for user input
     */
    public static void withdrawFunds(User theUser, Scanner sc) {
        //init
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from 
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "to withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt() - 1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the amount to transfer 
        do {
            System.out.printf("Enter the amount to withdraw(max $%.02f) : $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than \n"
                        + "balance of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        // gobble up rest of previous input 
        sc.nextLine();

        // get a memo 
        System.out.printf("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdraw 
        theUser.addAcctTransaction(fromAcct, -1 * amount, memo);
    }

    /**
     * Process a fund deposit to an account
     *
     * @param theUser the logged-in User object
     * @param sc the scanner object used for user input
     */
    public static void depositFunds(User theUser, Scanner sc) {
        // inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from 
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "to deposit in: ", theUser.numAccounts());
            toAcct = sc.nextInt() - 1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again. ");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        // get the amount to transfer 
        do {
            System.out.printf("Enter the amount to transfer(max $%.02f) : $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }

        } while (amount < 0);

        // gobble up rest of previous input 
        sc.nextLine();

        // get a memo 
        System.out.printf("Enter a memo: ");
        memo = sc.nextLine();

        
        // do the withdraw 
        theUser.addAcctTransaction(toAcct, amount, memo);

    }

    private static class SwingDemo extends JFrame {
        // private JFrame frame = new JFrame();

        public SwingDemo() {
            setSize(350, 250);
            endPanel closePanel = new endPanel();
             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().add(closePanel);
            setVisible(true);
            setExtendedState(JFrame.NORMAL);

            //setDefaultCloseOperation(EXIT_ON_CLOSE);
            Timer tm = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            setVisible(false);
                            //setExtendedState(JFrame.NORMAL);
                        }
                    });
                }
            });
            tm.setRepeats(false);
            tm.start();
        }
        //public static void main(String[] args) {
        // new SwingDemo();
        //}
    }
}
