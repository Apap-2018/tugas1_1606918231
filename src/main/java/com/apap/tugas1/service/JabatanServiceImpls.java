package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("jabatanService")
@Transactional
public class JabatanServiceImpls implements JabatanService{
	@Autowired
	private JabatanDb jabatanDb;

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}

	@Override
	public List<JabatanModel> findAllJabatan(){
		return jabatanDb.findAll();
	}

	@Override
	public JabatanModel getJabatanById(long id) {
		return jabatanDb.getOne(id);
	}

	@Override
	public JabatanModel updateJabatan(JabatanModel jabatan) {
		JabatanModel resJabatan = this.getJabatanById(jabatan.getId());
		
		resJabatan.setNama(jabatan.getNama());
		resJabatan.setDeskripsi(jabatan.getDeskripsi());
		resJabatan.setGajiPokok(jabatan.getGajiPokok());
		
		jabatanDb.save(resJabatan);
		
		return resJabatan;
	}

	@Override
	public void deleteJabatanById(long id) {
		jabatanDb.deleteById(id);
	}	
}
