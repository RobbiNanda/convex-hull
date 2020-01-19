import java.util.*;
import javax.swing.JOptionPane;

public class Tucil_13518100 {
    public static class Point {
        /* Point object */
        private int absis;
        private int ordinat;

        public Point (int x, int y) {
            absis = x;
            ordinat = y;
        }

        public int getAbsis() {
            return this.absis;
        }

        public int getOrdinat() {
            return this.ordinat;
        }

        public void printPoint() {
            System.out.printf("(%d, %d) ", this.absis, this.ordinat);
        }
    } 

    public static class Unit {
        public int getA(Point P1, Point P2) {
            return P2.ordinat - P1.ordinat;
        }

        public int getB(Point P1, Point P2) {
            return P1.absis - P2.absis;
        }

        public int getC(Point P1, Point P2) {
            return P1.absis * P2.ordinat - P2.absis * P2.ordinat;
        }
    }

    public static void main(String args[]) {
        /* Input how many points user want to generate */
        Scanner in = new Scanner(System.in);
        int N = Integer.parseInt(JOptionPane.showInputDialog("Masukkan jumlah titik: "));
        int minPoint = 0;
        int maxPoint = 100;

        /* Generate array of Point by random */
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = (int)(Math.random() * (maxPoint - minPoint) + 1);
            int y = (int)(Math.random() * (maxPoint - minPoint) + 1);

            Point p = new Point(x, y);
            points[i] = p;
            points[i].printPoint();
        }

        /* BRUTE FORCE ALGORITHM */
        /* Create list of points that belong to part of convex hull */
        Point[] convexList = new Point[N + 1];

        /* Finding the first convex hull line segment */
        boolean firstConvexNotFound = true;
        int i = 0;
        while (i < N - 1 && firstConvexNotFound) {

            int j = i + 1;
            while (j < N && firstConvexNotFound) {

                Unit unit = new Unit();
                int a = unit.getA(points[i], points[j]);
                int b = unit.getB(points[i], points[j]);
                int c = unit.getC(points[i], points[j]);

                int k = j + 1;
                boolean isConvex = true;
                int sign = 0;
                if (points[j + 1].absis * a + points[j + 1].ordinat * b - c > 0) {
                    sign = 1;
                } else if (points[j + 1].absis * a + points[j + 1].ordinat * b - c < 0) {
                    sign = -1;
                }
                while ((k < N) && isConvex) {
                    if (points[k].absis * a + points[k].ordinat * b - c > 0) {
                        if (sign == -1) {
                            isConvex = false;
                        }    
                    } else if (points[k].absis * a + points[k].ordinat * b - c < 0) {
                        if (sign == 1) {
                            isConvex = false;
                        }    
                    }
                    k++;
                }
                if (isConvex) {
                    convexList[0] = points[i];
                    convexList[1] = points[j];
                    firstConvexNotFound = false;
                }
                j++;
            }
            i++;
        }

        convexList[0].printPoint();
        convexList[1].printPoint();
    }
}