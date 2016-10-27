package com.ge.pmms.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.ReminderInfo;
import com.ge.pmms.po.Role;
import com.ge.pmms.po.RoleModule;
import com.ge.pmms.po.RoleSubModule;
import com.ge.pmms.po.RoleVo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.service.PmmsService;
import com.ge.pmms.service.RoleMgmtService;
import com.ge.pmms.utils.PMMSConstants;
import com.ge.pmms.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author iGATE
 *
 */
@Scope("request")
@Controller
public class PmmsController extends BaseController {
	
	@Autowired
	private PmmsService pmmsService;
	
	@Autowired
	private RoleMgmtService roleMgmtService;
	
	private Log logger = LogFactory.getLog(
			this.getClass());
	
	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	/**
	 * @param model
	 * @return control to login page
	 */
	@RequestMapping("showpage")
	public String showPage(Model model) {
		
		model.addAttribute(
				PMMSConstants.USER_SESSION_DATA,
				new User());
		return "login";
	}

	 @RequestMapping(value = "/verifyUser")
  public @ResponseBody String verifyUser(@RequestParam("sso") String sso, 
      @RequestParam("password") String password){
	   returns = new ServiceReturns();
	   
	   User user = new User();
	    user.setSso(sso);
	    user.setPassword(Tools.md5Encript(password));
	    
	    List<User> list = pmmsService.validUser(user);
	    if(CollectionUtils.isEmpty(list)){
	      returns.setSuccessFlag(false);
	    }
	  return returns.generateJsonData();
	}
	 
	 @RequestMapping(value = "/updatePwd")
	  public @ResponseBody String updatePwd(@RequestParam("sso") String sso, 
	      @RequestParam("password") String password){
	     returns = new ServiceReturns();
	     
	     String pwd = Tools.md5Encript(password);
	      
	      int cnt = pmmsService.updatePwd(sso,pwd);
	      if(cnt < 1){
	        returns.setSuccessFlag(false);
	      }
	    return returns.generateJsonData();
	  }
	 
	/**
	 * @param sso
	 * @param password
	 * @param model
	 * @param request
	 * @param response
	 * @return control to home page or login page
	 */
	@RequestMapping("validate")
	public ModelAndView validateUser(
			@RequestParam("sso") String sso, 
			@RequestParam("password") String password,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		User user = new User();
		user.setSso(sso);
		user.setPassword(Tools.md5Encript(password));
		HttpSession session = request.getSession(true);
		
		List<User> list = pmmsService.validUser(user);
		
		if (!CollectionUtils.isEmpty(list)) {
			User user1 = list.get(0);
			
			logger.info("User logged in with sso as "
			+user.getSso()
			+" at time "+Calendar.getInstance().getTime());
			
			String userRole = user1.getRole();
			
			int roleId =
	    		roleMgmtService.getRoleId(userRole);
			
			String status1 =
	    		roleMgmtService.getRoleAccessInfo(roleId);
			String[] firstSplit= status1.split("====");
				    
			getModules(session, firstSplit[0]);
      
			getSubModules(session, firstSplit[1]);

			getRemiders(request);

			List<WorkOrderInfo> workOrderList =
					pmmsService.getWorkOrderInfoDetails();

			Gson gson = new GsonBuilder().create();
			request.setAttribute(PMMSConstants.WO_JSON_LIST,
					gson.toJson(workOrderList));
						
			session.setAttribute(PMMSConstants.USER_SESSION_DATA,
					user1);
			model.addAttribute(PMMSConstants.USER_SESSION_DATA,
					user1);
			request.setAttribute(PMMSConstants.ROLE_REQUEST_DATA,
					user1.getRole());
      
			return new ModelAndView("/common/home");
		}
		else
		{
			model.addAttribute(PMMSConstants.LOGIN_FAILED_MSG,
					"用户名或密码错误/Invalid Credentials.");
			return new ModelAndView("login");
		}
	}
	
	/**
	 * @param request
	 */
	private void getRemiders(HttpServletRequest request) {
		
	  List<ReminderInfo> reminderInfoList =
			  pmmsService.getReminderInfoDetails();
	  
	  if (reminderInfoList.size()==0 ||
			  reminderInfoList.isEmpty()) {
		  request.setAttribute(
				  PMMSConstants.PENDING_WO_IS_EMPTY, "true");
		  }
	  else {
    	request.setAttribute(PMMSConstants.PENDING_WO_IS_EMPTY, "false");
    
    	ReminderInfo r = reminderInfoList.get(0);
    	String pending = r.getModuleId()+" "+r.getReceiver();
    	request.setAttribute(PMMSConstants.PENDING_WO_1, pending);
    	
    	if (reminderInfoList.size()>1) {
    		r = reminderInfoList.get(1);
    		pending = r.getModuleId()+" "+r.getReceiver();
    		request.setAttribute(PMMSConstants.PENDING_WO_2, pending);
    		}
    	if (reminderInfoList.size()>2) {
    		r = reminderInfoList.get(2);
    		pending = r.getModuleId()+" "+r.getReceiver();
    		request.setAttribute(PMMSConstants.PENDING_WO_3, pending);
    		}
    	if (reminderInfoList.size()>3) {
    		r = reminderInfoList.get(3);
    		pending = r.getModuleId()+" "+r.getReceiver();
    		request.setAttribute(PMMSConstants.PENDING_WO_4, pending);
    		}
    	if (reminderInfoList.size()>4) {
    		r = reminderInfoList.get(4);
    		pending = r.getModuleId()+" "+r.getReceiver();
    		request.setAttribute(PMMSConstants.PENDING_WO_5, pending);
    		}
    	if (reminderInfoList.size()>5) {
    		r = reminderInfoList.get(5);
    		pending = r.getModuleId()+" "+r.getReceiver();
    		request.setAttribute(PMMSConstants.PENDING_WO_6, pending);
    		}
    	if (reminderInfoList.size()>6) {
    		r = reminderInfoList.get(6);
    		pending = r.getModuleId()+" "+r.getReceiver();
    		request.setAttribute(PMMSConstants.PENDING_WO_7, pending);
    		}
    	}
	  }

  /**
 * @param session
 * @param subModules
 */
private void getSubModules(HttpSession session, String subModules) {
	
    String subModAccess1 = "Unchecked";
    String subModAccess2 = "Unchecked";
    String subModAccess3 = "Unchecked";
    String subModAccess4 = "Unchecked";
    String subModAccess5 = "Unchecked";
    String subModAccess6 = "Unchecked";
    String subModAccess7 = "Unchecked";
    String subModAccess8 = "Unchecked";
    String subModAccess9 = "Unchecked";
    String subModAccess10 = "Unchecked";
    String subModAccess11 = "Unchecked";
    String subModAccess12 = "Unchecked";
    String subModAccess13 = "Unchecked";
    String subModAccess14 = "Unchecked";
    String subModAccess15 = "Unchecked";
    String subModAccess16 = "Unchecked";
    String subModAccess17 = "Unchecked";
    				
    //if(firstSplit[1] != "" || firstSplit[1] != null)
    if (!StringUtils.isEmpty(subModules)) {
    	String[] fourthSplit = subModules.split(",");
    	
    	for (int iCount = 0; iCount < fourthSplit.length; iCount++) {
    		//if(fourthSplit[iCount] != "" || fourthSplit[iCount] != null)
    		if (!StringUtils.isEmpty(fourthSplit[iCount])) {
    			if (fourthSplit[iCount].equalsIgnoreCase("1")) {
    			  subModAccess1 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("2")) {
    				subModAccess2 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("3")) {
    				subModAccess3 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("4")) {
    				subModAccess4 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("5")) {
    				subModAccess5 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("6")) {
    				subModAccess6 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("7")) {
    				subModAccess7 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("8")) {
    				subModAccess8 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("9")) {
    				subModAccess9 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("10")) {
    				subModAccess10 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("11")) {
    				subModAccess11 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("12")) {
    				subModAccess12 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("13")) {
    				subModAccess13 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("14")) {
    				subModAccess14 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("15")) {
    				subModAccess15 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("16")) {
    				subModAccess16 = "Checked";
    			}
    			else if (fourthSplit[iCount].equalsIgnoreCase("17")) {
    				subModAccess17 = "Checked";
    			}
    		}
    	}
    }
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_1, subModAccess1);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_2, subModAccess2);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_3, subModAccess3);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_4, subModAccess4);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_5, subModAccess5);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_6, subModAccess6);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_7, subModAccess7);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_8, subModAccess8);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_9, subModAccess9);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_10, subModAccess10);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_11, subModAccess11);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_12, subModAccess12);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_13, subModAccess13);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_14, subModAccess14);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_15, subModAccess15);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_16, subModAccess16);
    session.setAttribute(PMMSConstants.SUB_MODULE_ACCESS_17, subModAccess17);
  }

  /**
 * @param session
 * @param user1
 * @param modules
 */
private void getModules(HttpSession session, String modules) {
	
	  List<String> modAccessList = new ArrayList<String>();
	  
	  //if(firstSplit[0] != "" || firstSplit[0] != null)
	  if (!StringUtils.isEmpty(modules)) {
		  String[] secondSplit = modules.split("\\|\\|");
		  
		  for (int iCount=0; iCount<secondSplit.length; iCount++) {
			  //if(secondSplit[iCount] != "" || secondSplit[iCount] != null)
			  if (!StringUtils.isEmpty(secondSplit[iCount])) {
				  String[] thirdSplit = secondSplit[iCount].split("==");
				  modAccessList.add(thirdSplit[1]);
				  }
			  }
		  }
	  
	  String modAccess1 = modAccessList.get(0);
	  String modAccess2 = modAccessList.get(1);
	  String modAccess3 = modAccessList.get(2);
	  String modAccess4 = modAccessList.get(3);
	  String modAccess5 = modAccessList.get(4);
	  String modAccess6 = modAccessList.get(5);
	  String modAccess7 = modAccessList.get(6);
	  
	  session.setAttribute(PMMSConstants.MODULE_ACCESS_1, modAccess1);
	  session.setAttribute(PMMSConstants.MODULE_ACCESS_2, modAccess2);
	  session.setAttribute(PMMSConstants.MODULE_ACCESS_3, modAccess3);
	  session.setAttribute(PMMSConstants.MODULE_ACCESS_4, modAccess4);
	  session.setAttribute(PMMSConstants.MODULE_ACCESS_5, modAccess5);
	  session.setAttribute(PMMSConstants.MODULE_ACCESS_6, modAccess6);
	  session.setAttribute(PMMSConstants.MODULE_ACCESS_7, modAccess7);
	  }

	
	/**
	 * @param session
	 * @param model
	 * @param request
	 * @param response
	 * @return control to users page or login page
	 */
	@RequestMapping("userMgmt")
	public ModelAndView manageUser(HttpSession session,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if (session.getAttribute(PMMSConstants.USER_SESSION_DATA)
				!= null) {
			session.getAttribute(PMMSConstants.USER_SESSION_DATA);
			
			List<User> userInfoList = pmmsService.getUserInfo();
	
			Gson gson = new GsonBuilder().create();
			request.setAttribute(PMMSConstants.USER_JSON_LIST, gson.toJson(userInfoList));
			request.setAttribute(PMMSConstants.USER_LIST, userInfoList);
			
			List<String> roleNameList = pmmsService.getRoleNames();
		
			model.addAttribute(PMMSConstants.ROLE_NAME_JSON_LIST, roleNameList);
			
			return new ModelAndView("/userMgmt/users");
		}
		else {
			return new ModelAndView("login");
		}
	}

	/**
	 * @param model
	 * @param sso
	 * @param password
	 * @param fname
	 * @param lname
	 * @param emailID
	 * @param cnumber
	 * @param role
	 * @return ajax data
	 */
  @RequestMapping("addNewUser")
  @ResponseBody
  public String addNewUser(Model model, @RequestParam("sso") String sso,
      @RequestParam("password") String password, @RequestParam("fname") String fname,
      @RequestParam("lname") String lname, @RequestParam("emailID") String emailID,
      @RequestParam("cnumber") String cnumber, @RequestParam("role") String role) {
    String status = "";
    User user = new User();
    user.setSso(sso);
    user.setPassword(Tools.md5Encript(password));
    user.setFirstName(fname);
    user.setLastName(lname);
    user.setEmailId(emailID);
    user.setContactNo(cnumber);
    user.setRole(role);
    status = pmmsService.addNewUser(user);
    model.addAttribute(PMMSConstants.STATUS_DATA, status);
    return status;
  }

	/**
	 * @param model
	 * @param id
	 * @param sso
	 * @param password
	 * @param fname
	 * @param lname
	 * @param emailID
	 * @param cnumber
	 * @param role
	 * @return ajax data
	 */
  @RequestMapping("updateUser")
  @ResponseBody
  public String updateUser(Model model, @RequestParam("userId") String id,
      @RequestParam("sso") String sso, @RequestParam("password") String password,
      @RequestParam("oriPwd") String oriPwd,
      @RequestParam("fname") String fname, @RequestParam("lname") String lname,
      @RequestParam("emailID") String emailID, @RequestParam("cnumber") String cnumber,
      @RequestParam("role") String role) {
    String status = "";
    User user = new User();
    user.setId(Integer.parseInt(id));
    user.setSso(sso);
    //如果用户修改了密码，需要加密
    if(!StringUtils.isEmpty(password)){
      user.setPassword(Tools.md5Encript(password));
    }else{
      user.setPassword(oriPwd);
    }
    user.setFirstName(fname);
    user.setLastName(lname);
    user.setEmailId(emailID);
    user.setContactNo(cnumber);
    user.setRole(role);
    status = pmmsService.updateUser(user);
    model.addAttribute(PMMSConstants.STATUS_DATA, status);
    return status;
  }

	/**
	 * @param session
	 * @param model
	 * @param id
	 * @return ajax data
	 */
	@RequestMapping("deleteUser")
	@ResponseBody
	public String deleteUser(HttpSession session, Model model,
			@RequestParam("userId") String id) {
		
		User user = new User();

		user.setId(Integer.parseInt(id));
		
		String status = pmmsService.deleteUser(user);
		model.addAttribute(PMMSConstants.STATUS_DATA, status);

		return status;
	}
	
	
	/**
	 * @param session
	 * @param request
	 * @param response
	 * @return control to roles page or login page
	 */
	@RequestMapping("roleMgmt")
	public ModelAndView manageRole(HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		if (session.getAttribute(PMMSConstants.USER_SESSION_DATA) != null) {
			session.getAttribute(PMMSConstants.USER_SESSION_DATA);
			
			List<RoleVo> roleInfoList = roleMgmtService.getRoleDetails();
			
			Gson gson = new GsonBuilder().create();
			request.setAttribute(PMMSConstants.ROLE_JSON_LIST, gson.toJson(roleInfoList));
			request.setAttribute(PMMSConstants.ROLE_LIST, roleInfoList);
			
			return new ModelAndView("/roleMgmt/roles");
		}
		else {
			return new ModelAndView("login");
		}
	}
	
	/**
	 * @param role
	 * @return ajax data
	 */
	@RequestMapping("accessDetails")
	@ResponseBody
	public String getRoleAccessDetails(
			@RequestParam("roleId") String role) {
		
		int roleId = Integer.parseInt(role);
		
		String status = roleMgmtService.getRoleAccessInfo(roleId);
		
		return status;
	}
	
	
	/**
	 * @param session
	 * @param model
	 * @param roleName
	 * @param roleDesc
	 * @param moduleAccess1
	 * @param moduleAccess_2
	 * @param moduleAccess_3
	 * @param moduleAccess_4
	 * @param moduleAccess_5
	 * @param moduleAccess_6
	 * @param moduleAccess_7
	 * @param submoduleAccess1
	 * @param subModuleAccess_2
	 * @param subModuleAccess_3
	 * @param subModuleAccess_4
	 * @param subModuleAccess_5
	 * @param subModuleAccess_6
	 * @param subModuleAccess_7
	 * @param subModuleAccess_8
	 * @param subModuleAccess_9
	 * @param submoduleAccess10
	 * @param submoduleAccess11
	 * @param submoduleAccess12
	 * @param submoduleAccess13
	 * @param submoduleAccess14
	 * @param submoduleAccess15
	 * @param submoduleAccess16
	 * @param submoduleAccess17
	 * @return ajax data
	 */
	@RequestMapping("addNewRole")
	@ResponseBody
	public String addNewRoles(HttpSession session,
			Model model,
			@RequestParam("roleName") String roleName,
			@RequestParam("roleDesc") String roleDesc, 
			@RequestParam("moduleAccess_1") String moduleAccess1,
			@RequestParam("moduleAccess_2") String moduleAccess_2, 
			@RequestParam("moduleAccess_3") String moduleAccess_3,
			@RequestParam("moduleAccess_4") String moduleAccess_4, 
			@RequestParam("moduleAccess_5") String moduleAccess_5,
			@RequestParam("moduleAccess_6") String moduleAccess_6, 
			@RequestParam("moduleAccess_7") String moduleAccess_7, 
			@RequestParam("sub1") Integer submoduleAccess1, 
			@RequestParam("sub2") Integer subModuleAccess_2, 
			@RequestParam("sub3") Integer subModuleAccess_3, 
			@RequestParam("sub4") Integer subModuleAccess_4, 
			@RequestParam("sub5") Integer subModuleAccess_5, 
			@RequestParam("sub6") Integer subModuleAccess_6, 
			@RequestParam("sub7") Integer subModuleAccess_7,
			@RequestParam("sub8") Integer subModuleAccess_8, 
			@RequestParam("sub9") Integer subModuleAccess_9,
			@RequestParam("sub10") Integer submoduleAccess10, 
			@RequestParam("sub11") Integer submoduleAccess11,
			@RequestParam("sub12") Integer submoduleAccess12, 
			@RequestParam("sub13") Integer submoduleAccess13, 
			@RequestParam("sub14") Integer submoduleAccess14, 
			@RequestParam("sub15") Integer submoduleAccess15,
			@RequestParam("sub16") Integer submoduleAccess16, 
			@RequestParam("sub17") Integer submoduleAccess17) {
		
		int roleId = roleMgmtService.getRoleId();
		
		List<String> moduleAccessList = new ArrayList<String>();
		moduleAccessList.add(moduleAccess1);
		moduleAccessList.add(moduleAccess_2);
		moduleAccessList.add(moduleAccess_3);
		moduleAccessList.add(moduleAccess_4);
		moduleAccessList.add(moduleAccess_5);
		moduleAccessList.add(moduleAccess_6);
		moduleAccessList.add(moduleAccess_7);
		
		List<RoleModule> rmList = new ArrayList<RoleModule>();
		
		for (int iCount = 1; iCount <= 7; iCount++) {
			RoleModule rm = new RoleModule();
			rm.setRoleId(roleId);
			rm.setModuleId(iCount);
			rm.setEditable(moduleAccessList.get(iCount - 1));
			
			rmList.add(rm);
		}
		
		List<Integer> sbList = new ArrayList<Integer>();
		
			if (submoduleAccess1 != 0) {
				sbList.add(submoduleAccess1);
			}
			if (subModuleAccess_2 != 0) {
				sbList.add(subModuleAccess_2);
			}
			if (subModuleAccess_3 != 0) {
				sbList.add(subModuleAccess_3);
			}
			if (subModuleAccess_4 != 0) {
				sbList.add(subModuleAccess_4);
			}
			if (subModuleAccess_5 != 0) {
				sbList.add(subModuleAccess_5);
			}
			if (subModuleAccess_6 != 0) {
				sbList.add(subModuleAccess_6);
			}
			if (subModuleAccess_7 != 0) {
				sbList.add(subModuleAccess_7);
			}
			if (subModuleAccess_8 != 0) {
				sbList.add(subModuleAccess_8);
			}
			if (subModuleAccess_9 != 0) {
				sbList.add(subModuleAccess_9);
			}
			if (submoduleAccess10 != 0) {
				sbList.add(submoduleAccess10);
			}
			if (submoduleAccess11 != 0) {
				sbList.add(submoduleAccess11);
			}
			if (submoduleAccess12 != 0) {
				sbList.add(submoduleAccess12);
			}
			if (submoduleAccess13 != 0) {
				sbList.add(submoduleAccess13);
			}
			if (submoduleAccess14 != 0) {
				sbList.add(submoduleAccess14);
			}
			if (submoduleAccess15 != 0) {
				sbList.add(submoduleAccess15);
			}
			if (submoduleAccess16 != 0) {
				sbList.add(submoduleAccess16);
			}
			if (submoduleAccess17 != 0) {
				sbList.add(submoduleAccess17);
			}
										
		List<RoleSubModule> roleSubList = new ArrayList<RoleSubModule>();
		
		for (int iCount = 1; iCount <= sbList.size(); iCount++) {
			RoleSubModule rsm = new RoleSubModule();
			rsm.setRoleId(roleId);
			rsm.setSubModuleId(sbList.get(iCount - 1));
			
			roleSubList.add(rsm);
		}
		
		Role role = new Role();
		role.setId(roleId);
		role.setName(roleName);
		role.setDescription(roleDesc);
		role.setRoleModule(rmList);
		role.setRoleSubModule(roleSubList);

		String status = roleMgmtService.addNewRole(role);
		
		model.addAttribute(PMMSConstants.STATUS_DATA, status);

		return status;
	}
	
	
	/**
	 * @param session
	 * @param roleId
	 * @param roleName
	 * @param roleDesc
	 * @param moduleAccess1
	 * @param moduleAccess2
	 * @param moduleAccess3
	 * @param moduleAccess4
	 * @param moduleAccess5
	 * @param moduleAccess6
	 * @param moduleAccess7
	 * @param subModuleAccess1
	 * @param subModuleAccess2
	 * @param subModuleAccess3
	 * @param subModuleAccess4
	 * @param subModuleAccess5
	 * @param subModuleAccess6
	 * @param subModuleAccess7
	 * @param subModuleAccess8
	 * @param subModuleAccess9
	 * @param subModuleAccess10
	 * @param subModuleAccess11
	 * @param subModuleAccess12
	 * @param subModuleAccess13
	 * @param subModuleAccess14
	 * @param subModuleAccess15
	 * @param subModuleAccess16
	 * @param subModuleAccess17
	 * @return ajax data
	 */
	@RequestMapping("updateRole")
	@ResponseBody
	public String updateRoleDetails(HttpSession session,
			@RequestParam("roleId") String roleId, 
			@RequestParam("roleName") String roleName,
			@RequestParam("roleDesc") String roleDesc, 
			@RequestParam("moduleAccess_1") String moduleAccess1,
			@RequestParam("moduleAccess_2") String moduleAccess2, 
			@RequestParam("moduleAccess_3") String moduleAccess3,
			@RequestParam("moduleAccess_4") String moduleAccess4, 
			@RequestParam("moduleAccess_5") String moduleAccess5,
			@RequestParam("moduleAccess_6") String moduleAccess6, 
			@RequestParam("moduleAccess_7") String moduleAccess7, 
			@RequestParam("sub1") Integer subModuleAccess1,
			@RequestParam("sub2") Integer subModuleAccess2, 
			@RequestParam("sub3") Integer subModuleAccess3,
			@RequestParam("sub4") Integer subModuleAccess4, 
			@RequestParam("sub5") Integer subModuleAccess5,
			@RequestParam("sub6") Integer subModuleAccess6, 
			@RequestParam("sub7") Integer subModuleAccess7,
			@RequestParam("sub8") Integer subModuleAccess8, 
			@RequestParam("sub9") Integer subModuleAccess9,
			@RequestParam("sub10") Integer subModuleAccess10, 
			@RequestParam("sub11") Integer subModuleAccess11,
			@RequestParam("sub12") Integer subModuleAccess12, 
			@RequestParam("sub13") Integer subModuleAccess13,
			@RequestParam("sub14") Integer subModuleAccess14, 
			@RequestParam("sub15") Integer subModuleAccess15,
			@RequestParam("sub16") Integer subModuleAccess16, 
			@RequestParam("sub17") Integer subModuleAccess17) {
		
		String status;
		
		Role role = new Role();
		
		role.setId(Integer.parseInt(roleId));

		status = roleMgmtService.deleteRole(role);
		
		if (status.equals("1")) {
			List<String> moduleAccessList = new ArrayList<String>();
			moduleAccessList.add(moduleAccess1);
			moduleAccessList.add(moduleAccess2);
			moduleAccessList.add(moduleAccess3);
			moduleAccessList.add(moduleAccess4);
			moduleAccessList.add(moduleAccess5);
			moduleAccessList.add(moduleAccess6);
			moduleAccessList.add(moduleAccess7);
			
			List<RoleModule> rmList = new ArrayList<RoleModule>();
			
			for (int iCount = 1; iCount <= 7; iCount++) {
				RoleModule rm = new RoleModule();
				rm.setRoleId(Integer.parseInt(roleId));
				rm.setModuleId(iCount);
				rm.setEditable(moduleAccessList.get(iCount - 1));
				
				rmList.add(rm);
			}
			
			List<Integer> sbList = new ArrayList<Integer>();
			
				if (subModuleAccess1 != 0) {
					sbList.add(subModuleAccess1);
				}
				if (subModuleAccess2 != 0) {
					sbList.add(subModuleAccess2);
				}
				if (subModuleAccess3 != 0) {
					sbList.add(subModuleAccess3);
				}
				if (subModuleAccess4 != 0) {
					sbList.add(subModuleAccess4);
				}
				if (subModuleAccess5 != 0) {
					sbList.add(subModuleAccess5);
				}
				if (subModuleAccess6 != 0) {
					sbList.add(subModuleAccess6);
				}
				if (subModuleAccess7 != 0) {
					sbList.add(subModuleAccess7);
				}
				if (subModuleAccess8 != 0) {
					sbList.add(subModuleAccess8);
				}
				if (subModuleAccess9 != 0) {
					sbList.add(subModuleAccess9);
				}
				if (subModuleAccess10 != 0) {
					sbList.add(subModuleAccess10);
				}
				if (subModuleAccess11 != 0) {
					sbList.add(subModuleAccess11);
				}
				if (subModuleAccess12 != 0) {
					sbList.add(subModuleAccess12);
				}
				if (subModuleAccess13 != 0) {
					sbList.add(subModuleAccess13);
				}
				if (subModuleAccess14 != 0) {
					sbList.add(subModuleAccess14);
				}
				if (subModuleAccess15 != 0) {
					sbList.add(subModuleAccess15);
				}
				if (subModuleAccess16 != 0) {
					sbList.add(subModuleAccess16);
				}
				if (subModuleAccess17 != 0) {
					sbList.add(subModuleAccess17);
				}
												
			List<RoleSubModule> roleSubList = new ArrayList<RoleSubModule>();
			
			for (int iCount = 1; iCount <= sbList.size(); iCount++) {
				RoleSubModule rsm = new RoleSubModule();
				rsm.setRoleId(Integer.parseInt(roleId));
				rsm.setSubModuleId(sbList.get(iCount-1));
				
				roleSubList.add(rsm);
			}
			
			Role role1 = new Role();
			role1.setId(Integer.parseInt(roleId));
			role1.setName(roleName);
			role1.setDescription(roleDesc);
			role1.setRoleModule(rmList);
			role1.setRoleSubModule(roleSubList);

			status = roleMgmtService.addNewRole(role1);
		}
		else {
			status = "0";
		}
		
		return status;
	}
	
	/**
	 * @param session
	 * @param model
	 * @param roleId
	 * @return ajax data
	 */
	@RequestMapping("deleteRole")
	@ResponseBody
	public String deleteRole(HttpSession session,
			Model model,
			@RequestParam("roleId") String roleId) {
		
		Role role = new Role();
		
		role.setId(Integer.parseInt(roleId));

		String status = roleMgmtService.deleteRole(role);
		model.addAttribute(PMMSConstants.STATUS_DATA, status);

		return status;
	}
	

	/**
	 * @param session
	 * @param request
	 * @return control to home page or login page
	 */
	@RequestMapping("toHome")
	public ModelAndView navigateToHomePage(HttpSession session,
			HttpServletRequest request) {
		
		if (session.getAttribute(PMMSConstants.USER_SESSION_DATA)
				!= null) {
			session.getAttribute(PMMSConstants.USER_SESSION_DATA);

			getRemiders(request);

			List<WorkOrderInfo> workOrderList = 
					pmmsService.getWorkOrderInfoDetails();

			Gson gson = new GsonBuilder().create();
			request.setAttribute(PMMSConstants.WO_JSON_LIST,
					gson.toJson(workOrderList));
			
			return new ModelAndView("/common/home");
		}
		else {
			return new ModelAndView("login");
		}
	}

	/**
	 * @param session
	 * @return control to index page
	 */
	@RequestMapping("toLogout")
	public ModelAndView loggingOut(HttpSession session) {
		
		if (session != null) {
			session.removeAttribute
			(PMMSConstants.USER_SESSION_DATA);
			session.invalidate();
		}

		return new ModelAndView("index");
	}
}
