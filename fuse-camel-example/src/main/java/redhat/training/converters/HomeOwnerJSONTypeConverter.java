package redhat.training.converters;

import org.apache.camel.Converter;

import redhat.training.model.HomeOwner;
import com.google.gson.Gson;

@Converter
public class HomeOwnerJSONTypeConverter {

	@Converter
	public String convertToJSON(HomeOwner ho) {
		return new Gson().toJson(ho);
	}
}
