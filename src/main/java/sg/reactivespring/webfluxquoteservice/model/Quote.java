package sg.reactivespring.webfluxquoteservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;

/*
 * created by NR on 13 Jul 2019
 */
@Data
@NoArgsConstructor
public class Quote {
    private static final MathContext MATH_CONTEXT = new MathContext(2);

    private String sticker;
    private BigDecimal price;
    private Instant instant;

    public Quote(String sticker, BigDecimal price) {
        this.sticker = sticker;
        this.price = price;
    }

    public Quote(String sticker, Double price) {
        this.sticker = sticker;
        this.price = new BigDecimal(price, MATH_CONTEXT);
    }


}
