import org.junit.Test;
import org.junit.Assert;
public class TestCheckClauseDatabase {
    @Test
    public void TestCheckClauseDatabaseTrue(){
        Solver solver = new Solver();
        int[] assignment = new int[]{0,1,-1,-1,1};
        int[][] clauseDatabase = new int[][]{{1,2,3},{1,2,3,4,5}};
        boolean result = solver.checkClauseDatabase(assignment, clauseDatabase);

        Assert.assertTrue(result);
    }
    @Test
    public void TestCheckClauseDatabaseFalse(){
        Solver solver = new Solver();
        int[] assignment = new int[]{-1};
        int[][] clauseDatabase = new int[][]{{1}};
        boolean result = solver.checkClauseDatabase(assignment, clauseDatabase);
        Assert.assertFalse(result);
    }
}


