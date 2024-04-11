package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;


public interface IToolBoxDetailsService 
{

	public ValueObject saveToolBoxDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getToolBoxDetailsList(ValueObject valueObject) throws Exception;
	
	public ValueObject getToolBoxDetailsByToolBoxDetailsId(ValueObject valueObject) throws Exception;
	
	public ValueObject editToolBoxDetails(ValueObject valueObject) throws Exception;

	public ValueObject deleteToolBoxDetails(ValueObject valueObject) throws Exception;

	public ValueObject getToolBoxDetailsByVid(ValueObject valueObject) throws Exception;

	
}
