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
		
		System.out.println(pegawai.getJabatanList().size());
		
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai-submit";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String ubahProfilPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNIP(nip);
		
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai";
	}
}
