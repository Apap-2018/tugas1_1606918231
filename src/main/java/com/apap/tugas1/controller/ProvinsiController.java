package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class ProvinsiController {
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping(value = "/get-provinsi", method = RequestMethod.GET)
	public @ResponseBody List<InstansiModel> findAllInstansi(@RequestParam(value = "provinsiId", required = true) long id, Model model) {
	    ProvinsiModel provinsi = provinsiService.getProvinsiById(id);
	    return provinsi.getInstansiList();
	}
	
	@RequestMapping(value = "/get-provinsi-update", method = RequestMethod.GET)
	public @ResponseBody List<InstansiModel> findAllInstansi(@RequestParam(value = "provinsiId", required = true) long id, @RequestParam(value = "pegawaiNip", required = true) String pegawaiNip, Model model) {
	    String nip = pegawaiNip.substring(0, 4);
	    InstansiModel instansi = instansiService.getInstansiById(Long.parseLong(nip));
		
	    ProvinsiModel provinsi = provinsiService.getProvinsiById(id);
	    List<InstansiModel> instansiList = provinsi.getInstansiList();
	    instansiList.add(instansi);
	    return instansiList; 
	}
}