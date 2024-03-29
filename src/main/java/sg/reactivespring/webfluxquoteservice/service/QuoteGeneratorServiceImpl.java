package sg.reactivespring.webfluxquoteservice.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import sg.reactivespring.webfluxquoteservice.model.Quote;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

/**
 * created by NR on 13 Jul 2019
 */
@Service
public class QuoteGeneratorServiceImpl implements QuoteGeneratorService {

    private final MathContext mathContext= new MathContext(2);
    private final Random random = new Random();
    private final List prices = new ArrayList<>();

    QuoteGeneratorServiceImpl() {
        this.prices.add(new Quote("APPL",166.78));
        this.prices.add(new Quote("MSFT",77.56));
        this.prices.add(new Quote("GOOG",847.24));
        this.prices.add(new Quote("ORCL",45.16));
        this.prices.add(new Quote("IBM", 159.34));
        this.prices.add(new Quote("INTC", 39.29));
        this.prices.add(new Quote("RHT", 84.29));
        this.prices.add(new Quote("VMW",92.21));
        this.prices.add(new Quote("V",170.03));
    }

    @Override
    public Flux<Quote> fetchQuoteStream(Duration period) {
        //We use Flux.generate to create quotes
        //iterating on each stock starting at index 0
        return Flux.generate(() -> 0,
                (BiFunction<Integer, SynchronousSink<Quote>, Integer>)(index, sink) -> {
                Quote updatedQuote = updateQuote((Quote) this.prices.get(index));
                sink.next(updatedQuote);
                return ++index % this.prices.size();
                })
                //we want to emit them with a specific period;
                //to do so, we zip that Flux with a Flux.interval
                .zipWith(Flux.interval(period))
                .map(t -> t.getT1())
                //Because values are generated in batches,
                //we need to set their timestamp after their creation
                .map(quote -> {
                    quote.setInstant(Instant.now());
                    return quote;
                })
                .log("sg.reactivespring.webfluxquoteservice.service.QuoteGeneratorService");

    }

    private Quote updateQuote(Quote quote) {
        BigDecimal priceChange = quote.getPrice()
                .multiply(new BigDecimal(0.05 * this.random.nextDouble()), this.mathContext);
        return new Quote(quote.getTicker(),quote.getPrice().add(priceChange));
    }
}
