

// Question::
/*
You have a material with n temperature levels. You know that there exists a critical temperature f where 
0 <= f <= n such that the material will react or change its properties at temperatures higher than f but 
remain unchanged at or below f.
Rules:
 You can measure the material's properties at any temperature level once.
 If the material reacts or changes its properties, you can no longer use it for further measurements.
 If the material remains unchanged, you can reuse it for further measurements.
Goal:
Determine the minimum number of measurements required to find the critical temperature.
Input:
 k: The number of identical samples of the material.
 n: The number of temperature levels.
Output:
 The minimum number of measurements required to find the critical temperature
*/




/*
 * The Java program I have coded is a dynamic programming implementation for determining the minimum number of tests to determine the critical 
 * temperature of a substance, given the number of samples (k) and the number of temperature levels (n). Here i used Dynamic Programming to solve this problem.
Code Step
Dynamic Programming Table Initialization: I start by declaring a 2D array dp such that dp[i][j] is the 
minimum number of measurements required with i samples and j temperature levels. The array is declared of size (k + 1) by (n + 1) in order to provide space for
 all the potential sample values and temperature levels.

Base Cases Setup: I set the base cases so as to account for some special cases:

If there are no temperature levels (j = 0), no measurements are needed, so I set dp[i][0] = 0.
If there is only one temperature level (j = 1), then only one measurement is necessary, so dp[i][1] = 1.
If there is only one sample (i = 1), all temperature levels must be tested sequentially and therefore dp[1][j] = j.
Filling the DP Table: Next, I iterate through all combinations of samples (i) and temperature levels (j) from 2. For each combination, I initialize dp[i][j] to infinity so 
any good measurement count will be smaller. Then, I attempt each possible temperature level x (from 1 to j):

If the material reacts at temperature x, the critical temperature is in range [0, x-1], and dp[i - 1][x - 1] measurements are required.
If the material doesn't react, the critical temperature is in range [x, j], and dp[i][j - x] measurements are required.
I calculate the worst-case for this test as worstCase = Math.max(dp[i - 1][x - 1], dp[i][j - x]).
I then update the minimum number of measurements for dp[i][j] to be the smaller of its current value and 1 + worstCase, where 1 is for the current measurement.
Final Result: After filling up the DP table, the final answer, the minimum number of measurements required with k samples and n temperature levels, is in dp[k][n].

Main Method: Inside the main method, I call the minMeasurements method with various arguments and print output for various combinations of sample and temperature levels.
 */
public class q1A {
    // making a static function which takes two inputs k and n where k is the number of same material's small part
    // and n is the number of temperatures

    public static int minMeasurements(int k, int n) {

    
    // Let ( dp[i][j] ) represent the minimum number of measurements required to find the critical temperature with ( i ) samples and ( j ) temperature levels.

    // The recurrence relation can be defined as follows:

    // If you test a temperature ( x ):
    // If the material reacts, ( f ) is in the range ( [0, x-1] ).
    // If the material does not react, ( f ) is in the range ( [x, j] ).
    // Thus, the relation can be expressed as: worstCase = Math.max(dp[i - 1][x - 1], dp[i][j - x]); Where:
    // ( dp[i-1][x-1] ) is the case where the material reacts (we lose one sample).
    // ( dp[i][j-x] ) is the case where the material does not react (we still have all samples).
    // Base Cases
    // ( dp[i][0] = 0 ) (no temperature levels to test)
    // ( dp[1][j] = n ) (with one sample, we must test each temperature sequentially)


        // Create a DP table
        int[][] dp = new int[k + 1][n + 1];

        // Fill the base cases
        for (int i = 1; i <= k; i++) {
            dp[i][0] = 0; // 0 temperature levels does not require to be measured
            dp[i][1] = 1; // 1 temperature level requires only 1 measurement
        }

        for (int j = 1; j <= n; j++) {
            dp[1][j] = j; // if one material needs to react then, we need to test each temperature level one by one
        }

        // Fill the DP table
        for (int i = 2; i <= k; i++) {
            for (int j = 2; j <= n; j++) {
                dp[i][j] = Integer.MAX_VALUE; // first putting infinity to make it biggest value
                for (int x = 1; x <= j; x++) { 
                    // Calculate the worst case for the current measurement
                    int worstCase = Math.max(dp[i - 1][x - 1], dp[i][j - x]);   
                    dp[i][j] = Math.min(dp[i][j], 1 + worstCase);
                }
            }
        }

        return dp[k][n];
    }

    public static void main(String[] args) {
        System.out.println(minMeasurements(1, 2));  
        System.out.println(minMeasurements(2, 6));  
        System.out.println(minMeasurements(3, 14)); 
    }

    /* Inputs: 1,2   output: 2 */
    /* Inputs: 2,6   output: 3*/
    /* Inputs: 3,14   output: 4*/

}