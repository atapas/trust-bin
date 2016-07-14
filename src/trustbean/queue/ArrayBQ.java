
package trustbean.queue;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This is an Array Blocking Queue Impementation
 * @author atapas
 *
 */
public class ArrayBQ {
    
    private static final String MAGIC_STARS = "*******************************************";
    
    private BlockingQueue<String> shoppingQueue;
    
    private List<String> shoppers;
    
    public ArrayBQ() {
        shoppingQueue = new ArrayBlockingQueue<String>(100);
        shoppers = new ArrayList<String>();
        shoppers.add("Tapas");
        shoppers.add("Harry");
        shoppers.add("Brian");
        shoppers.add("Sam");
        shoppers.add("John");
        shoppers.add("Pepper");
    }
    
    private void enterQueue() throws InterruptedException
    {
        for (String shopper : shoppers) {
            System.out.println("Shopper " + shopper
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
    
    private void leaveQueue() throws InterruptedException
    {
         String done =  shoppingQueue.take();
         System.out.println("Shopper " + done
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