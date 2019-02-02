package ec.edu.utpl.modelo.dao.implementacion;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.utpl.modelo.dao.interfase.InterfaceComponenteDao;
import ec.edu.utpl.modelo.entidad.EntidadComponente;

@Repository
@Transactional
public class ImplementacionComponenteDao extends ImplementacionDao<EntidadComponente>
		implements InterfaceComponenteDao<EntidadComponente> {

	public ImplementacionComponenteDao() {
		super(EntidadComponente.class);
	}

}
