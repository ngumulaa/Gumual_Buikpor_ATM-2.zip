package atm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {

    //the first name of the user
    private String firstName;
    //the last name of the user
    private String lastName;
    //the id number of the user 
    private String uuid;
    //the MD5 hash of the user's pin number
    private byte pinHash[];
    //the list of accounts for the user
    private ArrayList<Account> accounts;

    //constructors
    /**
     * Create a new user
     *
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param pin the user's account pin number
     * @param theBank the Bank object that the user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {
        //set the user's name
        this.firstName = firstName;
        this.lastName = lastName;

        try {

            //store the pin's MD5 hash, instead of the original value for
            //security purpose
            //the class MessageDigest here is to help us change the string pin into a hashed using the 
            //the hash algorithm MD5
            //BUT by doing so with the next line, there is an exeption error because Java does not know the 
            // algorithm MD5 for hashing and to avoid that we use the try-catch
            MessageDigest md = MessageDigest.getInstance("MD5");

            //this line is still a declaration of the constructor component but using the MessageDigest to
            //subtitute the pin write in String to the Hashed 
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException ex) {

            //those three following line is just incase the program does not work due to an algorithm error
            //which we know is not going to happen because the MD5 is a reall algorithm
            System.err.println("error, caught NoSuchAlgorithmException");
            ex.printStackTrace();
            System.exit(1);

            //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        //here we will generate a UUID for the user
        this.uuid = theBank.getNewUserUUID();

        // create an empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print a log message 
        System.out.printf("New user %s, %s with ID %s created.\n", firstName, lastName, this.uuid);
    }

    /**
     * add an account for the user
     *
     * @param anAcct an new account created for the user
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    //making the uuid accessible to the public
    //declaration of the getter
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Check wether a given pin matches the true User pin
     *
     * @param aPin the pin to check
     * @return wether the pin is valid or not
     */
    public boolean validatePin(String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;

    }

    /**
     * Return the user's first name
     *
     * @return
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Print summaries for the accounts of this user.
     */
    public void printAccountsSummary() {

        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf(" %d)%s\n", a + 1,
                    this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Get the number of accounts of the user
     *
     * @return the number of accounts
     */
    public int numAccounts() {
        return this.accounts.size();
    }

    /**
     * Print transaction history of a particular account
     *
     * @param acctIdx the index of the account to use
     */
    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    /**
     * Get the balance of a particular account
     *
     * @param acctIdx the index of the account to use
     * @return the balance of the account
     */
    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    /**
     * Get the UUID of a particular account
     *
     * @param acctIdx the index of the account to use
     * @return the UUID of the account
     */
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID();
    }

    /**
     * Add a transaction to a particular account
     * @param acctIdx the index of the account
     * @param amount the amount of the transaction
     * @param memo the memo of the transaction
     */
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }

}
