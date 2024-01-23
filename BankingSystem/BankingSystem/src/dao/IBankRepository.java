package dao;

import java.util.Date;
import java.util.List;
import entity.*;
public interface IBankRepository {
    void createAccount(Customer customer, long account_id, String account_type, float balance);


    List<Account> listAccounts();

    void calculateInterest();


    float getBalance(long account_id);

    float deposit(long account_id, float amount);

    float withdraw(long account_id, float amount);

    void transfer(long fromAccount_id, long toAccount_id, float amount);

    String getAccountDetails(long account_id);

	List<Transaction> getTransactions(long account_id, String fromDate, String toDate);
}

