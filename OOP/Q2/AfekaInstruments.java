import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class AfekaInstruments {

    public static void main(String[] args) {
        ArrayList<MusicalInstrument> allInstruments = new ArrayList<MusicalInstrument>();
        File file = getInstrumentsFileFromUser();

        loadInstrumentsFromFile(file, allInstruments);

        if(allInstruments.size() == 0) {
            System.out.println("There are no instruments in the store currently");
            return;
        }

        printInstruments(allInstruments);

        int different = getNumOfDifferentElements(allInstruments);

        System.out.println("\n\nDifferent Instruments: " + different);

        MusicalInstrument mostExpensive = getMostExpensiveInstrument(allInstruments);

        System.out.println("\n\nMost Expensive Instrument:\n" + mostExpensive);

        startInventoryMenu(allInstruments);
    }

    public static void startInventoryMenu (ArrayList<MusicalInstrument> instruments)
    {
        AfekaInventory<MusicalInstrument> inventory = new AfekaInventory<MusicalInstrument>();
        ArrayList<MusicalInstrument> inventoryInstruments = new ArrayList<MusicalInstrument>();
        String brandNameSearched;
        Double priceSearched;
        boolean shouldExit = false;
        while (!shouldExit) {
            System.out.println("-------------------------------------------------------------------------\n"+
                    "AFEKA MUSICAL INSTRUMENT INVENTORY MENU\n"+
                    "-------------------------------------------------------------------------\n");
            System.out.println("What would you like to do?");
            System.out.println("1. Copy All String Instruments To Inventory");
            System.out.println("2. Copy All Wind Instruments To Inventory");
            System.out.println("3. Sort Instruments By Brand And Price");
            System.out.println("4. Search Instrument By Brand And Price");
            System.out.println("5. Delete Instrument");
            System.out.println("6. Delete all Instruments");
            System.out.println("7. Print Inventory Instruments");
            System.out.println("Choose your option or any other key to EXIT");
            Scanner consoleScanner = new Scanner(System.in);
            String optionSelected = consoleScanner.nextLine();
            switch (optionSelected) {
                case "1":
                    System.out.println("Option selected: 1");
                    inventory.addAllStringInstruments(instruments, inventory.getInventorysInstrumentsList());
                    System.out.println("All string instruments added to the inventory.\n");
                    break;
                case "2":
                    System.out.println("Option selected: 2");
                    inventory.addAllWindInstruments(instruments, inventory.getInventorysInstrumentsList());
                    System.out.println("All wind instruments added to the inventory.\n");
                    break;
                case "3":
                    System.out.println("Option selected: 3");
                    inventory.sortByBrandAndPrice(inventory.getInventorysInstrumentsList());
                    System.out.println("Instruments in inventory has been sorted by brand and price\n");
                    break;
                case "4":
                    System.out.println("Option selected: 4");
                    brandNameSearched = getBrandFromUser();
                    priceSearched = getPriceFromUser();
                    int indexSearched = inventory.binarySearchByBrandAndPrice(inventory.getInventorysInstrumentsList(), brandNameSearched, priceSearched);
                    if (indexSearched != -1)
                        System.out.println(inventory.getInventorysInstrumentsList().get(indexSearched).toString());
                    else
                        System.out.println("Instrument does not exist in inventory.");
                    System.out.print("Search instrument completed\n");
                    break;
                case "5":
                    System.out.println("Option selected: 5\n"+"DELETE AN INSTRUMENTS:\n");
                    brandNameSearched = getBrandFromUser();
                    priceSearched = getPriceFromUser();
                    int instrumentIndexToDelete = inventory.binarySearchByBrandAndPrice(inventory.getInventorysInstrumentsList(), brandNameSearched, priceSearched);
                    System.out.println(inventory.getInventorysInstrumentsList().get(instrumentIndexToDelete).toString());
                    System.out.println("Are you sure ? (Y/N)\n");
                    Scanner scanner = new Scanner( System.in );
                    String input=scanner.nextLine();
                    if(input.equalsIgnoreCase("Y")) {
                        if (instrumentIndexToDelete != -1) {
                            MusicalInstrument instrumentToRemove = inventory.getInventorysInstrumentsList().get(instrumentIndexToDelete);
                            inventory.removeInstrument(inventory.getInventorysInstrumentsList(), instrumentToRemove);
                            System.out.print("Instruments removal completed\n");
                            scanner.close();
                            break;
                        } else {
                            System.out.println("Instrument does not exist in inventory.\n");
                            break;
                        }
                    }
                    else
                        break;
                case "6":
                    System.out.println("Option selected: 6\n"+"DELETE ALL INSTRUMENTS:\n");
                    System.out.println("Are you sure ? (Y/N)\n");
                    scanner = new Scanner(System.in);
                    input = scanner.nextLine();
                    if(input.equalsIgnoreCase("Y")) {
                        inventory.removeAll(inventory.getInventorysInstrumentsList());
                        System.out.println("All instruments were removed\n");
                        break;
                    }
                    else
                        break;
                case "7":
                    System.out.println("Option selected: 7");
                    System.out.println(inventory.toString());
                    break;
                default:
                    System.out.println("Finished!\n");
                    shouldExit = true;
                    break;
            }
        }


    }

    public static String getBrandFromUser()
    {
        Scanner consoleScanner = new Scanner(System.in);
        System.out.println("Please type the instrument's brand name:");
        String brandName=consoleScanner.nextLine();
        brandName=brandName.trim().substring(0,1).toUpperCase()+brandName.substring(1).toLowerCase();
        return consoleScanner.nextLine();
    }

    public static Double getPriceFromUser()
    {
        Scanner consoleScanner = new Scanner(System.in);
        String decimalPatternDouble = "([0-9]*)\\.([0-9]*)";
        String decimalPatternInteger = "([0-9]*)";
        boolean enteredValidPrice = false;
        while (!enteredValidPrice) {
            System.out.println("Please type the instrument's price:");
            String price = consoleScanner.nextLine();
            boolean matchingInteger = Pattern.matches(decimalPatternInteger, price);
            boolean matchingDouble = Pattern.matches(decimalPatternDouble, price);
            if (matchingInteger || matchingDouble) {
                if (Double.parseDouble(price) >= 0)
                    return Double.parseDouble(price);
            }
            else System.out.println("Invalid input, please enter a valid price");
        }
        return -1.0;
    }


    public static File getInstrumentsFileFromUser(){
        boolean stopLoop = true;
        File file;
        Scanner consoleScanner = new Scanner(System.in);

        do {
            System.out.println("Please enter instruments file name / path:");
            String filepath = consoleScanner.nextLine();
            file = new File("instruments1b.txt");
            stopLoop = file.exists() && file.canRead();

            if(!stopLoop)
                System.out.println("\nFile Error! Please try again\n\n");
        }while (!stopLoop);

        return file;
    }

    public static void loadInstrumentsFromFile(File file, ArrayList<MusicalInstrument> allInstruments){
        Scanner scanner = null;

        try {

            scanner = new Scanner(file);

            addAllInstruments(allInstruments ,loadGuitars(scanner));

            addAllInstruments(allInstruments ,loadBassGuitars(scanner));

            addAllInstruments(allInstruments ,loadFlutes(scanner));

            addAllInstruments(allInstruments ,loadSaxophones(scanner));

        }catch (InputMismatchException | IllegalArgumentException ex){
            System.err.println("\n"+ ex.getMessage());
            System.exit(1);
        }catch (FileNotFoundException ex){
            System.err.println("\nFile Error! File was not found");
            System.exit(2);
        } finally {
            scanner.close();
        }
        System.out.println("\nInstruments loaded from file successfully!\n");

    }

    public static ArrayList<Guitar> loadGuitars(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList<Guitar> guitars = new ArrayList<Guitar>(numOfInstruments);
        for(int i = 0; i < numOfInstruments ; i++) {
            guitars.add(new Guitar(scanner));
        }
        return guitars;
    }

    public static ArrayList<Bass> loadBassGuitars(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList<Bass> bassGuitars = new ArrayList<Bass>(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            bassGuitars.add(new Bass(scanner));

        return bassGuitars;
    }

    public static ArrayList<Flute> loadFlutes(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList<Flute> flutes = new ArrayList<Flute>(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            flutes.add(new Flute(scanner));
        return flutes;
    }

    public static ArrayList<Saxophone> loadSaxophones(Scanner scanner){
        int numOfInstruments = scanner.nextInt();
        ArrayList<Saxophone> saxophones = new ArrayList<Saxophone>(numOfInstruments);

        for(int i = 0; i < numOfInstruments ; i++)
            saxophones.add(new Saxophone(scanner));

        return saxophones;
    }

    public static void addAllInstruments(ArrayList<MusicalInstrument> instruments, ArrayList<? extends MusicalInstrument> moreInstruments){
        for(int i = 0 ; i < moreInstruments.size() ; i++){
            instruments.add(moreInstruments.get(i));
        }
    }

    public static void printInstruments(ArrayList<MusicalInstrument> instruments){
        for(int i = 0 ; i < instruments.size() ; i++)
            System.out.println(instruments.get(i));
    }



    public static int getNumOfDifferentElements(ArrayList<MusicalInstrument> instruments){
        int numOfDifferentInstruments;
        ArrayList<MusicalInstrument> differentInstruments = new ArrayList<MusicalInstrument>();
        System.out.println();

        for(int i = 0 ; i < instruments.size() ; i++){
            if(!differentInstruments.contains((instruments.get(i)))){
                differentInstruments.add(instruments.get(i));
            }
        }

        if(differentInstruments.size() == 1)
            numOfDifferentInstruments = 0;

        else
            numOfDifferentInstruments = differentInstruments.size();


        return numOfDifferentInstruments;
    }

    public static MusicalInstrument getMostExpensiveInstrument(ArrayList<MusicalInstrument> instruments){
        Number maxPrice = 0;
        MusicalInstrument mostExpensive = instruments.get(0);

        for(int i = 0 ; i < instruments.size() ; i++){
            MusicalInstrument temp = instruments.get(i);

            if(temp.getPrice().doubleValue() > maxPrice.doubleValue()){
                maxPrice = temp.getPrice();
                mostExpensive = temp;
            }
        }

        return mostExpensive;
    }

}
