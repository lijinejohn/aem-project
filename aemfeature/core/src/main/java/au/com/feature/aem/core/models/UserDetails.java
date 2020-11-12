/**
 * 
 */
package au.com.feature.aem.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

/**
 * @author xljn2
 *
 * Sample sling model class that will be used in the component for use java class
 */
@Model(adaptables= Resource.class)
public class UserDetails {
	
	@Inject
	private String firstName;
	
	@Inject
	private String lastName;
	
	@Inject
	private String location;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLocation() {
		return location;
	}

	public String getMessage() {
		// write custom business logic
		return "firstName - "+firstName+" and LastName - "+lastName+" and location - "+location;
	}
}
