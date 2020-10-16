package au.com.feature.aem.core.listeners;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = EventListener.class, immediate = true)
public class CustomEventListener implements EventListener {

	private static final Logger log = LoggerFactory.getLogger(CustomEventListener.class);

	@Reference
	private ResourceResolverFactory resolverFactory;

	private ResourceResolver resolver;

	@Reference
	private SlingRepository repository;

	private Session adminSession;

	@Override
	public void onEvent(EventIterator events) {
		// TODO Auto-generated method stub
		try {

			while (events.hasNext()) {
				Object object = (Object) events.next();
				log.info("something is added - " + events.nextEvent().getPath());
			}

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(), e);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}

	}

	@Activate
	protected void activate(ComponentContext componentContext) {

		log.info(" Activating the event starts ");
		try {

			adminSession = repository.loginService("datawrite",null);
	         adminSession.getWorkspace().getObservationManager().addEventListener(
	          this, //handler
	          Event.PROPERTY_ADDED|Event.NODE_ADDED, //binary combination of event types
	          "/apps/example", //path
	          true, //is Deep?
	          null, //uuids filter
	          null, //nodetypes filter
	          false);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (adminSession != null) {

			adminSession.logout();
		}
	}

}
