
public class Flute extends WindInstrument {
	private String type;
	
	public Flute (String madeOf, int price, String brand, String type) throws Exception
	{
		super (madeOf, price, brand);
		this.type = type;
		try
		{
		if(!(type.equals("Recorder")||type.equals("Bass Flute")||type.equals("flute")))
			throw new IllegalArgumentException("Invalid Flute type!");
		}
		catch(IllegalArgumentException e)
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
		if (instrument instanceof Flute) // checking if the instrument is a bass guitar first.
		{
			Flute flute = (Flute)instrument; // casting it into a bass guitar object.
			if (this.price == flute.price && this.brand.compareTo(flute.brand)==0 && 
				this.type.compareTo(flute.type)==0 && this.madeOf.compareTo(flute.madeOf)==0) // checking if all defining values are equal
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString()
	{
		return super.toString() + "Type: " + type;
	}
	
}
