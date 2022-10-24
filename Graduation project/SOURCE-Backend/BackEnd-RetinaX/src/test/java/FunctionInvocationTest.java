import com.retinaX.entities.cellData.CellData;
import com.retinaX.entities.function.Variable;
import com.retinaX.services.function.FunctionInvocation;
import com.retinaX.services.simulation.cellSimulation.functionInvokers.MXFunctionParserInvoker;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class FunctionInvocationTest {

    @Test
    public void testLinearFunctions() {
        FunctionInvocation functionInvocation = new MXFunctionParserInvoker();

        Variable aVar = new Variable("a");
        Variable bVar = new Variable("b");
        Variable xVar = new Variable("x");

        functionInvocation.parse("a*x + b", List.of(aVar, bVar, xVar));
        Map<Variable, CellData> args = new HashMap<>();

        for(double a = -2 ; a < 2 ; a += 1){
            System.out.println("a: " + a);
            for(double b = -2 ; b < 2 ; b += 1){
                System.out.println("b: " + b);
                for(double x = -1 ; x < 1 ; x += 0.001){
                    args.put(aVar, new CellData(a));
                    args.put(bVar, new CellData(b));
                    args.put(xVar, new CellData(x));
                    CellData result = functionInvocation.invoke(args);
                    assertThat(result.getValue())
                            .isCloseTo(axPlusb(a, b, x), offset(0.000001));
                }
            }
        }
    }

    @Test
    public void testTrigonometricFunctions() {
        FunctionInvocation functionInvocation = new MXFunctionParserInvoker();
        Variable xVar = new Variable("x");

        functionInvocation.parse("sin(x) + cos(x)", List.of(xVar));
        Map<Variable, CellData> args = new HashMap<>();

        for(double x = -Math.PI + 1 ; x < Math.PI + 1; x += 0.001){
            args.put(xVar, new CellData(x));
            CellData result = functionInvocation.invoke(args);
            assertThat(result.getValue())
                    .isCloseTo(cosPlusSin(x), offset(0.00000001));
        }

    }

    @Test
    public void testSigmoid() {
        FunctionInvocation functionInvocation = new MXFunctionParserInvoker();
        Variable xVar = new Variable("x");

        functionInvocation.parse("1 / (1 + e^(-x))", List.of(xVar));
        Map<Variable, CellData> args = new HashMap<>();

        for(double x = -Math.PI + 1 ; x < Math.PI + 1; x += 0.001){
            args.put(xVar, new CellData(x));
            CellData result = functionInvocation.invoke(args);
            assertThat(result.getValue())
                    .isCloseTo(sigmoid(x), offset(0.00000001));
        }

    }

    private double axPlusb(double a, double b, double x){
        return a*x + b;
    }

    private double cosPlusSin(double x){
        return Math.cos(x) + Math.sin(x);
    }

    private double sigmoid(double x) {
        return (1/( 1 + Math.pow(Math.E,(-1*x))));
    }
}
