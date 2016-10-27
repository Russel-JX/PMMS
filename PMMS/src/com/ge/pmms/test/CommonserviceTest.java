package com.ge.pmms.test;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:/springMVC.xml"})//指代项目在服务器中的路径
public class CommonserviceTest extends TestCase{
	@Autowired
    //private CommonService commonService;
	
	@org.junit.Test
	@Rollback(false)
	public void test(){
		//String info = commonService.getItemValueById("1001");
		//Assert.assertNotNull(info);
		//System.out.println("ddd = "+info.getItemValue());
	}
}
