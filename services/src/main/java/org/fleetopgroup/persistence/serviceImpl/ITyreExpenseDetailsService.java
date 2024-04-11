package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.document.TyreExpenseDetailsDocument;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadAmount;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.web.multipart.MultipartFile;

//import com.fleetop.dto.UploadFile;

public interface ITyreExpenseDetailsService {

	public ValueObject saveTyreExpenseDetails(ValueObject valueObject,MultipartFile[] file) throws Exception;
	
	public ValueObject getAllTyreExpenseDetails()throws Exception;
	
	public ValueObject getAllTyreExpenseDetailsByTyreTypeId(ValueObject valueInObject)throws Exception;

	public ValueObject getTyreExpenseDetailsByTyreExpenseDetailsId(ValueObject valueInObject)throws Exception;

	public ValueObject updateTyreExpenseDetails(ValueObject valueInObject)throws Exception;

	public ValueObject deleteTyreExpenseDetails(ValueObject valueInObject)throws Exception;

	public ValueObject getTyreExpenseDetailsReport(ValueObject valueInObject)throws Exception;

	public ValueObject getTyreExpenseDetailsByTyreId(ValueObject valueInObject)throws Exception;

	public ValueObject saveRetreadTyreExpenseDetails(ValueObject valueInObject,MultipartFile[] file)throws Exception;

	public ValueObject reOpenTyreRetreadAndDeleteTyreExpense(ValueObject valueInObject)throws Exception;

	public void updateRetreadTyreExpenseDetails(InventoryTyreRetreadAmount tyreRetread)throws Exception;

	public TyreExpenseDetailsDocument getTyreExpenseDetailsDocumentId(long tyreExpenseDetailsDocumentId,Integer company_id)throws Exception;

	
}