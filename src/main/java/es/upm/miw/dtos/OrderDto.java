package es.upm.miw.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import es.upm.miw.documents.core.Order;
import es.upm.miw.documents.core.OrderLine;
import es.upm.miw.dtos.validations.ListNotEmpty;

public class OrderDto extends OrderBaseOutputDto {

    @NotNull
    private String providerId;

    @ListNotEmpty
    private List<OrderLineDto> ordersLine;

    private Date closingDate;

    public OrderDto() {
    }

    public OrderDto(Order order) {
        this.setId(order.getId());
        this.setDescription(order.getDescription());
        this.setProviderCompany(order.getProvider().getCompany());
        this.setOpeningDate(order.getOpeningDate());
        this.setProviderId(order.getProvider().getId());
        this.ordersLine = new ArrayList<>();
        for (OrderLine orderLine : order.getOrderLine()) {
            ordersLine.add(new OrderLineDto(orderLine));
        }
        this.closingDate = order.getClosingDate();
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public List<OrderLineDto> getOrdersLine() {
        return ordersLine;
    }

    public void setOrdersLine(List<OrderLineDto> ordersLineDto) {
        this.ordersLine = ordersLineDto;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    @Override
    public String toString() {
        return "OrderDto [providerId=" + providerId + ", ordersLineDto=" + ordersLine + ", closingDate=" + closingDate + "]";
    }

}
