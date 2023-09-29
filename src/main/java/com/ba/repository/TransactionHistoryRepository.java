package com.ba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ba.model.TransactionHistory;
@Repository
public interface TransactionHistoryRepository  extends JpaRepository<TransactionHistory, Integer> {
	
	@Modifying
	@Transactional
	@Query(value ="select * from Transaction_History t where t.customer_Id= :custId order by t.datetime desc limit 10", nativeQuery = true)
	public List<TransactionHistory> fetchLast10TxnByCustomerID( @Param("custId") long custId );
	
	

	@Modifying
	@Transactional
	@Query(value= "select * from Transaction_History t where t.customer_Id= :custId order by t.datetime desc", nativeQuery = true)
	public List<TransactionHistory> fetchTxnByCustomerID( @Param("custId") long custId );
	
	
	

}



