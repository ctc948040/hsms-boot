package com.hsms.mybatis.model;

import lombok.Data;

@Data
public class ComModel {
	private String uuid;
	
	@Override
    public String toString() {
        return "ComModel{" +
                "uuid=" + uuid +
                '}';
    }
}
