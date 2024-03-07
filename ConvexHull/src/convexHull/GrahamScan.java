package convexHull;

/**
 * 
 * @author Luke Fernando
 * 
 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException; 

public class GrahamScan extends ConvexHull
{
	/**
	 * Stack used by Grahma's scan to store the vertices of the convex hull of the points 
	 * scanned so far.  At the end of the scan, it stores the hull vertices in the 
	 * counterclockwise order. 
	 */
	private PureStack<Point> vertexStack;  


	/**
	 * Call corresponding constructor of the super class.  Initialize two variables: algorithm 
	 * (from the class ConvexHull) and vertexStack. 
	 * 
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public GrahamScan(Point[] pts) throws IllegalArgumentException 
	{
		super(pts); 
		this.algorithm = "GrahamScan";
		vertexStack = new ArrayBasedStack<Point>();
	}
	

	/**
	 * Call corresponding constructor of the super class.  Initialize algorithm and vertexStack.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public GrahamScan(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		super(inputFileName); 
		this.algorithm = "GrahamScan";
		vertexStack = new ArrayBasedStack<Point>();
	}

	
	// -------------
	// Graham's scan
	// -------------
	
	/**
	 * This method carries out Graham's scan in several steps below: 
	 * 
	 *     1) Call the private method setUpScan() to sort all the points in the array 
	 *        pointsNoDuplicate[] by polar angle with respect to lowestPoint.    
	 *        
	 *     2) Perform Graham's scan. To initialize the scan, push pointsNoDuplicate[0] and 
	 *        pointsNoDuplicate[1] onto vertexStack.  
	 * 
     *     3) As the scan terminates, vertexStack holds the vertices of the convex hull.  Pop the 
     *        vertices out of the stack and add them to the array hullVertices[], starting at index
     *        vertexStack.size() - 1, and decreasing the index toward 0.    
     *        
     * Two degenerate cases below must be handled: 
     * 
     *     1) The array pointsNoDuplicates[] contains just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) The array contains only collinear points, in which case the hull is the line segment 
     *        connecting the two extreme points.   
	 */
	public void constructHull()
	{
		long startTime = System.nanoTime();
		if (pointsNoDuplicate.length == 1)
		{
			this.hullVertices = new Point[1];
			hullVertices[0] = pointsNoDuplicate[0];
			return;
		}
		if (pointsNoDuplicate.length == 2)
		{
			this.hullVertices = new Point[2];
			hullVertices[0] = pointsNoDuplicate[0];
			hullVertices[1] = pointsNoDuplicate[1];
			return;
		}
		this.setUpScan();//sorts all the points by polar angle
		this.vertexStack.push(pointsNoDuplicate[0]);
		this.vertexStack.push(pointsNoDuplicate[1]);
		Point ref = pointsNoDuplicate[0];
		Point p1 = pointsNoDuplicate[1];
		int i = 2;
		Point p2 = pointsNoDuplicate[i];
		while (i < pointsNoDuplicate.length)
		{
			PolarAngleComparator comp = new PolarAngleComparator(ref, true);
			int result = comp.compare(p1, p2);
			if (result == -1) //left turn
			{
				vertexStack.push(p2);
				ref = p1;
				p1 = p2;
				i++;
				if (i < pointsNoDuplicate.length)
					p2 = pointsNoDuplicate[i];
			}
			else if (result == 1) //right turn
			{
				p1 = ref;
				vertexStack.pop();
				vertexStack.pop();
				ref = vertexStack.peek();
				vertexStack.push(p1);
			}
		}
		
		hullVertices = new Point[vertexStack.size()];
		for (int j = hullVertices.length-1; j >= 0; j--)
		{
			hullVertices[j] = vertexStack.pop();
		}
		long endTime = System.nanoTime();
		this.time = endTime - startTime;
	}
	
	
	/**
	 * Set the variable quicksorter from the class ConvexHull to sort by polar angle with respect 
	 * to lowestPoint, and call quickSort() from the QuickSortPoints class on pointsNoDuplicate[]. 
	 * The argument supplied to quickSort() is an object created by the constructor call 
	 * PolarAngleComparator(lowestPoint, true).       
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 *
	 */
	public void setUpScan()
	{
		quicksorter = new QuickSortPoints(pointsNoDuplicate);
		PolarAngleComparator comp = new PolarAngleComparator(lowestPoint, true);
		quicksorter.quickSort(comp);
		pointsNoDuplicate = quicksorter.getSortedPoints(pointsNoDuplicate);
	}	
}
