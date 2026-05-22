package day4;
import java.util.*;

public class Main {
    public static void main(String[] args){
       List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", "IT", 55000));
        employees.add(new Employee("Bob", "Finance", 60000));
        employees.add(new Employee("Alice", "HR", 50000)); // duplicate name
        employees.add(new Employee("Ken", "IT", 60000));
        employees.add(new Employee("Maria", "HR", 52000));
        employees.add(new Employee("John", "Finance", 70000));
        employees.add(new Employee("Ken", "Finance", 65000)); // duplicate name
        employees.add(new Employee("Lara", "IT", 62000));
        employees.add(new Employee("Sam", "HR", 48000));
        employees.add(new Employee("Bob", "IT", 59000)); // duplicate name

        HashSet<String> tracker = new HashSet<>();
        Map<String, List<Employee>> map = new HashMap<>();
        List<Employee> employeesCleanedList = new ArrayList<>();
        Set<Double> uniqueSalaries = new TreeSet<>();

        for (Employee employee : employees){
            if (!tracker.contains(employee.getName())){
                tracker.add(employee.getName());
                employeesCleanedList.add(employee);
            }
        }   

        System.out.println("==== Unique Employees ====");
        for (Employee employee : employeesCleanedList){
            System.out.println(employee.getName() + " |  " + employee.getDepartment() + " | $" + employee.getSalary());
        }

        for (Employee e : employees){
            if (!map.containsKey(e.getDepartment())){
                map.put(e.getDepartment(), new ArrayList<>());
            }

            map.get(e.getDepartment()).add(e);
        }

        System.out.println("\n==== Employees By Department ====");
        for (String dept : map.keySet()){
            System.out.println(dept + ": ");
            
            for (Employee e : map.get(dept)){
                System.out.println(" - " + e.getName() + " | " + e.getDepartment() + " | $" + e.getSalary());
            }
        }

        System.out.println("\n==== Highest Paid Per Department ====");
        for (String dept : map.keySet()){
            Employee currentHighest = map.get(dept).get(0);

            for (Employee e : map.get(dept)){
                if (e.getSalary() > currentHighest.getSalary()){
                    currentHighest = e;
                }
            }
            System.out.println(dept + ": "  + currentHighest.getName() + " | " +  currentHighest.getDepartment() + " | $"  + currentHighest.getSalary());
        }

        Collections.sort(employees, new Comparator<Employee>() {
            public int compare (Employee e1, Employee e2){
                return Double.compare(e2.getSalary(), e1.getSalary());
            }
        });

        System.out.println("\n==== Employess Sorted by Salary (Desc) ====");
        for (Employee e : employees){
            System.out.println(e.getName() + " | " + e.getDepartment() + " | $" + e.getSalary());
        }


        for (Employee e : employees){
            uniqueSalaries.add(e.getSalary());
        }

        System.out.println("\n==== Unique Salaries ====");

        for (Double salaries : uniqueSalaries){
            System.out.println("$" + salaries);
        }

    }
}
