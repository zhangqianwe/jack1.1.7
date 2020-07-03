package com.jack.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 与数据库表有对应关系的Model类的基类
 * 
 * @author Bobbie.Qi
 *
 */
@Data
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = 6437114463749744698L;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date createTime;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String createUser;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Date updateTime;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String updateUser;

}
