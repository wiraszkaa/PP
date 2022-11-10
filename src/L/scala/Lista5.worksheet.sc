def point2D (x: Double, y: Double): (Double, Double) = (x, y)
var point1t = point2D(1, 1)
var point2t = point2D(2, 2)

def distance (a: (Double, Double), b: (Double, Double)): Double =
    Math.sqrt(Math.pow(b._2 - a._2, 2) + Math.pow(b._1 - a._1, 2))
distance(point1t, point2t)

class Point2D(var x: Int, var y: Int)
var point1 = Point2D(1, 1)
var point2 = Point2D(2, 2)

def distance (a: Point2D, b: Point2D): Double =
    Math.sqrt(Math.pow(b.y - a.y, 2) + Math.pow(b.x - a.x, 2))
distance(point1, point2)


def person (n: String, s: String, a: Int, g: String, sh: Float) =
    (n, s, a, g, sh)

def partnership (x: (String, String, Int, String, Float), 
                y: (String, String, Int, String, Float)): 
    ((String, String, Int, String, Float), 
    (String, String, Int, String, Float)) =
    (x, y)

def younger (p: ((String, String, Int, String, Float), (String, String, Int, String, Float))): 
    (String, String, Int, String, Float) =
    if p._1._3 < p._2._3 then p._1 else p._2

class Person