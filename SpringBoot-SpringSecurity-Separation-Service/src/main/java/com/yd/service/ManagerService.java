package com.yd.service;

import com.yd.pojo.Manager;

public interface ManagerService {

	
	Manager findManagerByUserName(String username);

	Manager findById(int id);

	/**
	 * 更新管理员信息
	 * @param userInfo
	 */
	int alterManagerByIdSelective(Manager userInfo);

}
