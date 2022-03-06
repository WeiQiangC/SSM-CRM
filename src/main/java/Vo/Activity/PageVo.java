package Vo.Activity;

//创建接收页面的vo类
public class PageVo {
	private String name;
	private String owner;
	private String startDate; 
	private String endDate ;
	private Integer pageNo; 
	private Integer pageSize;
	private Integer skipCount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getSkipCount() {
		return skipCount;
	}
	public void setSkipCount(Integer skipCount) {
		this.skipCount = skipCount;
	}
	@Override
	public String toString() {
		return "PageVo [name=" + name + ", owner=" + owner + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", pageNo=" + pageNo + ", pageSize=" + pageSize + ", skipCount=" + skipCount + "]";
	}
	
}
