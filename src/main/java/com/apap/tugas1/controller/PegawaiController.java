package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanServiceImpls;
import com.apap.tugas1.service.PegawaiServiceImpls;
import com.apap.tugas1.service.ProvinsiServiceImpls;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiServiceImpls  pegawaiService;
	
	@Autowired
	private JabatanServiceImpls jabatanService;
	
	@Autowired
	private ProvinsiServiceImpls provinsiService;
	
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
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setInstansi(new InstansiModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getListProvinsi());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		
		pegawaiService.updateNip(pegawai);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai-submit";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String ubahProfilPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNIP(nip);
		
		System.out.println(pegawai.getNama());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getListProvinsi());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String ubahProfilPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		
		pegawaiService.updateNip(pegawai);
		pegawaiService.updatePegawai(pegawai);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai-submit";
	}
}
