package es.upm.miw.dtos;

import java.util.List;

import es.upm.miw.dtos.validations.ListNotEmpty;

public class TicketUpdationInputDto {
    @ListNotEmpty
    private List<Integer> listAmountsShoppings;

    @ListNotEmpty
    private List<Boolean> listCommitedsShoppings;

    public TicketUpdationInputDto() {
        // Empty for framework
    }

    public TicketUpdationInputDto(List<Integer> listAmountsShoppings, List<Boolean> listCommitedsShoppings) {
        super();
        this.listAmountsShoppings = listAmountsShoppings;
        this.listCommitedsShoppings = listCommitedsShoppings;
    }

    public List<Integer> getListAmountsShoppings() {
        return listAmountsShoppings;
    }

    public void setListAmountsShoppings(List<Integer> listAmountsShoppings) {
        this.listAmountsShoppings = listAmountsShoppings;
    }

    public List<Boolean> getListCommitedsShoppings() {
        return listCommitedsShoppings;
    }

    public void setListCommitedsShoppings(List<Boolean> listCommitedsShoppings) {
        this.listCommitedsShoppings = listCommitedsShoppings;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((listAmountsShoppings == null) ? 0 : listAmountsShoppings.hashCode());
        result = (prime * result) + ((listCommitedsShoppings == null) ? 0 : listCommitedsShoppings.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TicketUpdationInputDto other = (TicketUpdationInputDto) obj;
        if (listAmountsShoppings == null) {
            if (other.listAmountsShoppings != null) {
                return false;
            }
        } else if (!listAmountsShoppings.equals(other.listAmountsShoppings)) {
            return false;
        }
        if (listCommitedsShoppings == null) {
            if (other.listCommitedsShoppings != null) {
                return false;
            }
        } else if (!listCommitedsShoppings.equals(other.listCommitedsShoppings)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TicketUpdationInputDto [listAmountsShoppings=" + listAmountsShoppings + ", listCommitedsShoppings=" + listCommitedsShoppings
                + "]";
    }

}
