package be.kuleuven.assemassist.domain;

public class Car {
	
	private CarModel carModel;
	
	public Car(CarModel carModel){
		this.setCarModel(carModel);
	}

	public CarModel getCarModel() {
		return carModel;
	}

	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}
	
	
	
}
