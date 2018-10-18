package com.apap.tugas1.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "jabatan")
public class JabatanModel implements Comparable<JabatanModel>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama_jabatan", nullable = false, unique = true)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "deskripsi_jabatan", nullable = false)
	private String deskripsi;
	
	@NotNull
	@Column(name = "gaji_pokok")
	private Double gajiPokok;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			mappedBy = "jabatanList")
	private List<PegawaiModel> pegawaiList;
	
	public int compareTo(JabatanModel jabatan) {
		return jabatan.gajiPokok.intValue() - this.gajiPokok.intValue();
	}
	
	public List<PegawaiModel> getPegawaiList() {
		return pegawaiList;
	}

	public void setPegawaiList(List<PegawaiModel> pegawaiList) {
		this.pegawaiList = pegawaiList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public Double getGajiPokok() {
		return gajiPokok;
	}

	public void setGajiPokok(Double gajiPokok) {
		this.gajiPokok = gajiPokok;
	}
	
	

}
