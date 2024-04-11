package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.document.SequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceCounterService implements ISequenceCounterService {
	
	@Autowired
	private MongoTemplate	mongoTemplate;
	
	@Override
	public long getNextSequence(String name) {
		return increaseCounter(name);
	}
	
	/**
	    * @author Manish Singh
	    * this method will update the counter in database and return counter value
	    * @param counterName // to define counter name in database
	    * @return
	    */
	   private long increaseCounter(String counterName){
	       Query query = new Query(Criteria.where("_id").is(counterName));
	       Update update = new Update().inc("nextVal", 1);
	       SequenceCounter counter = mongoTemplate.findAndModify(query, update, SequenceCounter.class); // return old Counter object
	       if(counter == null) {
	    	   counter = new SequenceCounter();
	    	   counter.setId(counterName);
	    	   counter.setNextVal((long) 1);
	    	   mongoTemplate.save(counter);
	       }
	       return counter.getNextVal(); 
	   }
}
