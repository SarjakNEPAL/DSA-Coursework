public class q1B {

    public static int findKthSmallest(int[] arr1, int[] arr2, int k) {
        int len1 = arr1.length;
        int len2 = arr2.length;

        // Ensure arr1 is the smaller array
        if (len1 > len2) {
            return findKthSmallest(arr2, arr1, k);
        }

        int left = 0, right = len1;

        while (left < right) {
            int partition1 = (left + right) / 2;
            int partition2 = k - partition1;

            // If partition2 is out of bounds, set to -infinity or +infinity
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : arr1[partition1 - 1];
            int minRight1 = (partition1 == len1) ? Integer.MAX_VALUE : arr1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : arr2[partition2 - 1];
            int minRight2 = (partition2 == len2) ? Integer.MAX_VALUE : arr2[partition2];

            // Check if we have found the correct partitions
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                return Math.max(maxLeft1, maxLeft2);
            } else if (maxLeft1 > minRight2) {
                // Move towards the left in arr1
                right = partition1;
            } else {
                // Move towards the right in arr1
                left = partition1 + 1;
            }
        }

        // If we exit the loop, it means we found the kth element
        return -1; // This line should never be reached if inputs are valid
    }

    public static void main(String[] args) {
        int[] arr1 = {2, 5};
        int[] arr2 = {3, 4};
        int k = 2;
        System.out.println(findKthSmallest(arr1, arr2, k)); // Output: 4

        int[] arr3 = {-4, -2, 0, 3};
        int[] arr4 = {2, 4};
        k = 6;
        System.out.println(findKthSmallest(arr3, arr4, k)); // Output: 4
    }
}