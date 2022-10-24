import java.util.ArrayList;
import java.util.Scanner;

public class Run {
	public static void main(String[] args) throws Exception
	{
		
		AfekaInstruments store = new AfekaInstruments();
		InstrumentsFileReader reader = new InstrumentsFileReader();
		while (reader.getInstruments().size() == 0)
			reader.readTextFileIntoModels();
		ArrayList<Instrument> instruments = reader.getInstruments();
		store.printAllInstruments(instruments);
		if(store.getNumOfDifferentElements(instruments)==0)
			System.out.println("There are no instruments in the store currently");
		else
			System.out.println("different instruments: " + store.getNumOfDifferentElements(instruments));
			System.out.println("most expensive instrument is: \n" + store.getMostExpensiveInstrument(instruments));
		
	}

}
