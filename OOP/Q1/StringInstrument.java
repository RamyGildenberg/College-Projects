
public class StringInstrument extends Instrument {
	protected int numOfStrings;
	
	public StringInstrument(int numOfStrings, float price, String brand) throws Exception
	{
		super(price, brand);
		this.numOfStrings = numOfStrings;
		
	}

	public int getNumOfStrings() {
		return numOfStrings;
	}
	
	public String toString()
	{
		return super.toString() + ", Number of strings: " + String.format("%3s", numOfStrings);
	}


	
}
