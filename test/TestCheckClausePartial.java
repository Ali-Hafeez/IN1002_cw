import org.junit.Test;
import org.junit.Assert;

public class TestCheckClausePartial {
    @Test
    public void TestCheckClausePartialPstve(){
        Solver solver = new Solver();
        int[] partialAssignment = new int[]{0,1,1,-1};
        int[]  clause  = new int[]{1,2,3,4};
        int result = solver.checkClausePartial(partialAssignment, clause );

        if (result == 1){
            assert true:"Expected result : 1";
        }
    }
    @Test
    public void TestCheckClausePartialZero(){
        Solver solver = new Solver();
        int[] partialAssignment = new int[]{0,1,0,-1,0};
        int[] clause = new int[]{1,2,3,4};
        int result = solver.checkClausePartial(partialAssignment, clause);
        if (result == 0){
            assert true: "Expected result: 0";
        }
    }
    @Test
    public void TestCheckClausePartialNgtve(){
        Solver solver = new Solver();
        int[] partialAssignment = new int[]{0,-1,-1,-1};
        int[] clause = new int[]{1,2,3,4};
        int result = solver.checkClausePartial(partialAssignment, clause);
        if (result == -1){
            assert true:"Expected result:-1";

        }
    }
}

