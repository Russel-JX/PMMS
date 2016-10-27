package com.ge.pmms.utils;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.po.SysParamsInfo;
import com.ge.pmms.service.CommonService;


@Service
@Transactional
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private CommonService commonService;

  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (event.getApplicationContext().getParent() == null) {
      List<SysParamsInfo> list = commonService.getAllSysItems();
      if (!CollectionUtils.isEmpty(list)) {
        Iterator<SysParamsInfo> itor = list.iterator();
        Integer itemId = null;
        String value = "";
        while (itor.hasNext()) {
          SysParamsInfo config =  itor.next();
          itemId = config.getId();
          value = config.getItemValue();
          Constants.SYS_CONFIG_MAP.put(itemId.toString(), value);
        }
        System.out.println("======== Load sys configration items successful. =========");
      } else {
        System.out.println("从数据库装载配系统参数失败!!");
        System.exit(0);
      }
    }
  }
}
