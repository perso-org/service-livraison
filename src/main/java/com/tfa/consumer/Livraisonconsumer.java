package com.tfa.consumer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.tfa.dto.EvenementCommande;
import com.tfa.producer.LivraisonProducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class Livraisonconsumer {

	private final LivraisonProducer producer;
	@RabbitListener(queues = "queue-rl-cl-002")
	public void remboursement(EvenementCommande commande) {
		log.info("Traitement plannification de livraison {}",commande.getCommande().getIdCmd());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		commande.setMessage(String.format("La livraison est prévue le %s", LocalDateTime.now().plusDays(3).format(formatter)));
		commande.setStatus("Date prévisionnelle de livraison donnée...");
		producer.traiterRemboursement(commande);
	}
}
