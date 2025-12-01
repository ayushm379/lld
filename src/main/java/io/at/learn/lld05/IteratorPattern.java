package io.at.learn.lld05;

import java.util.ArrayList;
import java.util.List;

public class IteratorPattern {
    public static void main(String[] args) {
        EmployeeCollection employees = new EmployeeDirectory();
        employees.addEmployee(new Employee(1, "A"));
        employees.addEmployee(new Employee(2, "B"));
        employees.addEmployee(new Employee(3, "C"));
        employees.addEmployee(new Employee(4, "D"));

        Iterator<Employee> iterator = employees.createIterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
    }
}

record Employee(int id, String name) {}

interface Iterator<T> {
    boolean hasNext();
    T next();
}

interface EmployeeCollection {
    Iterator<Employee> createIterator();
    void addEmployee(Employee employee);
}

class EmployeeDirectory implements EmployeeCollection {
    private final List<Employee> employeeList = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    @Override
    public Iterator<Employee> createIterator() {
        return new EmployeeIterator(employeeList);
    }
}

class EmployeeIterator implements Iterator<Employee> {

    private final List<Employee> employees;
    private int index = 0;

    public EmployeeIterator(List<Employee> employees) {
        this.employees = employees;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < employees.size();
    }

    @Override
    public Employee next() {
        return employees.get(index++);
    }
}