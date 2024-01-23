package dao;
import entity.*;
import exception.*;

import java.util.*;
public class BankServiceProviderImpl extends CustomerServiceProviderImpl implements IBankServiceProvider {
    //private Account[] accountList;
	//private List<Account> accountList;
	private Map<Long, Account> accountList;
    private String branchName;
    private String branchAddress;
    BankRepositoryImpl bankdb=null;
    public BankServiceProviderImpl(String branchName, String branchAddress) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;

        //this.accountList = new ArrayList<>();
        //this.accountList = new HashMap<>();
        bankdb= new BankRepositoryImpl() ;
        accountList=listAccounts();
        //this.accountList=new Account[0];


    }

	@Override
    public Account createAccount(Customer customer_id, long account_id, String account_type, double balance) {
        // Overriding createAccount to update accountList
    	// to check account number set manual or automatic
    	Account account=null;
        if ("Savings".equals(account_type)) {
            account = new SavingsAccount(4.5, customer_id); // Assume 4.5% interest rate for now
        } else if ("Current".equals(account_type)) {
            account = new CurrentAccount(0.0, customer_id); // Assume 0 overdraft limit for now
        } else if ("ZeroBalance".equals(account_type)){
            account = new ZeroBalanceAccount(customer_id);
        }
        else
        {
        	System.out.println("Invalid Account Type");
        	return null;
        }

        // Set account number and balance
        account.setAccount_id(account_id);
        account.setBalance(balance);

        // Update accountList
//        accountList = Arrays.copyOf(accountList, accountList.length + 1);
//        accountList[accountList.length - 1] = account;
        //accountList.add(account);
        accountList.put(account_id,account);
        bankdb.createAccount(customer_id, account_id, account_type, (float) balance);
        // Return the created account
        return account;
    }

    @Override
    public Map<Long, Account> listAccounts() {
        // List all accounts in the bank
    	accountList=castToMap(bankdb.listAccounts());

    	if(accountList.size()==0)
    	{
    		throw new NullPointerException("No Accounts created");
    	}
        return accountList;
    }

    private Map<Long, Account> castToMap(List<Account> listAccounts) {
		// TODO Auto-generated method stub
    	Map<Long,Account> hm=new HashMap<>();
    	hm=new HashMap<>();
    	for(int i=0;i<listAccounts.size();i++)
    	{
    		hm.put(listAccounts.get(i).getAccount_id(), listAccounts.get(i));

    	}
		return hm;
	}

	@Override
    public void calculateInterest() {
        // Calculate interest for all accounts in the bank

    	for (Map.Entry<Long, Account> entry : accountList.entrySet()) {
    		long account_id = entry.getKey();
            Account account = entry.getValue();
    		if (account instanceof SavingsAccount) {
                double interestRate = ((SavingsAccount) account).getInterestRate();
                double interest = (account.getBalance()/100) * interestRate;
                account.setBalance(account.getBalance() + interest);
                accountList.put(account_id, account);
                System.out.println("Interest calculated for Savings Account " + account.getAccount_id() +
                        ": Rs." + interest);
            }
    	}

        //for (Account account : accountList) {

        }

    public Account findAccountObject(long account_id)
    {
    	if(accountList.get(account_id)!=null)
    	{
    		return accountList.get(account_id);
    	}
    	return null;
    }
    public void setAccountObject(Account acc)
    {
    	accountList.put(acc.getAccount_id(),acc);

    }

    @Override
    public double getBalance(long account_id) {
    	Account acc=findAccountObject(account_id);
    	if(acc==null)
    	{
    		throw new InvalidAccountException("No account Found");
    	}
    	return bankdb.getBalance(account_id);
    }

    public double deposit(long account_id, double amount) {
        // Deposit the specified amount into the account
        // Implement logic to update the account balance in storage or database
        // For demonstration purposes, return a dummy balance
    	Account acc=findAccountObject(account_id);
    	if(acc==null)
    	{
    		System.out.println("Receiver Account Invalid");
    		throw new InvalidAccountException("Receiver Account Invalid");
    	}
    	acc.setBalance(acc.getBalance()+amount);

		String transaction_type = "Deposit";

		Transaction transaction = new Transaction(account_id, "Deposit by self", "Deposit", amount);
		transaction.setTransaction_type("Deposit");
		transaction.initializeTransactionDate();
		Date currentDate = transaction.getTransaction_date();
		bankdb.deposit(account_id, (float) amount);
		bankdb.addTransaction(transaction);
		accountList = listAccounts();
		return bankdb.getBalance(account_id);

    }

	@Override
	public double withdraw(long account_id, double amount) {
		Account account = findAccountObject(account_id);
		if (account != null) {
			try {
				// Check for sufficient funds
				if (account.getBalance() >= amount) {
					double newBalance = account.getBalance() - amount;
					account.setBalance(newBalance);
					bankdb.withdraw(account_id, (float) amount);
					accountList.put(account_id, account);
					System.out.println("Transaction successful. New balance: Rs." + newBalance);
					return newBalance;
				} else {
					throw new InsufficientFundException("Insufficient Funds in account");
				}
			} catch (InvalidAccountException e) {
				throw new InvalidAccountException("Account Invalid");
			} catch (OverDraftLimitExcededException e) {
				throw new OverDraftLimitExcededException("Overdraft Limit Exceeded");
			}
		} else {
			throw new InvalidAccountException("Account Not Found");
		}
	}

    @Override
    public void transfer(long fromAccount_id, long toAccount_id, double amount) {
//    	Account senderAccount = findAccountObject(fromAccountNumber);
//    	Account receiverAccount = findAccountObject(toAccountNumber);
    	if(accountList.containsKey(fromAccount_id)==false)
    	{
    		System.out.println("Sender Account Invalid");
    		throw new InvalidAccountException("Sender Account Invalid");
    	}
    	if(accountList.containsKey(toAccount_id)==false)
    	{
    		System.out.println("Receiver Account Invalid");
    		throw new InvalidAccountException("Receiver Account Invalid");
    	}
    	try {
			double sendAmount=withdraw(fromAccount_id,amount);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			throw new InvalidAccountException("Sender Account Invalid");
		}
    	catch (InsufficientFundException e) {
			// TODO Auto-generated catch block
			throw new InsufficientFundException("Insufficient Funds in sender account");
		}
    	catch (OverDraftLimitExcededException e) {
			// TODO Auto-generated catch block
			throw new OverDraftLimitExcededException("Overdraft Limit Exceeded");
		}
    	try
    	{
        	double newAmount=deposit(toAccount_id,amount);
    	}
    	catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
    		double newAmount=deposit(fromAccount_id,amount);
    		System.out.println("Deposited back to Sender account, new balance Rs. "+newAmount);
			throw new InvalidAccountException("Receiver Account Invalid");
    	}


        System.out.println("Transferred Rs." + amount + " from account " + fromAccount_id + " to account " + toAccount_id);
    }
    public String getAccountDetails(long account_id) {
    	Account account = findAccountObject(account_id);
    	if(account==null)
    	{
    		throw new InvalidAccountException("Invalid Account Number");
    	}
    	String customerdetails=" Customer first_name: "+account.getCustomer().getFirst_name()+" Customer last_name: "+account.getCustomer().getLast_name()+" Customer ID: "+account.getCustomer().getCustomer_id()+" Customer email: "+account.getCustomer().getEmail()+" Customer Phonenumber: "+account.getCustomer().getPhone_number()+" Customer address: "+account.getCustomer().getAddress();
    	String result=" Account Type: "+account.getAccount_type()+" Account Balance: "+account.getBalance();
        return "Account details for account number " + account_id+result+customerdetails;
    }
    @Override
	public List<Transaction> getTransactions(long account_id, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return bankdb.getTransactions(account_id, startDate, endDate);
	}
}
