package be.kuleuven.assemassist.domain;

public class CarModelSpecification {
	
	/*/
	 * instantiates CarOrder
	 */
	
	private Car car;
	
	public CarModelSpecification(Car car){
		setCar(car);
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	
}
