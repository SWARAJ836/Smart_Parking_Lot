
SWARAJ Smart Parking Lot

================================================================================================

Tech Stack:
	Java 21
	Spring Boot
	Spring Data JPA
	MySQL

------------------------------------------------------------------------------------------------------

	==== Functional Overview : ====

A. Parking Spot Allocation :
	
	1.Vehicles are allocated spots based on vehicle size :
		Bus fits in "Large" slot,
		Car fits in "Medium" or Large,
		Bike fits in "Small"

	2. Vehicles are allocated first-fit spot with proximity optimization based on an algorithm.


	#Steps for Spot Allocation Algorithm :

		i.  Determine required 'spot_type' from 'vehicle_type'
		ii. Fetch list of ParkingSpot where:

			a. 'spot_type' is greater than or equal to required

			b. 'is_occupied' = false

			c. Sort by proximity (level, zone)

			d. Select first spot and mark as occupied

------------------------------------------------------------------------------------------------------

B. Check-In / Check-Out:

	1. On check-in vehicle is assigned slot, 
	2. Ticket is generated with entry time,
	3. On check-out: exit time is recorded, 
	4. Fee is calculated

------------------------------------------------------------------------------------------------------

C. Fee Calculation:

	Vehicle Type	Rate per Hour
	Motorcycle	₹10
	Car		₹20
	Bus		₹50

------------------------------------------------------------------------------------------------------

D. Real-Time Availability:

	Slot status is updated immediately


------------------------------------------------------------------------------------------------------
Concurrency Handling Approach:

	1. Use @Transactional at service layer

	2. Add a version column with @Version for optimistic locking





