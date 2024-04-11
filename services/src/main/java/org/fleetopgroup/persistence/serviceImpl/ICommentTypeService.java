package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.CommentTypeDto;
import org.fleetopgroup.persistence.model.CommentType;
import org.fleetopgroup.web.util.ValueObject;

public interface ICommentTypeService {

	
	public ValueObject saveCommentTypeDetails(ValueObject valueObject) throws Exception;
	
	public List<CommentType> validateCommentTypeName(String subCategoryName, Integer companyId) throws Exception;

	ValueObject getcommentTypeListByCompanyId(ValueObject valueObject) throws Exception;
	
	public List<CommentTypeDto> getcommentTypeList(Integer pageNumber, Integer companyId) throws Exception ;
	
	public ValueObject getCommentTypeById(ValueObject valueObject) throws Exception ;
	
	public ValueObject editCommentType(ValueObject valueObject) throws Exception ;
	
	public ValueObject deleteCommentTypeById(ValueObject valueObject) throws Exception ;
	
	
}
