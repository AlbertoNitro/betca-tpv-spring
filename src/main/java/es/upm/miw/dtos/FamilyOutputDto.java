package es.upm.miw.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.dtos.validations.ListNotEmpty;

@JsonInclude(Include.NON_NULL)
public class FamilyOutputDto {

    @ListNotEmpty
    private List<Object> listComponent;

    public FamilyOutputDto() {
    }

    public FamilyOutputDto(List<Object> listComponent) {
        super();
        this.listComponent = listComponent;
    }

    public List<Object> getListComponent() {
        return listComponent;
    }

    public void setListComponent(List<Object> listComponent) {
        this.listComponent = listComponent;
    }

    @Override
    public String toString() {
        return "FamilyOutputDto [listComponent=" + listComponent + "]";
    }

}
