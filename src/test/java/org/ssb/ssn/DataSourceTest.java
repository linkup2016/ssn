package org.ssb.ssn;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.ssb.ssn.data.DataSource;
import org.ssb.ssn.exceptions.RecordNotFoundException;
import org.ssb.ssn.models.Record;

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
