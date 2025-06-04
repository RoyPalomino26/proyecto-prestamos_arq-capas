package dev.pitt.loans.loan_project.utils;

import static java.util.Objects.isNull;

public class UtilValidator {

	public static Boolean validId(Long id){
		return !isNull(id) && id > 0;
	}

}
