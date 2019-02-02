package ec.edu.utpl.modelo.dao.implementacion;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.utpl.modelo.dao.interfase.InterfaceConvalidacionDao;
import ec.edu.utpl.modelo.entidad.EntidadConvalidacion;

@Repository
@Transactional
public class ImplementacionConvalidacionDao extends ImplementacionDao<EntidadConvalidacion>
		implements InterfaceConvalidacionDao<EntidadConvalidacion> {

	public ImplementacionConvalidacionDao() {
		super(EntidadConvalidacion.class);
	}

}
