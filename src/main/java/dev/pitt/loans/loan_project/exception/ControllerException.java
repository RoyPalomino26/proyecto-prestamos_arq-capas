package dev.pitt.loans.loan_project.exception;

import org.springframework.validation.BindingResult;

public class ControllerException extends Exception {

	private BindingResult bindingResult;
	
	public ControllerException() {
		// TODO Auto-generated constructor stub
	}

	public ControllerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ControllerException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	public ControllerException(BindingResult bindingResult) {
		this.bindingResult=bindingResult;
	}
}
