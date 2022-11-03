package labshopeventuate.infra;

import javax.naming.NameParser;

import javax.naming.NameParser;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import labshopeventuate.domain.*;


@Configuration
public class PolicyHandler{    
    
    @Bean
    public DomainEventDispatcher domainEventDispatcher(DomainEventDispatcherFactory domainEventDispatcherFactory) {
      return domainEventDispatcherFactory.make("orderServiceEvents", DomainEventHandlersBuilder
      .forAggregateType("labshopeventuate.domain.Order")
      .onEvent(OrderPlaced.class, PolicyHandler::wheneverOrderPlaced_DecreaseStock)

      .build());
    }


    public static void wheneverOrderPlaced_DecreaseStock(DomainEventEnvelope<OrderPlaced> orderPlacedEvent){

        OrderPlaced event = orderPlacedEvent.getEvent();
        System.out.println("\n\n##### listener DecreaseStock : " + event + "\n\n");

        if(event.getProductId()!=null) 
            Inventory.decreaseStock(event);
        
    }

}


