package be.kuleuven.assemassist.domain.carmodel;

import java.io.IOException;
import java.util.List;

public interface CarModelReader {

	List<CarModel> readCarModels() throws IOException;
}
