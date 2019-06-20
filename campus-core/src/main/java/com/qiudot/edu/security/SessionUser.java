package com.qiudot.edu.security;

import com.acooly.core.common.facade.InfoBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionUser extends InfoBase {

	private Long customerId;
	
	private String customerUserNo;
	
	private String customerUserName;
	
	private String alipayUserId;
}
