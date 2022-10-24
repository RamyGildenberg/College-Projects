
import java.util.InputMismatchException;

import java.util.Scanner;

public abstract class MusicalInstrument implements InstrumentFunc<MusicalInstrument>{
    private Number price;
    private String brand;

    public MusicalInstrument(String brand, Number price){
        setBrand(brand);
        setPrice(price);
    }


    // NOT TESTED
    public int compareTo(MusicalInstrument other)
    {
        int comparedBrands = this.getBrand().compareTo(other.getBrand());
        if (comparedBrands == 0)
        {
            double difference = this.getPrice().doubleValue() - other.getPrice().doubleValue();
            if (difference == 0)
                return 0;
            else if (difference < 0)
                return -1;
            else return 1;
        }
        else return comparedBrands;
    }


    public MusicalInstrument(Scanner scanner){
        price = 0;
        String brand;
        try {
            Number p = null;
            if (scanner.hasNextInt()) {
                price = new Integer(scanner.nextInt());
            }
            else if (scanner.hasNextDouble()) {
                price = new Double(scanner.nextDouble());
            }
        }catch (InputMismatchException ex){
            throw new InputMismatchException("Price not found!");
        }
        setPrice(price);
        scanner.nextLine();
        brand = scanner.nextLine();
        setBrand(brand);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        if(price.doubleValue() > 0) // Maybe check later.
            this.price = price;
        else
            throw new InputMismatchException("Price must be a positive number!");

    }


    protected boolean isValidType(String[] typeArr, String material){
        for(int i = 0; i < typeArr.length ; i++) {
            if (material.equals(typeArr[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof MusicalInstrument))
            return false;

        MusicalInstrument otherInstrument = (MusicalInstrument) o;

        return getPrice().doubleValue() == otherInstrument.getPrice().doubleValue() && getBrand().equals(otherInstrument.getBrand());
    }


    @Override
    public String toString() {
        return String.format("%-8s %-9s| Price: %7.2f,", getBrand(), getClass().getCanonicalName(), getPrice().doubleValue());
    }
}
