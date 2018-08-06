package com.yd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.mapper.ManagerMapper;
import com.yd.pojo.Manager;
import com.yd.service.ManagerService;

import lombok.extern.slf4j.Slf4j;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private  ManagerMapper  managerMapper;
	
	@Override
	public Manager findManagerByUserName(String username) {
		return managerMapper.selectManagerByUsername(username);
	}

	@Override
	public Manager findById(int id) {
		return managerMapper.selectByPrimaryKey(id);
	}

	@Override
	public int alterManagerByIdSelective(Manager userInfo) {
		return managerMapper.updateByPrimaryKeySelective(userInfo);
	}

}
