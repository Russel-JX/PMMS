/**
 * ============================================================
 * File : EquipInfoServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.service.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 14, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;












import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.EquipInfoDao;
import com.ge.pmms.po.DeptInfo;
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.po.PlanInfo;
import com.ge.pmms.service.EquipInfoService;
import com.ge.pmms.utils.Tools;

/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 14, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
@Service
@Transactional
public class EquipInfoServiceImpl extends BaseService<EquipInfo> implements EquipInfoService{
	@Autowired
	private EquipInfoDao equipInfoDao;
	
	private List<EquipInfo> equipInfoLst;
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public ServiceReturns getEquipInfoByType(EquipInfo equipInfoReq){
		List<EquipInfo> equipInfo;
	    if(equipInfoReq.getEquipType().equalsIgnoreCase("all")){
	    	equipInfo=equipInfoDao.getAllEquipInfo(equipInfoReq);
	    }else{
	    	equipInfo=equipInfoDao.getEquipInfoByType(equipInfoReq);
	    }
	    equipInfoLst=equipInfo;
		serviceReturns = new ServiceReturns();
		serviceReturns.putData("detail",equipInfo);
		return serviceReturns;
	}
	
	
	/**
	 * @return the equipInfoLst
	 */
	public List<EquipInfo> getEquipInfoLst() {
		return equipInfoLst;
	}

	
	/**
	 * @param equipInfoLst the equipInfoLst to set
	 */
	public void setEquipInfoLst(List<EquipInfo> equipInfoLst) {
		this.equipInfoLst = equipInfoLst;
	}

	public EquipInfo getEquipInfoByEpId(String equipId){
		List<EquipInfo> tempList=new ArrayList<EquipInfo>();
		EquipInfo equipInfo=equipInfoDao.getEquipInfoByEpId(equipId);
		tempList.add(equipInfo);
		equipInfoLst=tempList;
		return equipInfo;
	}
	
	public ServiceReturns saveEquip(JSONObject jsonObj){
		serviceReturns = new ServiceReturns();
		EquipInfo equipInfoReq=formReqPo(jsonObj,"save");
		EquipInfo epRt=getEquipInfoByEpId(equipInfoReq.getEquipId());
		if(epRt!=null){
			 serviceReturns.putData("detail", epRt);
			 
		}else{
			super.save(equipInfoReq);
			serviceReturns.putData("detail", null);
		}
		return serviceReturns;
	}
	public ServiceReturns deleteEquipByEpId(EquipInfo equipInfo){
		serviceReturns = new ServiceReturns();
		serviceReturns.putData("detail",equipInfoDao.deleteEquipByEpId(equipInfo));
		return serviceReturns;
	}
	
	public ServiceReturns updateEquip(JSONObject json){
		serviceReturns = new ServiceReturns();
		EquipInfo equipInfoReq=formReqPo(json,"update");
		getSession().update(equipInfoReq);
	//	getSession().flush();
		return serviceReturns;
	}
	
	

	 private EquipInfo formReqPo (JSONObject jsonObj,String flag){
		 
		 if(flag.equalsIgnoreCase("save")){
			 EquipInfo equipInfo=new EquipInfo();
		//	 equipInfo = getEquipInfoByEpId(Tools.getParameters(jsonObj,"equipId"));
		//	 if(equipInfo==null){
		//		 return null;
		//	 }
			// equipInfo.setId(getParameters(jsonObj,"id"));
			 equipInfo.setEquipId(Tools.getParameters(jsonObj,"equipId"));
			 equipInfo.setAssetId(Tools.getParameters(jsonObj,"assetId"));
			 equipInfo.setEquipNmId(Tools.getParameters(jsonObj,"equipNmId"));
			// equipInfo.setEquipmentName(getParameters(jsonObj,"equipmentName"));
			 equipInfo.setEquipModel(Tools.getParameters(jsonObj,"equipModel"));
			 equipInfo.setSource(Tools.getParameters(jsonObj,"source"));
			 equipInfo.setFactoryNo(Tools.getParameters(jsonObj,"factoryNo"));
			
			 String relesDate=Tools.getParameters(jsonObj,"releaseDate");
			 String installDate=Tools.getParameters(jsonObj,"installDate");
			 if(!Tools.isEmpty(relesDate)){
				 equipInfo.setReleaseDate(relesDate);
			 }
			 if(!Tools.isEmpty(installDate)){
				 equipInfo.setInstallDate(installDate);
			 }
			 equipInfo.setPowerConsum(Tools.getParameters(jsonObj,"powerConsum"));
			
			 equipInfo.setOriginPrice(Tools.getParameters(jsonObj,"originPrice"));
			 equipInfo.setCurrPrice(Tools.getParameters(jsonObj,"currPrice"));
			 equipInfo.setSize(Tools.getParameters(jsonObj,"size"));
			 equipInfo.setWeight(Tools.getParameters(jsonObj,"weight"));
			 equipInfo.setInUse(Tools.getParameters(jsonObj,"inUse"));
			 equipInfo.setDeptId(Tools.getParameters(jsonObj,"deptId"));
			 equipInfo.setTypeId(Tools.getParameters(jsonObj,"equipType"));
			 equipInfo.setEquipType(Tools.getParameters(jsonObj,"equipType"));
			 equipInfo.setCreator(Tools.getParameters(jsonObj,"creator"));
			 equipInfo.setCreateDate(new Date());
			 equipInfo.setLastUpdateDate(new Date());
		//	 equipInfo.setUpdater(getParameters(jsonObj,"updater"));
		//	 String update=getParameters(jsonObj,"lastUpdateDate");
		//	 equipInfo.setLastUpdateDate(format2.parse(update));
			 equipInfo.setRemark(Tools.getParameters(jsonObj,"remark"));
			 return equipInfo;
		 }else if(flag.equalsIgnoreCase("update")){
			 EquipInfo equipInfo;
			try {
				equipInfo = getEquipInfoByEpId(Tools.getParameters(jsonObj,"equipId"));
				//super.get(EquipInfo.class, id)
				 equipInfo.setAssetId(Tools.getParameters(jsonObj,"assetId"));
				equipInfo.setEquipNmId(Tools.getParameters(jsonObj,"equipNmId"));
				// equipInfo.setEquipmentName(getParameters(jsonObj,"equipmentName"));
				 equipInfo.setEquipModel(Tools.getParameters(jsonObj,"equipModel"));
				 equipInfo.setSource(Tools.getParameters(jsonObj,"source"));
				 equipInfo.setFactoryNo(Tools.getParameters(jsonObj,"factoryNo"));
				 equipInfo.setPowerConsum(Tools.getParameters(jsonObj,"powerConsum"));
				 String relesDate=Tools.getParameters(jsonObj,"releaseDate");
				 String installDate=Tools.getParameters(jsonObj,"installDate");
				 if(!Tools.isEmpty(relesDate)){
					 equipInfo.setReleaseDate(relesDate);
				 }else{
					 equipInfo.setReleaseDate(null);
				 }
				 if(!Tools.isEmpty(installDate)){
					 equipInfo.setInstallDate(installDate);
				 }else{
					 equipInfo.setInstallDate(null);
				 }
				 equipInfo.setOriginPrice(Tools.getParameters(jsonObj,"originPrice"));
				 equipInfo.setCurrPrice(Tools.getParameters(jsonObj,"currPrice"));
				 equipInfo.setSize(Tools.getParameters(jsonObj,"size"));
				 equipInfo.setWeight(Tools.getParameters(jsonObj,"weight"));
				 equipInfo.setInUse(Tools.getParameters(jsonObj,"inUse"));
				 equipInfo.setDeptId(Tools.getParameters(jsonObj,"deptId"));
				 equipInfo.setTypeId(Tools.getParameters(jsonObj,"equipType"));
				 equipInfo.setEquipType(Tools.getParameters(jsonObj,"equipType"));
			//	 equipInfo.setCreator(getParameters(jsonObj,"creator"));
			//	 equipInfo.setCreateDate(getParameters(jsonObj,"createDate"));
				 equipInfo.setUpdater(Tools.getParameters(jsonObj,"updater"));
				 equipInfo.setLastUpdateDate(new Date());
				 equipInfo.setRemark(Tools.getParameters(jsonObj,"remark"));
				 return equipInfo;
			}
			catch (Exception e) {
				LOGGER.info(e);
			}
		 }
		return null;
	 }
	
		 
	 public int updateEpDocByEpID(String equipDocId,String equipId){
		 serviceReturns = new ServiceReturns();
		 return equipInfoDao.updateEpDocByEpId(equipDocId,equipId);
	 }
	 
	 public int updateEpDocByEpModelId(String equipDocId,String equipModel){
		 serviceReturns = new ServiceReturns();
		 return equipInfoDao.updateEpDocByEpModelId(equipDocId,equipModel);
	 }
	 
	 public ServiceReturns getDeptInfo(){

		 List<DeptInfo> dpInfoLst=equipInfoDao.getDeptInfo();
		 serviceReturns = new ServiceReturns();
		 serviceReturns.putData("detail", dpInfoLst);
			return serviceReturns;
	 }
	 
	 public List getEquipIdByEpModel(String epModel){
		 List eqIdLst=equipInfoDao.getEquipIdByEpModel(epModel);
		return eqIdLst;
	 }
	 
	 @SuppressWarnings("deprecation")
	public HSSFWorkbook  exportToExcel(){
		 HSSFWorkbook hwb = new HSSFWorkbook();
		 HSSFSheet sheet = hwb.createSheet("EquipInfo");
		 int rowNum = 0;
		 	HSSFRow  heading= sheet.createRow(rowNum);
			HSSFRow title = sheet.createRow(++rowNum);
			//heading样式
			HSSFFont headFont = hwb.createFont();
			headFont.setFontHeightInPoints((short) 17);
			headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle csHead = hwb.createCellStyle();
			csHead.setFont(headFont);
			csHead.setAlignment(CellStyle.ALIGN_CENTER);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 16)); //合并单元格
			
			heading.setHeightInPoints((short)19);
			heading.createCell(0).setCellValue("设备设施表");//创建第一行第一列,并写入值
			heading.getCell(0).setCellStyle(csHead);
			
			//title 样式
			HSSFFont titleFont = hwb.createFont();
			titleFont.setFontHeightInPoints((short) 11);
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle csTitle = hwb.createCellStyle();
			csTitle.setFont(titleFont);
			csTitle.setAlignment(CellStyle.ALIGN_CENTER);
			
			title.createCell(0).setCellValue("类别");
			//title.getCell(0).setCellStyle(csHead);
			title.createCell(1).setCellValue("设备编号");
			title.createCell(2).setCellValue("固定资产编号");
			title.createCell(3).setCellValue("设备名称");
			title.createCell(4).setCellValue("设备型号");
			title.createCell(5).setCellValue("制造厂");
			title.createCell(6).setCellValue("耗电总容量");
			title.createCell(7).setCellValue("安装日期");
			title.createCell(8).setCellValue("原值");
			title.createCell(9).setCellValue("现值");
			title.createCell(10).setCellValue("外形尺寸（MM)");
			title.createCell(11).setCellValue("重量（KG）");
			title.createCell(12).setCellValue("是否使用");
			title.createCell(13).setCellValue("出厂日期");
			title.createCell(14).setCellValue("出厂编号");
			title.createCell(15).setCellValue("使用部门");
			title.createCell(16).setCellValue("备注");//17
			Tools.setCellStyleForEachCell(title,csTitle);
				
			HSSFRow temp = null;
			for (EquipInfo epInfo : equipInfoLst) {
				temp = sheet.createRow((short) ++rowNum);
				if(epInfo.getEquipType().equals("1")){
					temp.createCell(0).setCellValue("生产设备");
				}
				if(epInfo.getEquipType().equals("2")){
					temp.createCell(0).setCellValue("特种设备");
				}
				if(epInfo.getEquipType().equals("3")){
					temp.createCell(0).setCellValue("设施");
				}
				//temp.createCell(0).setCellValue(epInfo.getEquipType());//need if else
				temp.createCell(1).setCellValue(epInfo.getEquipId());
				temp.createCell(2).setCellValue(epInfo.getAssetId());
				temp.createCell(3).setCellValue(epInfo.getEquipmentName());
				temp.createCell(4).setCellValue(epInfo.getEquipModel());
				temp.createCell(5).setCellValue(epInfo.getSource());
				temp.createCell(6).setCellValue(epInfo.getPowerConsum());
				temp.createCell(7).setCellValue(epInfo.getInstallDate());
				temp.createCell(8).setCellValue(epInfo.getOriginPrice());
				temp.createCell(9).setCellValue(epInfo.getCurrPrice());
				temp.createCell(10).setCellValue(epInfo.getSize());
				temp.createCell(11).setCellValue(epInfo.getWeight());
				//temp.createCell(12).setCellValue(epInfo.getInUse());//need if else
				if(epInfo.getInUse().equals("0")){
					temp.createCell(12).setCellValue("否");
				}else{
					temp.createCell(12).setCellValue("是");
				}
				temp.createCell(13).setCellValue(epInfo.getReleaseDate());
				temp.createCell(14).setCellValue(epInfo.getFactoryNo());
				temp.createCell(15).setCellValue(epInfo.getDeptNm());
				temp.createCell(16).setCellValue(epInfo.getRemark());
			}

		 return hwb;
	 }
	 
	 //根据设备名称模糊查询设备
	public ServiceReturns getEquipsByEQName(String eqName){
		serviceReturns = new ServiceReturns();
		try{
			List<EquipInfo> list = equipInfoDao.getEquipsByEQName(eqName);
			serviceReturns.putData("eqNameList",list);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("getEquipsByEQName() method occures exception......Exception:"+e.getMessage());
		}
		return serviceReturns;
	}
}
