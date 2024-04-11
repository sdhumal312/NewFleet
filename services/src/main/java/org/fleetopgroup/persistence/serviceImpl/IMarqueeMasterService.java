package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.MarqueeMasterDto;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IMarqueeMasterService {

	public List<MarqueeMasterDto> getCompayWiseMarqueeMessage() throws Exception;

	public ValueObject saveMarqueeMessage(ValueObject object) throws Exception;//same new Method (Save)	

	public ValueObject getMessageByCompanyId(ValueObject object)throws Exception;

	public List<MarqueeMasterDto> getAllMarqueeMessagesList() throws Exception;

	
	
}
