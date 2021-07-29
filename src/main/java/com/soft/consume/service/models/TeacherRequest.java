package com.soft.consume.service.models;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TeacherRequest {


	private String matricule;

	private String firstName;
	private String lastName;
	private int score;
	private LocalDate dateOfBirth;
	
	private LaptopRequest laptop;
	
	private ClubRequest club;
	
	private List<ClassRoomRequest> classes;
	
	
}
