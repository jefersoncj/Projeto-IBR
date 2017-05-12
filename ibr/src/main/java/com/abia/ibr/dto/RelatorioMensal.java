package com.abia.ibr.dto;

import java.math.BigDecimal;
import java.util.Comparator;

public class RelatorioMensal implements Comparator<String>{

	int codigo;
	String referencia;
	String nome;
	BigDecimal valor;
	String tipo;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int compare(String s1, String s2) {
		 	String[] str1 = s1.split ("\\.");
	        String[] str2 = s2.split ("\\.");
	        int[] int1 = new int[str1.length];
	        int[] int2 = new int[str2.length];
	        for (int i = 0; i < int1.length; ++i) { int1[i] = Integer.parseInt (str1[i]); }
	        for (int i = 0; i < int2.length; ++i) { int2[i] = Integer.parseInt (str2[i]); }
	        return compareIntArrays (int1, int2);
	}
	 private int compareIntArrays (int[] a1, int[] a2) {
	        int minLength = Math.min (a1.length, a2.length);
	        for (int i = 0; i < minLength; ++i) {
	            if (a1[i] < a2[i])
	                return -1;
	            if (a1[i] > a2[i])
	                return +1;
	        }
	        if (a1.length < a2.length)
	            return -1;
	        if (a1.length > a2.length)
	            return +1;
	        return 0;
	    }
	
}
