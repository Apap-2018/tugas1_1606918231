package com.apap.tugas1.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.PegawaiModel;


@Service
public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNIP(String nip);
	void addPegawai(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel pegawai, long idPegawai);
	List<PegawaiModel> getListPegawai();
}
