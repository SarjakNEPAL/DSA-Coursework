/*
 * Here in this program, we are trying to solve the problem of printing a series of numbers in a specific order: zero, even numbers, 
 * and odd numbers, until a certain integer ( n ). The provided NumberPrinter class has three methods: printZero, printEven, and printOdd, 
 * that print the respective numbers. The most significant problem is handling three threads such that they print in the correct order without any race 
 * conditions. To achieve this, we use synchronization techniques in Java. The approach is to use a lock object to synchronize access to shared resources 
 * and boolean flags to know whose turn it is to print. This will make the threads wait for their turn and print the numbers in the desired order.
 */

/*
 * Step-by-Step Explanation
Define the NumberPrinter Class:

It has three methods: printZero, printEven, and printOdd. These print their respective numbers to the console.
Define the Q6A Class:

This class is the thread coordinator. It has:
An integer n for specifying the upper bound of the sequence.
A NumberPrinter object for invoking the print methods.
A lock object for synchronisation.
Two boolean flags: zeroTurn and evenTurn for the control of sequence of printing.
Constructor of Q6A

The constructor initializes the n and printer fields.
Implement the zeroThread Method:

This method runs a loop from 0 to ( n-1 ).
It checks if it is the turn of zero to print.
If yes, it prints zero and initializes the flags to indicate if it is time for even or odd numbers to print.
It then notifies the other threads.
Implement the evenThread Method:

This approach prints the even numbers between 2 and ( n ).
It checks whether it is time to print the even's turn.
If not, it waits.
It makes the zeroTurn flag equal to true and notifies other threads after printing an even number.
Implement the oddThread Method:

This method prints the odd numbers between 1 and ( n ).
It checks the flags to see whether it is the odd's turn before printing.
On printing, it sets the zeroTurn flag to true and notifies the other threads.
Main Method:

In the main method, we create an array of values for ( n ) to show the functionality.
For each value of ( n ):
We create Q6A.
We create threads for printing zero, even, and odd.
We start these threads and wait for all of them to finish with join().
Finally, we print a newline to mark the outputs for different values of ( n ).
This code effectively demonstrates how multithreading and synchronization in Java can be used to print numbers in a specific order without any race conditions.
 */


// Define the NumberPrinter class with methods to print zero, even, and odd numbers.
class NumberPrinter {
    public void printZero() {
        System.out.print(0); // Print 0 to the console
    }

    public void printEven(int num) {
        System.out.print(num); // Print an even number to the console
    }

    public void printOdd(int num) {
        System.out.print(num); // Print an odd number to the console
    }
}

// Define the main class Q6A to coordinate threads
public class Q6A {
    private int n; // Number up to which we need to print the sequence
    private NumberPrinter printer; // Instance of NumberPrinter to print numbers
    private final Object lock = new Object(); // Object for synchronization
    private boolean zeroTurn = true; // Flag to track if it's zero's turn
    private boolean evenTurn = false; // Flag to track if it's even's turn

    // Constructor to initialize the class with n and printer
    public Q6A(int n, NumberPrinter printer) {
        this.n = n;
        this.printer = printer;
    }

    // Method for the zero thread
    public void zeroThread() {
        for (int i = 0; i < n; i++) {
            synchronized (lock) { // Synchronize the block
                while (!zeroTurn) { // Check if it's not zero's turn
                    try {
                        lock.wait(); // Wait for the turn
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle interruption
                    }
                }
                printer.printZero(); // Print zero
                zeroTurn = false; // Set zeroTurn to false
                if (i % 2 == 0) {
                    evenTurn = false; // Set evenTurn to false if index is even
                } else {
                    evenTurn = true; // Set evenTurn to true if index is odd
                }
                lock.notifyAll(); // Notify all waiting threads
            }
        }
    }

    // Method for the even thread
    public void evenThread() {
        for (int i = 2; i <= n; i += 2) {
            synchronized (lock) { // Synchronize the block
                while (zeroTurn || !evenTurn) { // Check if it's zero's turn or not even's turn
                    try {
                        lock.wait(); // Wait for the turn
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle interruption
                    }
                }
                printer.printEven(i); // Print an even number
                zeroTurn = true; // Set zeroTurn to true
                lock.notifyAll(); // Notify all waiting threads
            }
        }
    }

    // Method for the odd thread
    public void oddThread() {
        for (int i = 1; i <= n; i += 2) {
            synchronized (lock) { // Synchronize the block
                while (zeroTurn || evenTurn) { // Check if it's zero's turn or even's turn
                    try {
                        lock.wait(); // Wait for the turn
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Handle interruption
                    }
                }
                printer.printOdd(i); // Print an odd number
                zeroTurn = true; // Set zeroTurn to true
                lock.notifyAll(); // Notify all waiting threads
            }
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        int[] values = {5, 3, 6}; // Static values for n
        NumberPrinter printer = new NumberPrinter(); // Create an instance of NumberPrinter

        // Loop through each value in the values array
        for (int n : values) {
            System.out.println("Printing sequence for n = " + n + ":"); // Print the prompt for the current sequence
            Q6A controller = new Q6A(n, printer); // Create an instance of Q6A with current n and printer

            Thread zeroThread = new Thread(controller::zeroThread); // Create a thread for the zeroThread method
            Thread evenThread = new Thread(controller::evenThread); // Create a thread for the evenThread method
            Thread oddThread = new Thread(controller::oddThread); // Create a thread for the oddThread method

            zeroThread.start(); // Start the zeroThread
            evenThread.start(); // Start the evenThread
            oddThread.start(); // Start the oddThread

            try {
                zeroThread.join(); // Wait for zeroThread to finish
                evenThread.join(); // Wait for evenThread to finish
                oddThread.join(); // Wait for oddThread to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interruption
            }

            // Print a new line between sequences
            System.out.println("\n");
        }
    }
}

/*


output
 * Printing sequence for n = 5:
0102030405

Printing sequence for n = 3:
010203

Printing sequence for n = 6:
010203040506

 * 
 */