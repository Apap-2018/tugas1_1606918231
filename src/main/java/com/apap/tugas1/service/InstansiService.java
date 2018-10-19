package com.apap.tugas1.service;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;

@Service
public interface InstansiService {
	InstansiModel getInstansiById(long id);
}
