import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) {
        process(new Reader(System.in));
    }

    static void process(Reader reader) {

        try {
            int numCases = reader.nextInt();

            for (int i = 1; i <= numCases; i++) {

                int kLines = reader.nextInt();

                Point destination = new Point(reader.nextInt(), reader.nextInt());
                // 0: X
                // 1: Y
                // 2: Angle from X axis
                Point[][] deathlyRays = new Point[kLines][2];

                for (int j = 0; j < kLines; j++) {
                    Point startPoint = new Point(reader.nextInt(), reader.nextInt());
                    deathlyRays[j][0] = startPoint;
                    int angle = reader.nextInt();
                    double endX = startPoint.x + 1000000 * Math.sin(angle);
                    double endY = startPoint.y + 1000000 * Math.cos(angle);
                    Point endPoint = new Point(endX, endY);
                    deathlyRays[j][1] = endPoint;
                }

                result.append(i).append(": ");
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

    private static void executeMethod(Point destination, Point[][] deathlyRays) {
        /*
        As David Eisenstat mentions, you require the shortest bitonic tour covering each point.

        This can be done through dynamic programming in O(n^2) time.

        Let Pij (1 <= i <= j <= n) be a bitonic path from point pi to pj such that the path starts from pi,
        goes strictly left to p1, then goes strictly right to pj,
        in the process covering all the points to the left of pj.

        Let d[i,j] be the length of the shortest such path from i to j.

        Note that d[1,2] = dist(p1,p2)
        d[1,3] = d[1,2] + dist(p2,p3).
        d[i,j] = d[i,j-1] + dist(j-1,j) for i < j-1.
        d[j-1,j] = min( d[k,j-1] + dist(k,j) ) for 1 <= k < j-1
         */

        Point start = new Point(0, 0);
        Point savePoint = new Point(0, 0);
        ArrayList<Point> intermediatePoints = new ArrayList<>();

        double distance = 0.0;

        // Point[] visited = new Point[(int) (destination.x * destination.y) - deathlyRays.length];

        int visited = 0;
        int totalvisiblePoints = (int) ((destination.x+1) * (destination.y+1)) - deathlyRays.length;
        boolean lineInterseects = false;
        while (!savePoint.equals(destination) && visited != totalvisiblePoints) {
            visited++;
            for (Point[] deathlyRay : deathlyRays) {
                lineInterseects = intersect(savePoint, destination, deathlyRay[0], deathlyRay[1]);
                if (lineInterseects) {
                    savePoint = deathlyRay[0];
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
            DecimalFormat df = new DecimalFormat("#.######");
//            df.setRoundingMode(RoundingMode.HALF_UP);
            df.setMinimumFractionDigits(6);
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
        return Math.sqrt(end.y - start.y) * (end.y - start.y) + (end.x - start.x) * (end.x - start.x);
    }

    private static boolean intersect(Point savepoint, Point destination, Point deathRayStart, Point deathRayEnd) {
        if (isPointOnTheLine(savepoint, destination, deathRayStart)) return true;
        else if (isPointOnTheLine(savepoint, destination, deathRayEnd)) return true;
        else if (isPointOnTheLine(deathRayStart, deathRayEnd, savepoint)) return true;
        else return isPointOnTheLine(deathRayStart, deathRayEnd, destination);
    }

    private static boolean isPointOnTheLine(Point A, Point B, Point C) {
        // if AC is vertical
        if (A.x == C.x) return false;
        // if AC is horizontal
        if (A.y == C.y) return false;
        // match the gradients
        return (A.x - C.x) * (A.y - C.y) == (C.x - B.x) * (C.y - B.y);
    }

    private static class Point {
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