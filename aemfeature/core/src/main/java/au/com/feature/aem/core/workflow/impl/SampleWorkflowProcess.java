/**
 * 
 */
package au.com.feature.aem.core.workflow.impl;

import java.io.IOException;
import java.util.Collections;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

import au.com.feature.aem.core.listeners.CustomEventListener;

/**
 * @author xljn2
 *
 */
@Component(property = { "process.label=Sample Workflhow Process" })
public class SampleWorkflowProcess implements WorkflowProcess {

	private static final Logger log = LoggerFactory.getLogger(CustomEventListener.class);

	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.day.cq.workflow.exec.WorkflowProcess#execute(com.day.cq.workflow.exec.
	 * WorkItem, com.day.cq.workflow.WorkflowSession,
	 * com.day.cq.workflow.metadata.MetaDataMap)
	 */
	@Override
	public void execute(WorkItem item, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {
		// TODO Auto-generated method stub
		WorkflowData workflowData = item.getWorkflowData();
		String payloadType = workflowData.getPayloadType();
		if (!StringUtils.equals(payloadType, "JCR_PATH")) {
			return;
		}
		String path = workflowData.getPayload().toString();
		log.info("path" + path);
		ResourceResolver resourceResolver = null;
		try {
			resourceResolver = getResourceResolver(workflowSession.getSession());
			Resource resource = resourceResolver.getResource(path);
			log.info("resource path ss- "+resource.getPath() + " and name is -"+resource.getName());
			sendGet();
			
		}catch(Exception e) {
			log.error("Error has occurred in execute method of the SampleWorkflowProcess - ",e);
			
		}finally {
			try {
				close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private ResourceResolver getResourceResolver(Session session) throws LoginException {
		return resourceResolverFactory.getResourceResolver(
				Collections.<String, Object>singletonMap(JcrResourceConstants.AUTHENTICATION_INFO_SESSION, session));
	}
	
	private void close() throws IOException {
        httpClient.close();
    }

	private void sendGet() throws Exception {

        HttpGet request = new HttpGet("https://www.google.com/search?q=toyota");

        // add request headers
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            log.info(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            log.info("header - "+headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                log.info(result);
            }

        }

    }

}
