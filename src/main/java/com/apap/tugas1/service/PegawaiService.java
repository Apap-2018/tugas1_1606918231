package com.apap.tugas1.service;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.PegawaiModel;


@Service
public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNIP(String nip);
}
