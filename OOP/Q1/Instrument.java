
public class Instrument {
	protected float price;
	protected String brand;
	
	public Instrument (float price, String brand)
	{
		this.price = price;
		this.brand = brand;
	}

	public float getPrice() {
		return price;
	}

	public String getBrand() {
		return brand;
	}

	public boolean equals(Instrument instrument) {
		return this == instrument;
	}
	
	public String toString()
	{
		return String.format("%-10s", brand) + String.format("%-10s", getClass().getCanonicalName()) + 
				"| Price: " + String.format("%7s", String.format("%.2f", price));
	}
	
	
	
	
}
