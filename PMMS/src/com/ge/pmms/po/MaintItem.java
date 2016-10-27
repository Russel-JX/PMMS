
package com.ge.pmms.po;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ge.pmms.utils.Tools;


@Entity(name="maintItem")
@Table(name="MAINT_ITEM")
public class MaintItem {
	private Integer id;            			// ID
	private String equip_name_id;         	// 设备名称编号
	private String maint_item;       			// 保养内容
	private String cycle;         			// 保养周期
	private String maint_way;         		// 保养方法
	private String standard;        		// 正常状态
	private String creator;         		// 创建人编号
	private Date created_date;         	// 创建日期
	private String updater;         		// 修改人编号
	private Date last_updated_date;       // 最后修改时间
	private String remark;         			// 备注
	
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getEquip_name_id() {
		return equip_name_id;
	}

	
	public void setEquip_name_id(String equip_name_id) {
		this.equip_name_id = equip_name_id;
	}

	
	public String getMaint_item() {
		return maint_item;
	}

	
	public void setMaint_item(String maint_item) {
		this.maint_item = maint_item;
	}

	
	public String getCycle() {
		return cycle;
	}

	
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	
	public String getMaint_way() {
		return maint_way;
	}

	
	public void setMaint_way(String maint_way) {
		this.maint_way = maint_way;
	}

	
	public String getStandard() {
		return standard;
	}

	
	public void setStandard(String standard) {
		this.standard = standard;
	}

	
	public String getCreator() {
		return creator;
	}

	
	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	public Date getCreated_date() {
		if(created_date==null){
			return null;
		}
		return (Date)created_date.clone();
	}

	
	public void setCreated_date(Date created_date) {
		if(!Tools.isNull(created_date)){
			this.created_date = (Date)created_date.clone();
		}
	}

	
	public String getUpdater() {
		return updater;
	}

	
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	
	public Date getLast_updated_date() {
		if(last_updated_date==null){
			return null;
		}
		return (Date)last_updated_date.clone();
	}

	
	public void setLast_updated_date(Date last_updated_date) {
		if(!Tools.isNull(last_updated_date)){
			this.last_updated_date = (Date)last_updated_date.clone();
		}
	}

	
	public String getRemark() {
		return remark;
	}

	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
