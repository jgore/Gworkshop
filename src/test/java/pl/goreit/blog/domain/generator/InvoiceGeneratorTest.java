package pl.goreit.blog.domain.generator;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.goreit.blog.domain.model.*;

import java.math.BigDecimal;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Profile("test")
class InvoiceGeneratorTest {


    @Test
    @DisplayName("generate invoice")
    public void generateSimple() {

        OrderLine orderLine = new OrderLine("1", "admin", "Tarcze", 2, BigDecimal.valueOf(122));
        OrderLine orderLine2 = new OrderLine("1", "admin", "Klocki", 2, BigDecimal.valueOf(40));
        Order order = new Order("admin", "pawel", Lists.newArrayList(orderLine, orderLine2));

        Address address = new Address("Polsna", "17", "11", "52-214", "Sidzina", "Polska");

        Person person = new Person("Pablito", "Saklito", "123213231", address);
        Company company = new Company("Goreit", "8955555555", address);
        Workshop workshop = new Workshop("1", "2", "3", company, null, null, null);

        InvoiceGenerator invoiceGenerator = new InvoiceGenerator(order, workshop, person);
        invoiceGenerator.generate("1.pdf");
    }
}