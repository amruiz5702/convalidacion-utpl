package ec.edu.utpl.modelo.dao.implementacion;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.utpl.modelo.entidad.EntidadPersistenciaLogin;

@Repository("implementacionPersistenciaTokenDao")
@Transactional
public class ImplementacionPersistenciaTokenDao implements PersistentTokenRepository {

	@Autowired
	private SessionFactory sessionFactory;

	protected final Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		EntidadPersistenciaLogin logins = new EntidadPersistenciaLogin();
		logins.setUsername(token.getUsername());
		logins.setSeries(token.getSeries());
		logins.setToken(token.getTokenValue());
		logins.setLastUsed(token.getDate());
		currentSession().save(logins);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		EntidadPersistenciaLogin logins = currentSession().get(EntidadPersistenciaLogin.class, series);
		logins.setToken(tokenValue);
		logins.setLastUsed(lastUsed);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		EntidadPersistenciaLogin logins = sessionFactory.getCurrentSession().get(EntidadPersistenciaLogin.class,
				seriesId);
		if (logins != null) {
			return new PersistentRememberMeToken(logins.getUsername(), logins.getSeries(), logins.getToken(),
					logins.getLastUsed());
		}
		return null;
	}

	@Override
	public void removeUserTokens(String username) {
		sessionFactory.getCurrentSession().createQuery("delete from EntidadPersistenciaLogin" 
		        + " where username=:userName")
		        .setParameter("userName", username).executeUpdate();
	}

}
