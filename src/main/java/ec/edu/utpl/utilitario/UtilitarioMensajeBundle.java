package ec.edu.utpl.utilitario;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilitarioMensajeBundle {

    private static final Logger LOGGER = LogManager.getLogger(UtilitarioMensajeBundle.class);
    private static ResourceBundle bundle;

    private static ResourceBundle getBundle() {
        if (bundle == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            bundle = context.getApplication().getResourceBundle(context, "mensaje");
        }
        return bundle;
    }

    public static String getValor(String strClave, Object[] argumentos) {
        String result;
        try {
            result = getBundle().getString(strClave);
        } catch (MissingResourceException excepcion) {
            LOGGER.fatal(excepcion.getMessage(), excepcion);
            result = "";
        }
        if (argumentos == null) {
            return result;
        }
        MessageFormat format = new MessageFormat(result);
        return format.format(argumentos);
    }
}
