import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InstrumentsFileReader  {

	
	private ArrayList<Instrument> instruments = new ArrayList<Instrument>();
	private String path;
	private Scanner scanner = new Scanner(System.in); 
	
	public ArrayList<Instrument> getInstruments()
	{
		return instruments;
	}
	public ArrayList<Instrument> readTextFileIntoModels() throws Exception 
	{
		getPathFromUser();
		File file = new File(path);
	    try {
	        Scanner sc = new Scanner(file);
	        if (sc.hasNextLine()) { // Reading all string guitars.
	            String currentLine = sc.nextLine();
	            int numOfGuitars = Integer.parseInt(currentLine);
	            for (int i = 0; i < numOfGuitars; i++)
	            {
	            	float price = Float.parseFloat(sc.nextLine());
	            	String brand = sc.nextLine();
	            	int numOfStrings = Integer.parseInt(sc.nextLine());
	            	String type = sc.nextLine();
	            	Guitar currentGuitar = new Guitar (type, price, brand, numOfStrings);
	            	instruments.add(currentGuitar);
	            }
	        }
	        if (sc.hasNextLine()) { // Reading all bass guitars.
	            String currentLine = sc.nextLine();
	            int numOfBassGuitars = Integer.parseInt(currentLine);
	            for (int i = 0; i < numOfBassGuitars; i++)
	            {
	            	int price = Integer.parseInt(sc.nextLine());
	            	String brand = sc.nextLine();
	            	int numOfStrings = Integer.parseInt(sc.nextLine());
	            	String isFrettedString = sc.nextLine();
	            	try
	            	{
	            		if(!(isFrettedString.equals("True")||isFrettedString.equals("False")))
	            			throw new IllegalArgumentException("invalid input for fret status on the bass guitar");
	            	}
	            	catch (IllegalArgumentException e)
	        		{
	        			System.out.println("Error! " + e.getMessage());
	        			System.exit(1);
	        		}
	            	boolean isFretted;
	            	if (isFrettedString.equals("True"))
	            		isFretted = true;
	            	else
	            		isFretted = false;
	            	
	            	BassGuitar currentBassGuitar = new BassGuitar(price, brand, numOfStrings, isFretted);
	            	instruments.add(currentBassGuitar);
	            }
	        }
	        if (sc.hasNextLine()) { // Reading all flutes.
	            String currentLine = sc.nextLine();
	            int numOfFlutes = Integer.parseInt(currentLine);
	            for (int i = 0; i < numOfFlutes; i++)
	            {
	            	int price = Integer.parseInt(sc.nextLine());
	            	String brand = sc.nextLine();
	            	String madeOf = sc.nextLine();
	            	String type = sc.nextLine();
	            	Flute currentFlute = new Flute(madeOf, price, brand, type);
	            	instruments.add(currentFlute);
	            }
	        }
	        if (sc.hasNextLine()) { // Reading all Saxophones.
	            String currentLine = sc.nextLine();
	            int numOfSaxophones = Integer.parseInt(currentLine);
	            for (int i = 0; i < numOfSaxophones; i++)
	            {
	            	int price = Integer.parseInt(sc.nextLine());
	            	String brand = sc.nextLine();
	            	String madeOf = sc.nextLine(); // only needed to catch for exception because saxophones are by default made of metal
	            	try
	            	{
	            		if(!(madeOf.trim().equals("Metal")))
	            			throw new IllegalArgumentException("Saxophones cant be made from any material other than Metal");
	            			
	            	}
	            	catch (IllegalArgumentException e)
	            	{
	            		System.out.println("Error! " + e.getMessage());
	            		System.exit(1);
	            	}
	            	Saxophone currentSaxophone = new Saxophone(price, brand);
	            	instruments.add(currentSaxophone);
	            }
	        }
	        System.out.println("Instruments loaded from file successfully!");
	        if(instruments.size()==0)
	        {
	        	System.out.println("There are no instruments in the store currently");
	        	System.exit(1);
	        }
	        sc.close();
	        scanner.close();
	    } 
	    catch (FileNotFoundException e) {
	    	System.out.println("File error! Please try again!");
	    }
	    return instruments;
	}
	
	private void getPathFromUser()
	{
		System.out.println("Please enter the file name or file path");
		path = scanner.nextLine();
	}
	
	
}
