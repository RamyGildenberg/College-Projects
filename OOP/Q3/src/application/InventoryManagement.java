package application;
import java.util.ArrayList;

public interface InventoryManagement<T extends MusicalInstrument> {

    public void addAllStringInstruments(ArrayList<T> sourceArr, ArrayList<T> toArr);
    public void addAllWindInstruments(ArrayList<T> sourceArr, ArrayList<T> toArr);
    public void sortByBrandAndPrice (ArrayList<T> arr);
    public int binarySearchByBrandAndPrice(ArrayList<T> arr, String brand, Number price);
    public void addInstrument(ArrayList<MusicalInstrument> arr, MusicalInstrument instrument);
    public <E extends T> boolean removeInstrument (ArrayList<T> arr, E instrument);
    public boolean removeAll (ArrayList<T> arr);
}


