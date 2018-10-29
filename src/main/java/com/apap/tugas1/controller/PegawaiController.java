package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiServiceImpls;
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
	
	@Autowired
	private InstansiServiceImpls instansiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		
		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		List<InstansiModel> listInstansi = instansiService.getListInstansi();
		
		model.addAttribute("title", "Home");
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
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
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
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
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewTermudaTertua(@RequestParam(value = "idInstansi") long idInstansi, Model model) {
		List<PegawaiModel> listPegawai = instansiService.getInstansiById(idInstansi).getPegawaiInstansi();
		PegawaiModel pegawaiMuda = listPegawai.get(0);
		PegawaiModel pegawaiTua = listPegawai.get(1);
		
		// loop untuk mencari pegawai termuda
		for(int i=0 ; i<listPegawai.size() ; i++) {
			if(i+1 != listPegawai.size()) {
				if(pegawaiMuda.getTanggalLahir().before(listPegawai.get(i+1).getTanggalLahir())) {
					pegawaiMuda = listPegawai.get(i+1);
				}
			}
		}
		
		// loop untuk mencari pegawai tertua
		for(int i=0 ; i<listPegawai.size() ; i++) {
			if(i+1 != listPegawai.size()) {
				if(pegawaiTua.getTanggalLahir().after(listPegawai.get(i+1).getTanggalLahir())) {
					pegawaiTua = listPegawai.get(i+1);
				}
			}
		}
		
		model.addAttribute("pegawaiTua", pegawaiTua);
		model.addAttribute("pegawaiMuda", pegawaiMuda);
		return "pegawai-tertua-termuda";
	}
	
	
	 /*
	@RequestMapping(value = "/pegawai/cari")
	private String cariPegawai(Model model) {
		model.addAttribute("listProvinsi", provinsiService.getListProvinsi());
		model.addAttribute("listInstansi", instansiService.getListInstansi());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		return "pegawai-cari";	
	}*/
	
	
	
	@RequestMapping(value= "/pegawai/cari", method=RequestMethod.GET)
	private String cariPegawaiSubmit(
			@RequestParam(value="idProvinsi", required = false) String idProvinsi,
			@RequestParam(value="idInstansi", required = false) String idInstansi,
			@RequestParam(value="idJabatan", required = false) String idJabatan,
			Model model) {
		
		
		
		model.addAttribute("listProvinsi", provinsiService.getListProvinsi());
		model.addAttribute("listInstansi", instansiService.getListInstansi());
		model.addAttribute("listJabatan", jabatanService.getListJabatan());
		List<PegawaiModel> listPegawai = pegawaiService.getListPegawai();
		
		if ((idProvinsi==null || idProvinsi.equals("")) && (idInstansi==null||idInstansi.equals("")) && (idJabatan==null||idJabatan.equals(""))) {
			return "pegawai-cari";
		}
		else {
			if (idProvinsi!=null && !idProvinsi.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel peg: listPegawai) {
					if (((Long)peg.getInstansi().getProvinsi().getId()).toString().equals(idProvinsi)) {
						temp.add(peg);
					}
				}
				listPegawai = temp;
				model.addAttribute("idProvinsi", Long.parseLong(idProvinsi));
			}
			else {
				model.addAttribute("idProvinsi", "");
			}
			if (idInstansi!=null&&!idInstansi.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel peg: listPegawai) {
					if (((Long)peg.getInstansi().getId()).toString().equals(idInstansi)) {
						temp.add(peg);
					}
				}
				listPegawai = temp;
				model.addAttribute("idInstansi", Long.parseLong(idInstansi));
			}
			else {
				model.addAttribute("idInstansi", "");
			}
			if (idJabatan!=null&&!idJabatan.equals("")) {
				List<PegawaiModel> temp = new ArrayList<PegawaiModel>();
				for (PegawaiModel peg: listPegawai) {
					for (JabatanModel jabatan:peg.getJabatanList()) {
						if (((Long)jabatan.getId()).toString().equals(idJabatan)) {
							temp.add(peg);
							break;
						}
					}
					
				}
				listPegawai = temp;
				model.addAttribute("idJabatan", Long.parseLong(idJabatan));
			}
			else {
				model.addAttribute("idJabatan", "");
			}
		}
		model.addAttribute("listPegawai",listPegawai);
		return "pegawai-cari";
		
	}
}
