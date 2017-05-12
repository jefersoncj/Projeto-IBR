package com.abia.ibr.dto;

import java.math.BigDecimal;
import java.util.Comparator;

public class ItemPorDataFilter implements Comparator<String>{
	
	int codigo;
	
	String referencia;
	
	String nome;
	
	BigDecimal valorJan;
	
	BigDecimal valorFev;
	
	BigDecimal valorMar;
	
	BigDecimal valorAbr;
	
	BigDecimal valorMaio;
	
	BigDecimal valorJun;
	
	BigDecimal valorJul;
	
	BigDecimal valorAgo;
	
	BigDecimal valorSet;
	
	BigDecimal valorOut;
	
	BigDecimal valorNov;
	
	String tipo;

	BigDecimal valorDez;
	

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

	public BigDecimal getValorJan() {
		return valorJan;
	}

	public void setValorJan(BigDecimal valorJan) {
		this.valorJan = valorJan;
	}

	public BigDecimal getValorFev() {
		return valorFev;
	}

	public void setValorFev(BigDecimal valorFev) {
		this.valorFev = valorFev;
	}

	public BigDecimal getValorMar() {
		return valorMar;
	}

	public void setValorMar(BigDecimal valorMar) {
		this.valorMar = valorMar;
	}

	public BigDecimal getValorAbr() {
		return valorAbr;
	}

	public void setValorAbr(BigDecimal valorAbr) {
		this.valorAbr = valorAbr;
	}

	public BigDecimal getValorMaio() {
		return valorMaio;
	}

	public void setValorMaio(BigDecimal valorMaio) {
		this.valorMaio = valorMaio;
	}

	public BigDecimal getValorJun() {
		return valorJun;
	}

	public void setValorJun(BigDecimal valorJun) {
		this.valorJun = valorJun;
	}

	public BigDecimal getValorJul() {
		return valorJul;
	}

	public void setValorJul(BigDecimal valorJul) {
		this.valorJul = valorJul;
	}

	public BigDecimal getValorAgo() {
		return valorAgo;
	}

	public void setValorAgo(BigDecimal valorAgo) {
		this.valorAgo = valorAgo;
	}

	public BigDecimal getValorSet() {
		return valorSet;
	}

	public void setValorSet(BigDecimal valorSet) {
		this.valorSet = valorSet;
	}

	public BigDecimal getValorOut() {
		return valorOut;
	}

	public void setValorOut(BigDecimal valorOut) {
		this.valorOut = valorOut;
	}

	public BigDecimal getValorNov() {
		return valorNov;
	}

	public void setValorNov(BigDecimal valorNov) {
		this.valorNov = valorNov;
	}

	public BigDecimal getValorDez() {
		return valorDez;
	}

	public void setValorDez(BigDecimal valorDez) {
		this.valorDez = valorDez;
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
