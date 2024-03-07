package convexHull;

/**
 *  
 * @author Luke Fernando
 *
 */

import java.util.Comparator;

/**
 * 
 * This class compares two points p1 and p2 by polar angle with respect to a reference point.  
 *  
 */
public class PolarAngleComparator implements Comparator<Point>
{
	private Point referencePoint; 
	private boolean flag;  // used for breaking a tie between two points that have 
	                       // the same polar angle with respect to referencePoint
	
	/**
	 * 
	 * @param p reference point
	 */
	public PolarAngleComparator(Point p, boolean flag)
	{
		referencePoint = p; 
		this.flag = flag; 
	}
	
	/**
	 * Use cross product and dot product to implement this method.  Do not take square roots 
	 * or use trigonometric functions. Calls private methods crossProduct() and dotProduct(). 
	 * 
	 * Precondition: both p1 and p2 are different from referencePoint. 
	 * 
	 * @param p1
	 * @param p2
	 * @return  0 if p1 and p2 are the same point
	 *         -1 if one of the following three conditions holds: 
	 *                a) the cross product between p1 - referencePoint and p2 - referencePoint 
	 *                   is greater than zero. 
	 *                b) the above cross product equals zero, flag == true, and p1 is closer to 
	 *                   referencePoint than p2 is. 
	 *                c) the above cross product equals zero, flag == false, and p1 is further 
	 *                   from referencePoint than p2 is.   
	 *          1  otherwise. 
	 *                   
	 */
	public int compare(Point p1, Point p2)
	{
		if (p1.equals(p2))
			return 0;
		if (crossProduct(p1, p2) > 0 || //turning left
		   (crossProduct(p1, p2) == 0 && this.flag == true && dotProduct(p1, p1) < dotProduct(p2, p2)) || //p1 is closer than p2
		   (crossProduct(p1, p2) == 0 && this.flag == false && dotProduct(p1, p1) > dotProduct(p2, p2))) //p1 is further than p2
		{
			return -1; // turning left or p1's polar angle is less than p2's polar angle
		}
		else
			return 1; // turning right or p1's polar angle is greater than p2s polar angle
	}
	    

    /**
     * 
     * @param p1
     * @param p2
     * @return cross product of two vectors: p1 - referencePoint and p2 - referencePoint
     */
    private int crossProduct(Point p1, Point p2)
    {
    	int xref = referencePoint.getX();
    	int yref = referencePoint.getY();
    	
    	int x1 = p1.getX();
    	int y1 = p1.getY();
    	
    	int x2 = p2.getX();
    	int y2 = p2.getY();
    	
    	return (x1-xref)*(y2-yref) - (x2-xref)*(y1-yref);
    	
    }

    /**
     * 
     * @param p1
     * @param p2
     * @return dot product of two vectors: p1 - referencePoint and p2 - referencePoint
     */
    private int dotProduct(Point p1, Point p2) 
    {
    	int xref = referencePoint.getX();
    	int yref = referencePoint.getY();
    	
    	int x1 = p1.getX();
    	int y1 = p1.getY();
    	
    	int x2 = p2.getX();
    	int y2 = p2.getY();
    	
    	return (x1-xref)*(x2-xref) + (y1-yref)*(y2-yref);
    	
    }
}
