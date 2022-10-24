
public class WindInstrument extends Instrument{
	protected String madeOf;
	
	public WindInstrument(String madeOf, int price, String brand)
	{
		super(price, brand);
		this.madeOf = madeOf;
	}

	public String getMadeOf() {
		return madeOf;
	}
	
	public String toString()
	{
		return super.toString() + ", Made of: " + String.format("%13s", madeOf) + "|  ";
	}
	
	

}
