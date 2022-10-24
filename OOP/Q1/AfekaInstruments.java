import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class AfekaInstruments {
	
	
	public void addAllInstruments (ArrayList<Instrument> sourceArray, ArrayList<Instrument> targetArray)
	{
		for (int i = 0; i < sourceArray.size(); i++)
		{
			targetArray.add(sourceArray.get(i));
		}
	}
	
	public Instrument getMostExpensiveInstrument (ArrayList<Instrument> instruments)
	{
		float currentMaxPrice = -9999999;
		int currentMaxPriceIndex = 0;
		for (int i = 0; i < instruments.size(); i++)
		{
			Instrument currentInstrument = instruments.get(i);
			if (currentInstrument.getPrice() > currentMaxPrice)
			{
				currentMaxPrice = currentInstrument.getPrice();
				currentMaxPriceIndex = i;
			}
		}
		return instruments.get(currentMaxPriceIndex);
	}
	
	public int getNumOfDifferentElements (ArrayList<Instrument> instruments)
	{
		ArrayList<Instrument> uniqueInstruments = new ArrayList<Instrument>();
		uniqueInstruments.add(instruments.get(0));
		for (int i = 1; i < instruments.size(); i++)
		{
			boolean foundSameInstrument = false;
			Instrument currentInstrument = instruments.get(i);
			int j = 0;
			while (j < uniqueInstruments.size() && !foundSameInstrument)
			{
				if (currentInstrument.equals(uniqueInstruments.get(j)))
					foundSameInstrument = true;
				j++;
			}
			if (!foundSameInstrument)
				uniqueInstruments.add(currentInstrument);
		}
		return uniqueInstruments.size();
		
	}

	
	
	public void printAllInstruments(ArrayList<Instrument> instruments) {
		for (int i = 0; i < instruments.size(); i++)
		{
			System.out.println(instruments.get(i).toString());
		}
	}
	
	
	
	
	
	
}
	
	
	

