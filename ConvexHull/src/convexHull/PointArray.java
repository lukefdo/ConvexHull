package convexHull;

/**
 * 
 * @author Luke Fernando
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;



/**
 * 
 * Class that makes a Point array from reading a file. only used for Junit tests
 *
 */

public class PointArray {

	public Point[] points;
	
	public PointArray(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		ArrayList<Point> list = new ArrayList<Point>();
		File file = new File(inputFileName);
		@SuppressWarnings("resource")
		Scanner input = new Scanner(file);
		
		while (input.hasNextInt())
		{
			int x = input.nextInt();
			if (!input.hasNextInt())
			{
				throw new InputMismatchException("input file contains an odd number of integers");
			}
			int y  = input.nextInt();
			list.add(new Point(x, y));
		}
		input.close();
		
		points = new Point[list.size()];
		for (int i = 0; i < points.length; i++)
		{
			points[i] = list.get(i);
		}
	}
}
