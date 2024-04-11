package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.RenewalReminderAppDocument;
import org.fleetopgroup.persistence.model.RenewalReminderDocument;
import org.fleetopgroup.persistence.model.RenewalReminderDocumentHistory;
import org.springframework.stereotype.Service;

@Service
public interface IRenewalReminderDocumentService {

	public void transferRenewalReminderDocument(List<RenewalReminderDocument> list) throws Exception;
	
	public void save(org.fleetopgroup.persistence.document.RenewalReminderDocument	document) throws Exception;
	
	public org.fleetopgroup.persistence.document.RenewalReminderDocument get_RenewalReminder_Document(Long renewal_id, Integer companyId) ;
	
	public void delete_document(Long id, Integer companyId);
	
	public List<org.fleetopgroup.persistence.document.RenewalReminderDocument>  getBigDocList() throws Exception;
	
	public void updateCompanyId() ;

	public void transferRenewalReminderAppDocument(List<RenewalReminderAppDocument> renewalReminderAppDocumentList) throws Exception;

	public void transferRenewalReminderDocumentHistory(List<RenewalReminderDocumentHistory> renewalReminderDocumentHistoryList) throws Exception;
	
	public long getRenewalReminderAppDocumentMaxId() throws Exception;
	
	public long getRenewalReminderDocumentHistoryMaxId() throws Exception;
	
	public List<org.fleetopgroup.persistence.document.RenewalReminderDocument> getRenewalDocumentsByRenewalId(long renewalId, int companyId) throws Exception;
}
