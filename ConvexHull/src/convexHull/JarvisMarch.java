package convexHull;

/**
 * 
 * @author Luke Fernando
 * 
 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException;

public class JarvisMarch extends ConvexHull
{
	// last element in pointsNoDuplicate(), i.e., highest of all points (and the rightmost one in case of a tie)
	private Point highestPoint; 
	
	// left chain of th
	private PureStack<Point> leftChain; 
	
	// right chain o
	private PureStack<Point> rightChain;
		

	/**
	 * Call corresponding constructor of the super class.  Initialize the variable algorithm 
	 * (from the class ConvexHull). Set highestPoint. Initialize the two stacks leftChain 
	 * and rightChain. 
	 * 
	 * @throws IllegalArgumentException  when pts.length == 0
	 */
	public JarvisMarch(Point[] pts) throws IllegalArgumentException 
	{
		super(pts); 
		algorithm  = "Jarvis March";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length-1];
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
		
	}

	
	/**
	 * Call corresponding constructor of the superclass.  Initialize the variable algorithm.
	 * Set highestPoint.  Initialize leftChain and rightChain.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public JarvisMarch(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		super(inputFileName); 
		algorithm  = "Jarvis March";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length-1];
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
	}


	// ------------
	// Javis' march
	// ------------

	/**
	 * Calls createRightChain() and createLeftChain().  Merge the two chains stored on the stacks  
	 * rightChain and leftChain into the array hullVertices[].
	 * 
     * Two degenerate cases below must be handled: 
     * 
     *     1) The array pointsNoDuplicates[] contains just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) The array contains collinear points, in which case the hull is the line segment 
     *        connecting the two extreme points from them.   
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
		this.createRightChain();
		this.createLeftChain();
		
		hullVertices = new Point[rightChain.size() + leftChain.size()];
		int i = hullVertices.length - 1;
		while (!leftChain.isEmpty())
		{
			hullVertices[i] = leftChain.pop();
			i--;
		}
		while (!rightChain.isEmpty())
		{
			hullVertices[i] = rightChain.pop();
			i--;
		}
		long endTime = System.nanoTime();
		this.time = endTime - startTime;
	}
	
	
	/**
	 * Construct the right chain of the convex hull.  Starts at lowestPoint and wrap around the 
	 * points counterclockwise.  For every new vertex v of the convex hull, call nextVertex()
	 * to determine the next vertex, which has the smallest polar angle with respect to v.  Stop 
	 * when the highest point is reached.  
	 * 
	 * Use the stack rightChain to carry out the operation.  
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createRightChain()
	{
		rightChain.push(lowestPoint);
		while (!rightChain.peek().equals(highestPoint))
		{
			Point curVer = rightChain.peek();
			Point nextVer = nextVertex(curVer);
			rightChain.push(nextVer);
		}
		rightChain.pop();
	}
	
	
	/**
	 * Construct the left chain of the convex hull.  Starts at highestPoint and continues the 
	 * counterclockwise wrapping.  Stop when lowestPoint is reached.  
	 * 
	 * Use the stack leftChain to carry out the operation. 
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createLeftChain()
	{
		leftChain.push(highestPoint);
		while (!leftChain.peek().equals(lowestPoint))
		{
			Point curVer = leftChain.peek();
			Point nextVer = nextVertex(curVer);
			leftChain.push(nextVer);
		}
		leftChain.pop();
	}
	
	
	/**
	 * Return the next vertex, which is less than all other points by polar angle with respect
	 * to the current vertex v. When there is a tie, pick the point furthest from v. Comparison 
	 * is done using a PolarAngleComparator object created by the constructor call 
	 * PolarAngleCompartor(v, false).
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param v  current vertex 
	 * @return
	 */
	public Point nextVertex(Point v)
	{
		PolarAngleComparator comp = new PolarAngleComparator(v, false);
		Point p1 = pointsNoDuplicate[0];
		 for (int i = 1; i < pointsNoDuplicate.length; i++)
		 {
			 Point p2 = pointsNoDuplicate[i];
			 if (comp.compare(p1, p2) > 0)
			 {
				 p1 = p2;
			 }
		 }
		 return p1;
	}
}
