package com.abia.ibr.repository.helper.movimento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.abia.ibr.dto.BalancoTotal;
import com.abia.ibr.dto.ItemPorDataFilter;
import com.abia.ibr.dto.MesAno;
import com.abia.ibr.dto.MesAnoData;
import com.abia.ibr.dto.MovimentosMes;
import com.abia.ibr.dto.RelatorioMensal;
import com.abia.ibr.model.GrupoConta;
import com.abia.ibr.model.Item;
import com.abia.ibr.model.Movimento;
import com.abia.ibr.model.SubGrupo;
import com.abia.ibr.model.Tipo;
import com.abia.ibr.model.Usuario;
import com.abia.ibr.repository.filter.MovimentoFilter;
import com.abia.ibr.security.UsuarioSistema;


public class MovimentosImpl  implements MovimentosQueries{

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Movimento> filtrar(MovimentoFilter filtro) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Movimento.class);
		
		
		adicionarFiltro(filtro, criteria);
		criteria.createAlias("item.subGrupo", "sb");
		criteria.createAlias("item.subGrupo.grupoConta", "gc");
		criteria.createAlias("conta", "c");
		
		return criteria.list();
	}
	

	

	
	private void adicionarFiltro(MovimentoFilter filtro, Criteria criteria) {
	if(filtro != null){
		
			criteria.add(Restrictions.eq("tipo", filtro.getTipo()));
		
			if(!StringUtils.isEmpty(filtro.getDataInicio()) && !StringUtils.isEmpty(filtro.getDataFim())){
			criteria.add(Restrictions.between("dataMovimento", filtro.getDataInicio(),filtro.getDataFim()));
			}
			
			if(filtro.getItem() != null && filtro.getItem().getCodigo() != null){
				criteria.add(Restrictions.eq("item", filtro.getItem()));
			}
			
			if(filtro.getItem() != null && filtro.getItem().getSubGrupo() != null && filtro.getItem().getSubGrupo().getCodigo() != null){
				criteria.add(Restrictions.eq("sb.codigo", filtro.getItem().getSubGrupo().getCodigo()));
			}
			
			if(filtro.getItem() != null && filtro.getItem().getSubGrupo().getGrupoConta() != null && filtro.getItem().getSubGrupo().getGrupoConta().getCodigo() != null){
				criteria.add(Restrictions.eq("gc.codigo", filtro.getItem().getSubGrupo().getGrupoConta().getCodigo()));
			}
			
			if(filtro.getConta() != null && filtro.getConta().getCodigo() != null){
				criteria.add(Restrictions.eq("c.codigo", filtro.getConta().getCodigo()));
			}
						
			criteria.addOrder(Order.desc("dataMovimento"));
			
		}
	}
	
	@Override
	public BigDecimal valorTotalNoAno(Tipo tipo) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valor) from Movimento where year(dataMovimento) = :ano and tipo = :tipo ", BigDecimal.class)
					.setParameter("ano", Year.now().getValue())
					.setParameter("tipo", tipo)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal valorTotalNoMes(Tipo tipo) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valor) from Movimento where month(dataMovimento) = :mes and tipo = :tipo ", BigDecimal.class)
					.setParameter("mes", MonthDay.now().getMonthValue())
					.setParameter("tipo", tipo)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override	
	public List<MovimentosMes> totalPorMes(Tipo tipo) {
		String jpql = "select new com.abia.ibr.dto.MovimentosMes(date_format(m.dataMovimento,'%Y/%m'), sum(m.valor), m.tipo) from Movimento m	where year(dataMovimento) = :mes group by  date_format(m.dataMovimento, '%Y/%m'),m.tipo";
		List<MovimentosMes> movimento =  manager.createQuery(jpql, MovimentosMes.class)
				.setParameter("mes", Year.now().getValue())
				.getResultList();
		
		LocalDate now = LocalDate.now();
		ArrayList<MovimentosMes> entradasESaidas = new ArrayList<>();
		
		ArrayList<MovimentosMes> saidas = new ArrayList<>();
		ArrayList<MovimentosMes> entradas = new ArrayList<>();
		for (MovimentosMes movimentos : movimento) {
			if(movimentos.getTipo().equals(Tipo.C)){
				entradas.add(movimentos);
			}else{
				saidas.add(movimentos);
			}
		}
		for (int i = 1; i <= 12; i++) {
			String mesIdeal = String.format("%d/%02d", now.getYear(), i);
			
			boolean possuiMes = saidas.stream().filter(v -> v.getDataMovimento().equals(mesIdeal)).findAny().isPresent();
			if (!possuiMes) {
				saidas.add(i - 1, new MovimentosMes(mesIdeal,BigDecimal.ZERO , Tipo.D));
			}
			
		}
		
		for (int i = 1; i <= 12; i++) {
			String mesIdeal = String.format("%d/%02d", now.getYear(), i);
			
			boolean possuiMes = entradas.stream().filter(v -> v.getDataMovimento().equals(mesIdeal)).findAny().isPresent();
			if (!possuiMes) {
				entradas.add(i - 1, new MovimentosMes(mesIdeal,BigDecimal.ZERO , Tipo.C));
			}
			
		}
		entradasESaidas.addAll(entradas);
		entradasESaidas.addAll(saidas);
				
	   return  entradasESaidas;
		 
	}

	@Override	
	public List<ItemPorDataFilter> balancoSaida(Integer ano){
		ArrayList<ItemPorDataFilter> itemPorDataFilters = new ArrayList<>();
		
		ArrayList<GrupoConta> grupoContaSaida = new ArrayList<>();
		ArrayList<SubGrupo> subGrupoSaida = new ArrayList<>();
		ArrayList<Item> itemSaida = new ArrayList<>();
		
		String jpql = "select it from Item it  "
		+ "left join it.subGrupo sg "
		+ "left join it.subGrupo.grupoConta g ";
		List<Item> itens =  manager.createQuery(jpql, Item.class).getResultList();
		
		for (Item item : itens) {
			if (!grupoContaSaida.contains(item.getSubGrupo().getGrupoConta()) && item.getTipo().equals(Tipo.D)) {
				grupoContaSaida.add(item.getSubGrupo().getGrupoConta());
			}
			
			if (!subGrupoSaida.contains(item.getSubGrupo()) && item.getTipo().equals(Tipo.D)) {
				subGrupoSaida.add(item.getSubGrupo());
			}
			
			if (!itemSaida.contains(item) && item.getTipo().equals(Tipo.D)) {
				itemSaida.add(item);
			}
		}

		
		
		
//		String jpql1 = "select gc from GrupoConta gc";
//		List<GrupoConta> grupoConta =  manager.createQuery(jpql1, GrupoConta.class).getResultList();
//		
//		String jpql2 = "select sb from SubGrupo sb";
//		List<SubGrupo> subGrupo =  manager.createQuery(jpql2, SubGrupo.class).getResultList();
//		
//		String jpql3 = "select it from Item it";
//		List<Item> item =  manager.createQuery(jpql3, Item.class).getResultList();
//		
		for (GrupoConta gp : grupoContaSaida) {
			ItemPorDataFilter grupoContas = new ItemPorDataFilter() ;
			
			grupoContas.setCodigo(Integer.parseInt(gp.getReferencia().toString()));
			grupoContas.setReferencia(gp.getReferencia().toString());
			grupoContas.setNome(gp.getNome());
			
		for (int i = 1; i <= 12; i++) {
			LocalDate mes = LocalDate.now().withMonth(i).withYear(ano);
			System.err.println(mes);
			BigDecimal total = totalPorGrupoContaSaida(mes,gp);
			
				switch (i) {
				case 1:
					grupoContas.setValorJan(total);
					break;
				case 2:
					grupoContas.setValorFev(total);
					break;
				case 3:
					grupoContas.setValorMar(total);
					break;
				case 4:
					grupoContas.setValorAbr(total);
					break;
				case 5:
					grupoContas.setValorMaio(total);
					break;
				case 6:
					grupoContas.setValorJun(total);
					break;
				case 7:
					grupoContas.setValorJul(total);
					break;
				case 8:
					grupoContas.setValorAgo(total);
					break;
				case 9:
					grupoContas.setValorSet(total);
					break;
				case 10:
					grupoContas.setValorOut(total);
					break;
				case 11:
					grupoContas.setValorNov(total);
					break;
				case 12:
					grupoContas.setValorDez(total);
					break;
				default:
					break;
				}
				grupoContas.setTipo("grupo");
			}	itemPorDataFilters.add(grupoContas);
		}
		
		for (SubGrupo sb : subGrupoSaida) {
			ItemPorDataFilter subGrupos = new ItemPorDataFilter() ;
			subGrupos.setReferencia(sb.getReferencia());
			subGrupos.setCodigo(Integer.parseInt(sb.getReferencia().replace( "." , "")));
			subGrupos.setNome(sb.getNome());
			for (int i = 1; i <= 12; i++) {
				LocalDate mes = LocalDate.now().withMonth(i).withYear(ano);
				BigDecimal total = totalPorSubGrupoSaida(mes,sb);
				
					switch (i) {
					case 1:
						subGrupos.setValorJan(total);
						break;
					case 2:
						subGrupos.setValorFev(total);
						break;
					case 3:
						subGrupos.setValorMar(total);
						break;
					case 4:
						subGrupos.setValorAbr(total);
						break;
					case 5:
						subGrupos.setValorMaio(total);
						break;
					case 6:
						subGrupos.setValorJun(total);
						break;
					case 7:
						subGrupos.setValorJul(total);
						break;
					case 8:
						subGrupos.setValorAgo(total);
						break;
					case 9:
						subGrupos.setValorSet(total);
						break;
					case 10:
						subGrupos.setValorOut(total);
						break;
					case 11:
						subGrupos.setValorNov(total);
						break;
					case 12:
						subGrupos.setValorDez(total);
						break;
					default:
						break;
					}
					subGrupos.setTipo("subGrupo");
				}itemPorDataFilters.add(subGrupos);
				
		}
		for (Item it : itemSaida) {
			ItemPorDataFilter ite = new ItemPorDataFilter() ;
				ite.setReferencia(it.getReferencia());
				ite.setCodigo(Integer.parseInt(it.getReferencia().replace( "." , "")));
				ite.setNome(it.getNome());
				for (int i = 1; i <= 12; i++) {
					LocalDate mes = LocalDate.now().withMonth(i).withYear(ano);
					BigDecimal total = totalPorItem(mes,it);
					
						switch (i) {
						case 1:
							ite.setValorJan(total);
							break;
						case 2:
							ite.setValorFev(total);
							break;
						case 3:
							ite.setValorMar(total);
							break;
						case 4:
							ite.setValorAbr(total);
							break;
						case 5:
							ite.setValorMaio(total);
							break;
						case 6:
							ite.setValorJun(total);
							break;
						case 7:
							ite.setValorJul(total);
							break;
						case 8:
							ite.setValorAgo(total);
							break;
						case 9:
							ite.setValorSet(total);
							break;
						case 10:
							ite.setValorOut(total);
							break;
						case 11:
							ite.setValorNov(total);
							break;
						case 12:
							ite.setValorDez(total);
							break;
						default:
							break;
						}
						ite.setTipo("item");
					}itemPorDataFilters.add(ite);
		}
		
		
	
	            Collections.sort(itemPorDataFilters, new Comparator<ItemPorDataFilter>() {  
	                @Override  
	                public int compare(ItemPorDataFilter pessoa1, ItemPorDataFilter pessoa2) {  
	                    if (pessoa1.compare(pessoa1.getReferencia(), pessoa2.getReferencia()) == -1) {  
	                        return -1;  
	                    } else if (pessoa1.compare(pessoa1.getReferencia(), pessoa2.getReferencia()) == 0) {  
	                        return 0;  
	                    }  
	  
	                    return 1;  
	                }  
	            });  
		
		return itemPorDataFilters;
		
	}
	@Override	
	public List<ItemPorDataFilter> balancoEntrada(Integer ano){
		ArrayList<ItemPorDataFilter> itemPorDataFilters = new ArrayList<>();
		
		ArrayList<GrupoConta> grupoContaEntrada = new ArrayList<>();
		ArrayList<SubGrupo> subGrupoEntrada = new ArrayList<>();
		ArrayList<Item> itemEntrada = new ArrayList<>();
		
		String jpql = "select it from Item it  "
		+ "left join it.subGrupo sg "
		+ "left join it.subGrupo.grupoConta g ";
		List<Item> itens =  manager.createQuery(jpql, Item.class).getResultList();
		
		for (Item item : itens) {
			
			if (!grupoContaEntrada.contains(item.getSubGrupo().getGrupoConta()) && item.getTipo().equals(Tipo.C)) {
				grupoContaEntrada.add(item.getSubGrupo().getGrupoConta());
			}
			
		   if (!subGrupoEntrada.contains(item.getSubGrupo().getGrupoConta()) && item.getTipo().equals(Tipo.C)) {
				subGrupoEntrada.add(item.getSubGrupo());
			}
			
			if (!itemEntrada.contains(item) && item.getTipo().equals(Tipo.C)) {
				itemEntrada.add(item);
			}
		}


		
		for (GrupoConta gp : grupoContaEntrada) {
			ItemPorDataFilter grupoContas = new ItemPorDataFilter() ;
			
			grupoContas.setCodigo(Integer.parseInt(gp.getReferencia().toString()));
			grupoContas.setReferencia(gp.getReferencia().toString());
			grupoContas.setNome(gp.getNome());
			
		for (int i = 1; i <= 12; i++) {
			LocalDate mes = LocalDate.now().withMonth(i).withYear(ano);
			System.err.println(mes);
			BigDecimal total = totalPorGrupoContaEntrada(mes,gp);
			
				switch (i) {
				case 1:
					grupoContas.setValorJan(total);
					break;
				case 2:
					grupoContas.setValorFev(total);
					break;
				case 3:
					grupoContas.setValorMar(total);
					break;
				case 4:
					grupoContas.setValorAbr(total);
					break;
				case 5:
					grupoContas.setValorMaio(total);
					break;
				case 6:
					grupoContas.setValorJun(total);
					break;
				case 7:
					grupoContas.setValorJul(total);
					break;
				case 8:
					grupoContas.setValorAgo(total);
					break;
				case 9:
					grupoContas.setValorSet(total);
					break;
				case 10:
					grupoContas.setValorOut(total);
					break;
				case 11:
					grupoContas.setValorNov(total);
					break;
				case 12:
					grupoContas.setValorDez(total);
					break;
				default:
					break;
				}
				grupoContas.setTipo("grupo");
			}	itemPorDataFilters.add(grupoContas);
		}
		
		for (SubGrupo sb : subGrupoEntrada) {
			ItemPorDataFilter subGrupos = new ItemPorDataFilter() ;
			subGrupos.setReferencia(sb.getReferencia());
			subGrupos.setCodigo(Integer.parseInt(sb.getReferencia().replace( "." , "")));
			subGrupos.setNome(sb.getNome());
			for (int i = 1; i <= 12; i++) {
				LocalDate mes = LocalDate.now().withMonth(i).withYear(ano);
				BigDecimal total = totalPorSubGrupoEntrada(mes,sb);
				
					switch (i) {
					case 1:
						subGrupos.setValorJan(total);
						break;
					case 2:
						subGrupos.setValorFev(total);
						break;
					case 3:
						subGrupos.setValorMar(total);
						break;
					case 4:
						subGrupos.setValorAbr(total);
						break;
					case 5:
						subGrupos.setValorMaio(total);
						break;
					case 6:
						subGrupos.setValorJun(total);
						break;
					case 7:
						subGrupos.setValorJul(total);
						break;
					case 8:
						subGrupos.setValorAgo(total);
						break;
					case 9:
						subGrupos.setValorSet(total);
						break;
					case 10:
						subGrupos.setValorOut(total);
						break;
					case 11:
						subGrupos.setValorNov(total);
						break;
					case 12:
						subGrupos.setValorDez(total);
						break;
					default:
						break;
					}
					subGrupos.setTipo("subGrupo");
				}itemPorDataFilters.add(subGrupos);
				
		}
		for (Item it : itemEntrada) {
			ItemPorDataFilter ite = new ItemPorDataFilter() ;
				ite.setReferencia(it.getReferencia());
				ite.setCodigo(Integer.parseInt(it.getReferencia().replace( "." , "")));
				ite.setNome(it.getNome());
				for (int i = 1; i <= 12; i++) {
					LocalDate mes = LocalDate.now().withMonth(i).withYear(ano);
					BigDecimal total = totalPorItem(mes,it);
					
						switch (i) {
						case 1:
							ite.setValorJan(total);
							break;
						case 2:
							ite.setValorFev(total);
							break;
						case 3:
							ite.setValorMar(total);
							break;
						case 4:
							ite.setValorAbr(total);
							break;
						case 5:
							ite.setValorMaio(total);
							break;
						case 6:
							ite.setValorJun(total);
							break;
						case 7:
							ite.setValorJul(total);
							break;
						case 8:
							ite.setValorAgo(total);
							break;
						case 9:
							ite.setValorSet(total);
							break;
						case 10:
							ite.setValorOut(total);
							break;
						case 11:
							ite.setValorNov(total);
							break;
						case 12:
							ite.setValorDez(total);
							break;
						default:
							break;
						}
						ite.setTipo("item");
					}itemPorDataFilters.add(ite);
		}
		
		
	
	            Collections.sort(itemPorDataFilters, new Comparator<ItemPorDataFilter>() {  
	                @Override  
	                public int compare(ItemPorDataFilter pessoa1, ItemPorDataFilter pessoa2) {  
	                    if (pessoa1.compare(pessoa1.getReferencia(), pessoa2.getReferencia()) == -1) {  
	                        return -1;  
	                    } else if (pessoa1.compare(pessoa1.getReferencia(), pessoa2.getReferencia()) == 0) {  
	                        return 0;  
	                    }  
	  
	                    return 1;  
	                }  
	            });  
		
		return itemPorDataFilters;
		
	}
	
	@Override
	public List<RelatorioMensal> entradasMensal(LocalDate mesAno){
		ArrayList<RelatorioMensal> relatorioMensais = new ArrayList<>();
		
		ArrayList<GrupoConta> grupoContaEntrada = new ArrayList<>();
		ArrayList<SubGrupo> subGrupoEntrada = new ArrayList<>();
		
		String jpql = "select it from Item it  "
		+ "left join it.subGrupo sg "
		+ "left join it.subGrupo.grupoConta g ";
		List<Item> itens =  manager.createQuery(jpql, Item.class).getResultList();
		
		for (Item item : itens) {
			
			if (!grupoContaEntrada.contains(item.getSubGrupo().getGrupoConta()) && item.getTipo().equals(Tipo.C)) {
				grupoContaEntrada.add(item.getSubGrupo().getGrupoConta());
			}
			
		   if (!subGrupoEntrada.contains(item.getSubGrupo().getGrupoConta()) && item.getTipo().equals(Tipo.C)) {
				subGrupoEntrada.add(item.getSubGrupo());
			}
			
		}


		
		for (GrupoConta gp : grupoContaEntrada) {
			RelatorioMensal grupoContas = new RelatorioMensal() ;
			
			grupoContas.setCodigo(Integer.parseInt(gp.getReferencia().toString()));
			grupoContas.setReferencia(gp.getReferencia().toString());
			grupoContas.setNome(gp.getNome());
			
	
			LocalDate mes = LocalDate.now().withMonth(mesAno.getMonthValue()).withYear(mesAno.getYear());
			System.err.println(mes);
			BigDecimal total = totalPorGrupoContaEntrada(mes,gp);
			
			grupoContas.setValor(total);
			grupoContas.setTipo("grupo");
			relatorioMensais.add(grupoContas);
		}
		
		for (SubGrupo sb : subGrupoEntrada) {
			RelatorioMensal subGrupos = new RelatorioMensal() ;
			subGrupos.setReferencia(sb.getReferencia());
			subGrupos.setCodigo(Integer.parseInt(sb.getReferencia().replace( "." , "")));
			subGrupos.setNome(sb.getNome());
			
				LocalDate mes = LocalDate.now().withMonth(mesAno.getMonthValue()).withYear(mesAno.getYear());
				BigDecimal total = totalPorSubGrupoEntrada(mes,sb);
				
				subGrupos.setValor(total);
				subGrupos.setTipo("subGrupo");
				relatorioMensais.add(subGrupos);
				
		}
	            Collections.sort(relatorioMensais, new Comparator<RelatorioMensal>() {  
	                @Override  
	                public int compare(RelatorioMensal pessoa1, RelatorioMensal pessoa2) {  
	                    if (pessoa1.compare(pessoa1.getReferencia(), pessoa2.getReferencia()) == -1) {  
	                        return -1;  
	                    } else if (pessoa1.compare(pessoa1.getReferencia(), pessoa2.getReferencia()) == 0) {  
	                        return 0;  
	                    }  
	  
	                    return 1;  
	                }  
	            });  
		
		return relatorioMensais;
		
	}
	@Override
	public List<RelatorioMensal> saidasMensal(LocalDate mesAno){
		ArrayList<RelatorioMensal> relatorioMensais = new ArrayList<>();
		
		ArrayList<GrupoConta> grupoContaSaidas = new ArrayList<>();
		ArrayList<SubGrupo> subGrupoSaidas = new ArrayList<>();
		
		String jpql = "select it from Item it  "
		+ "left join it.subGrupo sg "
		+ "left join it.subGrupo.grupoConta g ";
		List<Item> itens =  manager.createQuery(jpql, Item.class).getResultList();
		
		for (Item item : itens) {
			
			if (!grupoContaSaidas.contains(item.getSubGrupo().getGrupoConta()) && item.getTipo().equals(Tipo.D)) {
				grupoContaSaidas.add(item.getSubGrupo().getGrupoConta());
			}
			
		   if (!subGrupoSaidas.contains(item.getSubGrupo()) && item.getTipo().equals(Tipo.D)) {
				subGrupoSaidas.add(item.getSubGrupo());
			}
			
		}


		
		for (GrupoConta gp : grupoContaSaidas) {
			RelatorioMensal grupoContas = new RelatorioMensal() ;
			
			grupoContas.setCodigo(Integer.parseInt(gp.getReferencia().toString()));
			grupoContas.setReferencia(gp.getReferencia().toString());
			grupoContas.setNome(gp.getNome());
			
	
			LocalDate mes = LocalDate.now().withMonth(mesAno.getMonthValue()).withYear(mesAno.getYear());
			System.err.println(mes);
			BigDecimal total = totalPorGrupoContaSaida(mes,gp);
			
			grupoContas.setValor(total);
			grupoContas.setTipo("grupo");
			relatorioMensais.add(grupoContas);
		}
		
		for (SubGrupo sb : subGrupoSaidas) {
			RelatorioMensal subGrupos = new RelatorioMensal() ;
			subGrupos.setReferencia(sb.getReferencia());
			subGrupos.setCodigo(Integer.parseInt(sb.getReferencia().replace( "." , "")));
			subGrupos.setNome(sb.getNome());
			
				LocalDate mes = LocalDate.now().withMonth(mesAno.getMonthValue()).withYear(mesAno.getYear());
				BigDecimal total = totalPorSubGrupoSaida(mes,sb);
				
				subGrupos.setValor(total);
				subGrupos.setTipo("subGrupo");
				relatorioMensais.add(subGrupos);
				
		}
	            Collections.sort(relatorioMensais, new Comparator<RelatorioMensal>() {  
	                @Override  
	                public int compare(RelatorioMensal pessoa1, RelatorioMensal pessoa2) {  
	                    if (pessoa1.compare(pessoa1.getReferencia(), pessoa2.getReferencia()) == -1) {  
	                        return -1;  
	                    } else if (pessoa1.compare(pessoa1.getReferencia(), pessoa2.getReferencia()) == 0) {  
	                        return 0;  
	                    }  
	  
	                    return 1;  
	                }  
	            });  
		
		return relatorioMensais;
		
	}
	
	
	public BigDecimal totalPorGrupoContaEntrada(LocalDate data, GrupoConta grupoConta) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valor) from Movimento m  where month(dataMovimento) = :mes and year(dataMovimento) = :ano and m.item.subGrupo.grupoConta = :item and tipo = 'C' ", BigDecimal.class)
					.setParameter("mes", data.getMonthValue())
					.setParameter("ano", data.getYear())
					.setParameter("item", grupoConta)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	public BigDecimal totalPorSubGrupoEntrada(LocalDate data, SubGrupo subGrupo) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valor) from Movimento m  where month(dataMovimento) = :mes and year(dataMovimento) = :ano and m.item.subGrupo = :item and tipo = 'C'", BigDecimal.class)
					.setParameter("mes", data.getMonthValue())
					.setParameter("ano", data.getYear())
					.setParameter("item", subGrupo)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	public BigDecimal totalPorGrupoContaSaida(LocalDate data, GrupoConta grupoConta) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valor) from Movimento m  where month(dataMovimento) = :mes and year(dataMovimento) = :ano and m.item.subGrupo.grupoConta = :item and tipo = 'D' ", BigDecimal.class)
					.setParameter("mes", data.getMonthValue())
					.setParameter("ano", data.getYear())
					.setParameter("item", grupoConta)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	public BigDecimal totalPorSubGrupoSaida(LocalDate data, SubGrupo subGrupo) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valor) from Movimento m  where month(dataMovimento) = :mes and year(dataMovimento) = :ano and m.item.subGrupo = :item and tipo = 'D'", BigDecimal.class)
					.setParameter("mes", data.getMonthValue())
					.setParameter("ano", data.getYear())
					.setParameter("item", subGrupo)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	public BigDecimal totalPorItem(LocalDate data, Item item) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valor) from Movimento m  where month(dataMovimento) = :mes and year(dataMovimento) = :ano and m.item = :item ", BigDecimal.class)
					.setParameter("mes", data.getMonthValue())
					.setParameter("ano", data.getYear())
					.setParameter("item", item)
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override	
	public  List<Integer> anosMovimento() {
		String jpql = "SELECT YEAR(data_movimento) as ano FROM Movimento m GROUP BY YEAR(data_movimento) ORDER BY ano DESC";
		List<Integer> anos =  manager.createQuery(jpql, Integer.class)
				.getResultList();
			return anos;
		}
	
	@Override	
	public  BigDecimal totalEtradaMes(LocalDate data) {
			
			Optional<BigDecimal> optional = Optional.ofNullable(
					manager.createQuery("SELECT SUM(valor) FROM Movimento m WHERE month(dataMovimento) = :mes and year(dataMovimento) = :ano  and tipo = 'C'", BigDecimal.class)
					.setParameter("mes", data.getMonthValue())
					.setParameter("ano", data.getYear())
						.getSingleResult());
			return optional.orElse(BigDecimal.ZERO);
		}
	
	@Override	
	public  BigDecimal totalSaidaMes(LocalDate data) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("SELECT SUM(valor) FROM Movimento m WHERE month(dataMovimento) = :mes and year(dataMovimento) = :ano  and tipo = 'D'", BigDecimal.class)
				.setParameter("mes", data.getMonthValue())
				.setParameter("ano", data.getYear())
					.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
		
		}
	


	@Override	
	public  BigDecimal saldoMesAnterior(LocalDate data) {
		Optional<BigDecimal> optional = Optional.ofNullable(
		  totalEtradaMes(data).subtract(totalSaidaMes(data)));
		return optional.orElse(BigDecimal.ZERO) ;
		
		}


	@Override	
	public  List<MesAnoData> MesesMovimento() {
		
		String jpql = "SELECT new com.abia.ibr.dto.MesAno(MONTH(data_movimento) as mes, YEAR(data_movimento) as ano) FROM Movimento m GROUP BY MONTH(data_movimento), YEAR(data_movimento) ORDER BY  ano,mes ASC";
		List<MesAno> meseAnos =  manager.createQuery(jpql, MesAno.class)
				.getResultList();
		ArrayList<MesAnoData> datas = new ArrayList<>();
		
		for (MesAno mesAno : meseAnos) {
			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MMMM / yyyy");
			LocalDate mesAnoFormat = LocalDate.now().withMonth(mesAno.getMes()).withYear(mesAno.getAno());
			
			MesAnoData mesAnoData = new MesAnoData();
			mesAnoData.setMes(mesAno.getMes());
			mesAnoData.setAno(mesAno.getAno());
			mesAnoData.setMesAno( mesAnoFormat.format(formatador));
			
			datas.add(0,mesAnoData);
		}
		
			return datas;
		}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BalancoTotal> balancoTotal(Integer ano) {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication(); 
		Usuario usuario = new Usuario();
		if(authentication != null){
			Object obj = authentication.getPrincipal();
			if (obj instanceof UsuarioSistema){
			UsuarioSistema u = (UsuarioSistema) obj;
			usuario = u.getUsuario();
			}
		}
		return  manager.createNamedQuery("Movimento.balancoTotal")
				.setParameter("ano", ano)
				.setParameter("tenantId", usuario.getTenantId())
				.getResultList();
				
		 
	}

}

