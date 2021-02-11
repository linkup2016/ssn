package org.ssb.ssn.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data
public class SSNApplication extends Candidate{
	private String id;
	private String birthDate;
	private String sex;	
}
