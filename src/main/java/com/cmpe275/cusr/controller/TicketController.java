package com.cmpe275.cusr.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmpe275.cusr.model.Booking;
import com.cmpe275.cusr.model.CustomUserDetails;
import com.cmpe275.cusr.model.Ticket;
import com.cmpe275.cusr.model.User;
import com.cmpe275.cusr.repository.TicketRepository;
import com.cmpe275.cusr.service.TicketService;
import com.cmpe275.cusr.service.UserService;

@Controller
public class TicketController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private TicketService ticketService;
	
//	@Autowired
//	private EmailService emailService;
	
	@PostMapping("/purchaseConfirm")
	public String purchase(Model model, @ModelAttribute Booking booking,
		@AuthenticationPrincipal CustomUserDetails userdetails) {
			User users = new User();
			String user1=userdetails.getUsername();
			User user=userService.getUserFromDB(user1);
	
	
		model.addAttribute("email", user.getEmail());
	boolean purchaseRes = ticketService.purchase(user,booking);

		model.addAttribute("purchaseRes", purchaseRes == true ? "success" : "fail");
		if (purchaseRes) {
			model.addAttribute("numOfSeats", booking.getNumOfSeats());
			model.addAttribute("passenger", booking.getPassenger());
			model.addAttribute("totalPrice", booking.getPrice() + 1);
			model.addAttribute("departureDate", booking.getDepartureDate());
			model.addAttribute("departureTrip", booking.getDepartureTrip());
			String returnDate = booking.getReturnDate();
			String isRound = (returnDate != null && !returnDate.isEmpty()) ? "Y" : "N";
			model.addAttribute("round", isRound);
			if (isRound.equals("Y")) {
				model.addAttribute("returnDate", returnDate);
				model.addAttribute("returnTrip", booking.getReturnTrip());
			}
		}
	
		//	String content = emailService.mailBuilder(booking);
	//	emailService.sendMail(user.getEmail(),"CUSR Ticket Booking Confirmation", content);
	//	return "purchaseConfirm";
		if (purchaseRes) {
			model.addAttribute("numOfSeats", booking.getNumOfSeats());
			model.addAttribute("passenger", booking.getPassenger());
			model.addAttribute("totalPrice", booking.getPrice() + 1);
			model.addAttribute("departureDate", booking.getDepartureDate());
			model.addAttribute("departureTrip", booking.getDepartureTrip());
			String returnDate = booking.getReturnDate();
			String isRound = (returnDate != null && !returnDate.isEmpty()) ? "Y" : "N";
			model.addAttribute("round", isRound);
			if (isRound.equals("Y")) {
				model.addAttribute("returnDate", returnDate);
				model.addAttribute("returnTrip", booking.getReturnTrip());
			}
			String msg = "Thanks for your booking! Here is the ticket details:";
	//		String content1 = emailService.bookingMailBuilder(booking, "emailTemplateBook", msg);
	//	emailService.sendMail(user.getEmail(),"CUSR Ticket Booking Confirmation", content1);
			return "ticketbooking";
		} else {
			String msg = "Sorry, we could not proceed with the following booking. Please try your search again!";
	//	String content1 = emailService.bookingMailBuilder(booking, "emailTemplateBook", msg);
//		emailService.sendMail(user.getEmail(),"CUSR Ticket Booking Fail", content1);
			return "puchaseFail";
		}
	}

	@GetMapping("/ticketCancel")
	public String cancel(@RequestParam("id") long ticketId, Model model) {
		Ticket ticket = ticketRepository.findOne(ticketId);
		model.addAttribute("numOfSeats", ticket.getNumOfSeats());
		model.addAttribute("passenger", ticket.getPassenger());
		model.addAttribute("totalPrice", ticket.getPrice());
		model.addAttribute("departureDate", ticket.getDepartDate());
		model.addAttribute("departureTime", ticket.getDepartSegment1DepartTime());
		model.addAttribute("departureStation", ticket.getDepartStation());
		model.addAttribute("arrivalStation", ticket.getArrivalStation());
		String returnDate = ticket.getReturnDate();
		String isRound = (returnDate != null && !returnDate.isEmpty()) ? "Y" : "N";
		model.addAttribute("round", isRound);
		if (isRound.equals("Y")) {
			model.addAttribute("returnDate", returnDate);
			model.addAttribute("returnTime", ticket.getReturnSegment1DepartTime());
		}
	//	User user = userService.findUser();
		if (ticketService.cancel(ticketId)) {
			String msg = "The following booking has been successfully cancelled!";
			model.addAttribute("message", msg);
		//	String content = emailService.ticketMailBuilder(ticketId, "emailTemplateCancel", msg);
		//	emailService.sendMail(user.getEmail(),"CUSR Ticket Cancellation Confirmation", content);
		} else {
			String msg = "The following booking cannot be cancelled. Please cancel your ticket(s) earlier.";
			model.addAttribute("message", msg);
	//		String content = emailService.ticketMailBuilder(ticketId, "emailTemplateCancel", msg);
	//		emailService.sendMail(user.getEmail(),"CUSR Ticket Cancellation Fail", content);			
		}
		return "cancelticket";
	}
	@GetMapping("/print")
	public String print(@RequestParam("id") long ticketId,Model model)
	{
		
		Ticket ticket = ticketRepository.findOne(ticketId);
		model.addAttribute("email",ticket.getUser());
		model.addAttribute("numOfSeats", ticket.getNumOfSeats());
		model.addAttribute("passenger", ticket.getPassenger());
		model.addAttribute("totalPrice", ticket.getPrice());
		model.addAttribute("departureDate", ticket.getDepartDate());
		model.addAttribute("departureTime", ticket.getDepartSegment1DepartTime());
		model.addAttribute("departureStation", ticket.getDepartStation());
		model.addAttribute("arrivalStation", ticket.getArrivalStation());
		
		String msg="Thanku For Purchasing the Ticket -Click on Print Ticket to a copy of your ticket";
		model.addAttribute("message",msg);
		model.addAttribute("id",ticketId);
		
		
		
		
		
		return "ticketprint";
	}
	
	@GetMapping("/tickets")
	public String showUserTickets(Model model,@AuthenticationPrincipal CustomUserDetails userdetails) {
		User users = new User();
		String user1=userdetails.getUsername();
		User user=userService.getUserFromDB(user1) ;
		long userId =user.getUserId();
		
		
		List<Ticket> qRes =  ticketRepository.findTicketsByUserId(userId);
		ArrayList<Ticket> ticketList = new ArrayList<>();
		for(Ticket ticket : qRes) {
			if(ticketService.timeCheck(ticket.getDepartDate(), ticket.getDepartSegment1ArrivalTime(), 0))
				ticketList.add(ticket);
		}
		
		model.addAttribute("ticketList", ticketList);
		return "viewticket";
	}
}
