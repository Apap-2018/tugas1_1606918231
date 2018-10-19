package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jabatanPegawaiService")
public class JabatanPegawaiServiceImpls implements JabatanPegawaiService{

	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
	@Override
	public void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai) {
		jabatanPegawaiDb.save(jabatanPegawai);
	}

	@Override
	public List<JabatanPegawaiModel> getListJabatanPegawai() {
		return jabatanPegawaiDb.findAll();
	}
	
	
	
}
