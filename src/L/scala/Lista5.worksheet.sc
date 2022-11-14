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

def youngert (p: ((String, String, Int, String, Float), (String, String, Int, String, Float))): 
    (String, String, Int, String, Float) =
    if p._1._3 < p._2._3 then p._1 else p._2

class Person (
    var name: String, 
    var surname: String, 
    var age: Int, 
    var gender: String,
    var shoe: Float)

class Partnership (var first: Person, var second: Person)
val partners = Partnership(Person("Xyz", "Zyx", 20, "male", 42.5), Person("Abc", "Cba", 25, "female", 40))

def younger (p: Partnership): Person = if p.first.age < p.second.age then p.first else p.second
younger(partners).age


enum WeekDay {
    case Monday
    case Tuesday
    case Wednesday
    case Thursday
    case Friday
    case Saturday
    case Sunday
}

val pon = WeekDay.Monday
def weekDayToString (d: WeekDay): String =
    d match {
        case WeekDay.Monday => "Poniedziałek"
        case WeekDay.Tuesday => "Wtorek"
        case WeekDay.Wednesday => "Środa"
        case WeekDay.Thursday => "Czwartek"
        case WeekDay.Friday => "Piątek"
        case WeekDay.Saturday => "Sobota"
        case WeekDay.Sunday => "Niedziela"
    }
weekDayToString(pon)

def nextDay (d: WeekDay): WeekDay =
    d match {
        case WeekDay.Monday => WeekDay.Tuesday
        case WeekDay.Tuesday => WeekDay.Wednesday
        case WeekDay.Wednesday => WeekDay.Thursday
        case WeekDay.Thursday => WeekDay.Friday
        case WeekDay.Friday => WeekDay.Saturday
        case WeekDay.Saturday => WeekDay.Sunday
        case WeekDay.Sunday => WeekDay.Monday
    }
nextDay(pon)

enum Maybe {
    case Just[A] (v: A)
    case Nothing
}

def safeHead[A] (xs: List[A]): Maybe =
    xs match {
        case Nil => Maybe.Nothing
        case h :: tl => Maybe.Just(h)
    }
safeHead(List())
safeHead(List(1, 2, 3, 4, 5))


enum SolidFigure {
    case Cube(x: Double, y: Double, z: Double)
    case Cone(r: Double, h: Double)
    case Sphere(r: Double)
    case Cylinder(r: Double, h: Double)
}

def volume (f: SolidFigure): Double =
    f match {
        case SolidFigure.Cube(x, y, z) => x * y * z
        case SolidFigure.Cone(r, h) => (Math.PI * math.pow(r, 2) * h) / 3
        case SolidFigure.Sphere(r) => (4 * Math.PI * math.pow(r, 2)) / 3
        case SolidFigure.Cylinder(r, h) => Math.PI * math.pow(r, 2) * h
    }
volume(SolidFigure.Cube(1, 1, 1))
volume(SolidFigure.Cone(1, 1))
volume(SolidFigure.Sphere(1))
volume(SolidFigure.Cylinder(1, 1))