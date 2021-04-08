package com.Railway.Railway_Manangement_System.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Railway.Railway_Management_System.models.Train;
import com.Railway.Railway_Management_System.models.TrainStatus;

public interface TrainStatusRepository extends JpaRepository<TrainStatus, Long>{
	
	TrainStatus findByTrainAndDate(Train train, String date);

}
