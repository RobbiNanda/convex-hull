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

        public boolean isEqual(Point P1, Point P2) {
            return P1.absis == P2.absis && P1.ordinat == P2.ordinat;
        }
    }

    public static void main(String args[]) {
        /* Input how many points user want to generate */
        Scanner in = new Scanner(System.in);
        int N = Integer.parseInt(JOptionPane.showInputDialog("Enter number of points: "));
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
            System.out.println();
        }

        /* Object to perform some Point calculation */
        Unit unit = new Unit();

        /* BRUTE FORCE ALGORITHM */
        /* Create list of points that belong to part of convex hull */
        /* Notice that number of element effective is less than N + 1 */
        Point[] convexList = new Point[N * 2 + 1];
        int convexListNEff = 0;

        if (N <= 3) {
            /* If number of points is less and equal than 3, then
            all the points is part of convex hull */
            for (int i = 0; i < N; i++) {
                convexList[i] = points[i];
            }
            convexList[N] = points[0];
            convexListNEff = N + 1;
        } else {
            for (int i = 0; i < N - 1; i++) {
                for (int j = i + 1; j < N; j++) {
                    int a = unit.getA(points[i], points[j]);
                    int b = unit.getB(points[i], points[j]);
                    int c = unit.getC(points[i], points[j]);
                    boolean isConvex = true;
                    for (int k = 0; k < N - 1; k++) {
                        /* find the sign of ax + by - c for each other n - 2 points */
                        if (a * points[k].absis + b * points[k].ordinat - c != 0) { 
                            if ((a * points[k].absis + b * points[k].ordinat - c)
                                * (a * points[k + 1].absis + b * points[k + 1].ordinat - c) < 0) {
                                isConvex = false;
                            }
                        }
                    }
                    if (isConvex) {
                        convexList[convexListNEff] = points[i];
                        convexList[convexListNEff++] = points[j];   
                    }
                    convexListNEff++;
                }
            }
        }

        /* Make function to check all points are in the same segment given a linear equation */



        /* Print output */
        System.out.print("[ ");
        for (int i = 0; i < convexListNEff; i++) {
            convexList[i].printPoint();
        }
        System.out.print("]");
    }
}