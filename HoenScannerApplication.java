import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {
    private final List<SearchResult> searchResults = new ArrayList<>();

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "HoenScanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {
        // Application initialization
    }

    @Override
    public void run(final HoenScannerConfiguration configuration, final Environment environment) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Load rental cars
        List<SearchResult> rentalCars = mapper.readValue(
                new File("src/main/resources/rental_cars.json"),
                new TypeReference<>() {}
        );

        // Load hotels
        List<SearchResult> hotels = mapper.readValue(
                new File("src/main/resources/hotels.json"),
                new TypeReference<>() {}
        );

        // Combine results
        searchResults.addAll(rentalCars);
        searchResults.addAll(hotels);

        // Register resources
        environment.jersey().register(new SearchResource(searchResults));
    }
}
