/**
 * 
 */
package au.com.feature.aem.core.models.exporter.impl;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;


@Model(
	    adaptables = { SlingHttpServletRequest.class },
	    resourceType = SampleSingModelExporter.RESOURCE_TYPE,
	    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
	@Exporter(name = "jackson",
	    extensions = "json", options = {
	    @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true"),
	    @ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "false")
	    })
public class SampleSingModelExporter {
	
	public static final String RESOURCE_TYPE = "aemfeature/components/content/slingModelExporter";

}
