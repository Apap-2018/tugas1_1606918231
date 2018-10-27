package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;

@Service("instansiService")
public class InstansiServiceImpls implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public InstansiModel getInstansiById(long id) {
		return instansiDb.findById(id);
	}

	@Override
	public List<InstansiModel> getListInstansi() {
		return instansiDb.findAll();
	}
	
}
