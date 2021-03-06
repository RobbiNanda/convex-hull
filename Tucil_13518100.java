/* Nama		: Kevin Rizki Mohammad */
/* NIM 		: 13518100 */
/* Tanggal	: 22 Jan 2020 */
/* Deskripsi	: Penerapan algoritma brute force untuk masalah mencari convex 
		  hull dari himpunan titik pada bidang */

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
            System.out.printf("(%d, %d)", this.absis, this.ordinat);
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
            return P1.absis * P2.ordinat - P2.absis * P1.ordinat;
        }

        public boolean isEqual(Point P1, Point P2) {
            return P1.absis == P2.absis && P1.ordinat == P2.ordinat;
        }
        
        public int calculateLineEquation(int a, int b, int c, Point p) {
            int d = a * p.absis + b * p.ordinat - c;
            if (d > 0) {
                return 1;
            } else if (d < 0) {
                return -1;
            } else {
                return 0;
            }
        } 
    }

    public static class ConvexList {
        public Point[] ch;
        public int nEff;
        
        public ConvexList() {
            ch = new Point[100];
            nEff = 0;
        }
    }

    public static class TabInt {
        public int[] ti;
        public int nEff;
        
        public TabInt() {
            ti = new int[100];
            nEff = 0;
        }
        
        public void printTab() {
            for (int i = 0; i < this.nEff; i++) {
                System.out.print(this.ti[i]);
                System.out.print(' ');
            }
        }
        
        public boolean isConvex() {
            int result = 0;
            for (int i = 1; i < this.nEff; i++) {
                result = this.ti[0] * this.ti[i];
                if (result == -1) {
                    return false;
                }
            }
            return result == 1;
        }
    }
    
    public static void main(String args[]) {
        /* Input how many points user want to generate */
        Scanner in = new Scanner(System.in);
        // int N = Integer.parseInt(JOptionPane.showInputDialog("Enter number of points: "));
        int N = in.nextInt();
        int minPoint = 0;
        int maxPoint = 100;

        long startTime;
        long endTime;

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
        ConvexList convexList = new ConvexList();

        if (N <= 3) {
            /* If number of points is less and equal than 3, then
            all the points is part of convex hull */
            startTime = System.nanoTime();
            for (int i = 0; i < N; i++) {
                convexList.ch[i] = points[i];
            }
            convexList.ch[N] = points[0];
            convexList.nEff = N + 1;
            endTime = System.nanoTime();
        } else {
            /* Find the first convex line */
            startTime = System.nanoTime();
            boolean done = false;
            boolean firstConvexFound = false;
            int i = 0;
            while (!done) {
                boolean convexFound = false;
                int j = 0;
                while (j < N && !convexFound) {
                    if (j != i) {
                        
                        /* Test is points[i] and points[j] are part of convex hull */
                        int a = unit.getA(points[i], points[j]);
                        int b = unit.getB(points[i], points[j]);
                        int c = unit.getC(points[i], points[j]);
                        
                        TabInt tabInt = new TabInt();
                        int k = 0;
                        while (k < N) {
                            if (k != i && k != j) {
                                int d = unit.calculateLineEquation(a, b, c, points[k]);
                                if (d != 0) {
                                    tabInt.ti[tabInt.nEff] = d;
                                    tabInt.nEff += 1;
                                }
                            }
                            k++;
                        }
                    
                        /* points[i] and points[j] are the first convex hull found */
                        if (tabInt.isConvex() && convexList.nEff == 0) {
                            convexList.ch[convexList.nEff++] = points[i];
                            convexList.ch[convexList.nEff++] = points[j];
                            firstConvexFound = true;
                            convexFound = true;
                            i = j;
                        } 
                        /* points[j] are part of the convex hull */
                        else if (tabInt.isConvex()) {
                            if (!unit.isEqual(points[j], convexList.ch[convexList.nEff - 2])) {
                                convexList.ch[convexList.nEff++] = points[j];
                                convexFound = true;
                                i = j;
                            }
                            if (unit.isEqual(points[j], convexList.ch[0]) && convexList.nEff != 2) {
                                done = true;
                            }
                        }
                    }
                    j++;
                }
                if (!firstConvexFound) {
                    i++;
                }
                if (!convexFound && convexList.nEff != 0) {
                    convexList.ch[convexList.nEff++] = convexList.ch[0];
                    done = true;
                }
            }
            endTime = System.nanoTime();
        }

        /* Make function to check all points are in the same segment given a linear equation */



        /* Print output */
        System.out.print("[");
        for (int i = 0; i < convexList.nEff; i++) {
            convexList.ch[i].printPoint();
            if (i != convexList.nEff - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        
        System.out.print("Time taken: ");
        System.out.print((endTime - startTime));
        System.out.println(" nano seconds");
    }
}
