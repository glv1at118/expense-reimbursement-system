package com.solar.www.model;

public class Reimb {

	private int reimbId;
	private double reimbAmount;
	private String reimbSubmitted;
	private String reimbResolved;
	private String reimbDescription;
	private Object reimbReceipt;
	private int authorId;
	private int resolverId;
	private int statusId;
	private int typeId;

	public Reimb(int reimbId, double reimbAmount, String reimbSubmitted, String reimbResolved, String reimbDescription,
			Object reimbReceipt, int authorId, int resolverId, int statusId, int typeId) {
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.reimbSubmitted = reimbSubmitted;
		this.reimbResolved = reimbResolved;
		this.reimbDescription = reimbDescription;
		this.reimbReceipt = reimbReceipt;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.statusId = statusId;
		this.typeId = typeId;
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

	public int getAuthorId() {
		return authorId;
	}

	public int getResolverId() {
		return resolverId;
	}

	public int getStatusId() {
		return statusId;
	}

	public int getTypeId() {
		return typeId;
	}

	public String toString() {
		return "Reimb [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", reimbSubmitted=" + reimbSubmitted
				+ ", reimbResolved=" + reimbResolved + ", reimbDescription=" + reimbDescription + ", reimbReceipt="
				+ reimbReceipt + ", authorId=" + authorId + ", resolverId=" + resolverId + ", statusId=" + statusId
				+ ", typeId=" + typeId + "]";
	}

}
