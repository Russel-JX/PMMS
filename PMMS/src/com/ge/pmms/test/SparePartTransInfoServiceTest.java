package com.ge.pmms.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.service.SparePartTransInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/springMVC.xml"})
public class SparePartTransInfoServiceTest {
	@Autowired
	private SparePartTransInfoService sparePartTransInfoService;

//	@Test
//	public void testGetSPTrans() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetSPTransByWOID() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSpTransExportToExcel() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetSysSafetyStock() throws PmmsException {
		sparePartTransInfoService.updateSysSafetyStock();
	}
}
