package ec.edu.utpl.modelo.dao.implementacion;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ec.edu.utpl.modelo.dao.interfase.InterfaceRolDao;
import ec.edu.utpl.modelo.entidad.EntidadRol;

@Repository
public class ImplementacionRolDao extends ImplementacionDao<EntidadRol> implements InterfaceRolDao<EntidadRol> {

	public ImplementacionRolDao(SessionFactory sessionFactory) {
		super(EntidadRol.class);
	}
}
