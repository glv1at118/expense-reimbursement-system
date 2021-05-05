package com.solar.www.model;

public class ReimbView {

	private int reimbId;
	private double reimbAmount;
	private String reimbSubmitted;
	private String reimbResolved;
	private String reimbDescription;
	private Object reimbReceipt;
	private String authorName;
	private String resolverName;
	private String statusName;
	private String typeName;

	public ReimbView(int reimbId, double reimbAmount, String reimbSubmitted, String reimbResolved,
			String reimbDescription, Object reimbReceipt, String authorName, String resolverName, String statusName,
			String typeName) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.reimbSubmitted = reimbSubmitted;
		this.reimbResolved = reimbResolved;
		this.reimbDescription = reimbDescription;
		this.reimbReceipt = reimbReceipt;
		this.authorName = authorName;
		this.resolverName = resolverName;
		this.statusName = statusName;
		this.typeName = typeName;
	}

	public int getReimbId() {
		return reimbId;
	}

	public double getReimbAmount() {
		return reimbAmount;
	}

	public String getReimbSubmitted() {
		return reimbSubmitted;
	}

	public String getReimbResolved() {
		return reimbResolved;
	}

	public String getReimbDescription() {
		return reimbDescription;
	}

	public Object getReimbReceipt() {
		return reimbReceipt;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getResolverName() {
		return resolverName;
	}

	public String getStatusName() {
		return statusName;
	}

	public String getTypeName() {
		return typeName;
	}

	public String toString() {
		return "ReimbView [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", reimbSubmitted=" + reimbSubmitted
				+ ", reimbResolved=" + reimbResolved + ", reimbDescription=" + reimbDescription + ", reimbReceipt="
				+ reimbReceipt + ", authorName=" + authorName + ", resolverName=" + resolverName + ", statusName="
				+ statusName + ", typeName=" + typeName + "]";
	}
}
