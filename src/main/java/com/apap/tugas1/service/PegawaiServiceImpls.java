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
	public Double getGajiPegawai(String nip) {
		
		PegawaiModel pegawai = pegawaiDb.findByNip(nip);
		List<JabatanModel> listJabatan = pegawai.getJabatanList();
		Collections.sort(listJabatan);
		
		Double gajiPokok = listJabatan.get(0).getGajiPokok();
		Double tunjangan = pegawai.getInstansi().getProvinsi().getPersentaseTunjangan();
		
		System.out.println(gajiPokok + ((tunjangan/100)*gajiPokok));
		return gajiPokok + ((tunjangan/100)*gajiPokok);
	}

	
	
}
