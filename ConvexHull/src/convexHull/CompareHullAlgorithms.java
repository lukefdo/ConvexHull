package convexHull;

/**
 *  
 * @author Luke Fernando
 *
 */

/**
 * 
 * This class executes two convex hull algorithms: Graham's scan and Jarvis' march, over randomly
 * generated integers as well integers from a file input. It compares the execution times of 
 * these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random; 


public class CompareHullAlgorithms 
{
	/**
	 * Repeatedly take points either randomly generated or read from files. Perform Graham's scan and 
	 * Jarvis' march over the input set of points, comparing their performances.  
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 **/
	public static void main(String[] args) throws InputMismatchException, FileNotFoundException 
	{		
		// 
		// Conducts multiple rounds of convex hull construction. Within each round, performs the following: 
		// 
		//    1) If the input are random points, calls generateRandomPoints() to initialize an array 
		//       pts[] of random points. Use pts[] to create two objects of GrahamScan and JarvisMarch, 
		//       respectively.
		//
		//    2) If the input is from a file, construct two objects of the classes GrahamScan and  
		//       JarvisMarch, respectively, using the file.     
		//
		//    3) Have each object call constructHull() to build the convex hull of the input points.
		//
		//    4) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 5 of the project description. 
		// 	
		int i = 1;
		String line = "---------------------------------------"; 
		System.out.println("Comparison between Convex Hull Algorithms\n");
		System.out.print("Trial 1: ");
		Scanner input = new Scanner(System.in);
		while (input.hasNextInt())
		{
			int gSize = 0;
			int mSize = 0 ;
			long gTime = 0;
			long mTime = 0;
			if (input.nextInt() == 1)
			{
				System.out.print("Enter the number of random points: ");
				int numPoints = input.nextInt();
				Random rand = new Random();
				Point[] points = generateRandomPoints(numPoints, rand);
				GrahamScan graham = new GrahamScan(points);
				JarvisMarch march = new JarvisMarch(points);
				graham.constructHull();
				march.constructHull();
				gSize = graham.pointsNoDuplicate.length;
				mSize = march.pointsNoDuplicate.length;
				gTime = graham.time;
				mTime = march.time;
				graham.draw();
				march.draw();
			}
			else if (input.nextInt() == 2)
			{
				String fileName;
				System.out.println("Points from a file");
				System.out.print("File name: ");
				fileName = input.next();
				GrahamScan graham = new GrahamScan(fileName);
				JarvisMarch march = new JarvisMarch(fileName);
				graham.constructHull();
				march.constructHull();
				gSize = graham.pointsNoDuplicate.length;
				mSize = march.pointsNoDuplicate.length;
				gTime = graham.time;
				mTime = march.time;
			}
			
			System.out.println("algorithim         size        time (ns)");
			System.out.println(line);
			System.out.println("Graham's Scan      " + gSize + "          " + gTime); 
			System.out.println("Jarvis' March      " + mSize + "          " + mTime);
			System.out.println(line);
			i++;
			System.out.println("Trial " + i);
		}
		input.close();
		
		// Within a hull construction round, have each algorithm call the constructHull() and draw()
		// methods in the ConvexHull class.  You can visually check the result. (Windows 
		// have to be closed manually before rerun.)  Also, print out the statistics table 
		// (see Section 5). 
		
	}
	
	
	/**
	 * This method generates a given number of random points.  The coordinates of these points are 
	 * pseudo-random numbers within the range [-50,50] � [-50,50]. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException 
	{ 
		Point[] points = new Point[numPts];
		for (int i = 0; i < numPts; i++)
		{
			int x = rand.nextInt(101) - 50;
			int y = rand.nextInt(101) - 50;
			points[i] = new Point(x, y);
		}
		return points;
	}
}
