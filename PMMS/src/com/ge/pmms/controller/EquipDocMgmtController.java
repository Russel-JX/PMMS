/**
 * ============================================================
 * File : EquipDocMgmt.java
 * Description : 
 * 
 * Package : com.ge.pmms.controller
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 20, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ge.pmms.base.BaseController;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.EquipDocDetail;
import com.ge.pmms.po.EquipDocInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.service.EquipDocDetailService;
import com.ge.pmms.service.EquipDocMgmtService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.PMMSConstants;
import com.ge.pmms.utils.Tools;

/*******************************************************************************
 * 
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Jan 20, 2015
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
@Scope("request")
@Controller
public class EquipDocMgmtController extends BaseController {
  
  private final Log LOGGER = LogFactory.getLog(this.getClass());

  @Autowired
  private CommonsMultipartResolver multipartResolver;
  @Autowired
  private EquipDocDetailService equipDocDetailService;
  @Autowired
  private EquipDocMgmtService equipDocMgmtService;

  @RequestMapping(value = "/equipDocMgmt/index", produces = "text/html;charset=utf-8")
  public ModelAndView getOrderInfo(HttpServletRequest request) {
    ModelAndView view = new ModelAndView();
    view.setViewName("docsMgmt/equipDocsMgmt");
    return view;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @RequestMapping(value = "/equipDocMgmt/addUpload.htm")
  public ModelAndView saveUploadDocs(HttpServletRequest request) {
    if(LOGGER.isDebugEnabled()){
      LOGGER.info("start to equip docu upload method...........");
    }
    
    ModelAndView view = new ModelAndView("docsMgmt/equipDocsMgmt");
    User user = (User) request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA);
    try {
      String docType = request.getParameter("docType");
      String equipCategory = request.getParameter("equipCategory");
      String equipModelId = request.getParameter("equipModelId");
      String equipNo = request.getParameter("equipNo");
      String desc = request.getParameter("desc");
      EquipDocInfo docsInfo = new EquipDocInfo();
      docsInfo.setDocType(docType);
      docsInfo.setEquipDocId(Tools.getTimeStr());
      docsInfo.setDescription(desc);
      docsInfo.setEquipCategory(equipCategory);
      if (null != user) {
        docsInfo.setCreator(String.valueOf(user.getSso()));
      }
      if (Constants.EQUIPDOC_EQMODEL.equals(docType)) {
        docsInfo.setEquipModelId(equipModelId);
        docsInfo.setCategoryName(equipModelId);
        /*
        List EpIdlst=equipInfoService.getEquipIdByEpModel(equipModelId);
        for(int i=0;i<EpIdlst.size();i++){
        	String equipDocId=equipInfoService.getEquipInfoByEpId(equipNo).getEquipDocId();
        	if(Tools.isEmpty(equipDocId)){
        		docsInfo.setEquipDocId(Tools.getTimeStr());
        	}else{
        		docsInfo.setEquipDocId(equipDocId);
        	}
        }
        */
      }
      if (Constants.EQUIPDOC_EQNO.equals(docType)) {
        docsInfo.setEquipNo(equipNo);
        docsInfo.setCategoryName(equipNo);
        /*
        String equipDocId=equipInfoService.getEquipInfoByEpId(equipNo).getEquipDocId();
        if(Tools.isEmpty(equipDocId)){
        	docsInfo.setEquipDocId(Tools.getTimeStr());
        }else{
        	docsInfo.setEquipDocId(equipDocId);
        }
        */
      }
      if(LOGGER.isDebugEnabled()){
        LOGGER.info("uploading files...........");
      }
      List list = uploadFiles(request, docsInfo);
      EquipDocInfo docsInfo1 = (EquipDocInfo) list.get(0);
      List<EquipDocDetail> lstDetail = (List<EquipDocDetail>) list.get(1);
      equipDocMgmtService.saveDocsInfo(docsInfo1, lstDetail);
      
      if(LOGGER.isDebugEnabled()){
        LOGGER.info("end equip docu upload method...........");
      }
      
    } catch (IllegalStateException e) {
      LOGGER.error("upload equipment docments failed", e);
    } catch (IOException e) {
      LOGGER.error("upload equipment docments failed", e);
    } catch (PmmsException e) {
      LOGGER.error("save equipment docments data failed", e);
    } catch (Exception e) {
      LOGGER.error("save equipment docments data failed", e);
    }
    return view;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @RequestMapping(value = "/equipDocMgmt/editUploadDocs.htm")
  public ModelAndView editUploadDocs(HttpServletRequest request) {
    if(LOGGER.isDebugEnabled()){
      LOGGER.info("start to editUploadDocs method...........");
    }
    
    final ModelAndView view = new ModelAndView("docsMgmt/equipDocsMgmt");
    User user = (User) request.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA);
    try {
      String equipId = request.getParameter("id");
      EquipDocInfo docuInfo = equipDocMgmtService.getEquipDocs(equipId);
      if (null != user) {
        docuInfo.setUpdater(String.valueOf(user.getSso()));
      }
      List list = uploadFiles(request, docuInfo);
      EquipDocInfo docsInfo = (EquipDocInfo) list.get(0);
      List<EquipDocDetail> lstDetail = (List<EquipDocDetail>) list.get(1);
      equipDocMgmtService.editEquipDocs(docsInfo, lstDetail);
      
      if(LOGGER.isDebugEnabled()){
        LOGGER.info("end to editUploadDocs method...........");
      }
    } catch (IllegalStateException e) {
      LOGGER.error("upload equipment docments failed", e);
    } catch (IOException e) {
      LOGGER.error("upload equipment docments failed", e);
    } catch (PmmsException e) {
      LOGGER.error("save equipment docments data failed", e);
    }
    return view;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private List uploadFiles(HttpServletRequest request, EquipDocInfo docsInfo)
      throws IllegalStateException, IOException, PmmsException {
    String folderName = docsInfo.getCategoryName();
    if (StringUtils.isEmpty(folderName)) {
      folderName = "All";
    }
    
    //验证文件夹名称是否包含非法字符:
    folderName = Tools.replaceByRegx(folderName, Constants.INVALID_WRDS, "");
    
    List list = new ArrayList();
    // 判断 request 是否有文件上传,即多部分请求
    if (multipartResolver.isMultipart(request)) {
      // 转换成多部分request
      MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
      Iterator<String> iter = multiRequest.getFileNames();
      long totalSize = 0L;
      int fileCount = 0;
      List<EquipDocDetail> lstDocsDetail = new ArrayList<EquipDocDetail>();
      String path = "";
      boolean tmpBl = false;
      while (iter.hasNext()) {
        // 取得上传文件
        MultipartFile file = multiRequest.getFile(iter.next());
        if (file != null) {
          EquipDocDetail docDetail = new EquipDocDetail();
          docDetail.setDocDetailId(Tools.getTimeStr());
          docDetail.setEquipDocId(docsInfo.getEquipDocId());
          totalSize += file.getSize();
          fileCount++;
          // 取得当前上传文件的文件名称
          String myFileName = file.getOriginalFilename();
          // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
          if (!StringUtils.isEmpty(myFileName)) {
            LOGGER.info(myFileName);
            docDetail.setFileName(myFileName);
            docDetail.setFileSize(String.valueOf(file.getSize()));
            // rename file
            String fileName = Tools.getTimeStr() + file.getOriginalFilename();
            // save path
            path = getUploadPath() + "/" + folderName;
            File localFile = new File(path);
            if (!localFile.exists() && !localFile.isDirectory()) {
              tmpBl = localFile.mkdirs();
              if (!tmpBl) {
                LOGGER.error("Create directory failed!!");
                throw new PmmsException("upload fail, Crtead directory failed");
              }
            }
            docDetail.setFilePath(path + "/" + fileName);
            docDetail.setCreateDate(Tools.getToday());
            localFile = new File(path + "/" + fileName);
            file.transferTo(localFile);
            lstDocsDetail.add(docDetail);
          }
        }
      }
      if (!StringUtils.isEmpty(docsInfo.getFileCount())) {
        int tmp = (int) Float.parseFloat(docsInfo.getFileCount());
        docsInfo.setFileCount(String.valueOf(fileCount + tmp));
      }
      docsInfo.setFileCount(String.valueOf(fileCount));
      if (!StringUtils.isEmpty(docsInfo.getFileSize())) {
        int tmp = (int) Float.parseFloat(docsInfo.getFileCount());
        docsInfo.setFileSize(String.valueOf(totalSize + tmp));
      }
      docsInfo.setFileSize(String.valueOf(totalSize));
      list.add(docsInfo);
      list.add(lstDocsDetail);
    }
    return list;
  }

  @RequestMapping(value = "/equipDocMgmt/getAll", produces = "text/html;charset=utf-8")
  public @ResponseBody
  String getEquipDoclst(HttpServletRequest request) {
    EquipDocDetail detail = new EquipDocDetail();
    returns = equipDocDetailService.getEquipDocsInfo(detail);
    return returns.generateJsonData();
  }

  @RequestMapping(value = "/equipDocMgmt/getDocDetail", produces = "text/html;charset=utf-8")
  public @ResponseBody
  String getDocDetail(HttpServletRequest request) {
    String equipDocId = request.getParameter("equipDocId");
    returns = equipDocDetailService.getDocDetailByDocId(equipDocId);
    return returns.generateJsonData();
  }

  @RequestMapping(value = "/equipDocMgmt/getEquipDocInfoById", produces = "text/html;charset=utf-8")
  public @ResponseBody
  String getEquipDocInfo(HttpServletRequest request) {
    String id = request.getParameter("id");
    returns = equipDocMgmtService.getEquipDocsInfo(id);
    return returns.generateJsonData();
  }

  @RequestMapping(value = "/equipDocMgmt/delOneDoc", produces = "text/html;charset=utf-8")
  public @ResponseBody
  String delDocDetail(HttpServletRequest request) {
    String id = request.getParameter("id");
    returns = equipDocMgmtService.delDocDetail(id);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("deleted successful,id:" + returns);
    }
    return returns.generateJsonData();
  }

  @RequestMapping(value = "/equipDocMgmt/delBulk", produces = "text/html;charset=utf-8")
  public @ResponseBody
  String delBulkDetail(HttpServletRequest request) {
    if(LOGGER.isDebugEnabled()){
      LOGGER.info("start to delBulkDetail method...........");
    }
    returns = new ServiceReturns();
    String equipDetailKeyIds = request.getParameter("ids");
    String paths = request.getParameter("paths");
    
    if (!StringUtils.isEmpty(equipDetailKeyIds)) {
      String tmp = StringUtils.substring(equipDetailKeyIds, 0, (equipDetailKeyIds.length() - 1));
      StringTokenizer token = new StringTokenizer(tmp, ",");
      List<Integer> ids = new ArrayList<Integer>();
      while (token.hasMoreTokens()) {
        ids.add(Integer.parseInt(token.nextToken()));
      }
      equipDocDetailService.deletlBulk(ids);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("deleted successful,ids:" + equipDetailKeyIds);
      }
      
      if(!StringUtils.isEmpty(paths)){
        String tmp1 = StringUtils.substring(paths, 0, (paths.length() - 1));
        StringTokenizer token1 = new StringTokenizer(tmp1, ",");
        File file = null;
        String path = "";
        while (token1.hasMoreTokens()) {
          path = token1.nextToken();
          file = new File(path);
          if(!file.delete()){
              LOGGER.info("File deleting falied,path:" + path);
          }
        }
      }
    }
    if(LOGGER.isDebugEnabled()){
      LOGGER.info("end to delBulkDetail method...........");
    }
    return returns.generateJsonData();
  }

  @RequestMapping(value = "/equipDocMgmt/downloadDoc", produces = "text/html;charset=utf-8")
  public @ResponseBody
  void doDownload(HttpServletRequest request, HttpServletResponse response) {
    if(LOGGER.isDebugEnabled()){
      LOGGER.info("start to doDownload method...........");
    }
    
    InputStream inputStream = null;
    try {
      String id = request.getParameter("id");
      EquipDocDetail detail = equipDocDetailService.getEqDocDetailById(id);
      ServletContext context = request.getServletContext();
      String fullPath = detail.getFilePath();
      File fileToDownload = new File(fullPath);
      String mimeType = context.getMimeType(fullPath);
      if (mimeType == null) {
        // set to binary type if MIME mapping not found
        mimeType = "application/octet-stream";
      }
      response.setContentType(mimeType);
      response.setContentLength((int) Float.parseFloat(detail.getFileSize()));
      inputStream = new FileInputStream(fileToDownload);
      response.setHeader("Content-Disposition", "attachment; filename=\"" + detail.getFileName()
          + "\"");
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
      inputStream.close();
      
      if(LOGGER.isDebugEnabled()){
        LOGGER.info("end to doDownload method...........");
      }
    } catch (Exception e) {
      LOGGER.error("Request could not be completed at this moment. ", e);
    }finally{
      if(null != inputStream){
        try {
          inputStream.close();
        } catch (IOException e) {
          LOGGER.error("faied to close stream, finally", e);
        }
      }
    }
  }

  private String getUploadPath() {
    return Constants.SYS_CONFIG_MAP.get(Constants.EQUIP_DOC_UPLOAD_PATH);
  }
  
}
