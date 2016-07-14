
package trustbean.queue;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This is an Array Blocking Queue Implementation. Here we are simulating a case where Number of shoppers are waiting in a billing queue.
 * They are waiting for their turns to come one by one and get exited from the billing counter.
 * 
 * @author atapas
 *
 */
public class ArrayBQ {
    
	// This is just for the print purpose
    private static final String MAGIC_STARS = "*******************************************";
    
    // This indicates the number of shoppers in the queue
    private static final int NUMBER_OF_SHOPPERS = 6;
    
    private BlockingQueue<String> shoppingQueue;
    
    private List<String> shoppers;
    
    public ArrayBQ() {
    	// Instantiate an Array Blocking Queue which can contain maximum of 100 elements at a time.
        shoppingQueue = new ArrayBlockingQueue<String>(100);
        
        // Lets make our shoppers list
        shoppers = new ArrayList<String>();
        for (int i = 0; i < NUMBER_OF_SHOPPERS; i++) {
        	// Basically "Shopper 1" , "Shopper 2" and so on..
        	shoppers.add("Shopper " + (i+1));
		}
        
    }
   
    /**
     * Entering the Queue
     * @throws InterruptedException
     */
    private void enterQueue() throws InterruptedException
    {
        for (String shopper : shoppers) {
            System.out.println(shopper
                    + " in entering the queue. Happy Shopping!!");
            System.out.println(MAGIC_STARS);
            
            shoppingQueue.put(shopper);
            
            Thread.sleep(1000);
            
            int numberOfShoppers = shoppingQueue.size();
            
            System.out
                    .println("Now we have " + numberOfShoppers + " shoppers in the queue. last person joined the queue is " + shopper);
            System.out.println(MAGIC_STARS);
        }
    }
    
    /**
     * Exiting the Queue
     * @throws InterruptedException
     */
    private void leaveQueue() throws InterruptedException
    {
         String done =  shoppingQueue.take();
         System.out.println(done
                + " done with the shopping and left the queue. See you again!!");
         System.out.println(MAGIC_STARS);
    }
    
    
    private class Entry implements Runnable{
        @Override
        public void run() {
            try {
                enterQueue();
            } catch (InterruptedException e) {
                System.out.println("Some problem, let's call the manager!! "
                        + e.getMessage());
            }
            
        }
        
    }
    
    private class Exit implements Runnable{
        @Override
        public void run() {
            try {
                for (int i = 0; i < shoppers.size(); i++) {
                    leaveQueue();
                }
            } catch (InterruptedException e) {
                System.out.println("Some problem, let's call the manager!! "
                        + e.getMessage());
            }
            
        }
        
    }
    
    private void startQueueOrganizer()
    {
        Entry entry = new Entry();
        Exit exit = new Exit();
        
        Thread enteryThread = new Thread(entry);
        Thread exitThread = new Thread(exit);
        
        enteryThread.start();
        exitThread.start();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
       
        ArrayBQ arrayBQ = new ArrayBQ();
        arrayBQ.startQueueOrganizer();

    }

}