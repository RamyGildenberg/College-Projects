
public class Saxophone extends WindInstrument {
	
	public Saxophone (int price, String brand)
	{
		super ("Metal", price, brand);
	}
	
	@Override
	public boolean equals (Instrument instrument)
	{
		if (instrument instanceof Saxophone) // checking if the instrument is a bass guitar first.
		{
			Saxophone saxophone = (Saxophone)instrument; // casting it into a bass guitar object.
			if (this.getPrice() == saxophone.getPrice() && this.getBrand().compareTo(saxophone.getBrand())==0) // checking if all defining values are equal
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString()
	{
		return super.toString();
	}
	
}
