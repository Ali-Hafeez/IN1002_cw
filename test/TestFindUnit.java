import org.junit.Test;
import org.junit.Assert;

public class TestFindUnit {
    @Test
    public void testFindUnit() {
        Solver solver = new Solver();
        int[] partialAssignment = {0,1,0,-1};
        int[] clause = {1, -2, 3};
        int expectedOutput = 2;
        int result = solver.findUnit(partialAssignment, clause);
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
