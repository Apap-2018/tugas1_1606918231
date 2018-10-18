package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanServiceImpls;
import com.apap.tugas1.service.PegawaiServiceImpls;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiServiceImpls  pegawaiService;
	
	@Autowired
	private JabatanServiceImpls jabatanService;
	
	@RequestMapping("/")
	private String home(Model model) {
		
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		
		model.addAttribute("title", "Home");
		model.addAttribute("listJabatan", listJabatan);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai/view", method = RequestMethod.GET)
	private String viewPilot(@RequestParam("nip") String nip, Model model) {
		
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNIP(nip);
		Double gajiPegawai = pegawaiService.getGajiPegawai(nip);
			
		model.addAttribute("title", "View Pegawai");
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", gajiPegawai);
		return "view-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari")
	private String findPegawai(Model model) {
		return "find-pegawai";
	}
}
