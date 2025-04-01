package com.example.AppointmentBook.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AppointmentBook.model.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

}
