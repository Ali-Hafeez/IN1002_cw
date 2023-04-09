// IN1002 Introduction to Algorithms
// Coursework 2022/2023
//
// Submission by
// Ali Muhammad Hafeez Buttar
// ali.hafeez-buttar@city.ac.uk

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Solver {

    private int [][] clauseDatabase = null;
    private int numberOfVariables = 0;

    /* You answers go below here */

    // Part A.1
    // Worst case complexity : O(1)
    // Best case complexity :  O(v), where v is the number of literals in the clause, when we need to iterate
    //                         through all the literals in the clause and none of them are satisfied by the assignment

    public boolean checkClause(int[] assignment, int[] clause) {
        for (int i = 0; i < clause.length; i++) {
            int literal = clause[i];
            boolean isNegated = literal < 0;
            int var = isNegated ? -literal : literal;
            boolean value = assignment[var - 1] == 1;
            if (isNegated) {
                value = !value;
            }
            if (value) {
                return true;
            }
        }

        return false;
    }

    // Part A.2
    // Worst case complexity :s O(1) when the clause database is empty
    // Best case complexity : O(cl * maxClauseLength), where cl is the number of clauses in the database
    //                        and maxClauseLength is the length of the longest clause


   public boolean checkClauseDatabase(int[] assignment, int[][] clauseDatabase) {
       for (int[] clause : clauseDatabase) {
           boolean isClauseSatisfied = false;
           for (int literal : clause) {
               boolean isNegated = literal < 0;
               int var = isNegated ? -literal : literal;
               int value = assignment[var - 1];
               if ((value == 1 && !isNegated) || (value == 0 && isNegated)){
              // if (checkClause(assignment, clause)) {
                   // the clause is satisfied
                   isClauseSatisfied = true;
                   break;
               }
           }
           if (isClauseSatisfied) {
               // the clause is unsatisfied, so the assignment does not satisfy all clauses
               return true;
           }
       }
       // all clauses are satisfied
       return false;
   }


    // Part A.3
// Worst case complexity :  O(1) when the clause contains only one literal, and it is satisfied by the partial assignment
// Best case complexity :   O(v), where v is the number of literals in the clause

    public int checkClausePartial(int[] partialAssignment, int[] clause) {
        boolean isSatisfied = false;
        boolean hasUnassignedVariable = false;
        for (int i = 0; i < clause.length; i++) {
            int literal = clause[i];
            boolean isNegated = literal < 0;
            int var = isNegated ? -literal : literal;
            int value = partialAssignment[var - 1];
            if (value == 1 && !isNegated || value == 0 && isNegated) {
                isSatisfied = true;
            } else if (value == -1) {
                hasUnassignedVariable = true;
            }
        }
        if (isSatisfied) {
            return 1;
        } else if (hasUnassignedVariable) {
            return 0;
        } else {
            return -1;
        }

    }

    // Part A.4
    // Worst case complexity :  O(1) when the clause contains no unknown literals or it contains exactly one unknown literal that satisfies the clause.
    // Best case complexity : O(v), where v is the number of literals in the clause

    public int findUnit(int[] partialAssignment, int[] clause) {
        int unknownCount = 0;
        int unitLiteral = 0;
        for (int i = 0; i < clause.length; i++) {
            int literal = clause[i];
            boolean isNegated = literal < 0;
            int var = isNegated ? -literal : literal;
            int value = partialAssignment[var - 1];
            if (value == 0) {
                if (unknownCount == 0) {
                    unitLiteral = literal;
                } else {
                    // more than one unknown literal, not a unit clause
                    return 0;
                }
                unknownCount++;
            } else if (value == 1 && !isNegated) {
                // already satisfied, not a unit clause
                return 0;
            }
        }

        if (unknownCount == 1) {
            return unitLiteral;
        } else {
            return 0;
        }

    }


    // Part B
    // I think this can solve ????#





    //data/220019969-clause-database-01.cnf

    private int[][] pureLiteralRule(int[][] clauseDatabase, int[] assignment) {
        boolean[] keepClauses = new boolean[clauseDatabase.length];
        Arrays.fill(keepClauses, true);


        for (int i = 1; i < assignment.length; i++) {
            if (assignment[i] == 0) {
                boolean positive = false;
                boolean negative = false;
                for (int[] clause : clauseDatabase) {
                    for (int literal : clause) {
                        if (Math.abs(literal) == i) {
                            if (literal > 0) {
                                positive = true;
                            } else {
                                negative = true;
                            }
                        }
                        if (positive && negative) {
                            break;
                        }
                    }
                    if (positive && negative) {
                        break;
                    }
                }
                if (positive ^ negative) {
                    int polarity = positive ? 1 : -1;
                    assignment[i] = polarity;
                    for (int j = 0; j < clauseDatabase.length; j++) {
                        if (keepClauses[j]) {
                            int[] clause = clauseDatabase[j];
                            for (int k = 0; k < clause.length; k++) {
                                if (Math.abs(clause[k]) == i && clause[k] != polarity) {
                                    keepClauses[j] = false;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        int numClauses = 0;
        for (int i = 0; i < clauseDatabase.length; i++) {
            if (keepClauses[i]) numClauses++;
        }
        int[][] newClauseDatabase = new int[numClauses][];
        int index = 0;
        for (int i = 0; i < clauseDatabase.length; i++) {
            if (keepClauses[i]) {
                newClauseDatabase[index] = clauseDatabase[i];
                index++;
            }
        }
        return newClauseDatabase;
    }
    public int[][] preprocess(int[][] clauseDatabase,int[]assignment) {
        boolean[] keepClauses = new boolean[clauseDatabase.length]; // initialize a boolean array to keep track of which clauses to keep
        Arrays.fill(keepClauses, true); // set all values to true initially

        // remove redundant clauses
        for (int i = 0; i < clauseDatabase.length; i++) {
            if (!keepClauses[i]) continue; // if the clause has already been marked for removal, skip it
            for (int j = i+1; j < clauseDatabase.length; j++) {
                if (!keepClauses[j]) continue; // if the clause has already been marked for removal, skip it
                if (Arrays.equals(clauseDatabase[i], clauseDatabase[j])) { // if two clauses are identical, mark one for removal
                    keepClauses[j] = false;
                }
            }
        }

        // remove unit clauses
        boolean unitClauseFound = true;
        while (unitClauseFound) {
            unitClauseFound = false;
            for (int i = 0; i < clauseDatabase.length; i++) {
                if (!keepClauses[i]) continue; // if the clause has already been marked for removal, skip it
                if (clauseDatabase[i].length == 1) { // if a clause has length 1, it is a unit clause
                    int lit = clauseDatabase[i][0];
                    if (assignment[Math.abs(lit)] == 0) { // if the variable in the unit clause has not been assigned yet, assign it
                        assignment[Math.abs(lit)] = lit > 0 ? 1 : -1;
                        unitClauseFound = true;
                        keepClauses[i] = false; // mark the unit clause for removal
                    } else if (assignment[Math.abs(lit)] != (lit > 0 ? 1 : -1)) { // if the variable has already been assigned but not to the value in the unit clause, the clause is unsatisfiable
                        return null;
                    }
                }
            }
        }

        // count the number of remaining clauses and create a new clause database with only those clauses
        int numClauses = 0;
        for (int i = 0; i < clauseDatabase.length; i++) {
            if (keepClauses[i]) numClauses++;
        }
        int[][] newClauseDatabase = new int[numClauses][];
        int index = 0;
        for (int i = 0; i < clauseDatabase.length; i++) {
            if (keepClauses[i]) {
                newClauseDatabase[index] = clauseDatabase[i];
                index++;
            }
        }
        return newClauseDatabase;
    }
    private boolean clauseEquals(int[] c1, int[] c2) {
        if (c1.length != c2.length) {
            return false;
        }

        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();

        for (int i = 0; i < c1.length; i++) {
            set1.add(c1[i]);
            set2.add(c2[i]);
        }

        return set1.equals(set2);
    }



    //data/220019969-clause-database-01.cnf
    int[] checkSat(int[][] clauseDatabase) {
        int numVariables = getNumVariables(clauseDatabase);
        int[] assignment = new int[numVariables + 1];
        int[][] processedData;
        if (numVariables>50){

            processedData = preprocess(clauseDatabase,assignment);

        }
        else{
            processedData = preprocess(pureLiteralRule(clauseDatabase,assignment),assignment);


        }

        return dpll(processedData, assignment,1,0)? assignment : null;
    }

    private boolean dpll(int[][] clauseDatabase, int[] assignment, int variable, int callCount) {
        if (callCount > 100) {
            return false;
        }
        if (variable == assignment.length) {
            return isAssignmentSatisfiable(clauseDatabase, assignment);
        }

        assignment[variable] = 1;
        if (dpll(clauseDatabase, assignment, variable + 1, callCount + 1)) {
            return true;
        }
        assignment[variable] = -1;
        if (dpll(clauseDatabase, assignment, variable + 1, callCount + 1)) {
            return true;
        }
        assignment[variable] = 0;
        return false;
    }

    private boolean isAssignmentSatisfiable(int[][] clauseDatabase, int[] assignment) {
        for (int[] clause : clauseDatabase) {
            boolean isClauseSatisfied = false;
            for (int literal : clause) {
                int variable = Math.abs(literal);
                if (assignment[variable] == 0) {
                    continue;
                }
                if (literal > 0 && assignment[variable] == 1) {
                    isClauseSatisfied = true;
                    break;
                }
                if (literal < 0 && assignment[variable] == -1) {
                    isClauseSatisfied = true;
                    break;
                }
            }
            if (!isClauseSatisfied) {
                return false;
            }
        }
        return true;
    }


    private int getNumVariables(int[][] clauseDatabase) {
        int numVariables = 0;
        for (int[] clause : clauseDatabase) {
            for (int literal : clause) {
                int variable = Math.abs(literal);
                if (variable > numVariables) {
                    numVariables = variable;
                }
            }
        }
        return numVariables;
    }




















    /*****************************************************************\
     *** DO NOT CHANGE! DO NOT CHANGE! DO NOT CHANGE! DO NOT CHANGE! ***
     *******************************************************************
     *********** Do not change anything below this comment! ************
     \*****************************************************************/

    public static void main(String[] args) {
        try {
            Solver mySolver = new Solver();

            System.out.println("Enter the file to check");

            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String fileName = br.readLine();

            int returnValue = 0;

            Path file = Paths.get(fileName);
            BufferedReader reader = Files.newBufferedReader(file);
            returnValue = mySolver.runSatSolver(reader);

            return;

        } catch (Exception e) {
            System.err.println("Solver failed :-(");
            e.printStackTrace(System.err);
            return;

        }
    }

    public int runSatSolver(BufferedReader reader) throws Exception, IOException {

        // First load the problem in, this will initialise the clause
        // database and the number of variables.
        loadDimacs(reader);
        // Then we run the part B algorithm
        int [] assignment = checkSat(clauseDatabase);

        // Depending on the output do different checks
        if (assignment == null) {
            // No assignment to check, will have to trust the result
            // is correct...
            System.out.println("s UNSATISFIABLE");
            return 20;

        } else {
            // Cross check using the part A algorithm
            boolean checkResult = checkClauseDatabase(assignment, clauseDatabase);

            if (checkResult == false) {
                throw new Exception("The assignment returned by checkSat is not satisfiable according to checkClauseDatabase?");
            }

            System.out.println("s SATISFIABLE");

            // Check that it is a well structured assignment
            if (assignment.length != numberOfVariables + 1) {
                throw new Exception("Assignment should have one element per variable.");
            }
            if (assignment[0] != 0) {
                throw new Exception("The first element of an assignment must be zero.");
            }
            for (int i = 1; i <= numberOfVariables; ++i) {
                if (assignment[i] == 1 || assignment[i] == -1) {
                    System.out.println("v " + (i * assignment[i]));
                } else {
                    throw new Exception("assignment[" + i + "] should be 1 or -1, is " + assignment[i]);
                }
            }

            return 10;
        }
    }

    // This is a simple parser for DIMACS file format
    void loadDimacs(BufferedReader reader) throws Exception, IOException {
        int numberOfClauses = 0;

        // Find the header line
        do {
            String line = reader.readLine();

            if (line == null) {
                throw new Exception("Found end of file before a header?");
            } else if (line.startsWith("c")) {
                // Comment line, ignore
                continue;
            } else if (line.startsWith("p cnf ")) {
                // Found the header
                String counters = line.substring(6);
                int split = counters.indexOf(" ");
                numberOfVariables = Integer.parseInt(counters.substring(0,split));
                numberOfClauses = Integer.parseInt(counters.substring(split + 1));

                if (numberOfVariables <= 0) {
                    throw new Exception("Variables should be positive?");
                }
                if (numberOfClauses < 0) {
                    throw new Exception("A negative number of clauses?");
                }
                break;
            } else {
                throw new Exception("Unexpected line?");
            }
        } while (true);

        // Set up the clauseDatabase
        clauseDatabase = new int[numberOfClauses][];

        // Parse the clauses
        for (int i = 0; i < numberOfClauses; ++i) {
            String line = reader.readLine();

            if (line == null) {
                throw new Exception("Unexpected end of file before clauses have been parsed");
            } else if (line.startsWith("c")) {
                // Comment; skip
                --i;
                continue;
            } else {
                // Try to parse as a clause
                ArrayList<Integer> tmp = new ArrayList<Integer>();
                String working = line;

                do {
                    int split = working.indexOf(" ");

                    if (split == -1) {
                        // No space found so working should just be
                        // the final "0"
                        if (!working.equals("0")) {
                            throw new Exception("Unexpected end of clause string : \"" + working + "\"");
                        } else {
                            // Clause is correct and complete
                            break;
                        }
                    } else {
                        int var = Integer.parseInt(working.substring(0,split));

                        if (var == 0) {
                            throw new Exception("Unexpected 0 in the middle of a clause");
                        } else {
                            tmp.add(var);
                        }

                        working = working.substring(split + 1);
                    }
                } while (true);

                // Add to the clause database
                clauseDatabase[i] = new int[tmp.size()];
                for (int j = 0; j < tmp.size(); ++j) {
                    clauseDatabase[i][j] = tmp.get(j);
                }
            }
        }

        // All clauses loaded successfully!
        return;
    }

}

