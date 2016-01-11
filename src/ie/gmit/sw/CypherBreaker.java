//CHRISTOPHER WEIR - G00309429
package ie.gmit.sw;
import java.util.*;
import java.util.concurrent.*;

public class CypherBreaker
{
    private static final int MAX_QUEUE_SIZE = 100;
    private BlockingQueue<Resultable> queue; //blocking queue will store resultable types
    private TextScorer test;
    private String cypherText;
    private Object lock = new Object();
    private int count = 2;//counter to be incremented
    private Resultable result = new Result(" ", 0, -1000.00);
    
    public CypherBreaker(String cypherText, TextScorer test)
    {
        queue = new ArrayBlockingQueue<Resultable>(MAX_QUEUE_SIZE);
        this.cypherText = cypherText;
        this.test = test;
        init();
    }
    
    public void increment()
    {
    	synchronized(lock)
    	{
    		count++;
    		if(count == cypherText.length()/2)
    		{
    			try 
    			{
					queue.put(new PoisonResult(" ", 0, -1000.00));//adds poison object to the end, and kills the queue.
				} 
    			catch (InterruptedException e) 
    			{
					e.printStackTrace();
				}
    		}
    	}
    }
    
    public void init()
    {
        //start producers
        for(int i = 2; i<=cypherText.length()/2; i++)
        {
            new Thread(new Decryptor(queue, cypherText, i, test)).start();
        }
        
        new Thread(new Runnable()
        {
           
            public void run()
            {
                while(!queue.isEmpty())
                {
                    try 
                    {
                    	Thread.sleep(150);
						Resultable r = queue.take();
						System.out.println("Took result for key " +r.getKey());
						if(r instanceof PoisonResult){
							System.out.println(result.getPlainText());
							System.out.println(result.getKey());
							System.out.println(result.getScore());
							
							return;
						}
						if(result.getScore()< r.getScore())
						{
							result = r;
							System.out.println("got new high score for key " + result.getKey());
						}
						increment();
						
					} 
                    catch (InterruptedException e) 
                    {
						e.printStackTrace();
					}
                }
            }
            
        }).start();
        
        
    }
    
}