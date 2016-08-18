package com.emc.ehc.cloudkeeper.model;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 18, 2016 11:01:37 AM
 * 
 */
public class User {
    private Long id;
    private String name;
    private Integer age;
    
    public User(){}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
