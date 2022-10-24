package application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AfekaInventory <T extends MusicalInstrument> implements InventoryManagement<T>{
    private ArrayList<T> instrumentsArr;
    private double totalPrice;
    private boolean sorted;

    public AfekaInventory()
    {
        instrumentsArr = new ArrayList<>();
        totalPrice = 0.0;
        sorted = false;
    }
    public double getTotalPrice()
    {
        return totalPrice;
    }
    public ArrayList<T> getInventorysInstrumentsList () { return instrumentsArr; }
    public boolean isSorted()
    {
        return sorted;
    }
    @Override
    public void addAllStringInstruments(ArrayList<T> sourceArr, ArrayList<T> toArr) {
        for (T instrument : sourceArr)
        {
            if (instrument instanceof StringInstrument) {
                toArr.add(instrument);
                sorted = false;
            }
        
        }
        for(int i=0;i<toArr.size();i++)
            setTotalPrice((toArr.get(i).getPrice().doubleValue()));
    }

    @Override
    public void addAllWindInstruments(ArrayList<T> sourceArr, ArrayList<T> toArr) {
        for (T instrument : sourceArr)
        {
            if (instrument instanceof WindInstrument) {
                toArr.add(instrument);
                sorted = false;
            }
            
        }
        for(int i=0;i<toArr.size();i++)
            setTotalPrice((toArr.get(i).getPrice().doubleValue()));
    }

    @Override
    public void sortByBrandAndPrice(ArrayList<T> arr) {
        Collections.sort(arr);
        sorted = true;
    }

    @Override
    public int binarySearchByBrandAndPrice(ArrayList<T> arr, String brand, Number price) {
        int firstBrandFoundIndex = -1;
        if (!sorted)
            sortByBrandAndPrice(instrumentsArr);
        int low = 0;
        int high = arr.size()-1;
        int middle = (low+high)/2;
        String currentBrand = "";
        while (low <= high) {
            currentBrand = arr.get(middle).getBrand();
            if (currentBrand.equals(brand))
            {
                if (firstBrandFoundIndex == -1)
                    firstBrandFoundIndex = middle;
                if (arr.get(middle).getPrice().doubleValue() == price.doubleValue())
                {
                    return middle;
                }
                else if (arr.get(middle).getPrice().doubleValue() < price.doubleValue())
                {
                    low = middle+1;
                    middle = (low+high)/2;
                }
                else {
                    high = middle-1;
                    middle = (low+high)/2;
                }
            }
            else if (currentBrand.compareTo(brand) < 0)
            {
                low = middle+1;
                middle = (low+high)/2;
            }
            else {
                high = middle-1;
                middle = (low+high)/2;
            }
        }
        return firstBrandFoundIndex;
    }

    @Override
    public void addInstrument(ArrayList<MusicalInstrument> arr, MusicalInstrument instrument) {
        setTotalPrice((instrument.getPrice().doubleValue()));
        arr.add(instrument);
    }

    @Override
    public <E extends T> boolean removeInstrument(ArrayList<T> arr, E instrument){
        if (arr.remove(instrument)) {
            setTotalPrice(-1*(instrument.getPrice().doubleValue()));
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean removeAll(ArrayList<T> arr) {
        if (arr.removeAll(arr)) {
            for (int i = 0; i < arr.size(); i++)
                setTotalPrice(-1*(arr.get(i).getPrice().doubleValue()));
            return true;
        }
        else
            return false;
    }

    public <E extends Number> double sum(E num1, E num2)
    {
        return num1.doubleValue() + num2.doubleValue();
    }
    
    public void setTotalPrice(Number totalPrice)
    {
       this.totalPrice=sum(getTotalPrice(),totalPrice);
    }
   
    public String toString()
    {
        String res = "";
        for (MusicalInstrument instrument : instrumentsArr)
        {
            res += instrument.toString() + "\n";
        }
        res+=  String.format("\nTotal Price: %-10.2f,  Sorted: %b\n",
                (getInventorysInstrumentsList().isEmpty() ? 0.00 : getTotalPrice()), isSorted());
        return res;
    }
}


