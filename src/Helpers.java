import java.awt.geom.Line2D;
import java.awt.geom.Point2D;


public class Helpers {
	
	public static boolean linesIntersect(Line2D line1, Line2D line2) {
		Point2D A = line1.getP1(); 
		Point2D B = line1.getP2();
		Point2D C = line2.getP1(); 
		Point2D D = line2.getP2();
		
		Point2D CmP = new Point2D.Double(C.getX() - A.getX(), C.getY() - A.getY());
		Point2D r = new Point2D.Double(B.getX() - A.getX(), B.getY() - A.getY());
		Point2D s = new Point2D.Double(D.getX() - C.getX(), D.getY() - C.getY());
 
		double CmPxr = CmP.getX() * r.getY() - CmP.getY() * r.getX();
		double CmPxs = CmP.getX() * s.getY() - CmP.getY() * s.getX();
		double rxs = r.getX() * s.getY() - r.getY() * s.getX();
 
		if(CmPxr == 0d) {
			// Lines are collinear, and so intersect id they have any overlap
 
			return ((C.getX() - A.getX() < 0d) != (C.getX() - B.getX() < 0d))
				|| ((C.getY() - A.getY() < 0d) != (C.getY() - B.getY() < 0d));
		}
 
		if (rxs == 0d)
			return false; // Lines are parallel.
 
		double rxsr = 1d / rxs;
		double t = CmPxs * rxsr;
		double u = CmPxr * rxsr;
 
		return (t >= 0d) && (t <= 1d) && (u >= 0d) && (u <= 1d);
	}
	
	public static double shortestDistance(Line2D line1, Line2D line2) {
		Point2D p1a = line1.getP1(); Point2D p1b = line1.getP2();
		
		Point2D p2a = line2.getP1(); Point2D p2b = line2.getP2();
		
		double shortestDist = 100000000;
		
		double p1ap2a = dist(p1a, p2a);
		if(p1ap2a < shortestDist)
			shortestDist = p1ap2a;
		
		double p1ap2b = dist(p1a, p2b);
		if(p1ap2b < shortestDist)
			shortestDist = p1ap2b;
		
		double p1bp2a = dist(p1b, p2a);
		if(p1bp2a < shortestDist)
			shortestDist = p1bp2a;
		
		double p1bp2b = dist(p1b, p2b);
		if(p1bp2b < shortestDist)
			shortestDist = p1bp2b;
		
		return shortestDist;		
	}
	
	public static double dist(Point2D p1, Point2D p2) {
		return Math.sqrt( (p1.getX() - p2.getX())*(p1.getX() - p2.getX()) + (p1.getY() - p2.getY())*(p1.getY() - p2.getY()) );
	}
	
}
