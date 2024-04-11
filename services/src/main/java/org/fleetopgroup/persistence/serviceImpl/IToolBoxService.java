package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.ToolBox;
import org.fleetopgroup.web.util.ValueObject;


public interface IToolBoxService 
{

	public ValueObject saveToolBox(ValueObject valueObject) throws Exception;
	
	public ToolBox validateToolBoxName(String toolBoxName, Integer companyId) throws Exception;
	
	public ValueObject getToolBoxList(ValueObject valueObject) throws Exception;
	
	public ValueObject getToolBoxListById(ValueObject valueObject) throws Exception;
	
	public ValueObject editToolBox(ValueObject valueObject) throws Exception;

	public ValueObject deleteToolBox(ValueObject valueObject) throws Exception;

	public ValueObject getAllToolBoxForAutoComplete(String term)throws Exception;
	
}
