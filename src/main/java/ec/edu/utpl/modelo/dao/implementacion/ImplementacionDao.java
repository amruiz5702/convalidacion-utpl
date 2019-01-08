package ec.edu.utpl.modelo.dao.implementacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class ImplementacionDao<E extends Serializable> {

	@Autowired
	private SessionFactory sessionFactory;
	private final Class<E> clazz;
	protected CriteriaBuilder criteriaBuilder;
	protected CriteriaQuery<E> criteriaQuery;
	protected List<Predicate> lstPredicados;
	private Integer primerRegistro;
	private Integer ultimoRegistro;
	protected Root<E> root;

	public ImplementacionDao(Class<E> clazz) {
		this.clazz = clazz;
	}

	protected final Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	public ImplementacionDao<E> setPrimerRegistro(Integer primerRegistro) {
		this.primerRegistro = primerRegistro;
		return this;
	}

	public ImplementacionDao<E> setUltimoRegistro(Integer ultimoRegistro) {
		this.ultimoRegistro = ultimoRegistro;
		return this;
	}

	@Transactional
	public void crear(E instancia) {
		currentSession().save(instancia);
	}

	@Transactional
	public void editar(E intancia) {
		currentSession().update(intancia);
	}

	@Transactional(readOnly = true)
	public E obtener(Integer id) {
		E entidad = (E) currentSession().get(clazz, id);
		return entidad;
	}

	@Transactional(readOnly = true)
	public E obtener(Map<String, Object> propiedades) {
		return this.prepararTransaccion()
				.agregarPredicado(propiedades)
				.obtenerUnicoRegistro();
	}

	@Transactional(readOnly = true)
	public List<E> obtener(HashMap<String, Object> propiedades) {
		return this.prepararTransaccion()
				.agregarPredicado(propiedades)
				.obtenerListaRegistro();
	}

	@Transactional(readOnly = true)
	public List<E> obtener(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		return this.prepararTransaccion()
				.setPrimerRegistro(first)
				.setUltimoRegistro(pageSize)
				.agregarPredicado(filters)
				.agregarOrder(sortField, sortOrder)
				.obtenerListaRegistro();

	}

	@Transactional(readOnly = true)
	public int contar(Map<String, Object> propiedades) {
		prepararTransaccion().agregarPredicado(propiedades);
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		criteriaQuery.select(criteriaBuilder.count(root));
		criteriaQuery.where(lstPredicados.toArray(new Predicate[lstPredicados.size()]));
		return currentSession().createQuery(criteriaQuery).getSingleResult().intValue();
	}

	protected ImplementacionDao<E> prepararTransaccion() {
		this.criteriaBuilder = currentSession().getCriteriaBuilder();
		this.criteriaQuery = criteriaBuilder.createQuery(clazz);
		this.root = criteriaQuery.from(clazz);
		this.lstPredicados = new ArrayList<Predicate>();
		return this;
	}

	protected Query<E> ejecutarConsulta() {
		this.criteriaQuery.select(root);
		this.criteriaQuery.where(lstPredicados.toArray(new Predicate[lstPredicados.size()]));
		return currentSession().createQuery(criteriaQuery);
	}

	protected List<E> obtenerListaRegistro() {
		Query<E> query = this.ejecutarConsulta();
		if (Objects.nonNull(primerRegistro)) {
			query.setFirstResult(primerRegistro);
		}
		if (Objects.nonNull(ultimoRegistro)) {
			query.setMaxResults(ultimoRegistro);
		}
		return query.getResultList();
	}

	protected E obtenerUnicoRegistro() {
		Query<E> query = this.ejecutarConsulta();
		return query.uniqueResult();
	}

	protected ImplementacionDao<E> agregarPredicado(Map<String, Object> filters) {
		String nombrePropiedad;
		Object valorPropiedad;
		for (Map.Entry<String, Object> filter : filters.entrySet()) {
			nombrePropiedad = filter.getKey();
			valorPropiedad = filter.getValue();
			if (valorPropiedad instanceof Object[]) {
				lstPredicados.add(crearIn(nombrePropiedad, valorPropiedad));
			} else {
				lstPredicados.add(crearEqual(nombrePropiedad, valorPropiedad));
			}
		}
		return this;
	}

	protected ImplementacionDao<E> agregarPredicado(Predicate predicado) {
		this.lstPredicados.add(predicado);
		return this;
	}

	protected ImplementacionDao<E> agregarOrder(String nombrePropiedad, SortOrder sortOrder) {
		if (StringUtils.isNotEmpty(nombrePropiedad)) {
			if (sortOrder == SortOrder.ASCENDING) {
				criteriaQuery.orderBy(criteriaBuilder.asc(crearPath(nombrePropiedad)));
			} else if (sortOrder == SortOrder.DESCENDING) {
				criteriaQuery.orderBy(criteriaBuilder.desc(crearPath(nombrePropiedad)));
			}
		}
		return this;
	}

	protected ImplementacionDao<E> agregarDistinct() {
		this.criteriaQuery.distinct(true);
		return this;
	}

	protected Predicate crearEqual(String nombrePropiedad, Object valorPropiedad) {
		return criteriaBuilder.equal(crearPath(nombrePropiedad), valorPropiedad);
	}

	protected Predicate crearLike(String nombrePropiedad, Object valorPropiedad) {
		return criteriaBuilder.like(crearPath(nombrePropiedad), "%" + valorPropiedad + "%");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Predicate crearLessThanOrEqual(Expression<Comparable> extession, Comparable valorPropiedad) {
		return criteriaBuilder.lessThanOrEqualTo(extession, valorPropiedad);
	}

	protected Predicate crearIn(String nombrePropiedad, Object valorPropiedad) {
		Object[] items = Object[].class.cast(valorPropiedad);
		In<Object> lstIn = criteriaBuilder.in(crearPath(nombrePropiedad));
		for (Object item : items) {
			lstIn.value(item);
		}
		return lstIn;
	}

	protected Predicate crearOr(List<Predicate> lstPredicadoOr) {
		return criteriaBuilder.or(lstPredicadoOr.toArray(new Predicate[lstPredicadoOr.size()]));
	}

	protected Predicate crearAnd(List<Predicate> lstPredicadoAnd) {
		return criteriaBuilder.and(lstPredicadoAnd.toArray(new Predicate[lstPredicadoAnd.size()]));
	}

	protected Predicate crearBetweenFecha(String nombrePropiedad, Date fechaDesde, Date fechaHasta) {
		return criteriaBuilder.between(crearPath(nombrePropiedad), fechaDesde, fechaHasta);
	}

	protected Expression<?> crearFuncionSql(String nombreFuncion, Class<?> type, Expression<?>... arguments) {
		int tamanio = arguments.length;
		javax.persistence.criteria.Expression<?> expression;
		switch (tamanio) {
		case 1:
			expression = criteriaBuilder.function(nombreFuncion, type, arguments[0]);
			break;
		case 2:
			expression = criteriaBuilder.function(nombreFuncion, type, arguments[0], arguments[1]);
			break;
		case 3:
			expression = criteriaBuilder.function(nombreFuncion, type, arguments[0], arguments[1], arguments[3]);
			break;
		default:
			expression = criteriaBuilder.function(nombreFuncion, type);
			break;
		}
		return expression;
	}

	protected <T> Path<T> crearPath(String nombrePropiedad) {
		String[] items = StringUtils.split(nombrePropiedad, ".");
		Path<T> path = null;
		for (String item : items) {
			if (path == null) {
				path = root.get(item);
			} else {
				path = path.get(item);
			}
		}
		return path;
	}
}
