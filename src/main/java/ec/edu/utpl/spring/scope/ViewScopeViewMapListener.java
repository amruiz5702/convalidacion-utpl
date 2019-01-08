package ec.edu.utpl.spring.scope;

import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.ViewMapListener;
import java.lang.ref.WeakReference;

/**
 * Listener for processing ViewMapDestroydEvent that indicates the argument root
 * just had its associated view map destroyed.
 *
 * @see javax.faces.event.PreDestroyViewMapEvent
 *
 * @author Vladislav Zablotsky
 * @author Michail Nikolaev (original codebase author)
 */
public class ViewScopeViewMapListener implements ViewMapListener {

//    private static final Logger logger = Logger.getLogger(ViewScope.class);
    private final String name;

    private final Runnable callback;

    private boolean callbackCalled = false;

    private final WeakReference<UIViewRoot> uiViewRootWeakReference;

    private final ViewScope viewScope;

    public ViewScopeViewMapListener(UIViewRoot root, String name, Runnable callback, ViewScope viewScope) {
        this.name = name;
        this.callback = callback;
        this.uiViewRootWeakReference = new WeakReference<UIViewRoot>(root);
        this.viewScope = viewScope;
    }

    public synchronized void doCallback() {
//        logger.trace("Going call callback for bean " + name);
        if (!callbackCalled) {
            try {
                callback.run();
            } finally {
                callbackCalled = true;
            }
        }
    }

    public String getName() {
        return name;
    }

    public boolean isListenerForSource(Object source) {
        return (source == uiViewRootWeakReference.get());
    }

    public void processEvent(SystemEvent event) throws AbortProcessingException {
        if (event instanceof PreDestroyViewMapEvent) {
//            logger.trace("Going call callback for bean " + name);
            doCallback();
            viewScope.unregisterListener(this);
        }
    }

}
