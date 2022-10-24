
public class Guitar extends StringInstrument {
	private String type;
	final int DEFAULT_NUM_OF_STRING=6,MAX_NUM_OF_STRINGS=8;
	
	public Guitar (String type, float price, String brand, int numOfStrings) throws Exception
	{
		super(numOfStrings, price, brand);
		this.type = type;
		try {
			if(!(type.equals("Acoustic")||type.equals("Electric")||type.equals("Classical")))
				throw new IllegalArgumentException("Invalid guitar type!");
			if(price<0)
				throw new IllegalArgumentException("Price cant be a negative number!");
			if(type.equals("Acoustic") && numOfStrings!=DEFAULT_NUM_OF_STRING)
				throw new IllegalArgumentException("Acoustic guitar can only have 6 strings!");
			if(type.equals("Electric")&&numOfStrings<DEFAULT_NUM_OF_STRING && numOfStrings>MAX_NUM_OF_STRINGS)
				throw new IllegalArgumentException("Acoustic guitar cant have more or less strings than 6!");
			if(type.equals("Classic") && numOfStrings<DEFAULT_NUM_OF_STRING && numOfStrings>MAX_NUM_OF_STRINGS)
				throw new IllegalArgumentException("Classic guitars can only have 6 strings!");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("Error! " + e.getMessage());
			System.exit(1);
		}

	}

	public String getType() {
		return type;
	}
	
	@Override
	public boolean equals (Instrument instrument)
	{
		if (instrument instanceof Guitar) // checking if the instrument is a bass guitar first.
		{
			Guitar guitar = (Guitar)instrument; // casting it into a bass guitar object.
			if (this.price == guitar.price && (this.brand.compareTo(guitar.brand)==0) && 
				type.compareTo(guitar.type)==0 && this.numOfStrings == guitar.numOfStrings) // checking if all defining values are equal
			{
				return true;
			}
		}
		return false;
	}
	@Override 
	public String toString()
	{
		return super.toString() +  "|  Type: " + type;		
		
	}

	
}
