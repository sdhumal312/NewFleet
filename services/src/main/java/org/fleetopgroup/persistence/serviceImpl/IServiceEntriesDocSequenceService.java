package org.fleetopgroup.persistence.serviceImpl;

import org.springframework.stereotype.Service;

@Service
public interface IServiceEntriesDocSequenceService {
	
	public long getNextSequence(String name) ;
}
