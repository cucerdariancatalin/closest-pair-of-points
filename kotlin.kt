import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Double, val y: Double) {
    fun distance(other: Point): Double {
        return sqrt((x - other.x).pow(2) + (y - other.y).pow(2))
    }
}

fun closestPair(points: Array<Point>): Pair<Point, Point> {
    // Sort the points by their x-coordinate
    points.sortBy { it.x }

    // Base case for two points
    if (points.size == 2) {
        return Pair(points[0], points[1])
    }

    // Divide the points into two halves
    val mid = points.size / 2
    val leftHalf = points.copyOfRange(0, mid)
    val rightHalf = points.copyOfRange(mid, points.size)

    // Recursively find the closest pair in the left and right halves
    val closestPairLeft = closestPair(leftHalf)
    val closestPairRight = closestPair(rightHalf)

    // Get the minimum distance between the closest pair in the left and right halves
    val minDistance = minOf(closestPairLeft.second.distance(closestPairLeft.first),
            closestPairRight.second.distance(closestPairRight.first))

    // Get all the points that lie within a strip of width 2 * minDistance around the mid line
    val strip = points.filter { (it.x - points[mid].x).pow(2) < minDistance }

    // Find the closest pair of points in the strip
    var closestPair = Pair(Point(0.0, 0.0), Point(Double.MAX_VALUE, Double.MAX_VALUE))
    for (i in 0 until strip.size) {
        for (j in i + 1 until strip.size) {
            if (strip[j].y - strip[i].y > minDistance) {
                break
            }
            val distance = strip[j].distance(strip[i])
            if (distance < closestPair.second.distance(closestPair.first)) {
                closestPair = Pair(strip[i], strip[j])
            }
        }
    }

    return closestPair
}
