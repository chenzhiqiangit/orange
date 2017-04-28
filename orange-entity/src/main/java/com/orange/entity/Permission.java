package com.orange.entity;

import java.io.Serializable;

/**
 * Created by chzq on 2017/4/28.
 */
public class Permission implements Serializable{
    private Long id;
    private Long parentFunctionId;
    private String privilegeName;
    private String privilegeId;
    private String attachedActions;
    private Integer sortOrder;
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentFunctionId() {
        return parentFunctionId;
    }

    public void setParentFunctionId(Long parentFunctionId) {
        this.parentFunctionId = parentFunctionId;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getAttachedActions() {
        return attachedActions;
    }

    public void setAttachedActions(String attachedActions) {
        this.attachedActions = attachedActions;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
