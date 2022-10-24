import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Guitar extends StringInstrument {

    public static final String GUITAR_TYPE[] = {"Classic", "Acoustic", "Electric"};

    public static final int CLASSIC = 0, ACOUSTIC = 1, ELECTRIC = 2;
    public static final int CLASSIC_NUM_OF_STRINGS = 6, ACOUSTIC_NUM_OF_STRINGS = 6,
            ELEC_MAX_NUM_OF_STRINGS = 8, ELEC_MIN_NUM_OF_STRINGS = 6;


    private String type;

    public Guitar(String brand, Number price, int numOfStrings, String type) {
        super(brand, price, numOfStrings);
    }

    public Guitar(Scanner scanner) {
        super(scanner);
        int numOfStrings;
        String type;

        numOfStrings = getNumOfStrings();

        scanner.nextLine();
        type = scanner.nextLine();
        setType(type);

        switch (indexOfType()) {
            case CLASSIC:
                if (numOfStrings != CLASSIC_NUM_OF_STRINGS)
                    throw new InputMismatchException("Classic Guitars have 6 strings, not " + numOfStrings);
                break;

            case ACOUSTIC:
                if (numOfStrings != ACOUSTIC_NUM_OF_STRINGS)
                    throw new InputMismatchException("Acoustic Guitars have 6 strings, not " + numOfStrings);
                break;

            case ELECTRIC:
                if (numOfStrings < ELEC_MIN_NUM_OF_STRINGS || numOfStrings > ELEC_MAX_NUM_OF_STRINGS)
                    throw new InputMismatchException("Acoustic Guitars have 6 strings, not " + numOfStrings);
                break;
        }
    }

    private boolean isGuitarType(String input) {
        return isValidType(GUITAR_TYPE, input);
    }

    private int indexOfType() {
        for (int i = 0; i < GUITAR_TYPE.length; i++) {
            if (getType().equals(GUITAR_TYPE[i])) {
                return i;
            }
        }
        return 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (isGuitarType(type))
            this.type = type;
        else
            throw new InputMismatchException("Invalid guitar type: " + type);
    }


    @Override
    public void setNumOfStrings(int numOfStrings) {
        if (numOfStrings < 0)
            throw new IllegalArgumentException("Number of strings cannot be negative!");
        else
            super.setNumOfStrings(numOfStrings);
    }


    @Override
    public boolean equals(Object o) {
        if (!super.equals(o))
            return false;

        if (!(o instanceof Guitar))
            return false;

        return getType().equals(((Guitar) o).getType());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" Type: %7s", getType().toString());
    }

    @Override
    public Guitar clone() throws CloneNotSupportedException {
        return (Guitar) super.clone();

    }
}
