import java.util.*;

public class Tucil_13518100 {
    public static class Point {
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

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int minPoint = in.nextInt();
        int maxPoint = in.nextInt();

        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = (int)(Math.random() * (maxPoint - minPoint) + 1);
            int y = (int)(Math.random() * (maxPoint - minPoint) + 1);

            Point p = new Point(x, y);
            points[i] = p;
            points[i].printPoint();
        }
    }
}