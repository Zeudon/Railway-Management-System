package com.Railway.Railway_Manangement_System.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Railway.Railway_Management_System.models.Station;
import com.Railway.Railway_Management_System.models.Train;
import com.Railway.Railway_Management_System.models.TrainSchedule;


public interface ScheduleRepository extends JpaRepository<TrainSchedule, Long>{
	
	
	TrainSchedule findByStopAndTrain(Station stop, Train train);
	
	List<TrainSchedule> findByStopAndDepartTimeGreaterThanEqual(Station stop, String departTime);
	
	List<TrainSchedule> findByStopAndArrivalTimeGreaterThan(Station stop, String departTime);
	
	List<TrainSchedule> findByStopAndDepartTime(Station stop, String departTime);
	
}
