package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.RenewalTypeHistory;

public interface IRenewalTypeHistoryService {

    public void registerNewRenewalTypeHistory(RenewalTypeHistory renewalTypeHistory) throws Exception;
}
