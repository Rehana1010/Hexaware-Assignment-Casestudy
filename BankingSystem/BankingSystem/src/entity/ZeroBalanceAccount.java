package entity;

public class ZeroBalanceAccount extends Account {
    // Default constructor for ZeroBalanceAccount
    public ZeroBalanceAccount() {
        super("Zero Balance", 0.0F, null);
    }

    // Overloaded constructor for ZeroBalanceAccount
    public ZeroBalanceAccount(Customer customer) {
        super("Zero Balance", 0.0F, customer);
    }

    // Method to print zero balance account information
    @Override
    public void printAccountInfo() {
        super.printAccountInfo();
    }
}
