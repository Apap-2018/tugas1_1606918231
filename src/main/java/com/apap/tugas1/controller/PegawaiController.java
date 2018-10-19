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
		
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		
		model.addAttribute("title", "Home");
		model.addAttribute("listJabatan", listJabatan);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai/view", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNIP(nip);
		
		double gajiPokok = 0;
		for (JabatanModel jabatan : pegawai.getJabatanList()) {
			if(jabatan.getGajiPokok() > gajiPokok) {
				gajiPokok = jabatan.getGajiPokok();
			}
		}
		
		double gaji = gajiPokok + (pegawai.getInstansi().getProvinsi().getPresentaseTunjangan() * 0.01 * gajiPokok);
		model.addAttribute("title", "View Pegawai");
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", gaji);
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
		model.addAttribute("listJabatan", jabatanService.findAllJabatan());
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
		
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai-submit";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String ubahProfilPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNIP(nip);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getListProvinsi());
		model.addAttribute("listJabatan", jabatanService.findAllJabatan());
		return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String ubahProfilPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String nip = ""+ pegawai.getInstansi().getId();

		String[] arrTglLahir= pegawai.getTanggalLahir().toString().split("-");
		String strTglLahir = arrTglLahir[2] + arrTglLahir[1] + arrTglLahir[0].substring(2, 4);
		nip += strTglLahir;
		
		nip += pegawai.getTahunMasuk();

		int counter = 1;
		for (PegawaiModel pegawaiInstansi:pegawai.getInstansi().getPegawaiInstansi()) {
			if (pegawaiInstansi.getTahunMasuk().equals(pegawai.getTahunMasuk()) && pegawaiInstansi.getTanggalLahir().equals(pegawai.getTanggalLahir()) && pegawaiInstansi.getId() != pegawai.getId()) {
				counter++;
			}	
		}
		nip += "0" + counter;
		
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai-submit";
	}
}
