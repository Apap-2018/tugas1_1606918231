package com.apap.tugas1.service;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;


@Service("pegawaiService")
@Transactional
public class PegawaiServiceImpls implements PegawaiService{

	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNIP(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai, long idPegawai) {
		
		PegawaiModel resPegawai = pegawaiDb.findById(idPegawai).get();
		
		resPegawai.setNama(pegawai.getNama());
		resPegawai.setTempatLahir(pegawai.getTempatLahir());
		resPegawai.setTahunMasuk(pegawai.getTahunMasuk());
		resPegawai.setInstansi(pegawai.getInstansi());
		resPegawai.setJabatanList(pegawai.getJabatanList());
		
		pegawaiDb.save(resPegawai);
		
	}
}
