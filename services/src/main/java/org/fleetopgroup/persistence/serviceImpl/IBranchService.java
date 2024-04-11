package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.BranchDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Branch;
import org.fleetopgroup.persistence.model.BranchDocument;
import org.fleetopgroup.persistence.model.BranchPhoto;
import org.fleetopgroup.web.util.ValueObject;

public interface IBranchService {

	public Branch registerNewBranch(Branch BranchDto) throws Exception;
	
	public Branch updateBranch(Branch BranchDto) throws Exception;
     
	public List<Branch> findAll(Integer company_Id)throws Exception;
    
    public Branch getBranchByID(Integer dri_id,int companyId);
    
    public List<Branch> validateBranchName(String Branch_name, Integer companyId)throws Exception;
    
    public Long countTotalBranch(Integer company_Id) throws Exception;

	public Long countActiveBranch(Integer company_Id) throws Exception;
	
	public void deleteBranch_ID(Integer branch_id, Integer companyId) throws Exception;
	public ValueObject saveBranch(BranchDto branchDto);
	public ValueObject updateBranch(BranchDto branchDto) throws Exception ;
	public ValueObject updateBranchStatus(BranchDto branchDto) throws Exception ;
	
	
	public List<Branch> SearchBranchList(String Search_list, Integer companyId)throws Exception;
	
	public List<Branch> SearchBranchLisrCompanyID(Integer company_ID)throws Exception;
	
	/**This Value Search Only drop down list Branch Order */
	public List<Branch> Search_Olny_Branch_name(String Search_list, CustomUserDetails userDetails)throws Exception;
	
	/** This Value search branch id to get branch name in issues  entries*/
	public Branch get_Issues_BranchID_getBranchName(Integer branch_id);
	
	/*Branch Document*/
	
	public void saveBranchDocument(org.fleetopgroup.persistence.document.BranchDocument BranchDocument);

	public BranchDocument updateBranchDocument(BranchDocument BranchDocument);

	public List<org.fleetopgroup.persistence.document.BranchDocument> listBranchDocument(int Branch_id, Integer companyId);

	public org.fleetopgroup.persistence.document.BranchDocument getBranchDocuemnt(int Branch_docid, Integer companyId);
	
	public void deleteBranchDocument(Integer Branch_documentid, Integer companyId)  throws Exception;
	
	public Long getBranchDocumentCount(int Branch_docid);
	
	
	/*Branch Photo*/
	
	public void addBranchPhoto(org.fleetopgroup.persistence.document.BranchPhoto diverPhoto);

	public BranchPhoto updateBranchPhoto(BranchPhoto diverPhoto);

	public List<org.fleetopgroup.persistence.document.BranchPhoto> listBranchPhoto(int diverPhoto, Integer companyId);

	public org.fleetopgroup.persistence.document.BranchPhoto getBranchPhoto(int Branch_photoid, Integer companyId);
	
	public void deleteBranchPhoto(Integer Branch_Photoid, Integer companyId)  throws Exception;
	
	public Long getPhotoCount(int Branch_docid);

	/**
	 * @param term
	 * @return
	 */
	public List<Branch> Search_Branch_Name_Json(String term, Integer companyId);

	public void transferBranchDocument(List<BranchDocument> branchDocumentList) throws Exception;

	public void transferBranchPhoto(List<BranchPhoto> branchPhotoList) throws Exception;
	
	public long getBranchDocumentMaxId() throws Exception;
	
	public long getBranchPhotoMaxId() throws Exception;
	
	public ValueObject searhByBranchNameInMobile(ValueObject object) throws Exception;

	public List<Branch> SearchBranchListByCompanyId(String term, Integer company_id) throws Exception;
}
