package org.fleetopgroup.persistence.serviceImpl;

public interface ISequenceService {
	
	public static final Integer STARTING_SEQUENCE_NUMBER	= 1;
	
	public void insertSequenceTableEntries(Integer companyId) throws Exception;
}
