import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retinaX.Application;
import com.retinaX.dal.CellInstanceDao;
import com.retinaX.entities.CellInstance;
import com.retinaX.entities.CellTransformType;
import com.retinaX.services.buildNetwork.cellInstance.CellInstanceService;
import com.retinaX.services.buildNetwork.cellType.CellTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestGetInputCells {

    private CellInstanceService cellInstanceService;

    @Test
    public void getInputCells() {
        List<CellInstance> cellInstances = cellInstanceService.getInputCells();

        cellInstances.forEach( cellInstance -> {
                    try {
                        System.out.println(new ObjectMapper().writeValueAsString(cellInstance));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Autowired
    public void setCellInstanceService(CellInstanceService cellInstanceService) {
        this.cellInstanceService = cellInstanceService;
    }
}
