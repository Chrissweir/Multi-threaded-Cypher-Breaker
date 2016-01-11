//CHRISTOPHER WEIR - G00309429
package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;

public class Decryptor implements Runnable
{
	private BlockingQueue<Resultable> queue;
	private String cypherText;
	private int key;
	private TextScorer test;

	public Decryptor(BlockingQueue<Resultable> queue, String cypherText, int key, TextScorer test)
	{
		super();
		this.queue = queue;
		this.cypherText = cypherText;
		this.key = key;
		this.test = test;
	}

	public void run()
	{
		RailFence rail = new RailFence();
		String plainText = rail.decrypt(cypherText, key);

		//get score
		double score = test.getScore(plainText);
		Resultable result = new Result(plainText, key, score);

		try 
		{
			System.out.println("Put in the score for the key " +key + " in the queue");
			queue.put(result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread for " + key + " is done");
	}
}