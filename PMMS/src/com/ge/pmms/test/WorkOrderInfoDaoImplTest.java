package com.ge.pmms.test;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.dao.WorkOrderInfoDao;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:/springMVC.xml"})//指代项目在服务器中的路径
public class WorkOrderInfoDaoImplTest {
	
	@Autowired
	private WorkOrderInfoDao workOrderInfoDao;
//	@Test
//	public void test() throws PmmsException, ParseException {
//		List<WorkOrderInfo> list = workOrderInfoDao.getWOINProcess(Tools.parseToDate("2015-03-18 00:00:00",Constants.DATE_PATTEN_SEC), Tools.parseToDate("2015-03-18 23:59:00",Constants.DATE_PATTEN_SEC));
//		System.out.println(list);
//	}
	
	@Test
	public void test11() throws PmmsException, ParseException {
		List<WorkOrderInfo> list = workOrderInfoDao.findUnfinishedWO("2015-03-18 00:00:00","2015-04-18 23:59:59");
		System.out.println(list);
	}
}
