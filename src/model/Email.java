package model;

public class Email {

	private String to;
	private String from;
	private String subject;
	private String body;
	private String attachmentPath;
	
	public Email(String to, String from, String subject, String body){
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.body = body;
	}
	
	public Email(String to, String from, String subject, String body, String attachmentPath){
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.body = body;
		this.attachmentPath = attachmentPath;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	
}
