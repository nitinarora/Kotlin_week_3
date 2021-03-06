package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.filter { driver -> trips.count { it.driver.name == driver.name } == 0 }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.filter { p -> trips.count { it.passengers.contains(p) } >= minTrips }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        trips.groupBy { it.driver.name }.mapValues { entry ->
            entry.value.flatMap { it.passengers.toList() }.groupingBy { it }.eachCount().filter { it.value > 1 }.keys
        }.get(driver.name)?: emptySet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers.filter {
        p -> trips.filter { p in it.passengers && it.discount != null }.count() > trips.filter { p in it.passengers && it.discount == null }.count()
    }.toSet()


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    fun Int.getRangeWithInterval(interval: Int) : IntRange {
        if(this / interval == 0) return 0 until interval
        val rangeStart = this/interval * interval
        return rangeStart until rangeStart + interval
    }

    val mapRangeCount = trips.map { it.duration.getRangeWithInterval(10) }.groupingBy { it }.eachCount()
    return mapRangeCount.maxBy { it.value }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if(trips.isEmpty()) return false
    val totalIncome: Double = trips.map { it.cost }.sum()
    var top20PercDriversIncome = 0.0

    fun top20PercentDrivers()  =  trips.sortedByDescending { it.cost }.map { it.driver }.take((allDrivers.size * 0.2).toInt())
    fun Driver.calculateDriversTotalIncome() = trips.filter { it.driver.name == this.name  }.sumByDouble { it.cost }

    top20PercentDrivers().forEach {
        driver -> top20PercDriversIncome += driver.calculateDriversTotalIncome()
    }
    return (top20PercDriversIncome / totalIncome * 100 >= 80.0)
}