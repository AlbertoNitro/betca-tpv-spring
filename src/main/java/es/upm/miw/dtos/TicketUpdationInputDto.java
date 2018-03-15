package es.upm.miw.dtos;

import java.util.List;

public class TicketUpdationInputDto {
    private List<Integer> listAmounts;
    
    private List<Integer> listCommitteds;
    
    public TicketUpdationInputDto() {
     // Empty for framework
    }
    
    public List<Integer> getListAmounts() {
        return listAmounts;
    }

    public void setListAmounts(List<Integer> listAmounts) {
        this.listAmounts = listAmounts;
    }

    public List<Integer> getListCommitteds() {
        return listCommitteds;
    }

    public void setListCommitteds(List<Integer> listCommitteds) {
        this.listCommitteds = listCommitteds;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((listAmounts == null) ? 0 : listAmounts.hashCode());
        result = prime * result + ((listCommitteds == null) ? 0 : listCommitteds.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TicketUpdationInputDto other = (TicketUpdationInputDto) obj;
        if (listAmounts == null) {
            if (other.listAmounts != null)
                return false;
        } else if (!listAmounts.equals(other.listAmounts))
            return false;
        if (listCommitteds == null) {
            if (other.listCommitteds != null)
                return false;
        } else if (!listCommitteds.equals(other.listCommitteds))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TicketUpdationInputDto [listAmounts=" + listAmounts + ", listCommitteds=" + listCommitteds + "]";
    }
         
}
