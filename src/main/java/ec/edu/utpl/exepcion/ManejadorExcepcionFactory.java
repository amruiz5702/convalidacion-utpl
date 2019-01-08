package ec.edu.utpl.exepcion;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ManejadorExcepcionFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory factory;

    @SuppressWarnings("deprecation")
	public ManejadorExcepcionFactory(ExceptionHandlerFactory factory) {
        this.factory = factory;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new ManejadorExcepcion(factory.getExceptionHandler());
    }

}