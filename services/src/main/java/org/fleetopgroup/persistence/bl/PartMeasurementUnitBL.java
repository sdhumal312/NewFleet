package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.PartMeasurementUnitDto;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;


public class PartMeasurementUnitBL {
	public PartMeasurementUnitBL() {
	}

	// save the PartMeasurementUnit Model
	public PartMeasurementUnit prepareModel(PartMeasurementUnit PartMeasurementUnitBean) {
		PartMeasurementUnit status = new PartMeasurementUnit();
		status.setPmuid(PartMeasurementUnitBean.getPmuid());
		status.setPmuName(PartMeasurementUnitBean.getPmuName());
		status.setPmuSymbol(PartMeasurementUnitBean.getPmuSymbol());
		status.setPmudescription(PartMeasurementUnitBean.getPmudescription());
		status.setNeedConversion(PartMeasurementUnitBean.isNeedConversion());
		if(PartMeasurementUnitBean.isNeedConversion()) {
			status.setConvertTo(PartMeasurementUnitBean.getConvertTo());
			status.setConversionRate(PartMeasurementUnitBean.getConversionRate());
		}

		return status;
	}

	// show the List Of Vehicle Status
	public List<PartMeasurementUnit> prepareListofBean(List<PartMeasurementUnit> PartMeasurementUnit) {
		List<PartMeasurementUnit> beans = null;
		if (PartMeasurementUnit != null && !PartMeasurementUnit.isEmpty()) {
			beans = new ArrayList<PartMeasurementUnit>();
			PartMeasurementUnit bean = null;
			for (PartMeasurementUnit PartCate : PartMeasurementUnit) {
				bean = new PartMeasurementUnit();
				bean.setPmuid(PartCate.getPmuid());
				bean.setPmuName(PartCate.getPmuName());
				bean.setPmuSymbol(PartCate.getPmuSymbol());
				bean.setPmudescription(PartCate.getPmudescription());
				bean.setNeedConversion(PartCate.isNeedConversion());
				bean.setConvertTo(PartCate.getConvertTo());
				bean.setConversionRate(PartCate.getConversionRate());

				beans.add(bean);
			}
		}
		return beans;
	}
	
	public List<PartMeasurementUnitDto> prepareListofBeanDto(List<PartMeasurementUnitDto> PartMeasurementUnit) {
		List<PartMeasurementUnitDto> beans = null;
		if (PartMeasurementUnit != null && !PartMeasurementUnit.isEmpty()) {
			beans = new ArrayList<PartMeasurementUnitDto>();
			PartMeasurementUnitDto bean = null;
			for (PartMeasurementUnitDto PartCate : PartMeasurementUnit) {
				
				bean = new PartMeasurementUnitDto();
				bean.setPmuid(PartCate.getPmuid());
				bean.setPmuName(PartCate.getPmuName());
				bean.setPmuSymbol(PartCate.getPmuSymbol());
				bean.setPmudescription(PartCate.getPmudescription());
				bean.setNeedConversion(PartCate.isNeedConversion());
				bean.setConvertTo(PartCate.getConvertTo());
				bean.setConvertToStr(PartCate.getConvertToStr());
				bean.setConversionRate(PartCate.getConversionRate());
				
				if(bean.isNeedConversion()) {
					bean.setNeedConversionStr("Yes");
				}else {
					bean.setNeedConversionStr("No");
				}
				
				

				beans.add(bean);
			}
		}
		return beans;
	}

	// edit Show PartMeasurementUnitBean
	public PartMeasurementUnit preparePartMeasurementUnitBean(PartMeasurementUnit status) {
		PartMeasurementUnit bean = new PartMeasurementUnit();
		bean.setPmuid(status.getPmuid());
		bean.setPmuName(status.getPmuName());
		bean.setPmuSymbol(status.getPmuSymbol());
		bean.setPmudescription(status.getPmudescription());
		bean.setNeedConversion(status.isNeedConversion());
		bean.setConvertTo(status.getConvertTo());
		bean.setConversionRate(status.getConversionRate());
		return bean;
	}
	
	public PartMeasurementUnitDto preparePartMeasurementUnitBean(PartMeasurementUnitDto status) {
		PartMeasurementUnitDto bean = new PartMeasurementUnitDto();
		bean.setPmuid(status.getPmuid());
		bean.setPmuName(status.getPmuName());
		bean.setPmuSymbol(status.getPmuSymbol());
		bean.setPmudescription(status.getPmudescription());
		bean.setNeedConversion(status.isNeedConversion());
		bean.setConvertTo(status.getConvertTo());
		bean.setConversionRate(status.getConversionRate());
		bean.setConvertToStr(status.getConvertToStr());
		bean.setNeedConversionStr(status.getNeedConversionStr());
		
		return bean;
	}
}
