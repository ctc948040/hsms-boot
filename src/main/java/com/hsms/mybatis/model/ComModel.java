package com.hsms.mybatis.model;

import lombok.Data;

@Data
public class ComModel {
	private String uuid;
	private String prefix = "DIR";
	
	@Override
    public String toString() {
        return "ComModel{" +
        		"prefix=" + prefix +
                "uuid=" + uuid +
                '}';
    }
}
