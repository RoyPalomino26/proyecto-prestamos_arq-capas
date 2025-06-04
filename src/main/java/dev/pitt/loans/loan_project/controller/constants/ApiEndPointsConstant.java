package dev.pitt.loans.loan_project.controller.constants;



public class ApiEndPointsConstant {

	// Maestras
	
	// Core
	
	// Cliente
	private final static String API_CLIENT			             = "/v1/clients";
	public final static String API_NATURAL_CLIENT		 = API_CLIENT + "/naturals";
	public final static String API_JURIDICAL_CLIENT		 = API_CLIENT + "/juridicals";
	public final static String API_CLIENT_PHONE			 = API_CLIENT + "/phones";
	public final static String API_CLIENT_DOCUMENT	 = API_CLIENT + "/documents";

	// Employee
	private final static String API_EMPLOYEE					= "/v1/employees";
	public final static String API_ANALYST						= API_EMPLOYEE + "/analysts";

	// Finance
	private final static String API_FINANCE					= "/v1/finances";
	public final static String API_CURRENCY					= API_FINANCE + "/currencies";
	public final static String API_INTEREST_RATE			= API_FINANCE + "/interest-rates";

	// Loan
	public final static String API_LOAN							= "/v1/loans";
	
	// Integracion
	
	// Organización
	// MySQL
	public final static String API_DEPARTAMENTO		= "/integracion/v1/departamentos";



}
