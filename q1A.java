

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