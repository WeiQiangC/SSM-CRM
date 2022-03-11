package Domain.setting;

public class Dept {
	
	private String id;
	private String deptId;
	private String deptName;
	private String deptBoss;
	private String bossTelephone;
	private String description;
	private String createTime;
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptBoss() {
		return deptBoss;
	}
	public void setDeptBoss(String deptBoss) {
		this.deptBoss = deptBoss;
	}
	public String getBossTelephone() {
		return bossTelephone;
	}
	public void setBossTelephone(String bossTelephone) {
		this.bossTelephone = bossTelephone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Dept [id=" + id + ", deptId=" + deptId + ", deptName=" + deptName + ", deptBoss=" + deptBoss
				+ ", bossTelephone=" + bossTelephone + ", description=" + description + ", createTime=" + createTime
				+ "]";
	}
	
}
