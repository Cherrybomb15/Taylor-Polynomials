package calculusSeries;

public class SeriesTester
{
	public static void main(String[] args)
	{
		int threads = 2; //The amount of threads to use from your computer. I reccomend 2 or 4
		Series.setThreads(threads);
		Series.setType("e"); //The function to call. For e put "e", for sine put "sin", for square root put "sqrt"

		double number = 1.25; //The x input in the series
		int n = 20000; //The upper bound for the sum
		for (int i = 0; i < threads; i++)
		{
			new Series(number, n).start();
		}
	}
}
