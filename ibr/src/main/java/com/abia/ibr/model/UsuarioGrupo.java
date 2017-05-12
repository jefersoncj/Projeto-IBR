package com.abia.ibr.model;



import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.abia.ibr.tenancy.TenancyInterceptor;

@Entity
@Table(name = "usuario_grupo")
@FilterDef(name = "tenant", parameters = { @ParamDef(name="id", type="string") })
@Filter(name = "tenant", condition = "tenant_id = :id")
public class UsuarioGrupo {

	@EmbeddedId
	private UsuarioGrupoId id;
	
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
	
	public UsuarioGrupoId getId() {
		return id;
	}

	public void setId(UsuarioGrupoId id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		UsuarioGrupo other = (UsuarioGrupo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
