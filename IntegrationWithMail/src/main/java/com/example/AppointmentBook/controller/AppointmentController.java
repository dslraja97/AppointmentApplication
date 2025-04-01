package com.example.AppointmentBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AppointmentBook.model.Appointment;
import com.example.AppointmentBook.repo.AppointmentRepo;
import com.example.AppointmentBook.service.MailService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentRepo appointmentRepo;

	@Autowired
	private MailService mailService;

	@PostMapping("/book")
	public String bookAppointment(@RequestBody Appointment appointment) {
		appointmentRepo.save(appointment);

		// Send Notification to the Patient!
		String patientMessage = "Dear " + appointment.getPatientName() + ",\n\n" + "Your appointment with "
				+ appointment.getDoctorName() + " is scheduled for " + appointment.getAppointmentDate() + ".\n\n"
				+ "Thank you for booking with us!";

		boolean patientEmailSent = mailService.sendMail(appointment.getEmail().toString(), "Appointment Confirmation",
				patientMessage);

		// Send notification email to doctor
		String doctorMessage = "Dear " + appointment.getDoctorName() + ",\n\n" + "You have a new appointment with "
				+ appointment.getPatientName() + " at " + appointment.getAppointmentDate() + ".\n\n" + "Best regards.";

		boolean doctorEmailSent = mailService.sendMail(appointment.getEmail().toString(), "New Appointment", doctorMessage);

		// Check if both emails were sent successfully
		if (patientEmailSent && doctorEmailSent) {
			return "Appointment booked successfully and confirmation emails sent!";
		} else if (!patientEmailSent) {
			return "Appointment booked, but failed to send confirmation email to the patient.";
		} else {
			return "Appointment booked, but failed to send notification email to the doctor.";
		}
	}
}
