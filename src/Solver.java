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

   /* public boolean checkClauseDatabase(int[] assignment, int[][] clauseDatabase) {
        for (int i = 0; i < clauseDatabase.length; i++) {
            //for (int j = 0; i < clauseDatabase[i].length; j++) {
                int[] clause = clauseDatabase[i];
               // int[] clauseArr = new int[]{clauseDatabase[i][j]};
                if (checkClause(assignment, clause)) {
                        return false;
                }


            }

        return true;

    }*/
   public boolean checkClauseDatabase(int[] assignment, int[][] clauseDatabase) {
       for (int[] clause : clauseDatabase) {
           boolean isClauseSatisfied = false;
           for (int literal : clause) {
               boolean isNegated = literal < 0;
               int var = isNegated ? -literal : literal;
               int value = assignment[var - 1];
               if ((value == 1 && !isNegated) || (value == 0 && isNegated)){
               //if (checkClause(assignment, clause)) {
                   // the clause is satisfied
                   isClauseSatisfied = true;
                   break;
               }
           }
           if (!isClauseSatisfied) {
               // the clause is unsatisfied, so the assignment does not satisfy all clauses
               return false;
           }
       }
       // all clauses are satisfied
       return true;
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
    int[] checkSat(int[][] clauseDatabase) { int[] assignment = new int[clauseDatabase[0].length];
        for (int i = 0; i < assignment.length; i++) {
            assignment[i] = -1;
        }
        return dpll(clauseDatabase, assignment);
    }

    private static int[] dpll(int[][] clauseDatabase, int[] assignment) {
        int var = selectVariable(clauseDatabase, assignment);
        if (var == 0) {
            return assignment;
        }
        int[][] reducedClauseDatabase = reduceClauseDatabase(clauseDatabase, var, true);
        int[] reducedAssignment = Arrays.copyOf(assignment, assignment.length);
        reducedAssignment[Math.abs(var)] = (var > 0) ? 1 : 0;
        int[] result = dpll(reducedClauseDatabase, reducedAssignment);
        if (result != null) {
            return result;
        }
        reducedClauseDatabase = reduceClauseDatabase(clauseDatabase, var, false);
        reducedAssignment = Arrays.copyOf(assignment, assignment.length);
        reducedAssignment[Math.abs(var)] = (var > 0) ? 0 : 1;
        return dpll(reducedClauseDatabase, reducedAssignment);
    }

    private static int selectVariable(int[][] clauseDatabase, int[] assignment) {
        for (int i = 1; i < assignment.length; i++) {
            if (assignment[i] == -1) {
                boolean pureLiteral = true;
                for (int[] clause : clauseDatabase) {
                    if (clauseContainsLiteral(clause, i) && clauseContainsLiteral(clause, -i)) {
                        pureLiteral = false;
                        break;
                    }
                }
                if (pureLiteral) {
                    return i;
                }
            }
        }
        for (int i = 1; i < assignment.length; i++) {
            if (assignment[i] == -1) {
                return i;
            }
        }
        return 0;
    }

    private static boolean clauseContainsLiteral(int[] clause, int literal) {
        for (int l : clause) {
            if (l == literal) {
                return true;
            }
        }
        return false;
    }

    private static int[][] reduceClauseDatabase(int[][] clauseDatabase, int var, boolean value) {
        List<int[]> newClauseDatabase = new ArrayList<>();
        for (int[] clause : clauseDatabase) {
            if (!clauseContainsLiteral(clause, var * (value ? -1 : 1))) {
                int[] newClause = new int[clause.length];
                int j = 0;
                for (int l : clause) {
                    if (Math.abs(l) != Math.abs(var)) {
                        newClause[j++] = l;
                    }
                }
                newClause = Arrays.copyOf(newClause, j);
                if (newClause.length == 0) {
                    return null;
                }
                newClauseDatabase.add(newClause);
            }
        }
        int[][] result = new int[newClauseDatabase.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = newClauseDatabase.get(i);
        }
        return result;
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

