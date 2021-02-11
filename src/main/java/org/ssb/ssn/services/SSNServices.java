package org.ssb.ssn.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssb.ssn.data.DataSource;
import org.ssb.ssn.exceptions.RecordNotFoundException;
import org.ssb.ssn.models.Confirmation;
import org.ssb.ssn.models.Record;
import org.ssb.ssn.models.SSNApplication;

@Service
public class SSNServices {
	private DataSource data;
	
	@Autowired
	public SSNServices(DataSource data) {
		this.data = data;
	}
	public Confirmation registerApplicant(SSNApplication app) {
		
		Confirmation confNotice = new Confirmation();
		confNotice.setNoticeId(data.addAnApplication(app));
		confNotice.setConfirmationMessage("Application Successful! Here is your new SSN: "+ data.addARecord(app));
		confNotice.setFirstName(app.getFirstName());
		confNotice.setMiddleName(app.getMiddleName());
		confNotice.setLastName(app.getLastName());
		
		return confNotice;		
	}

	public Record fetchARecord(String id) throws RecordNotFoundException {
		Record rec = data.getARecord(id);
		return rec;
	}

	public Confirmation removeARecord(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Confirmation updateARecord(Record toUpdate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Record> fetchAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Record> showSexDistribution(String sex) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Record> showByCreationDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
