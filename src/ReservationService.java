import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final List<Student> students = new ArrayList<>();
    private final List<Equipment> equipmentList = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private final DiscountPolicy discountPolicy;
    private int reservationCounter = 1;

    public ReservationService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public void addStudent(Student s) { students.add(s); }
    public void addEquipment(Equipment e) { equipmentList.add(e); }

    public List<Student> getStudents() { return students; }
    public List<Equipment> getEquipmentList() { return equipmentList; }

    public Reservation createReservation(String studentId, String equipmentId, int days) {
        Student student = findStudent(studentId);
        if (student == null) {
            System.out.println("Error: student " + studentId + " does not exist.");
            return null;
        }
        Equipment equipment = findEquipment(equipmentId);
        if (equipment == null) {
            System.out.println("Error: equipment " + equipmentId + " does not exist.");
            return null;
        }
        if (!equipment.isAvailable()) {
            System.out.println("Error: equipment " + equipmentId + " is not available.");
            return null;
        }
        if (days < 1 || days > 14) {
            System.out.println("Error: number of days must be between 1 and 14.");
            return null;
        }
        String id = String.format("R%03d", reservationCounter++);
        Reservation r = new Reservation(id, student, equipment, days);
        equipment.setAvailable(false);
        reservations.add(r);
        System.out.printf("Reservation %s created.%n", id);
        System.out.printf("Equipment: %s%n", equipment.getName());
        System.out.printf("Cost: %.2f PLN%n", r.calculateTotalCost(discountPolicy));
        System.out.printf("Status: %s%n", r.getStatus());
        return r;
    }

    public void returnEquipment(String reservationId) {
        Reservation r = findReservation(reservationId);
        if (r == null) {
            System.out.println("Error: reservation " + reservationId + " does not exist.");
            return;
        }
        if (r.getStatus() != ReservationStatus.ACTIVE) {
            System.out.println("Error: reservation " + reservationId + " is not active.");
            return;
        }
        r.setStatus(ReservationStatus.RETURNED);
        r.getEquipment().setAvailable(true);
        double cost = r.calculateTotalCost(discountPolicy);
        int points = (int) (cost / 10);
        r.getStudent().addLoyaltyPoints(points);
        System.out.printf("Equipment returned. The student received %d loyalty points.%n", points);
    }

    public List<Equipment> findAvailableEquipment() {
        List<Equipment> result = new ArrayList<>();
        for (Equipment e : equipmentList) {
            if (e.isAvailable()) result.add(e);
        }
        return result;
    }

    public void printActiveReservations() {
        System.out.println("--- Active reservations ---");
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.ACTIVE) {
                System.out.println(r.getDisplayText());
            }
        }
    }

    public void printReport() {
        System.out.println("--- Report: completed reservations ---");
        double revenue = 0;
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.RETURNED) {
                System.out.println(r.getDisplayText());
                revenue += r.calculateTotalCost(discountPolicy);
            }
        }
        System.out.printf("Total revenue: %.2f PLN%n", revenue);

        Student top = null;
        for (Student s : students) {
            if (top == null || s.getLoyaltyPoints() > top.getLoyaltyPoints()) {
                top = s;
            }
        }
        if (top != null) {
            System.out.printf("Student with most loyalty points: %s (%d)%n",
                    top.getFullName(), top.getLoyaltyPoints());
        }
    }

    private Student findStudent(String id) {
        for (Student s : students) if (s.getId().equals(id)) return s;
        return null;
    }

    private Equipment findEquipment(String id) {
        for (Equipment e : equipmentList) if (e.getId().equals(id)) return e;
        return null;
    }

    private Reservation findReservation(String id) {
        for (Reservation r : reservations) if (r.getId().equals(id)) return r;
        return null;
    }
}