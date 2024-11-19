import java.util.ArrayList;
import java.util.List;

public class HostelSystem {
    public static void main(String[] args) {
        List<Student> students = createStudents();
        
        //This shows that i am using layerd arcitectre
        EligibilityFilter eligibilityFilter = new EligibilityFilter();
        PaymentFilter paymentFilter = new PaymentFilter();
        ServiceFilter serviceFilter = new ServiceFilter();

        List<Student> eligibleStudents = eligibilityFilter.process(students);
        displayStudents("Eligible Students", eligibleStudents, "eligibility");

        List<Student> paidStudents = paymentFilter.process(eligibleStudents);
        displayStudents("Paid Students", paidStudents, "payment");

        List<Student> servicedStudents = serviceFilter.process(paidStudents);
        displayStudents("Students with Services Assigned", servicedStudents, "service");
    }

    private static List<Student> createStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Ali", 120));
        students.add(new Student("Sara", 50));
        students.add(new Student("Ahmed", 200));
        return students;
    }

    private static void displayStudents(String title, List<Student> students, String filterType) {
        System.out.println("\n" + title + ":");
        if (students.isEmpty()) {
            System.out.println("  None");
        } else {
            
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                if (filterType.equals("eligibility")) {
                    System.out.println("  Name: " + student.name + ", Distance: " + student.distance + " km");
                } else if (filterType.equals("payment")) {
                    System.out.println("  Name: " + student.name + ", Distance: " + student.distance + " km, Paid: " + student.hasPaid);
                } else if (filterType.equals("service")) {
                    System.out.println("  Name: " + student.name + ", Distance: " + student.distance + " km, Paid: " + student.hasPaid + ", Service: " + student.serviceType);
                }
            }
        }
    }
}

class Student {
    String name;
    int distance;
    boolean hasPaid = false;
    String serviceType = "None";

    public Student(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }
}

class EligibilityFilter {
    public List<Student> process(List<Student> students) {
        List<Student> eligible = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.distance > 100) {
                eligible.add(student);
            }
        }
        return eligible;
    }
}

class PaymentFilter {
    public List<Student> process(List<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            student.hasPaid = true;
        }
        return students;
    }
}

class ServiceFilter {
    public List<Student> process(List<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.hasPaid) {
                student.serviceType = student.distance > 150 ? "Luxury" : "Simple";
            }
        }
        return students;
    }
}
