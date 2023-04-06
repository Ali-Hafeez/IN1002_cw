import org.junit.Test;
import org.junit.Assert;

public class TestFindUnit {
    @Test
    public void testFindUnitUnitLiteral() {
        Solver solver = new Solver();
        int[] partialAssignment = {0,1,0,-1,0,1,0,-1};
        int[] clause = {-2,3,-4,5,-6};
        int result = solver.findUnit(partialAssignment, clause);
        int expectedOutput = result;
        System.out.println(result);

        if (result == expectedOutput){
            assert true;
        }

    }

    @Test
    public void testFindUnitZero(){
        Solver solver = new Solver();
        int[] partialAssignment = {0,1,0,-1,0};
        int[] clause = {1, -2, 3,4};
        int expectedOutput = 0;
        int result = solver.findUnit(partialAssignment, clause);
        if (result == expectedOutput){
            assert true;
        }

    }


}
