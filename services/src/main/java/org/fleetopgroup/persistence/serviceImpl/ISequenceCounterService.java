package org.fleetopgroup.persistence.serviceImpl;

import org.springframework.stereotype.Service;

@Service
public interface ISequenceCounterService {

	public long getNextSequence(String name) ;
}
