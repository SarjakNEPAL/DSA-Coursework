public class q2A {

    public static void main(String[] args) {
        int totalEmployees = 100;
        double totalBudget = 100000;

        // Define percentages for each category
        double topPerformersPercentage = 0.50; // 50% of the budget
        double averagePerformersPercentage = 0.30; // 30% of the budget
        double lowPerformersPercentage = 0.20; // 20% of the budget

        // Calculate the number of employees in each category
        int topPerformersCount = (int) (totalEmployees * 0.20); // 20% of employees
        int averagePerformersCount = (int) (totalEmployees * 0.60); // 60% of employees
        int lowPerformersCount = totalEmployees - topPerformersCount - averagePerformersCount; // Remaining

        // Calculate the budget allocation for each category
        double topPerformersBudget = totalBudget * topPerformersPercentage;
        double averagePerformersBudget = totalBudget * averagePerformersPercentage;
        double lowPerformersBudget = totalBudget * lowPerformersPercentage;

        // Print the results
        System.out.println("Total Employees: " + totalEmployees);
        System.out.println("Top Performers (Count: " + topPerformersCount + "): $" + topPerformersBudget);
        System.out.println("Average Performers (Count: " + averagePerformersCount + "): $" + averagePerformersBudget);
        System.out.println("Low Performers (Count: " + lowPerformersCount + "): $" + lowPerformersBudget);
    }
}