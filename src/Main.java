import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReservationService service = new ReservationService(new LoyaltyDiscountPolicy());

        service.addStudent(new Student("S001", "Anna Kowalska", "12c", 120));
        service.addStudent(new Student("S002", "Marek Nowak", "12c", 40));
        service.addStudent(new Student("S003", "Julia Zielinska", "13a", 0));

        service.addEquipment(new LaptopSet("E001", "Lenovo ThinkPad Lab", 80, 32, true));
        service.addEquipment(new LaptopSet("E002", "Dell XPS Demo", 100, 16, false));
        service.addEquipment(new CameraKit("E003", "Sony Content Kit", 90, 3, true));
        service.addEquipment(new CameraKit("E004", "Canon Interview Kit", 70, 1, true));

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n1. List students");
            System.out.println("2. List equipment");
            System.out.println("3. Create reservation");
            System.out.println("4. Return equipment");
            System.out.println("5. Active reservations");
            System.out.println("6. Report");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    for (Student s : service.getStudents())
                        System.out.println(s.getDisplayText());
                    break;
                case "2":
                    for (Equipment e : service.getEquipmentList())
                        System.out.println(e.getDisplayText());
                    break;
                case "3":
                    System.out.print("Student id: ");
                    String sid = sc.nextLine().trim();
                    System.out.print("Equipment id: ");
                    String eid = sc.nextLine().trim();
                    System.out.print("Number of days: ");
                    int days;
                    try {
                        days = Integer.parseInt(sc.nextLine().trim());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: invalid number of days.");
                        break;
                    }
                    service.createReservation(sid, eid, days);
                    break;
                case "4":
                    System.out.print("Reservation id: ");
                    service.returnEquipment(sc.nextLine().trim());
                    break;
                case "5":
                    service.printActiveReservations();
                    break;
                case "6":
                    service.printReport();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        sc.close();
        System.out.println("Program finished.");
    }
}