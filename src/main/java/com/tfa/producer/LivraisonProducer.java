package com.tfa.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.tfa.dto.EvenementCommande;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LivraisonProducer {

	private final RabbitTemplate template;
	
	public void traiterRemboursement(EvenementCommande commande) {
		template.convertAndSend("retour-client-exchange", "routing-key-retour", commande);
		log.info("Information de relivraison mise à jour... {}",commande);
		//creer une livraison dans la db
	}
}
