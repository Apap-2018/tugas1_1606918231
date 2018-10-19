package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("instansiService")
public class InstansiServiceImpls implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public InstansiModel getInstansiById(long id) {
		return instansiDb.getOne(id);
	}
	
}
