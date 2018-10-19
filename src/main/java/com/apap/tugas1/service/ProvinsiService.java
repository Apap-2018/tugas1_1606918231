package com.apap.tugas1.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinsiModel;


@Service
public interface ProvinsiService {
	List<ProvinsiModel> getListProvinsi();
	ProvinsiModel getProvinsiById(long id);
}
