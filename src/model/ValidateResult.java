package model;

public class ValidateResult {
	
	private boolean valid;
	private String message;
	
	public ValidateResult(boolean valid, String message) {
		this.valid = valid;
		this.message = message;
	}
	
	public ValidateResult() {
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
