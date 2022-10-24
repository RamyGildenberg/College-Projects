
public class BassGuitar extends StringInstrument {
	private boolean isFretless;
	
	
	public BassGuitar (int price, String brand, int numOfStrings, boolean isFretless) throws Exception
	{
		super(numOfStrings, price, brand);
		try
		{
		if(numOfStrings<4||numOfStrings>6) 
				throw new IllegalArgumentException("Bass guitar can only have between 4 to 6 strings!");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("Error! " + e.getMessage());
			System.exit(1);
		}


	}
	
	
	public boolean getIsFretless() {
		return isFretless;
	}



	@Override
	public boolean equals (Instrument instrument)
	{
		if (instrument instanceof BassGuitar) // checking if the instrument is a bass guitar first.
		{
			BassGuitar bassGuitar = (BassGuitar)instrument; // casting it into a bass guitar object.
			if (this.price == bassGuitar.price && this.brand.compareTo(bassGuitar.brand)==0 && 
				this.numOfStrings == bassGuitar.numOfStrings && 
				isFretless == bassGuitar.isFretless) // checking if all defining values are equal
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString()
	{
		String isItFretless = "";
		if (isFretless)
			isItFretless = "Yes";
		else isItFretless = "No";
		return super.toString() +  "|  Fretless: " + isItFretless;
	}
}
