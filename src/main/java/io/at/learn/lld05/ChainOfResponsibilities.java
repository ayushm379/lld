package io.at.learn.lld05;

public class ChainOfResponsibilities {
    public static void main(String[] args) {
        ExpenseApprover teamLead = new TeamLead();
        ExpenseApprover manager = new Manager();
        ExpenseApprover director = new Director();

        teamLead.setNext(manager);
        manager.setNext(director);

        try {
            teamLead.approve(new ExpenseRequest(5_000, "Team Lunch"));
            teamLead.approve(new ExpenseRequest(15_000, "Travel to client location"));
            teamLead.approve(new ExpenseRequest(55_000, "New Infrastructure"));
        } catch (RuntimeException exception) {
            System.out.println("Error : " + exception.getMessage());
        }
    }
}

record ExpenseRequest(double amount, String purpose) {}

interface ExpenseApprover {
    void setNext(ExpenseApprover next);
    void approve(ExpenseRequest request);
}

abstract class AbstractApprover implements ExpenseApprover {

    protected ExpenseApprover next;

    @Override
    public void setNext(ExpenseApprover next) {
        this.next = next;
    }

    protected void forwardRequest(ExpenseRequest request) {
        if(next != null) {
            next.approve(request);
        } else {
            throw new RuntimeException("No one available to approve");
        }
    }

}

class TeamLead extends AbstractApprover {

    @Override
    public void approve(ExpenseRequest request) {
        if(request.amount() < 10_000) {
            System.out.println("Team lead approved");
        } else {
            forwardRequest(request);
        }
    }
}

class Manager extends AbstractApprover {

    @Override
    public void approve(ExpenseRequest request) {
        if(request.amount() < 30_000) {
            System.out.println("Manager approved");
        } else {
            forwardRequest(request);
        }
    }
}

class Director extends AbstractApprover {

    @Override
    public void approve(ExpenseRequest request) {
        if(request.amount() < 50_000) {
            System.out.println("Director approved");
        } else {
            forwardRequest(request);
        }
    }
}