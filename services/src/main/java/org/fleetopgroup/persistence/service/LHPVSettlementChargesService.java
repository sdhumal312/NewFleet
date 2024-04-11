package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.persistence.dao.LHPVSettlementChargesRepository;
import org.fleetopgroup.persistence.model.LHPVSettlementCharges;
import org.fleetopgroup.persistence.serviceImpl.ILHPVSettlementChargesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LHPVSettlementChargesService implements ILHPVSettlementChargesService {

	private @Autowired	LHPVSettlementChargesRepository		lhpvSettlementChargesRepository;

	@Override
	public void saveLHPVSettlementCharges(LHPVSettlementCharges settlementCharges) throws Exception {
		lhpvSettlementChargesRepository.save(settlementCharges);
	}

	@Override
	public void saveLHPVSettlementChargesList(List<LHPVSettlementCharges> settlementCharges) throws Exception {
		
		lhpvSettlementChargesRepository.saveAll(settlementCharges);
	}
}
