package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.LHPVDetailsDto;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;

public class LHPVBL {

	
	public List<LHPVDetailsDto>	getLHPVDetailsDTO(JSONArray	jsonArray, ValueObject	valueObject) throws Exception {
		LHPVDetailsDto					lhpvDetails		= null;
		List<LHPVDetailsDto>			lhpvList		= null;
		try {
			lhpvList	= new ArrayList<>();
			if(jsonArray.length() > 0) {
				
				for(int i = 0; i<jsonArray.length(); i++) {
					lhpvDetails	= new LHPVDetailsDto();
					
					lhpvDetails.setLhpvId(jsonArray.getJSONObject(i).getLong("lhpvId"));
					lhpvDetails.setAccountGroupId(jsonArray.getJSONObject(i).getLong("accountGroupId"));
					lhpvDetails.setLhpvDateTimeStamp(new Timestamp((long)jsonArray.getJSONObject(i).get("creationDateTimeStamp")));
					lhpvDetails.setTotalAmount(jsonArray.getJSONObject(i).getDouble("totalAmount"));
					lhpvDetails.setAdvanceAmount(jsonArray.getJSONObject(i).getDouble("advanceAmount"));
					lhpvDetails.setBalanceAmount(jsonArray.getJSONObject(i).getDouble("balanceAmount"));
					//lhpvDetails.setVehicleNumberMasterId(jsonArray.getJSONObject(i).);
					//	lhpvDetails.setVehicleNumber(jsonArray.getJSONObject(i).vehicleNumber);
					lhpvDetails.setlHPVNumber(jsonArray.getJSONObject(i).getString("lHPVNumber"));
					lhpvDetails.setRemark(jsonArray.getJSONObject(i).getString("remark"));
					lhpvDetails.setLhpvChargeTypeMasterId(jsonArray.getJSONObject(i).getLong("lhpvChargeTypeMasterId"));
					lhpvDetails.setChargeName(jsonArray.getJSONObject(i).getString("chargeName"));
					lhpvDetails.setLhpvSettlementChargesId(jsonArray.getJSONObject(i).getLong("lhpvSettlementChargesId"));
					lhpvDetails.setVid(valueObject.getInt("vid", 0));
					lhpvDetails.setChargeAmount(jsonArray.getJSONObject(i).getDouble("chargeAmount"));
					lhpvDetails.setTotalActualWeight(jsonArray.getJSONObject(i).getDouble("totalActualWeight"));
					lhpvDetails.setBoliWeight(jsonArray.getJSONObject(i).getDouble("boliWeight"));
					
					lhpvList.add(lhpvDetails);
					
				}
			}
			
			return lhpvList;
		} catch (Exception e) {
			throw	e;
		}
	}
	
	
}
