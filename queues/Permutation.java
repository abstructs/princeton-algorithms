/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Excepted an intereger K");
            System.out.println("Usage example: Permutation 8");
            return;
        }

        int k = Integer.parseInt(args[0]);

        Permutation.getAndPrintCharacters(k);
    }

    private static void getAndPrintCharacters(int k) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while(!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        for(int i = 0; i < k; i++)
            System.out.println(randomizedQueue.dequeue());
    }
}
