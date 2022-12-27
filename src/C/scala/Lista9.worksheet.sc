// ZAD 1

class Time1(private val hourValue: Int):
    private var _hour = if hourValue >= 0 && hourValue < 24 then hourValue else 0
    def hour: Int = _hour
    def hour_=(newHour: Int): Unit =
        if newHour < 0 || newHour > 23 then
            _hour = 0
        else
            _hour = newHour
end Time1

val time = new Time1(25)
time.hour
time.hour = 10
time.hour

// ZAD 2

class TimeA(private val hourValue: Int, private val minuteValue: Int):
    private var _hour = checkHour(hourValue)
    private var _minute = checkMinute(minuteValue)
    def hour: Int = _hour
    def hour_=(newHour: Int): Unit =
        _hour = checkHour(newHour)
    def minute: Int = _minute
    def minute_=(newMinute: Int): Unit =
        _minute = checkMinute(newMinute)
    def before(other: TimeA): Boolean =
        if hour > other.hour then false
        else if hour == other.hour then
            minute < other.minute else true
    def checkHour(hour: Int): Int = if hour >= 0 && hour < 24 then hour else 0
    def checkMinute(minute: Int): Int = if minute >= 0 && minute < 60 then minute else 0
end TimeA

val time1 = new TimeA(25, 25)
time1.hour
time1.minute
val time2 = new TimeA(11, 61)
time2.hour
time2.minute
time1.before(time2)
time2.before(time1)

class TimeB(private val hourValue: Int, private val minuteValue: Int):
    private var _minutes = checkMinute(minuteValue) + checkHour(hourValue) * 60
    def hour: Int = _minutes / 60
    def hour_=(newHour: Int): Unit =
        _minutes = checkHour(newHour) + minute
    def minute: Int = _minutes - (_minutes / 60) * 60
    def minute_=(newMinute: Int): Unit =
        _minutes = hour + checkMinute(newMinute)
    def before(other: TimeB): Boolean =
        if hour > other.hour then false
        else if hour == other.hour then
            minute < other.minute else true
    def checkHour(hour: Int): Int = if hour >= 0 && hour < 24 then hour else 0
    def checkMinute(minute: Int): Int = if minute >= 0 && minute < 60 then minute else 0
end TimeB

val time3 = new TimeB(25, 25)
time1.hour
time1.hour = 10
time1.hour
time1.minute
val time4 = new TimeB(11, 61)
time2.hour
time2.minute
time1.before(time2)
time2.before(time1)

// ZAD 3

class Pojazd(val producer: String, val model: String, val year: Int, private val registrationString: String):
    private var _registration = registrationString
    def registration: String = _registration
    def registration_=(newRegistration: String) = _registration = newRegistration
    def this(producer: String, model: String) = {
        this(producer, model, -1, "")
    }
    def this(producer: String, model: String, year: Int) = {
        this(producer, model, year, "")
    }
    def this(producer: String, model: String, registration: String) = {
        this(producer, model, -1, registration)
    }
end Pojazd

val p1 = new Pojazd("Ferrari", "Laferrari")
val p2 = new Pojazd("Ford", "Raptor", 2014)
val p3 = new Pojazd("Seat", "Leon", 2014, "DW91770")
val p4 = new Pojazd("Dodge", "Charger", "LV91201")
p1.producer
p1.model
p1.year
p1.registration
p1.registration = "DSW21371"
p1.registration