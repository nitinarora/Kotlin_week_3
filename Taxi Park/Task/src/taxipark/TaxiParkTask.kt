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
    val listDuration = trips.map { it.duration }

    var highestRange = 0..9



    return 0..9
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    TODO()
}