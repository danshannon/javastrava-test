package test.apicheck.api;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javastrava.util.exception.JsonSerialisationException;
import javastrava.util.impl.gson.JsonUtilImpl;

import org.apache.commons.io.IOUtils;

import retrofit.client.Response;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author dshannon
 *
 */
public class ResponseValidator {
	/**
	 * @param errors
	 * @return
	 */
	private static boolean containsErrors(List<String> errors) {
		boolean fail = false;
		if (!errors.isEmpty()) {
			for (String error : errors) {
				System.out.println(error);
			}
			fail = true;
		}
		return fail;
	}

	/**
	 * @param response response to be validated
	 * @param class1 class of the element (or of the array contents if the input is a JsonArray)
	 * @throws JsonSerialisationException 
	 * @throws IOException 
	 */
	public static <T> void validate(Response response, Class<T> class1) throws JsonSerialisationException, IOException {
		String input = IOUtils.toString(response.getBody().in());
		JsonUtilImpl util = new JsonUtilImpl();
		JsonParser parser = new JsonParser();
		JsonElement inputElement = parser.parse(input);
		List<String> errors = new ArrayList<String>();
		if (inputElement == null || inputElement.isJsonNull() || inputElement.isJsonPrimitive()) {
			return;
		}
		
		
		if (inputElement.isJsonObject()) {
			T object = util.deserialise(input, class1);
			String outputString = util.serialise(object);
			JsonElement output = parser.parse(outputString);
			for (Entry<String, JsonElement> entry : inputElement.getAsJsonObject().entrySet()) {
				String name = entry.getKey();
				JsonElement element = entry.getValue();
				if (!(element.isJsonNull()) && output.getAsJsonObject().get(name)  == null) {
					errors.add("No element named '" + name + "'");
				}
			}
		}
		
		if (inputElement.isJsonArray()) {
			for (JsonElement element : inputElement.getAsJsonArray()) {
				T object = util.getGson().fromJson(element, class1);
				JsonElement output = util.getGson().toJsonTree(object);
				for (Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
					String name = entry.getKey();
					JsonElement subelement = entry.getValue();
					if (!(subelement.isJsonNull()) && output.getAsJsonObject().get(name)  == null) {
						errors.add("No element named '" + name + "'");
					}
				}
			}
		}
		
		assertFalse(containsErrors(errors));
	}

}
