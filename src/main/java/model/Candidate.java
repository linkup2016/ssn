package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Candidate {
	protected String firstName;
	protected String middleName;
	protected String lastName;
	protected String birthDate;
}
