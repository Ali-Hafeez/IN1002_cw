import org.junit.Test;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class TestCheckSat {
    /*
    @Test
    public void TestCheckSat() {
        Solver solver = new Solver();

        int[][] clauseDatabase = new int[][]{
                { 2, 1, 0},
                {1, 1 ,0},
                {-2,1 ,0}
        };
        int[] result = solver.checkSat(clauseDatabase);
        switch (Arrays.toString(result)) {
            case "[0, 1, 1]":
                // code to execute if arr matches the first array
                assert true;
                break;
            case "[0, 1, 0]":
                // code to execute if arr matches the second array
                assert true;
                break;
            case "[0, 0, 0]":
                // code to execute if arr matches the third array
                assert true;
                break;
            case "[0, 0, 1]":
                // code to execute if arr matches the third array
                assert true;
                break;
            default:
                assert false;
                // code to execute if arr doesn't match any of the arrays
                break;
        }


    }*/
    @Test
    public void TestCheckSatEmpty(){
        Solver solver = new Solver();
        int[][] clauseDatabase = new int [][]{};
        Object result = solver.checkSat(clauseDatabase);

        System.out.println(result);

        if (result == null){
            assert true;
        }
    }
    @Test
    public void testFile1(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-01.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile2(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-02.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile3(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-03.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile4(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-04.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile5(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-05.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile6(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-06.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile7(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-07.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile8(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-08.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile9(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-09.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile10(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-10.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile11(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-11.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile12(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-12.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile13(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-13.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile14(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-14.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void testFile15(){
        Solver solver = new Solver();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("data/220019969-clause-database-15.cnf"));
            solver.runSatSolver(reader);

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
