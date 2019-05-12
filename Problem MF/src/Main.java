import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) {
//        process(new Reader(System.in));
        Point p1 = new Point(4, 1);
        Point q1 = new Point(1, 1);
        Point p2 = new Point(4, 1);
        Point q2 = new Point(4, 0);
        System.out.println("intersect: " + intersect(p1, q1, p2, q2));
        System.out.println("isOnLineSegment: " + isOnLineSegment(p2, q2, q1));

    }

    static void process(Reader reader) {

        try {
            int numCases = reader.nextInt();

            for (int i = 1; i <= numCases; i++) {

                int kLines = reader.nextInt();

                int destX = reader.nextInt();
                int destY = reader.nextInt();
                Point destination = new Point(destX, destY);

                Point[][] deathlyRays = new Point[kLines][2];
                for (int j = 0; j < kLines; j++) {
                    Point startPoint = new Point(reader.nextInt(), reader.nextInt());
                    deathlyRays[j][0] = startPoint;
                    int angle = reader.nextInt();
                    double angleRadian = Math.toRadians(angle);// angle * Math.PI / 180.0;
                    int length = 10000000;
//                    int length = 3;
                    double endX = startPoint.x + (length * Math.cos(angleRadian));
                    double endY = startPoint.y + (length * Math.sin(angleRadian));

                    DecimalFormat df = new DecimalFormat("#.####");
                    df.setMinimumFractionDigits(4);
                    endX = Double.parseDouble(df.format(endX));
                    endY = Double.parseDouble(df.format(endY));

                    Point endPoint = new Point(endX, endY);
                    deathlyRays[j][1] = endPoint;
                }
                result.append(i).append(": ");
//                drawInCanvas(destination, deathlyRays);
                executeMethod(destination, deathlyRays);
                if (i != numCases) {
                    result.append("\n");
                }
            }

            System.out.println(result.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void drawInCanvas(Point destination, Point[][] deathlyRays) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final LinesComponent comp = new LinesComponent();
        comp.setPreferredSize(new Dimension(300, 300));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);

        int center = 150;
        comp.addLine(center, center, (int) destination.x + center * 2, (int) destination.y + center * 2, new Color(50, center, 0));


        for (Point[] deathlyRay : deathlyRays) {
            Point start = deathlyRay[0];
            Point end = deathlyRay[1];

            comp.addLine((int) start.x + center, (int) start.y + center, (int) end.x + center, (int) end.y + center);
            result.append(start).append("-->").append(end);
        }
        testFrame.pack();
        testFrame.setVisible(true);
    }

    /**
     * As David Eisenstat mentions, you require the shortest bitonic tour covering each point.
     * <p>
     * This can be done through dynamic programming in O(n^2) time.
     * <p>
     * Let Pij (1 <= i <= j <= n) be a bitonic path from point pi to pj such that the path starts from pi,
     * goes strictly left to p1, then goes strictly right to pj,
     * in the process covering all the points to the left of pj.
     * <p>
     * Let d[i,j] be the length of the shortest such path from i to j.
     * <p>
     * Note that d[1,2] = dist(p1,p2)
     * d[1,3] = d[1,2] + dist(p2,p3).
     * d[i,j] = d[i,j-1] + dist(j-1,j) for i < j-1.
     * d[j-1,j] = min( d[k,j-1] + dist(k,j) ) for 1 <= k < j-1
     */
    private static void executeMethod(Point destination, Point[][] deathlyRays) {

        Point start = new Point(0, 0);
        Point savePoint = new Point(0, 0);
        ArrayList<Point> intermediatePoints = new ArrayList<>();

        for (Point[] deathlyRay : deathlyRays) {
            if (isOnLineSegment(deathlyRay[0], deathlyRay[1], destination)) {
                result.append("inf");
                return;
            }
        }

        double distance = 0.0;

        // Point[] visited = new Point[(int) (destination.x * destination.y) - deathlyRays.length];

        int visited = 0;
        int totalvisiblePoints = (int) ((destination.x + 1) * (destination.y + 1)) - deathlyRays.length;
        totalvisiblePoints += 50;
        boolean lineInterseects = false;
        while (!savePoint.equals(destination) && visited != totalvisiblePoints) {
            visited++;
            for (Point[] deathlyRay : deathlyRays) {
                lineInterseects = intersect(savePoint, destination, deathlyRay[0], deathlyRay[1]);
                if (lineInterseects) {

                    // Remove savepoint because it adds another intesections
                    // NOTE WRONG!!!!!!!
                    if (intermediatePoints.size() > 0) intermediatePoints.remove(intermediatePoints.size() - 1);

                    savePoint = deathlyRay[0];
                    intermediatePoints.add(savePoint);
                    break;
                }
            }
            if (!lineInterseects) {
                distance = calculateDistanceBetweenNPoints(start, intermediatePoints, destination);
                savePoint = destination;
            }
        }
        if (distance == 0.0)
            result.append("inf");
        else
        // TODO Format to always show 4 decimal
        {
            DecimalFormat df = new DecimalFormat("#.####");
//            df.setRoundingMode(RoundingMode.HALF_UP);
            df.setMinimumFractionDigits(4);
            result.append(df.format(distance));
        }
    }

    private static double calculateDistanceBetweenNPoints(Point start, ArrayList<Point> inBetween, Point end) {
        double total = 0.0;

        Point previousPoint = start;
        for (Point point : inBetween) {
            total += calculateDistanceBetweenNPoints(previousPoint, point);
            previousPoint = point;
        }
        total += calculateDistanceBetweenNPoints(previousPoint, end);

        return total;
    }

    private static double calculateDistanceBetweenNPoints(Point start, Point end) {
        return Math.sqrt(Math.pow((end.y - start.y), 2) + Math.pow((end.x - start.x), 2));
    }

    public static int orientation(Point p, Point q, Point r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0.0)
            return 0; // colinear
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    public static boolean intersect(Point p1, Point q1, Point p2, Point q2) {

        if (isOnLineSegment(p1, p2, q2)) return true;
        if (p1.equals(p2) || q1.equals(q2) || p1.equals(q2) || q1.equals(p2)) return false;

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        return o1 != o2 && o3 != o4;

    }

    static boolean isOnLineSegment(Point startPoint, Point endPoint, Point test) {

        return ((test.y - startPoint.y) / (test.x - startPoint.x) == (test.y - endPoint.y) / (test.x - endPoint.y));

    }

    public static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return Double.compare(point.x, x) == 0 &&
                    Double.compare(point.y, y) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}