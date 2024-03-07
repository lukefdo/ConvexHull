package convexHull;

/**
 * 
 * @author Luke Fernando
 * 
 */

import java.util.Comparator;

/**
 * 
 * Comparator class for comparing the Points by y coordinate.
 *
 */

public class YComparator implements Comparator<Point> {
	
	/**
	 * compare the points by y coordinate. If they both have the same y coordinate,
	 * then it compares the points' x coordinates.
	 * @param p1, p2
	 * @return  -1  if this.y < q.y || (this.y == q.y && this.x < q.x)
	 * 		    0   if this.y == q.y && this.x == q.x 
	 * 			1	otherwise
	 */
	public int compare(Point p1, Point p2)
	{
		return p1.compareTo(p2);
	}
}
