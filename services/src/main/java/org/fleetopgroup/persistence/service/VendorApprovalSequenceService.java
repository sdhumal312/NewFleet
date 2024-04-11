package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.VendorApprovalSequenceRepository;
import org.fleetopgroup.persistence.model.VendorApprovalSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IVendorApprovalSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VendorApprovalSequenceService implements IVendorApprovalSequenceService {

	@Autowired	
	private VendorApprovalSequenceRepository	vendorApprovalSequenceRepository;
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
	public synchronized VendorApprovalSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception {
		VendorApprovalSequenceCounter	counter = vendorApprovalSequenceRepository.findNextSequenceNumber(companyId);
		if(counter != null)
			vendorApprovalSequenceRepository.updateNextSequenceNumber(counter.getNextVal() + 1, counter.getSequence_Id());
		return counter;
	}

	@Override
	@Transactional
	public void updateNextSequenceNumber(long nextVal, long sequece_id) throws Exception {
		
		vendorApprovalSequenceRepository.updateNextSequenceNumber(nextVal, sequece_id);
	}
	
	
}
