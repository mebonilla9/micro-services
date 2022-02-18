package dev.manuel.notification;

import dev.manuel.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;

  public void send(NotificationRequest notificationRequest){
    notificationRepository.save(
      Notification.builder()
        .toCustomerId(notificationRequest.toCustomerId())
        .toCustomerEmail(notificationRequest.toCustomerName())
        .sender("DevManuel")
        .message(notificationRequest.message())
        .sentAt(LocalDateTime.now())
        .build()
    );
  }
}
