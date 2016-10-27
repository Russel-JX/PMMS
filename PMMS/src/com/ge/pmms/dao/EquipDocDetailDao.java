package com.ge.pmms.dao;

import java.util.List;

import com.ge.pmms.po.EquipDocDetail;

public interface EquipDocDetailDao {

	public List<EquipDocDetail> getEquipDocsInfo(EquipDocDetail docDetail);
	
	public List<EquipDocDetail> getDocDetailByDocId(String equipDocId);
	
	public int deletlBulk(List<Integer> ids);
}
