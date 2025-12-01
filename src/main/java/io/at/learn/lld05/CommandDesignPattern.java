package io.at.learn.lld05;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Stack;

public class CommandDesignPattern {
    public static void main(String[] args) {
        TransactionManager transactionManager = new TransactionManager();
        BankAccount bankAccount = new BankAccount();

        transactionManager.execute(new DepositCommand(bankAccount, 50));
        transactionManager.execute(new DepositCommand(bankAccount, 150));
        transactionManager.execute(new DepositCommand(bankAccount, 750));
        transactionManager.execute(new DepositCommand(bankAccount, 100));
        transactionManager.execute(new WithdrawCommand(bankAccount, 100));
        transactionManager.execute(new WithdrawCommand(bankAccount, 750));
        transactionManager.execute(new DepositCommand(bankAccount, 200));

        transactionManager.undo();
        transactionManager.undo();
        transactionManager.undo();
        transactionManager.undo();
        transactionManager.undo();
    }
}

@ToString
class BankAccount {

    private int amount;

    public void deposit(int amount) {
        this.amount += amount;
    }

    public void withdraw(int amount) {
        this.amount -= amount;
    }

}

interface Command {
    BankAccount getBankAccount();
    int getAmount();
    void execute();
    void undo();
}

@AllArgsConstructor
@Getter
class DepositCommand implements Command {

    private BankAccount bankAccount;
    private int amount;

    @Override
    public void execute() {
        bankAccount.deposit(amount);
    }

    @Override
    public void undo() {
        bankAccount.withdraw(amount);
    }
}

@AllArgsConstructor
@Getter
class WithdrawCommand implements Command {

    private BankAccount bankAccount;
    private int amount;

    @Override
    public void execute() {
        bankAccount.withdraw(amount);
    }

    @Override
    public void undo() {
        bankAccount.deposit(amount);
    }
}

class TransactionManager {
    private static final Stack<Command> operationsStack = new Stack<>();

    public void execute(Command command) {
        command.execute();
        operationsStack.add(command);
        System.out.println("Deposit " +  command.getAmount() + ", Account INFO: " + command.getBankAccount().toString());
    }

    public void undo() {
        if(!operationsStack.empty()) {
            Command lastExecutedCommand = operationsStack.pop();
            lastExecutedCommand.undo();
            System.out.println("Withdraw " +  lastExecutedCommand.getAmount() + ", Account INFO: " + lastExecutedCommand.getBankAccount().toString());
        }
    }
}
