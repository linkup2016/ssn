package org.ssb.ssn.controllers;

import java.text.ParseException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.ssb.ssn.exceptions.InvalidInputException;
import org.ssb.ssn.exceptions.RecordNotFoundException;
import org.ssb.ssn.models.Confirmation;
import org.ssb.ssn.models.Record;
import org.ssb.ssn.models.SSNApplication;
import org.ssb.ssn.services.SSNServices;
import org.ssb.ssn.utilities.SSNUtility;

@RestController
@Slf4j
public class SSNController {

//	static Logger log = Logger.getLogger(SSNController.class.getName());
	private SSNServices service;

	@Autowired
	public SSNController(SSNServices service) {
		this.service = service;
	}

	@GetMapping({"/","/ssn"})
	public String welcomeCustomer() throws InvalidInputException {
		log.info("Get request received");
		return "Welcome to our site";
	}

	@PostMapping(path = "/apply", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Confirmation> acceptApplication(@RequestBody SSNApplication app)
			throws InvalidInputException, ParseException {
		log.info("Application validation in progress...");
		log.debug("Appliation details: {}", app);
		SSNUtility.validateApplication(app);
		log.info("Application validated successfully.");
		Confirmation confirmation = service.registerApplicant(app);
		log.info("Applicant successfully registered");
		return ResponseEntity.ok().body(confirmation);
	}

	@GetMapping(path = "/access/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Record> accessARecord(@PathVariable String id) throws InvalidInputException, RecordNotFoundException {
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
	public ResponseEntity<List<Record>> accessRecordsBySex(@PathVariable String sex) {
		SSNUtility.validateSex(sex);
		List<Record> records = service.showSexDistribution(sex);
		return ResponseEntity.ok().body(records);
	}

	@GetMapping(path = "/access/by-date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Record>> accessRecordsByCreationDate(@PathVariable String date) {
		SSNUtility.validateSex(date);
		List<Record> records = service.showByCreationDate(date);
		return ResponseEntity.ok().body(records);
	}

	@DeleteMapping(path = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Confirmation> removeARecord(@PathVariable String id) throws InvalidInputException {
		SSNUtility.validateSSN(id);
		Confirmation confirmation = service.removeARecord(id);
		return ResponseEntity.ok().body(confirmation);
	}

	@PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Confirmation> updateARecord(@RequestBody Record toUpdate) throws InvalidInputException, ParseException, RecordNotFoundException {
		SSNUtility.validateARecord(toUpdate);
		Confirmation confirmation = service.updateARecord(toUpdate);
		return ResponseEntity.ok().body(confirmation);
	}
}
