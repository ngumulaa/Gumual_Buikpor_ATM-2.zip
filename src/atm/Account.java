package atm;

import java.util.ArrayList;

public class Account {

    //the name of the account (checking or saving)
    private String name;
    //the balance of the account
    // private double balance;
    //the account ID number which is different the user ID
    private String uuid;
    //the User object that owns this account
    private User holder;
    // the list of transactions for this account
    private ArrayList<Transaction> transactions;

    //constructor
    public Account(String name, User holder, Bank theBank) {
        //set the account name and holder
        this.name = name;
        this.holder = holder;

        //get new account UUID
        this.uuid = theBank.getNewAccountUUID();
        //initialization of the transactions to an empty list 

        this.transactions = new ArrayList<Transaction>();
        

    }

    /**
     * Get the account ID
     *
     * @return the uuid
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Get summary line for the account
     *
     * @return the String summary
     */
    public String getSummaryLine() {
        // get the account's balance 
        double balance = this.getBalance();

        // format the summary line depending on whether the balance is negative 
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance,
                    this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance,
                    this.name);
        }
    }

    public double getBalance() {

        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();

        }
        return balance;
    }

    /**
     * Print the transaction history of the account
     *
     */
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Add a new transaction to this account
     *
     * @param amount the amount transacted
     * @param memo the transaction memo
     */
    public void addTransaction(double amount, String memo) {

        // create new transaction object and add it to our list 
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }

}
//Question for prof: what is the difference between a return type (void & static) for a method
