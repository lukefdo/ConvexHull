package convexHull;

/**
 * 
 * @author Luke Fernando
 * 
 */

import java.util.Comparator;

/**
 * This class sorts an array of Point objects using a provided Comparator.  For the purpose
 * you may adapt your implementation of quicksort from Project 2.  
 */

public class QuickSortPoints
{
	private Point[] points;  	// Array of points to be sorted.
	

	/**
	 * Constructor takes an array of Point objects. 
	 * 
	 * @param pts
	 */
	public QuickSortPoints(Point[] pts)
	{
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++)
		{
			points[i] = new Point(pts[i]);
		}
	}
	
	/**
	 * Copy the sorted array to pts[]. 
	 * 
	 * @param pts  array to copy onto
	 */
	Point[] getSortedPoints(Point[] pts)
	{
		pts = new Point[points.length];
		for (int i = 0; i < points.length; i++)
		{
			pts[i] = new Point(points[i]);
		}
		return pts;
	}

	
	/**
	 * Perform quicksort on the array points[] with a supplied comparator. 
	 * 
	 * @param comp
	 */
	public void quickSort(Comparator<Point> comp)
	{
		quickSortRec(0, points.length - 1, comp);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last, Comparator<Point> comp)
	{
		if (first < last)
		{
			int pIndex = partition(first, last, comp);
			quickSortRec(first, pIndex - 1, comp);
			quickSortRec(pIndex + 1, last, comp);
		}
	}
	

	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last, Comparator<Point> comp)
	{
		Point pivot = points[last];
		int pIndex = first;
		for (int i = first; i < last; i++)
		{
			if (comp.compare(points[i], pivot) <= 0)
			{
				swap(i, pIndex);
				pIndex++;
			}
		}
		swap(pIndex, last);
		return pIndex;
	}
	
	private void swap(int i, int j)
	{
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}
}


