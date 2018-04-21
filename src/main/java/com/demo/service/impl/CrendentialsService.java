package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.CredentialsDaoInt;
import com.demo.service.CredentialsServiceInt;


@Service("credentialsService")
public class CrendentialsService implements CredentialsServiceInt{
	
	@Autowired
	private CredentialsDaoInt credentialsDaoInt;

	@Override
	public long passwordDateDifference(String userName) {
		
		return credentialsDaoInt.passwordDateDifference(userName);
	}

}
