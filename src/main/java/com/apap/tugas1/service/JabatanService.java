package com.apap.tugas1.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;

@Service
public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	List<JabatanModel> getListJabatan();
	JabatanModel getJabatanById(long id);
	JabatanModel updateJabatan(JabatanModel jabatan);
	void deleteJabatanById(long id);
}
