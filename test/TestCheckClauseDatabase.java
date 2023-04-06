import org.junit.Test;
import org.junit.Assert;
public class TestCheckClauseDatabase {
    @Test
    public void TestCheckClauseDatabaseTrue(){
        Solver solver = new Solver();
        int[] assignment = new int[] {0,1,1,1,1,1,1};
        int[][] clauseDatabase = new int[][]{{1,-2},{-1,-2,3},{2}};
        boolean result = solver.checkClauseDatabase(assignment, clauseDatabase);

        Assert.assertTrue(result);
    }
    @Test
    public void TestCheckClauseDatabaseFalse(){
        Solver solver = new Solver();
        int[] assignment = new int[]{0,0,-1,-1,0,0,-1,-1,0};
        int[][] clauseDatabase = new int[][] {{1,-2},{-1,-2,3},{2}};
        boolean result = solver.checkClauseDatabase(assignment, clauseDatabase);
        Assert.assertFalse(result);
    }
}


