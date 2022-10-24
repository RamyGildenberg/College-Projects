import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class WindInstrument extends MusicalInstrument {
    public final static String WIND_INSTRUMENT_MATERIAL[] = {"Wood", "Metal", "Plastic"};

    private String material;

    public WindInstrument(String brand, Number price, String material){
        super(brand, price);
        setMaterial(material);
    }


    public WindInstrument(Scanner scanner){
        super(scanner);
        String material = scanner.nextLine();
        setMaterial(material);
    }



    public String getMaterial() {
        return material;
    }

    private boolean isMaterial(String substance){
        return isValidType(WIND_INSTRUMENT_MATERIAL, substance);
    }
    public void setMaterial(String substance) {
        if(isMaterial(substance)) {
            this.material = substance;
            return;
        }

        throw new InputMismatchException(substance + " is not a valid wind instrument material");
    }


    @Override
    public boolean equals(Object o) {
        if(!super.equals(o))
            return false;

        if(!(o instanceof  WindInstrument))
            return false;

        return getMaterial().equals(((WindInstrument)o).getMaterial());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" Made of: %12s| ", getMaterial().toString());
    }

}
