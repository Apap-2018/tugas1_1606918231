package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.service.InstansiService;


@Controller
public class InstansiController {
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping(value = "/get-instansi", method = RequestMethod.GET)
	public @ResponseBody InstansiModel getInstansiById(@RequestParam(value = "instansiId", required = true) long id) {
	    InstansiModel instansi = instansiService.getInstansiById(id);
	    return instansi;
	}
	
	@RequestMapping(value = "/get-instansi-update", method = RequestMethod.GET)
	public @ResponseBody InstansiModel getInstansiById(@RequestParam(value = "instansiId", required = true) String instansiId) {
		String id = instansiId.substring(0,4);
		InstansiModel instansi = instansiService.getInstansiById(Long.parseLong(id));
	    
	    return instansi;
	}
}