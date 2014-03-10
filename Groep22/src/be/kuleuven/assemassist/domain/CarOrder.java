package be.kuleuven.assemassist.domain;

import java.util.Date;
import java.util.UUID;

public class CarOrder {

	private UUID id;
	private Date completionTime;
	
	public CarOrder(){
		this.id = UUID.randomUUID();
	}
	
	public UUID getId() {
		return id;
	}

	public Date getEstimatedCompletionTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getCompletionTime() {
		return completionTime;
	}

}
