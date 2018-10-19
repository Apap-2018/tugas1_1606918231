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
		
		Double gajiPokok = listJabatan.get(0).getGajiPokok();
		Double tunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
		
		System.out.println(gajiPokok + ((tunjangan/100)*gajiPokok));
		return gajiPokok + ((tunjangan/100)*gajiPokok);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		
		PegawaiModel resPegawai = pegawaiDb.getOne(pegawai.getId());
		
		resPegawai.setNama(pegawai.getNama());
		resPegawai.setTempatLahir(pegawai.getTempatLahir());
		resPegawai.setTahunMasuk(pegawai.getTahunMasuk());
		resPegawai.setInstansi(pegawai.getInstansi());
		resPegawai.setJabatanList(pegawai.getJabatanList());
		
		pegawaiDb.save(resPegawai);
		
	}

	@Override
	public void updateNip(PegawaiModel pegawai) {
		
		String nip = ""+pegawai.getInstansi().getId();
		
		String[] tglLahir = pegawai.getTanggalLahir().toString().split("-");
		String stringTglLahir = tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2, 4);
		nip += stringTglLahir;
		
		nip += pegawai.getTahunMasuk();
		
		int counter = 1;
		for(PegawaiModel p : pegawai.getInstansi().getPegawaiInstansi()) {
			if (p.getTahunMasuk().equals(pegawai.getTahunMasuk()) &&
				p.getTanggalLahir().equals(pegawai.getTanggalLahir())
				) {
				counter++;
				
			}
		}
		
		nip += "0"+counter;
		
		pegawai.setNip(nip);
	}

	
	
}
