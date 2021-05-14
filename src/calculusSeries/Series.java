package calculusSeries;

import java.math.*;

public class Series implements Runnable
{
	private static boolean e;
	private static boolean sin;
	private static int numOfThreads = 0; //Number of threads that will be used
	private static int done = 0; //Counter that tells the thread that finishes last to print the calculated value
	private Thread t; //Thread stuff
	private int n; //Number of terms to be calculated in total
	private double val; //The x input of the value
	private static BigDecimal bigSum = new BigDecimal("0"); //The sum value that gets added to with each iteration of each thread
	private String thread; //Private name of the thread
	private static int num = 0; //Counter for the PIV number below to keep track of the threads generated and label them
	private int number; //PIV that is the number of the given thread
	
	/**
	 * Series object that uses multi threading for better speed
	 * @param value Value you want to find via the series
	 * @param terms The amount of terms you want to take the partial sum of
	 */
	Series(double value, int terms)
	{
		this.n = terms;
		this.val = value;
		num++;
		this.thread = "Thread " + num;
		this.number = num;
	}
	
	
	/**
	 * Run's the thread and tells the thread what section of the series to solve
	 */
	public void run()
	{
		System.out.println(this.thread +" is running");
		try
		{
			if (this.number == 1)
			{
				if (sin)
				{
					sin(this.val, 0, this.n / numOfThreads);
				}
				else if (e)
				{
					e(this.val, 0, this.n / numOfThreads);
				}
			}
			else
			{
				if (sin)
				{
					sin(this.val, ((this.n * (this.number - 1) / numOfThreads) + 1), this.n * this.number / numOfThreads);
				}
				else if (e)
				{
					e(this.val, ((this.n * (this.number - 1) / numOfThreads) + 1), this.n * this.number / numOfThreads);
				}
			}
			Thread.sleep(50);
		}
		catch (InterruptedException e)
		{
			System.out.println(this.thread + " interrupted");
		}
		System.out.println(this.thread + " is out");
		done = done + 1;
		if (done == num)
		{
			System.out.println(bigSum.toPlainString());
		}
		
	}
	
	/**
	 * Starts the thread and checks for existence of the thread
	 */
	public void start()
	{
		System.out.println(this.thread + " is starting");
		if (t == null)
		{
			t = new Thread(this, this.thread);
			t.start();
		}
	}
	
	/**
	 * Returns the factorial of the given input
	 * @param i Input number to take the factorial of
	 * @return Factorial of the given input number i
	 */
	public static BigDecimal fac(long i)
	{
		BigDecimal total = new BigDecimal("1");
		for (long j = 0; j <= i; j++)
		{
			if (j != 0)
			{
				total = total.multiply(new BigDecimal(j));
			}
		}
		return total;
	}
	
	/**
	 * Calculates the value of e
	 * @param a Number to put e to the power of
	 * @param b Lower bound of the taylor series
	 * @param n Upper bound of the taylor series
	 */
	public void e(double a, int b, int n)
	{
		BigDecimal sum = new BigDecimal("0");
		for (long i = b; i <= n; i++)
		//for (long i = b; i <= n; i+=numOfThreads)
		{
			sum = sum.add(new BigDecimal(a).pow((int) i).divide(fac(i), new MathContext(1500, RoundingMode.HALF_EVEN)));
		}
		bigSum = bigSum.add(sum,  new MathContext(800, RoundingMode.HALF_UP));
	}

	/**
	 * rat function
	 * @param num
	 * @param param
	 * @param n
	 */
	public void flint(double a, int b, int n)
	{
		BigDecimal sum = new BigDecimal("0");
		for (long i = b; i <= n; i++)
		//for (long i = b; i <= n; i+=numOfThreads)
		{

		}
		bigSum = bigSum.add(sum,  new MathContext(800, RoundingMode.HALF_UP));
	}

	public void sin(double num, int param, int n)
	{
		int b;
		BigDecimal sum = new BigDecimal("0");
		for (b = param; b <= n; b++)
		{
			if (b % 2 == 0)
			{
				sum = sum.add(new BigDecimal(num + "").pow(2 * b + 1).divide(new BigDecimal("" + fac(2 * b + 1)), new MathContext(10000, RoundingMode.HALF_UP)));
			}
			else
			{
				sum = sum.subtract(new BigDecimal(num + "").pow(2 * b + 1).divide(new BigDecimal("" + fac(2 * b + 1)), new MathContext(10000, RoundingMode.HALF_UP)));

			}
		}
		bigSum = bigSum.add(sum,  new MathContext(10000, RoundingMode.HALF_UP));
	}
    
    /**
     * Basic getter for the bigSum object
     * @return The value of the calculated series/function
     */
    public static BigDecimal getSum()
    {
    	return bigSum;
    }
    
    /**
     * Sets the amount of threads to be used
     * @param n The amount of threads
     */
    public static void setThreads(int n)
    {
    	numOfThreads = n;
    }

	/**
	 * Sets which function to use and calculate
	 * @param s String representing the function to be used
	 */
	public static void setType(String s)
    {
    	s = s.toLowerCase();
    	if (s.contains("e"))
    	{
    		e = true;
    	}
    	else if (s.contains("i"))
    	{
    		sin = true;
    	}
    }
}
