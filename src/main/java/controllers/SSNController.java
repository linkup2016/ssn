package controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import exceptions.InvalidInputException;
import model.Confirmation;
import model.Record;
import model.SSNApplication;
import services.SSNServices;
import utilities.SSNUtility;

@RestController
public class SSNController {
	
	private SSNServices service;
	
	@Autowired
	public SSNController(SSNServices service) {
		this.service = service;
	}
	
	@PostMapping(path = "/apply", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Confirmation> acceptApplication(SSNApplication app) throws InvalidInputException, ParseException {
		SSNUtility.validateApplication(app);
		Confirmation confirmation = service.registerApplicant(app);
		return ResponseEntity.ok().body(confirmation);
	}
	
	@GetMapping(path = "/access", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Record> accessARecord(String id) throws InvalidInputException {
		SSNUtility.validateSSN(id);
		Record record = service.fetchARecord(id);
		return ResponseEntity.ok().body(record);
	}
	
	@GetMapping(path = "/access/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Record>> accessAllRecords() {
		List<Record> records = service.fetchAllRecords();
		return ResponseEntity.ok().body(records);
	}
	
	@GetMapping(path = "/access/by-sex", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Record>> accessRecordsBySex(String sex) {
		SSNUtility.validateSex(sex);
		List<Record> records = service.showSexDistribution(sex);
		return ResponseEntity.ok().body(records);
	}
	
	@GetMapping(path = "/access/by-date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Record>> accessRecordsByCreationDate(String date) {
		SSNUtility.validateSex(date);
		List<Record> records = service.showByCreationDate(date);
		return ResponseEntity.ok().body(records);
	}
	
	@DeleteMapping(path = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Confirmation> removeARecord(String id) throws InvalidInputException {
		SSNUtility.validateSSN(id);
		Confirmation confirmation = service.removeARecord(id);
		return ResponseEntity.ok().body(confirmation);
	}
	
	@PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Confirmation> updateARecord(Record toUpdate) {
		SSNUtility.validateARecord(toUpdate);
		Confirmation confirmation = service.updateARecord(toUpdate);
		return ResponseEntity.ok().body(confirmation);
	}
}
