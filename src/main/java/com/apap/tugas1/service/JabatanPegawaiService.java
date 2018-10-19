package com.apap.tugas1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanPegawaiModel;

@Service
public interface JabatanPegawaiService {
	
	List<JabatanPegawaiModel> getListJabatanPegawai();
	void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai);
}
