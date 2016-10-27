package com.ge.pmms.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.po.MaintItem;
import com.ge.pmms.po.User;
import com.ge.pmms.service.MaintItemService;
import com.ge.pmms.utils.PMMSConstants;
import com.ge.pmms.utils.Tools;
@Scope("request")
@Controller
public class MaintItemController extends BaseController{
	@Autowired
	private MaintItemService maintItemService;//  maintItemService
	
	/**
	 * 
	 * @Author: Xun Jiang 
	 * @param request
	 * @return
	 * @Description:forward to maintItem.jsp
	 */
	@RequestMapping(value = "/maintItem/index", produces = "text/html;charset=utf-8")
	public String forwardToMaintItemIndex(HttpServletRequest request) {
		return "MaintItem/maintItem";
	}
	
	/**
	 * 
	 * @Author: iGATE 
	 * @param request
	 * @return
	 * @Description:根据设备类型查询相应的设备名称
	 */
	@RequestMapping(value = "/maintItem/getEquipNames", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getEquipNames(HttpServletRequest request) {
		try {
			String equipType = request.getParameter("equipType");
			
			returns = maintItemService.findEquipNamesByEquipType(equipType);
		} catch (Exception e) {
			LOGGER.error("getEquipNames() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/maintItem/getMaintItems", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String getMaintItems(HttpServletRequest request) {
		try {
			String equip_name_id = request.getParameter("equipName");
			
			returns = maintItemService.findMaintItemsByName(equip_name_id);
		} catch (Exception e) {
			LOGGER.error("getMaintItems() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}

	@RequestMapping(value = "/maintItem/saveMaintItem", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String saveMaintItem(HttpServletRequest request) {
		try {
			String equip_name_id = request.getParameter("equipName");
			String maint_item = request.getParameter("item");
			String cycle = request.getParameter("cycle");
			String way = request.getParameter("way");
			String standard = request.getParameter("standard");
			String remark = request.getParameter("remark");
			
			String creator = ((User)request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA)).getSso();
			
			MaintItem maintItem = new MaintItem();
			maintItem.setEquip_name_id(equip_name_id);
			maintItem.setMaint_item(maint_item);
			maintItem.setCycle(cycle);
			maintItem.setMaint_way(way);
			maintItem.setStandard(standard);
			maintItem.setCreator(creator);
			maintItem.setCreated_date(new Date());
			maintItem.setRemark(remark);
			
			
			returns = maintItemService.addMaintItem(maintItem);
		} catch (Exception e) {
			LOGGER.error("saveMaintItem() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/maintItem/modifyMaintItem", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String modifyMaintItem(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			String equip_name_id = request.getParameter("equipName");
			String maint_item = request.getParameter("item");
			String cycle = request.getParameter("cycle");
			String way = request.getParameter("way");
			String standard = request.getParameter("standard");
			String remark = request.getParameter("remark");
			
			String updater = ((User)request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA)).getSso();
			
			MaintItem maintItem = new MaintItem();
			maintItem.setId(Integer.parseInt(id));
			maintItem.setEquip_name_id(equip_name_id);
			maintItem.setMaint_item(maint_item);
			maintItem.setCycle(cycle);
			maintItem.setMaint_way(way);
			maintItem.setStandard(standard);
			maintItem.setUpdater(updater);
			maintItem.setLast_updated_date(Tools.parseToDate(new Date()));
			maintItem.setRemark(remark);
			
			
			returns = maintItemService.modifyMaintItem(maintItem);
		} catch (Exception e) {
			LOGGER.error("modifyMaintItem() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/maintItem/removeMaintItem", produces = "text/html;charset=utf-8")
	public @ResponseBody
	String removeMaintItem(HttpServletRequest request) {
		try {
			String ids = request.getParameter("ids");
			String[] idArray = null;
			if(!StringUtils.isEmpty(ids)){
				idArray = StringUtils.split(ids,",");
				returns = maintItemService.removeMaintItems(idArray);
			}
		} catch (Exception e) {
			LOGGER.error("removeMaintItem() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
		return returns.generateJsonData();
	}
	
	@RequestMapping(value = "/maintItem/export", produces = "text/html;charset=utf-8")
	public void export(HttpServletRequest request,HttpServletResponse response) {
		LOGGER.info("Export Maint Item to Local Starting......");
		try {
			String equip_name_id = request.getParameter("equipNameId");
			maintItemService.exportToExcel(equip_name_id, response);
			LOGGER.info("Export Maint Item to Local ending......");
		} catch (Exception e) {
			LOGGER.error("export() method occures exception......Exception:"+e.getMessage());
			returns = Tools.getExceptionControllerRetruns(e);
		}
	}
}
