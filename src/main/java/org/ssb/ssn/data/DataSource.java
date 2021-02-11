package org.ssb.ssn.data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Repository;
import org.ssb.ssn.exceptions.RecordNotFoundException;
import org.ssb.ssn.models.Record;
import org.ssb.ssn.models.SSNApplication;

@Repository
public class DataSource {
	private HashMap<String, Record> recordsTable = new HashMap<String, Record>();
	private HashMap<String, SSNApplication> applicationTable = new HashMap<String, SSNApplication>();
	static Integer[] usedPreloadNumbers = { 56210527, 51645523, 24416620, 100000000, 999999999, 100000001 };
	private static Set<Integer> usedNumbers = new HashSet<Integer>(Arrays.asList(usedPreloadNumbers));

	public DataSource() {
		this.recordsTable = preloadRecordsTable();
		this.applicationTable = preloadApplicationTable();
	}

	private HashMap<String, SSNApplication> preloadApplicationTable() {

		HashMap<String, SSNApplication> apps = new HashMap<String, SSNApplication>();

		SSNApplication app1 = new SSNApplication();
		app1.setId("56210527");
		app1.setFirstName("Hareg");
		app1.setMiddleName("Mola");
		app1.setLastName("Dems");
		app1.setBirthDate("12-01-2002");

		SSNApplication app2 = new SSNApplication();
		app2.setId("51645523");
		app2.setFirstName("Mesfin");
		app2.setMiddleName("Goitom");
		app2.setLastName("Hailegebriel");
		app2.setBirthDate("12-23-1990");

		SSNApplication app3 = new SSNApplication();
		app3.setId("24416620");
		app3.setFirstName("Rabsa");
		app3.setMiddleName("Lodo");
		app3.setLastName("Henda");
		app3.setBirthDate("1-2-1980");

		apps.put(app1.getId(), app1);
		apps.put(app2.getId(), app2);
		apps.put(app3.getId(), app3);

		return apps;
	}

	private HashMap<String, Record> preloadRecordsTable() {
		HashMap<String, Record> records = new HashMap<String, Record>();
		String today = LocalDate.now().toString();
		
		Record app1 = new Record();
		app1.setSsn("100000000");
		app1.setFirstName("Biritu");
		app1.setMiddleName("Tafa");
		app1.setLastName("Haydi");
		app1.setBirthDate("2002-12-01");
		app1.setSex("Female");
		app1.setDateCreated("2021-02-11");
		
		Record app2 = new Record();
		app2.setSsn("999999999");
		app2.setFirstName("Dergoga");
		app2.setMiddleName("Baynah");
		app2.setLastName("Desta");
		app2.setBirthDate("1990-12-23");
		app2.setSex("Male");
		app2.setDateCreated("1999-02-15");
		
		Record app3 = new Record();
		app3.setSsn("100000001");
		app3.setFirstName("Tbletse");
		app3.setMiddleName("Haile");
		app3.setLastName("Trnaf");
		app3.setBirthDate("1-2-1980");

		records.put(app1.getSsn(), app1);
		records.put(app2.getSsn(), app2);
		records.put(app3.getSsn(), app3);

		return records;
	}

	public String addAnApplication(SSNApplication app) {
		String appId = generateAppid().toString();
		app.setId(appId);
		applicationTable.put(appId, app);
		return app.getId();
	}

	public String addARecord(SSNApplication app) {
		String ssnId = generateSSNid().toString();
		Record record = new Record();
		record.setSsn(ssnId);
		record.setFirstName(app.getFirstName());
		record.setMiddleName(app.getMiddleName());
		record.setLastName(app.getLastName());
		record.setBirthDate(app.getBirthDate());
		record.setSex(app.getSex());

		recordsTable.put(record.getSsn(), record);
		
		return record.getSsn();
	}

	public Record getARecord(String ssn) throws RecordNotFoundException {
		Record record = recordsTable.get(ssn);
		if (record != null) {
			return recordsTable.get(ssn);
		} else
			throw new RecordNotFoundException("No Such Record Exists. ");
	}

	public Collection<Record> getAllRecords() {
		return recordsTable.values();
	}

	public void removeARecord(String ssn) throws RecordNotFoundException {
		if (recordsTable.get(ssn) != null) {
			recordsTable.remove(ssn);
		} else
			throw new RecordNotFoundException("No Such Record Exists. ");
	}

	public void updateRecord(Record record) throws RecordNotFoundException {
		Record existing = recordsTable.get(record.getSsn());
		if (existing != null) {
			existing.setFirstName(record.getFirstName());
			existing.setMiddleName(record.getMiddleName());
			existing.setLastName(record.getLastName());
			existing.setBirthDate(record.getBirthDate());
			existing.setSex(record.getSex());

			recordsTable.put(existing.getSsn(), existing);
		} else
			throw new RecordNotFoundException("No Such Record Exists. ");
	}

	private static Integer generateSSNid() {

		RandomDataGenerator SSNidGenerator = new RandomDataGenerator();
		Integer genNum = SSNidGenerator.nextInt(100000000, 999999999);
		if (!usedNumbers.contains(genNum)) {
			usedNumbers.add(genNum);
			return genNum;
		} else {
			genNum = SSNidGenerator.nextInt(1000000000, 999999999);
			usedNumbers.add(genNum);
			return genNum;
		}
	}

	private static Integer generateAppid() {
		RandomDataGenerator appIdGenerator = new RandomDataGenerator();
		Integer genNum = appIdGenerator.nextInt(10000000, 99999999);

		if (!usedNumbers.contains(genNum)) {
			usedNumbers.add(genNum);
			return genNum;
		} else {
			genNum = appIdGenerator.nextInt(100000000, 99999999);
			usedNumbers.add(genNum);
			return genNum;
		}
	}
}
