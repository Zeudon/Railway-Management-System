package com.Railway.Railway_Mangement_System.service;

import com.Railway.Railway_Management_System.models.OneWayList;
import com.Railway.Railway_Management_System.models.SearchContent;

public interface TrainService {
	
	public boolean verfiyDateAndTime(SearchContent content, OneWayList result);
	
	public void searchOneWay(SearchContent content, OneWayList result);
	
	

}
