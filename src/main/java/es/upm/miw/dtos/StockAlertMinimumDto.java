package es.upm.miw.dtos;

public class StockAlertMinimumDto {
	
    private String name;
    
    public StockAlertMinimumDto() {
    }

    public StockAlertMinimumDto(String name) {
        this.setName(name);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	 @Override
	    public String toString() {
	        return "name=" + name;
	    }
	}
