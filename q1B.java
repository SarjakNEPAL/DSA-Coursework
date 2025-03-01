import java.util.Arrays;

// A straightforward solution to obtain the kth lowest combined return from two sorted arrays of investment returns requires generating complete products
//  between the arrays. A new array holds the results of multiplying all first and second array elements against each other. 
//  After generating all the products we must perform a sorting operation to arrange the products in ascending order. 
//  The sorted array enables straightforward access of the kth smallest product through its index at k - 1 because array counting starts at one. 
//  The approach remains simple to execute with smaller datasets but it becomes less favorable for more extensive data because sorting operation
//  s demand increased processing time.

// Step-by-Step Approach:
// The first step initializes two sorted arrays named returns1 and returns2 containing investment returns.

// Make a products Array to store potential results obtained by multiplying elements from returns1 and returns2.

// The generation of all possible products uses nested loops to process each element from returns1 and returns2 while saving each product within the products array.

// The Products Array needs to be sorted in ascending order after product generation to produce a proper smallest-to-largest arrangement of products.

// Return the element at the k - 1 index from the sorted products array to obtain the kth smallest product functionally.

// To deliver the kth smallest product the algorithm will show it both by printing or by returning it through the final result.

// The solution implements fundamental array operations which produce the necessary outcome through an easy-to-follow process.

public class q1B{

    public static int kthLowestCombinedReturn(int[] returns1, int[] returns2, int k) {
        // Step 1: Create an array to hold all possible products
        int n = returns1.length;
        int m = returns2.length;
        int[] products = new int[n * m]; // Array to store products
        int index = 0; // Index for products array

        // Step 2: Generate all possible products
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                products[index++] = returns1[i] * returns2[j]; // Calculate product and store it
            }
        }

        // Step 3: Sort the products array
        Arrays.sort(products); // Sort the array to find the kth smallest product

        // Step 4: Return the kth smallest product
        // k is 1-based, so we return products[k - 1]
        return products[k - 1];
    }

    public static void main(String[] args) {
        // Example 1
        int[] returns1 = {2, 5};
        int[] returns2 = {3, 4};
        int k = 2;
        int output1 = kthLowestCombinedReturn(returns1, returns2, k);
        System.out.println(output1);  // Expected Output: 8

        // Example 2
        int[] returns1_2 = {-4, -2, 0, 3};
        int[] returns2_2 = {2, 4};
        k = 6;
        int output2 = kthLowestCombinedReturn(returns1_2, returns2_2, k);
        System.out.println(output2);  // Expected Output: 0
    }
}
  
/*
Input:

returns1 = {2, 5};
returns2 = {3, 4};
k = 2;
output: 8

returns1_2 = {-4, -2, 0, 3};
returns2_2 = {2, 4};
k = 6;
output:0

 * 
 * 
 */