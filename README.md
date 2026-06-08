# MediaLab Equipment Reservation System

A small console program in Java for the MediaLab assignment (PGO 13).
The program lets you check equipment, create reservations, return equipment
and print a report. It starts from the `Main` class.

## How to run

Compile and run from the `src` folder:

```
javac *.java
java Main
```

After start the program creates sample students and equipment, then shows a menu.

## Classes

- **Main** – starting point of the program. Creates the sample data and runs the
  menu in a loop, reading the user input from the console.
- **Student** – represents a student who borrows equipment. Stores id, full name,
  group and loyalty points. Can add new loyalty points.
- **Equipment** – abstract base class for all equipment. Stores id, name, base
  daily price and availability. Has an abstract method `calculateDailyPrice()`.
- **LaptopSet** – a concrete type of equipment (laptop). Adds RAM and docking
  station. Overrides `calculateDailyPrice()` with its own rules.
- **CameraKit** – a concrete type of equipment (camera). Adds number of lenses
  and a tripod. Also overrides `calculateDailyPrice()`.
- **Reservation** – connects a student, equipment, number of days and a status.
  Can calculate the total cost using a discount policy.
- **ReservationService** – contains the main logic: creating reservations,
  returning equipment, finding available equipment and printing reports.
- **LoyaltyDiscountPolicy** – calculates the discount for a reservation.
- **ReservationStatus** – an enum with the values ACTIVE, RETURNED, CANCELLED.

## Interfaces

- **Displayable** – has one method `getDisplayText()` that returns a readable
  text for the console. It is implemented by `Student`, `Equipment`
  (and through it `LaptopSet` and `CameraKit`) and `Reservation`.
- **DiscountPolicy** – has one method `applyDiscount()` that describes how the
  discount is calculated. It is implemented by `LoyaltyDiscountPolicy`.

## Where polymorphism works

The equipment is stored in one list of type `List<Equipment>`, but it can hold
both `LaptopSet` and `CameraKit` objects. When the program prints the equipment
and calls `calculateDailyPrice()`, Java automatically runs the correct version
of the method for each object – the laptop version for a laptop and the camera
version for a camera. So the same line of code gives different prices depending
on the real type of the object. This is polymorphism.

## Console output

/Users/vlad/Library/Java/JavaVirtualMachines/openjdk-25/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=63922 -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /Users/vlad/IdeaProjects/untitled4/out/production/untitled4 Main

1. List students
2. List equipment
3. Create reservation
4. Return equipment
5. Active reservations
6. Report
0. Exit
Choice: 3
Student id: S001
Equipment id: E001
Number of days: 5
Reservation R001 created.
Equipment: Lenovo ThinkPad Lab
Cost: 540.00 PLN
Status: ACTIVE

1. List students
2. List equipment
3. Create reservation
4. Return equipment
5. Active reservations
6. Report
0. Exit
Choice: 4
Reservation id: R001
Equipment returned. The student received 54 loyalty points.

1. List students
2. List equipment
3. Create reservation
4. Return equipment
5. Active reservations
6. Report
0. Exit
Choice: 
