package com.his.ops.entity;

import javax.validation.constraints.Size;

/**
 * 部门类
 * @author Administrator
 *
 */
public class DepartmentEntity {
	
	/**
	 * 部门唯一主键
	 */
    private Integer id;

    /**
     * 部门编号
     */
    @Size(min=3, max=8, message="长度在3-8位")
    private String depNo;

    /**
     * 部门名称
     */
    @Size(min=2, max=20, message="长度在2-20位")
    private String depName;

    /**
     * 部门描述
     */
    @Size(min=0, max=50, message="长度在50位以内")
    private String depDescribe = "无描述内容";

    /**
     * 该记录的显示隐藏状态（true:显示 false:隐藏）
     */
    private Boolean status = true;

    public DepartmentEntity(Integer id, String depNo, String depName, String depDescribe, Boolean status) {
        this.id = id;
        this.depNo = depNo;
        this.depName = depName;
        this.depDescribe = depDescribe;
        this.status = status;
    }

    public DepartmentEntity() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepNo() {
        return depNo;
    }

    public void setDepNo(String depNo) {
        this.depNo = depNo == null ? null : depNo.trim();
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public String getDepDescribe() {
        return depDescribe;
    }

    public void setDepDescribe(String depDescribe) {
        this.depDescribe = depDescribe == null ? null : depDescribe.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", depNo=").append(depNo);
        sb.append(", depName=").append(depName);
        sb.append(", depDescribe=").append(depDescribe);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DepartmentEntity other = (DepartmentEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDepNo() == null ? other.getDepNo() == null : this.getDepNo().equals(other.getDepNo()))
            && (this.getDepName() == null ? other.getDepName() == null : this.getDepName().equals(other.getDepName()))
            && (this.getDepDescribe() == null ? other.getDepDescribe() == null : this.getDepDescribe().equals(other.getDepDescribe()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDepNo() == null) ? 0 : getDepNo().hashCode());
        result = prime * result + ((getDepName() == null) ? 0 : getDepName().hashCode());
        result = prime * result + ((getDepDescribe() == null) ? 0 : getDepDescribe().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}