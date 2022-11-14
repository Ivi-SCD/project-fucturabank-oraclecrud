package com.iviprojects.entities;

import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_BANK")
@DynamicUpdate
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_BANK", nullable = false)
	private Integer id;

	@Column(name = "BANKNAME", nullable = false, length = 30)
	private String name;

	@Column(name = "CNPJ", nullable = false, length = 14)
	private String cnpj;

	public Bank() {
	}
	
	public Bank(String name, String cnpj) {
		this.name = name;
		this.cnpj = cnpj;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Integer getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bank other = (Bank) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Bank [id=" + id + ", name=" + name + ", cnpj=" + cnpj + "]";
	}
}