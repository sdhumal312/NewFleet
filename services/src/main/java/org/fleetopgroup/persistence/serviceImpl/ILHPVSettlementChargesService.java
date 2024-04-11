package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.LHPVSettlementCharges;
import org.springframework.stereotype.Service;

@Service
public interface ILHPVSettlementChargesService {

	public void saveLHPVSettlementCharges(LHPVSettlementCharges	settlementCharges) throws Exception;
	
	public void saveLHPVSettlementChargesList(List<LHPVSettlementCharges>	settlementCharges) throws Exception;
	
}
