package Domain.setting;

public class DeptType {
	private Integer id;
	private String deptId;
	private String depeName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDepeName() {
		return depeName;
	}
	public void setDepeName(String depeName) {
		this.depeName = depeName;
	}
	@Override
	public String toString() {
		return "DeptType [id=" + id + ", deptId=" + deptId + ", depeName=" + depeName + "]";
	}
	
	
}
