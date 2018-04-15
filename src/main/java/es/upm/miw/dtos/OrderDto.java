package es.upm.miw.dtos;

import java.util.ArrayList;
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

    public OrderDto() {
    }

    public OrderDto(Order order) {
        super(order);
        this.setProviderId(order.getProvider().getId());
        this.ordersLine = new ArrayList<>();
        for (OrderLine orderLine : order.getOrderLine()) {
            ordersLine.add(new OrderLineDto(orderLine));
        }
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

    @Override
    public String toString() {
        return "OrderDto [providerId=" + providerId + ", ordersLine=" + ordersLine + "]";
    }

}
