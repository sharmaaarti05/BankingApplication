package com.ba.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Transaction_History")
public class TransactionHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "datetime")
	private Date datetime;

	@Column(name = "credit_Debit")
	private String creditDebit;

	@Column(name = "amount")
	private double amount;

	@Column(name = "final_balance")
	private double finalBalance;

	@Column(name = "customer_id")
	private long customerId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getCreditDebit() {
		return creditDebit;
	}

	public void setCreditDebit(String creditDebit) {
		this.creditDebit = creditDebit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFinalBalance() {
		return finalBalance;
	}

	public void setFinalBalance(double finalBalance) {
		this.finalBalance = finalBalance;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "TransactionHistory [id=" + id + ", datetime=" + datetime + ", creditDebit=" + creditDebit + ", amount="
				+ amount + ", finalBalance=" + finalBalance + ", customerId=" + customerId + "]";
	}

	
	
}
