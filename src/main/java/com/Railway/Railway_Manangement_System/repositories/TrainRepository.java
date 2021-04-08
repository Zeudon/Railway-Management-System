package com.Railway.Railway_Manangement_System.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Railway.Railway_Management_System.models.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
	
	Train findByBound (String bound);
	
	@Modifying
	@Query("UPDATE Train t SET t.capacity = :capacity")
	@Transactional
	void updateCapacity(@Param("capacity") int capacity);
	
}