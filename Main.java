import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

// interface means blueprint of this application
interface AppFunctions
{
    // inside interface, cannot define methods
    // only can be declare
    void mainMenu();
    void addPassenger();
    void availableSeats();
    void unAvailableSeats();
    void deletePassenger();
    int findPassengerSeat();
    void passengerListAlphabetically();
    void savePassengers();
    void loadPassengers();
}

class Passenger
{
    // in this class used oop concept -> encapsulation
    // encapsulation means data binding with methods (basic idea)
    private int id;
    private String firstName;
    private String lastName;
    private int seatNo;

    // constructor of this class
    Passenger(int id, String firstName, String lastName, int seatNo)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.seatNo = seatNo;
    }

    // getter methods
    public int getId()
    {
        return id;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public int getSeatNo()
    {
        return seatNo;
    }

    public void display(){
        System.out.printf("\nId\t\t: %02d\n", getId());
        System.out.println("Name\t: " + getFirstName() + " " + getLastName());
        System.out.printf("Seat\t: %02d\n", getSeatNo());
    }
}

class App implements AppFunctions
{
    private static ArrayList<Passenger> passengerList = new ArrayList<Passenger>();

    App()
    {
        loadPassengers();
        mainMenu();
    }

    @Override
    public void mainMenu()
    {
        Scanner sc = new Scanner(System.in);

        menu:
        while (true) {

            System.out.println("\nMain menu:");
            System.out.println("Enter \"A\" to add passenger");
            System.out.println("Enter \"E\" to view available seats");
            System.out.println("Enter \"V\" to view unavailable seats");
            System.out.println("Enter \"F\" to find passenger");
            System.out.println("Enter \"D\" to delete passenger");
            System.out.println("Enter \"O\" to view passenger list alphabetically");
            System.out.println("Enter \"S\" to save passengers");
            System.out.println("Enter \"L\" to load passengers");
            System.out.println("Enter \"Q\" to quit");

            System.out.print("\nEnter Your Option: ");
            String option = sc.next().toUpperCase();
            switch (option) {
                case "A":
                    System.out.println("\n-Add Passenger-");
                    addPassenger();
                    break;
                case "E":
                    System.out.println("\n-Available Seats-");
                    availableSeats();
                    break;
                case "V":
                    System.out.println("\n-Unavailable Seats-");
                    unAvailableSeats();
                    break;
                case "F":
                    System.out.println("\n-Find Passenger-");
                    findPassengerSeat();
                    break;
                case "D":
                    System.out.println("\n-Delete Passenger-");
                    deletePassenger();
                    break;
                case "O":
                    System.out.println("\n-View Passenger List Alphabetically-");
                    passengerListAlphabetically();
                    break;
                case "S":
                    savePassengers();
                    break;
                case "L":
                    loadPassengers();
                    break;
                case "Q":
                    System.out.println("\nWe hope this program was helpful for you");
                    System.out.println("Thanks for using Nanjing Express Booking program");
                    System.out.println("\nQuited Nanjing Express Booking Program");
                    break menu;
                default:
                    System.out.println("Invalid Option !!!\nPlease re-enter your option");
            }
        }
    }

    public boolean idValidation(int id){
        boolean validation = true;
        if (id == 0){
            System.out.println("Invalid Input !!!");
            System.out.print("\nRe-enter travellers id: ");
            validation = false;
        }
        return validation;
    }

    public boolean nameValidation(String name)
    {
        boolean validation = true;
        char[] charArray = name.toCharArray();
        for (char character : charArray) {
            if (!Character.isLetter(character)){
                validation = false;
            }
        }
        return validation;
    }

    @Override
    public void addPassenger()
    {
        Scanner sc = new Scanner(System.in);

        int id;
        String firstName;
        String lastName;
        int seatNo;

        // travellers id
        System.out.print("\nEnter travellers id: ");
        while (true){
            try {
                id = Integer.parseInt(sc.nextLine());
                if (idValidation(id)){
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Invalid Input !!!");
                System.out.print("\nRe-enter travellers id: ");
            }
        }
        for (Passenger passenger :passengerList){
            if (id == passenger.getId()){
                System.out.println("Already reserved a seat using this id");
                mainMenu();
            }
        }

        // travelers first name
        System.out.print("\nEnter travellers first name: ");
        while (true){
            firstName = sc.nextLine();
            if (nameValidation(firstName)){
                break;
            }
            else {
                System.out.println("Invalid Input !!!");
                System.out.print("\nRe-enter travellers first name: ");
            }
        }

        // travelers last name
        System.out.print("\nEnter travellers last name: ");
        while (true){
            lastName = sc.nextLine();
            if (nameValidation(lastName)){
                break;
            }
            else {
                System.out.println("Invalid Input !!!");
                System.out.print("\nRe-enter travellers last name: ");
            }

        }

        // available seat list
        availableSeats();

        // travellers seat number
        System.out.print("\nEnter seat number: ");
        while (true){
            try {
                seatNo = Integer.parseInt(sc.nextLine());
                if (seatNo >= 1 && seatNo <=30 ){
                    boolean availability = true;
                    for (Passenger passenger : passengerList){
                        if (seatNo == passenger.getSeatNo()){
                            availability = false;
                        }
                    }
                    if (availability){
                        break;
                    }
                    else {
                        System.out.println("Already taken !!!");
                        availableSeats();
                        System.out.print("\nRe-enter seat number: ");
                    }
                }
                else{
                    System.out.println("Invalid Input !!!");
                    System.out.print("\nRe-enter seat number: ");
                }
            }
            catch (Exception e) {
                System.out.println("Invalid Input !!!");
                System.out.print("\nRe-enter seat number: ");
            }
        }

        Passenger newPassenger = new Passenger(id, firstName, lastName, seatNo);
        newPassenger.display();
        passengerList.add(newPassenger);
    }

    @Override
    public void availableSeats()
    {
        ArrayList<String> availableSeatList = new ArrayList<String>();
        for (int i=1; i<=30; i++){
            boolean availability = true;
            for (Passenger passenger : passengerList){
                if (i == passenger.getSeatNo()){
                    availability = false;
                }
            }
            if (availability){
                availableSeatList.add(String.format("%02d", i));
            }
            else {
                availableSeatList.add("--");
            }
        }

        System.out.println("\nAvailable Seats:");

        String display ="";
        for (int j=0; j<availableSeatList.size(); j++){
            if ((j+1)%3 == 0){
                display = display + availableSeatList.get(j) + "\t\t";
            }
            else {
                display = display + availableSeatList.get(j) + " | ";
            }
            if ((j+1)%6 == 0){
                System.out.println(display);
                display ="";
            }
        }
    }

    @Override
    public void unAvailableSeats()
    {
        ArrayList<String>   unavailableSeatList = new ArrayList<String>();
        for (int i=1; i<=30; i++){
            boolean availability = true;
            for (Passenger passenger : passengerList){
                if (i == passenger.getSeatNo()){
                    availability = false;
                }
            }
            if (availability){
                unavailableSeatList.add("--");
            }
            else {
                unavailableSeatList.add(String.format("%02d", i));
            }
        }

        System.out.println("\nUnavailable Seats:");

        String display ="";
        for (int j=0; j<unavailableSeatList.size(); j++){
            if ((j+1)%3 == 0){
                display = display + unavailableSeatList.get(j) + "\t\t";
            }
            else {
                display = display + unavailableSeatList.get(j) + " | ";
            }
            if ((j+1)%6 == 0){
                System.out.println(display);
                display ="";
            }
        }
    }

    @Override
    public int findPassengerSeat()
    {
        Scanner sc = new Scanner(System.in);

        int id;

        boolean found = false;
        int index = 0;

        // travellers id
        System.out.print("\nEnter travellers id: ");
        while (true){
            try {
                id = Integer.parseInt(sc.nextLine());
                if (idValidation(id)){
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Invalid Input !!!");
                System.out.print("\nRe-enter travellers id: ");
            }
        }
        for (Passenger passenger :passengerList){
            if (id == passenger.getId()){
                index = passengerList.indexOf(passenger);
                found = true;
            }
        }
        if (found){
            passengerList.get(index).display();
            return index;
        }
        else {
            System.out.println("Passenger not found");
            return 404;
        }
    }

    @Override
    public void deletePassenger()
    {
        int index = findPassengerSeat();

        if (index != 404){
            passengerList.remove(index);
            System.out.println("Passenger deleted from passenger list");
        }
    }

    @Override
    public void passengerListAlphabetically()
    {

        for (int i=0; i<passengerList.size()-1; i++){
            String passenger1 = passengerList.get(i).getFirstName() + "_" + passengerList.get(i).getLastName();
            for (int j=i+1; j<passengerList.size(); j++) {
                String passenger2 = passengerList.get(j).getFirstName() + "_" + passengerList.get(j).getLastName();
                if (passenger2.compareTo(passenger1)<0){
                    Passenger passenger = passengerList.get(j);
                    passengerList.remove(passenger);
                    passengerList.add(i, passenger);
                }
            }
        }

        System.out.println();
        for (Passenger passenger : passengerList) {
            System.out.println(passenger.getFirstName() + " " + passenger.getLastName());
        }
    }

    @Override
    public void savePassengers()
    {
        Formatter saveFile;
        try{
            saveFile = new Formatter("Nanjing-Express.txt");
            saveFile.format("-Nanjing Express Passenger Details-\n\n");
            for (Passenger passenger : passengerList){
                saveFile.format("Id\t: %02d \nName\t: %s %s \nSeat\t: %02d\n",passenger.getId(), passenger.getFirstName(), passenger.getLastName(), passenger.getSeatNo());
            }
            saveFile.close();
        }
        catch(Exception e){
            System.out.println("\nSaving Error !!!");
        }
    }

    @Override
    public void loadPassengers()
    {
        passengerList.clear();

        Scanner loadFile;
        try{
            loadFile = new Scanner(new File("Nanjing-Express.txt"));
            ArrayList<String> saveData = new ArrayList<>();
            while(loadFile.hasNext()){
                String data = loadFile.next();
                saveData.add(data);
            }

            for(int i=4 ; i<saveData.size() ; i+=10) {
                int id = Integer.parseInt(saveData.get(i+2));
                String firstName = saveData.get(i+5);
                String lastName = saveData.get(i+6);
                int seatNo = Integer.parseInt(saveData.get(i+9));
                Passenger passenger = new Passenger(id, firstName, lastName, seatNo);
                passengerList.add(passenger);
            }
            loadFile.close();
        }
        catch (FileNotFoundException e){
            System.out.println();
        }
        catch(Exception e){
            System.out.println("\nLoading Error !!!");
        }
    }
}

public class Main
{
    public static void main(String args[])
    {
        System.out.println("\nWelcome to Train Booking Program");
        System.out.println("\nThis program is design to book seats in");
        System.out.println("A/C compartment of the Nanjing Express");

        App object = new App();
    }
}
