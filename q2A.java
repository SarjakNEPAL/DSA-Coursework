public class q2A {
    // Detailed Approach:
    // A two-pass greedy algorithm algorithm enables the determination of employee reward requirements according to their performance ratings.
    //  Every staff member needs at least one prize and workers whose performance ratings surpass those of their peer employees will get additional prizes.
    
    // The beginning stage creates an array named rewards which matches ratings array size and fills each entry with value 1. 
    // Every member of staff receives minimum one reward during this system.
    
    // The first stage of our algorithm involves moving through the ratings array from the starting position to the right end. 
    // The reward count of an employee increases by one more than their preceding co-worker's reward count when their rating score exceeds the worker ahead of them in the array. 
    // During the first journey through the ratings array we enforce the system that superior ratings will attain more rewards than both their preceding and succeeding workers.
    
    // We move through the ratings array from right to left during the second pass operation. 
    // Each employee who gets a rating exceeding their succeeding employee will see their reward count reach its maximum value between their present
    //  reward count and the reward count of one more than the next employee. The algorithm meets its requirement by allowing higher rating scores to 
    //  obtain a higher number of rewards compared to their adjacent right value in the assessment.
    
    // At the conclusion we merge all rewards array values to determine the needed total reward count.

//     Step-by-Step Approach:
// Initialize the Rewards Array:

// Construct an array rewards that matches the length of ratings by initializing its elements all to one. 
// The number of employee rewards is guaranteed to be at minimum one for each worker.
// First Pass (Left to Right):

// The ratings array should be iterated using index values from 1 up to n-1.
// If ratings[i] > ratings[i - 1], set rewards[i] = rewards[i - 1] + 1.
//  During this process the present employee receives additional rewards than what the previous employee obtained.
// Second Pass (Right to Left):

// The loop will run through each rating starting from n-2 to the index of 0.
// If ratings[i] > ratings[i + 1], set rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1).
//  This employee reward system provides current employees with higher reward levels than successor employees when their evaluated rating exceeds the previous.
// Calculate Total Rewards:

// The program establishes totalRewards as a starting value equal to zero before it performs the summation of 
// all rewards array data to calculate the complete reward count.
// Output the Result:

// Present the overall number of rewards to the user.

    public static int minRewards(int[] ratings) {
        int n = ratings.length; // Get the number of employees
        int[] rewards = new int[n]; // Create an array to hold rewards for each employee

        // Step 1: Initialize rewards array
        for (int i = 0; i < n; i++) {
            rewards[i] = 1; // Every employee gets at least one reward
        }

        // Step 2: First Pass (Left to Right)
        for (int i = 1; i < n; i++) {
            // If the current employee has a higher rating than the previous one
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1; // Give them more rewards than the previous employee
            }
        }

        // Step 3: Second Pass (Right to Left)
        for (int i = n - 2; i >= 0; i--) {
            // If the current employee has a higher rating than the next one
            if (ratings[i] > ratings[i + 1]) {
                // Ensure they have more rewards than the next employee
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }

        // Step 4: Calculate Total Rewards
        int totalRewards = 0; // Initialize total rewards counter
        for (int reward : rewards) {
            totalRewards += reward; // Sum up all the rewards
        }

        // Step 5: Return the total number of rewards
        return totalRewards;
    }

    public static void main(String[] args) {
        // Test with example 1
        int[] ratings1 = {1, 0, 2};
        System.out.println("Total rewards needed: " + minRewards(ratings1)); // Output: 5

        // Test with example 2
        int[] ratings2 = {1, 2, 2};
        System.out.println("Total rewards needed: " + minRewards(ratings2)); // Output: 4
    }
}

// Output:
// Total rewards needed: 5
// Total rewards needed: 4
// Expected Output:
// Total rewards needed: 5
// Total rewards needed: 4
// Explanation:
// In the first example, the minimum rewards needed are [1, 0, 2] = 5.
// In the second example, the minimum rewards needed are [1, 2, 2] = 4.

