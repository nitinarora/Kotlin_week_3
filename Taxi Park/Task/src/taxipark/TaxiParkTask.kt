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
        }.values.flatten().toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val (discountedTrips, otherTrips) = trips.partition { it.discount != null }
    val mapPassengerDiscountedTripCount = discountedTrips.flatMap { it.passengers.toList() }.groupingBy { it }.eachCount()
    return mapPassengerDiscountedTripCount.keys.toSet()
}

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
    return mapRangeCount.maxBy { it.value }!!.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if(trips.isEmpty()) return false
    val numberDrivers = this.allDrivers.size
    val totalIncome: Double = trips.map { it.cost }.sum()
    val driverIncome: List<Pair<Driver, Double>> = trips.map { it.driver to it.cost }
    val map = HashMap<String, Double>()
    driverIncome.forEach {
        val key = it.first.name
        if(map.containsKey(key)) {
            map.put(key, map.getValue(key) + it.second)
        } else {
            map.put(key, it.second)
        }
    }

    map.forEach {
        if(it.value / totalIncome * 100 == 80.0 && numberDrivers == 5) return true
    }
    return false
}