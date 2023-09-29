package com.ba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ba.model.CustomerDetails;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerDetails, Integer> {

	public CustomerDetails findByAccountNumberAndPassword(int accountNumber, String password);

	@Query(value ="select IFNULL(max(customer_details),0) from customer_details" , nativeQuery = true)
	public int fetchMaxAccountNumber();

	@Modifying
	@Transactional
	@Query(value = "update customer_details  set password = :newPassword where account_Number = :accountNumber1", nativeQuery = true)
	public int updatePassword(@Param("newPassword") String newPassword, @Param("accountNumber1") int accountNumber1);



}
