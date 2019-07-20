package sg.reactivespring.webfluxquoteservice.service;

import reactor.core.publisher.Flux;
import sg.reactivespring.webfluxquoteservice.model.Quote;

import java.time.Duration;

/**
 * created by NR on 13 Jul 2019
 */
public interface QuoteGeneratorService {
    Flux<Quote> fetchQuoteStream(Duration period);
}
