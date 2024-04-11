package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripsheetDueAmount;
import org.fleetopgroup.persistence.model.TripsheetDueAmountPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripsheetDueAmountPaymentRepository extends JpaRepository<TripsheetDueAmountPayment, Long>{
	
}