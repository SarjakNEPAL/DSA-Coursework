import java.util.*;

public class q3A {
/*
 * Detailed Approach:
The network needs a solution which uses_minimum expenses by applying direct device connection costs while handling
 expenses of module installation on network devices. The network requires a solution that identifies cost-effective methods for device
  communication that includes direct connections or module installation.

A network consisting of devices with their linkages can be shown using a graphical structure. The network represents a set of 
nodes where each device constitutes a node and the cost-based connections function as weighted edges between nodes.

The array will serve as a storage unit for determining the minimum cost needed to link every device.
 The total includes both the price of device communication modules as well as expenses involved in linking to external devices.

The program will perform an iterative operation on module expenses and connection fees to modify the minimum cost information.

The total cost calculation will finalize by adding the minimum costs from each device to determine the total expense needed for device connection.
 */

/*
 Step-by-Step Approach:
Initialize Variables:

Set array minCost to n + 1 elements to house the minimum connection costs per device as well as the virtual node in the array.
The application needs a boolean array named "connected" containing n + 1 elements to save connectivity status about each device.
The initial values of minCost will use Integer.MAX_VALUE to mark unknown costs while the virtual node sets to 0.
Add Module Costs:

The program will adjust minCost values for each element in modules by evaluating installed communication module expenses. For device i, set minCost[i + 1] to the minimum of its current value and modules[i].
Add Connection Costs:

For each connection in the connections array apply the direct cost information to update the minCost of associated devices. The minimum cost calculation should update minCost for both components in each connection.
Calculate Total Cost:

Initialize a variable totalCost to 0. TotalCost will receive the minimum cost from each unconnected device while the device mark gets set as connected during this process.
Return the Result:

The totalCost variable holds the minimum value required for complete device connection.
 */
 
    public static int minCost(int n, int[] modules, int[][] connections) {
        // Create an array to store the minimum cost to connect each device
        int[] minCost = new int[n + 1]; // +1 for the virtual node
        boolean[] connected = new boolean[n + 1]; // Track connected devices
        Arrays.fill(minCost, Integer.MAX_VALUE); // Initialize costs to a large value
        minCost[0] = 0; // Cost to connect the virtual node is 0

        // Add module costs to the minCost array
        for (int i = 0; i < n; i++) {
            minCost[i + 1] = Math.min(minCost[i + 1], modules[i]);
        }

        // Connect devices based on the connections array
        for (int[] connection : connections) {
            int device1 = connection[0];
            int device2 = connection[1];
            int cost = connection[2];
            // Update the minimum cost for both devices
            minCost[device1] = Math.min(minCost[device1], cost);
            minCost[device2] = Math.min(minCost[device2], cost);
        }

        // Total cost to connect all devices
        int totalCost = 0;

        // Connect devices using the minimum costs
        for (int i = 0; i <= n; i++) {
            if (!connected[i]) {
                totalCost += minCost[i]; // Add the minimum cost for this device
                connected[i] = true; // Mark this device as connected
            }
        }

        return totalCost; // Return the total cost
    }

    public static void main(String[] args) {
        // Example input
        int n = 3;
        int[] modules = {1, 2, 2};
        int[][] connections = {{1, 2, 1}, {2, 3, 1}};

        // Find and print the minimum total cost to connect all devices
        int result = minCost(n, modules, connections);
        System.out.println("Minimum total cost: " + result); // Expected Output: 3
    }
}