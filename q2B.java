public class q2B {
    /* Detailed Approach:
    A nested loop system will determine the lexicographically smallest pair of point indices that demonstrate minimum Manhattan distance in a 2D plane by calculating distance between every pair of points. The Manhattan distance measures the distance between two points ((x_i, y_i)) and ((x_j, y_j)) through equation:

    
    The procedure begins by establishing variables for storing the minimum distance data along with the point indices.
    
    A nested loop system must analyze every point pair combination between arrays elements from x_coords and y_coords while elements (j) originate from same arrays. The distance measurement for each pair requires the calculation of Manhattan distance.
    
    The new calculated distance needs to update the minimum distance value when it proves smaller than the present value and save the point indices at that time. The current pair of indices requires checking for lexicographical smaller status against what is stored for equal distance cases. An update takes place when this check passes.
    
    After completing the pair check process return the indices of nearest points.*/
    /*
    Step-by-Step Approach:
Initialize Variables:

Declare a variable min_distance then assign it an extremely large integer value of Integer.MAX_VALUE.
Define the array closest_pair to store the indices of points which make up the closest pair.
Iterate Through Points:

The program should use inner loops to evaluate all elements in x_coords and y_coords arrays.
The iteration for (i) and (j) should be skipped when (i) equals (j) because this condition avoids testing the same point twice.
The Manhattan distance calculation must consider the points located at indices (i) and (j).
Update Minimum Distance:

A distance calculation less than min_distance will trigger an update of min_distance while setting closest_pair to ([i, j]).
The pair (i j) needs evaluating for a possible update when its distance matches min_distance and its lexicographic position surpasses the current closest_pair.
Return the Result:

The function should return closest_pair after checking all pairs.*/
    
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; // Get the number of points
        int min_distance = Integer.MAX_VALUE; // Initialize minimum distance to a large value
        int[] closest_pair = new int[2]; // Array to store the indices of the closest pair

        // Step 2: Iterate through all pairs of points
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Skip if i and j are the same
                if (i == j) continue;

                // Calculate the Manhattan distance
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // Step 3: Update minimum distance and closest pair
                if (distance < min_distance) {
                    min_distance = distance; // Update minimum distance
                    closest_pair[0] = i; // Update closest pair indices
                    closest_pair[1] = j;
                } else if (distance == min_distance) {
                    // Check for lexicographical order
                    if (i < closest_pair[0] || (i == closest_pair[0] && j < closest_pair[1])) {
                        closest_pair[0] = i; // Update closest pair indices
                        closest_pair[1] = j;
                    }
                }
            }
        }

        // Step 4: Return the indices of the closest pair
        return closest_pair;
    }

    public static void main(String[] args) {
        // Example input
        int[] x_coords = {1, 2, 3, 2, 4};
        int[] y_coords = {2, 3, 1, 2, 3};

        // Find and print the closest pair of indices
        int[] result = findClosestPair(x_coords, y_coords);
        System.out.println("Closest pair of indices: [" + result[0] + ", " + result[1] + "]"); // Expected Output: [0, 3]
    }
}
//input:
// x_coords = {1, 2, 3, 2, 4};
// y_coords = {2, 3, 1, 2, 3};
// Output:
// Closest pair of indices: [0, 3]
// Expected Output:
// Closest pair of indices: [0, 3]
