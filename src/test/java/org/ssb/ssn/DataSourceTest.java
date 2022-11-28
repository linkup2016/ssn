package org.ssb.ssn;

import org.junit.jupiter.api.Test;
import org.ssb.ssn.data.DataSource;
import org.ssb.ssn.exceptions.RecordNotFoundException;
import org.ssb.ssn.models.Record;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataSourceTest {

	@Test
	void testRegisterApplication() throws RecordNotFoundException {
		DataSource ds = new DataSource();
		try {
			Record r = ds.getARecord("56210527");
			assertEquals(r.getFirstName(),"Hareg");
		}catch(RecordNotFoundException e) {
			e.getMessage();
		}
	}
}
