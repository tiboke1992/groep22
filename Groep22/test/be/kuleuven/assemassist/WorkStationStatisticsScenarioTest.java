package be.kuleuven.assemassist;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

import be.kuleuven.assemassist.controller.WorkStationController;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.ProductionSchedule;
import be.kuleuven.assemassist.domain.carmodel.CarModel;

public class WorkStationStatisticsScenarioTest {

	@Test
	public void test() {
		DateTime today = DateTime.now();
		DateTime yesterday = today.minusDays(1);
		DateTime twodagen = yesterday.minusDays(1);
		CarManufacturingCompany m = new CarManufacturingCompany("", null);
	    ProductionSchedule s = m.getProductionSchedule();
	    CarModel model = new CarModel(null, null, 0, null);
	    CarOrder o1 = new CarOrder(model);
	    CarOrder o2 = new CarOrder(model);
	    CarOrder o3 = new CarOrder(model);
	    CarOrder o4 = new CarOrder(model);
	    CarOrder o5 = new CarOrder(model);
	    CarOrder o6 = new CarOrder(model);
	    CarOrder o7 = new CarOrder(model);
	    CarOrder o8 = new CarOrder(model);
	    
	    o5.init(twodagen, 20);
	    o5.getDeliveryTime().setCompletionTime(twodagen);
	    
	    
	    
	    ////////////////////////
	    o6.init(yesterday, 20);
	    o6.getDeliveryTime().setCompletionTime(yesterday);
	    
	    
	    o1.init(yesterday, 20);
	    o1.getDeliveryTime().setCompletionTime(yesterday);
	    
	    
	    o2.init(yesterday, 20);
	    o2.getDeliveryTime().setCompletionTime(yesterday);
	    
	    
	    o3.init(yesterday, 20);
	    o3.getDeliveryTime().setCompletionTime(yesterday);
	    
	    
	    
	    ///////////////////////
	    
	    o4.init(today, 20);
	    o4.getDeliveryTime().setCompletionTime(today);
	    
	    o7.init(today, 20);
	    o7.getDeliveryTime().setCompletionTime(today);
	    
	    o8.init(today, 20);
	    o8.getDeliveryTime().setCompletionTime(today);
	    
	    s.addCarOrder(o5);
	    s.addCarOrder(o6);
	    s.addCarOrder(o1);
	    s.addCarOrder(o2);
	    s.addCarOrder(o3);
	    s.addCarOrder(o4);
	    s.addCarOrder(o7);
	    s.addCarOrder(o8);
	    
	    
	    //s.completeOrder(o5);
	    s.completeOrder(o6);
	    s.completeOrder(o1);
	    s.completeOrder(o2);
	    s.completeOrder(o3);
	    s.completeOrder(o4);
	    s.completeOrder(o7);
	    s.completeOrder(o8);
	    
	    WorkStationController c = new WorkStationController(m);
	    
	  
	    System.out.println("average " + c.getAverageProducedCarsPerDay());
	    System.out.println("mediaan " + c.getMedianProducedCars());
		
	}

}
