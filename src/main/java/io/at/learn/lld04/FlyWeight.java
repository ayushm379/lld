package io.at.learn.lld04;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

public class FlyWeight {
    public static void main(String[] args) {
        DepartmentFlyweight itBangalorePhase1 = DepartmentFactory.getDepartment("IT", "Bangalore", "Phase 1");
        DepartmentFlyweight itBangalorePhase2 = DepartmentFactory.getDepartment("IT", "Bangalore", "Phase 2");
        DepartmentFlyweight hrNoidaSector144 = DepartmentFactory.getDepartment("IT", "Noida", "Sector 144");

        System.out.println(new Employee("Abc", itBangalorePhase1).toString());
        System.out.println(new Employee("Def", itBangalorePhase1).toString());
        System.out.println(new Employee("Ghi", itBangalorePhase2).toString());
        System.out.println(new Employee("Jkl", hrNoidaSector144).toString());
        System.out.println(new Employee("Mno", hrNoidaSector144).toString());
    }
}


@RequiredArgsConstructor
@ToString
class DepartmentFlyweight {
    private final String name;
    private final String location;
    private final String campus;
}

class DepartmentFactory {
    private static final Map<String, DepartmentFlyweight> cache = new HashMap<>();

    private static String getKey(String name, String location, String campus) {
        return String.format("%s##%s##%s", name, location, campus);
    }

    public static DepartmentFlyweight getDepartment(String name, String location, String campus) {
        return cache.computeIfAbsent(getKey(name, location, campus),
                k -> new DepartmentFlyweight(name, location, campus));
    }

}

@RequiredArgsConstructor
class Employee {
    private final String name;
    private final DepartmentFlyweight departmentFlyweight;

    @Override
    public String toString() {
        return String.format("[Employee: %s, %s, %d]", this.name, this.departmentFlyweight.toString(), this.departmentFlyweight.hashCode());
    }
}