package convexHull;

/**
 *  
 * @author Luke Fernando
 *
 */

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.InputMismatchException; 
import java.io.PrintWriter;
import java.util.Scanner;



/**
 * 
 * This class implements construction of the convex hull of a finite number of points. 
 *
 */

public abstract class ConvexHull 
{
	// ---------------|
	// Data Structures|
	// ---------------|
	protected String algorithm;  // Its value is either "Graham's scan" or "Jarvis' march". 
	                             // Initialized by a subclass.
	
	protected long time;         // execution time in nanoseconds
	
	/**
	 * The array points[] holds an input set of Points, which may be randomly generated or 
	 * input from a file.  Duplicates are possible. 
	 */
	private Point[] points;    
	

	/**
	 * Lowest point from points[]; and in case of a tie, the leftmost one of all such points. 
	 * To be set by a constructor. 
	 */
	protected Point lowestPoint; 

	
	/**
	 * This array stores the same set of points from points[] with all duplicates removed. 
	 * These are the points on which Graham's scan and Jarvis' march will be performed. 
	 */
	 protected Point[] pointsNoDuplicate; 
	
	
	/**
	 * Vertices of the convex hull in counterclockwise order are stored in the array 
	 * hullVertices[], with hullVertices[0] storing lowestPoint. 
	 */
	protected Point[] hullVertices;
	
	
	protected QuickSortPoints quicksorter;  // used (and reset) by this class and its subclass GrahamScan

	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Constructor over an array of points.  
	 * 
	 *    1) Store the points in the private array points[].
	 *    
	 *    2) Initialize quicksorter. 
	 *    
	 *    3) Call removeDuplicates() to store distinct points from the input in pointsNoDuplicate[].
	 *    
	 *    4) Set lowestPoint to pointsNoDuplicate[0]. 
	 * 
	 * @param pts
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public ConvexHull(Point[] pts) throws IllegalArgumentException 
	{
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++)
		{
			points[i] = new Point(pts[i]);
			
		}
		quicksorter = new QuickSortPoints(points);
		this.removeDuplicates();
		lowestPoint = pointsNoDuplicate[0];
	}
	
	
	/**
	 * Read integers from an input file.  Every pair of integers represent the x- and y-coordinates 
	 * of a point.  Generate the points and store them in the private array points[]. The total 
	 * number of integers in the file must be even.
	 * 
	 * You may declare a Scanner object and call its methods such as hasNext(), hasNextInt() 
	 * and nextInt(). An ArrayList may be used to store the input integers as they are read in 
	 * from the file.  
	 * 
	 * Perform the operations 1)-4) described for the previous constructor. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public ConvexHull(String inputFileName) throws FileNotFoundException, InputMismatchException
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
		quicksorter = new QuickSortPoints(points);
		this.removeDuplicates();
		lowestPoint = pointsNoDuplicate[0];
	}

	
	/**
	 * Construct the convex hull of the points in the array pointsNoDuplicate[]. 
	 */
	public abstract void constructHull(); 

	
		
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <convex hull algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * Graham's scan   1000	  9200867
	 *  
	 * Use the spacing in the sample run in Section 5 of the project description. 
	 */
	public String stats()
	{
		String stats = algorithm + "   " + points.length + "   " + "NEED TO FIND TIME";
		return stats;
	}
	
	
	/**
	 * The string displays the convex hull with vertices in counterclockwise order starting at  
	 * lowestPoint.  When printed out, it will list five points per line with three blanks in 
	 * between. Every point appears in the format "(x, y)".  
	 * 
	 * For illustration, the convex hull example in the project description will have its 
	 * toString() generate the output below: 
	 * 
	 * (-7, -10)   (0, -10)   (10, 5)   (0, 8)   (-10, 0)   
	 * 
	 * lowestPoint is listed only ONCE. 
	 *  
	 * Called only after constructHull(). 
	 * 
	 *  @throws IllegalStateException if ConstructHull() has not been called before
	 */
	public String toString() throws IllegalStateException
	{
		if (hullVertices == null)
			throw new IllegalStateException("must call ConstructHull before calling toString.");
		
		String s = "";
		for (int i = 0; i < hullVertices.length; i++)
		{
			if (i % 5 != 0) 
			{
				if ((i + 1) % 5 == 0)
					s = s + hullVertices[i].toString();
				else
					s = s + hullVertices[i].toString() + "   ";
			}
			else
			{
				s = s + "\n";
			}
		}
		return s;
	}
	
	
	/** 
	 * 
	 * Writes to the file "hull.txt" the vertices of the constructed convex hull in counterclockwise 
	 * order.  These vertices are in the array hullVertices[], starting with lowestPoint.  Every line
	 * in the file displays the x and y coordinates of only one point.  
	 * 
	 * For instance, the file "hull.txt" generated for the convex hull example in the project 
	 * description will have the following content: 
	 * 
     *  -7 -10 
     *  0 -10
     *  10 5
     *  0  8
     *  -10 0
	 * 
	 * The generated file is useful for debugging as well as grading. 
	 * 
	 * Called only after constructHull().  
	 * 
	 * 
	 * @throws IllegalStateException  if hullVertices[] has not been populated (i.e., the convex 
	 *                                   hull has not been constructed)
	 * @throws FileNotFoundException  if file cannot be found when creating print file
	 */
	public void writeHullToFile() throws IllegalStateException, FileNotFoundException 
	{
		if (hullVertices == null)
			throw new IllegalStateException("hullVertices[] has not been populated");
		PrintWriter print = new PrintWriter("hull.txt");
		for (int i = 0; i < hullVertices.length; i++)
		{
			print.println(hullVertices[i].toString());
		}
		print.close();
	}
	

	/**
	 * Draw the points and their convex hull.  This method is called after construction of the 
	 * convex hull.  You just need to make use of hullVertices[] to generate a list of segments 
	 * as the edges. Then create a Plot object to call the method myFrame().  
	 * 
	 * @throws IllegalStateException if construct hull has not been called
	 */
	public void draw() throws IllegalStateException
	{		
		if (hullVertices == null)
			throw new IllegalStateException("hullVertices[] has not been populated");
		int numSegs = hullVertices.length;  // number of segments to draw 

		// Based on Section 4, generate the line segments to draw for display of the convex hull.
		// Assign their number to numSegs, and store them in segments[] in the order. 
		Segment[] segments = new Segment[numSegs]; 
		
		for (int i = 0; i < hullVertices.length - 1; i++)
		{
			segments[i] = new Segment(hullVertices[i], hullVertices[i+1]);
		}
		int lastInd = hullVertices.length-1;
		segments[lastInd] = new Segment(hullVertices[lastInd], hullVertices[0]);
		
		// The following statement creates a window to display the convex hull.
		Plot.myFrame(pointsNoDuplicate, segments, getClass().getName());
		
	}

		
	/**
	 * Sort the array points[] by y-coordinate in increasing order.  Have quicksorter 
	 * invoke quicksort() with a comparator object which uses the compareTo() method of the Point 
	 * class. Copy the sorted sequence onto the array pointsNoDuplicate[] with duplicates removed.
	 *     
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void removeDuplicates()
	{
		YComparator ycomp = new YComparator();
		quicksorter.quickSort(ycomp);
		Point[] sorted = quicksorter.getSortedPoints(points);
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < points.length; i++)
		{
			//copying the points array to an array list
			list.add(sorted[i]);
		}
		//removing duplicates from the arraylist
		for (int i = 0; i < list.size() - 1; i++)
		{
			Point current = list.get(i);
			Point successor = list.get(i + 1);
			if (current.equals(successor))
			{
				list.remove(i+1);
			}
		}
		//add points to pointsNoDuplicate[] array
		pointsNoDuplicate = new Point[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			pointsNoDuplicate[i] = list.get(i);
		}
	}
}
