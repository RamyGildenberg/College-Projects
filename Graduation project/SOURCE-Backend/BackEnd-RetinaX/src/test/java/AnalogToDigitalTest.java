import com.retinaX.entities.cellData.CellData;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class AnalogToDigitalTest {
    @Test
    public void toDigitalArrayOfBitsTest() {
        CellData cellData = new CellData(0.5);

        Integer[] bits = cellData.toDigitalArrayOfBits(8);

        Integer[] expectedBits = {1, 0, 0, 0,
                0, 0, 0, 0};

        assertThat(bits)
                .containsExactly(expectedBits);
    }
}
