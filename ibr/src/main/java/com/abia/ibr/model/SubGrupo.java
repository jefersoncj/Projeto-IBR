package com.abia.ibr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.validator.constraints.NotBlank;

import com.abia.ibr.tenancy.TenancyInterceptor;

@Entity
@Table(name = "sub_grupo")
@FilterDef(name = "tenant", parameters = { @ParamDef(name="id", type="string") })
@Filter(name = "tenant", condition = "tenant_id = :id")
public class SubGrupo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotNull(message = "O Grupo é obrigatório")
	@ManyToOne
	@JoinColumn(name = "codigo_grupo_conta")
	private GrupoConta grupoConta;
	
	String referencia;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@PrePersist
	@PreUpdate
	private void tenantId() {
		String tenantId = TenancyInterceptor.getTenantId();
		if (tenantId != null) {
			this.tenantId = tenantId;
		}
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrupoConta getGrupoConta() {
		return grupoConta;
	}

	public void setGrupoConta(GrupoConta grupoConta) {
		this.grupoConta = grupoConta;
	}
	
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubGrupo other = (SubGrupo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome ;
	}
	
	

	

}
