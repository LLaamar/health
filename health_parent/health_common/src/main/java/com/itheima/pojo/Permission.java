package com.itheima.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限
 * @author LLaamar
 */
public class Permission implements Serializable{
    private Integer id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限关键字，用于权限控制
     */
    private String keyword;
    /**
     * 描述
     */
    private String description;
    private Set<Role> roles = new HashSet<Role>(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", keyword='" + keyword + '\'' +
                ", description='" + description + '\'' +
                ", roles=" + roles +
                '}';
    }
}
