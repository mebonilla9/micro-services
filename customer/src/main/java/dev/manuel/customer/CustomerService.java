package dev.manuel.customer;

import dev.manuel.amqp.RabbitMqMessageProducer;
import dev.manuel.clients.fraud.FraudCheckResponse;
import dev.manuel.clients.fraud.FraudClient;
// import dev.manuel.clients.notification.NotificationClient;
import dev.manuel.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;
  //private final NotificationClient notificationClient;
  //private final RestTemplate restTemplate;
  private final FraudClient fraudClient;
  private final RabbitMqMessageProducer rabbitMqMessageProducer;

  public void registerCustomer(CustomerRegistrationRequest request) {
    Customer customer = Customer.builder()
      .firstName(request.firstName())
      .lastName(request.lastName())
      .email(request.email())
      .build();
    // todo: check if email valid
    // todo: check if email not taken

    customerRepository.saveAndFlush(customer);
    // todo: check if is fraudster
    /*FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
      "http://FRAUD/api/v1/fraud-check/{customerId}",
      FraudCheckResponse.class,
      customer.getId()
    );*/

    FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

    if (fraudCheckResponse.isFraudster()) {
      throw new IllegalStateException("Fraudster");
    }
    // todo: send notification object
    NotificationRequest notificationRequest = new NotificationRequest(
      customer.getId(),
      customer.getEmail(),
      String.format("Hi %s, welcome to DevManuel...",
        customer.getFirstName())
    );

    // todo: make it async. i.e add to queue
    rabbitMqMessageProducer.publish(
      notificationRequest,
      "internal.exchange",
      "internal.notification.routing-key"
    );
  }
}
