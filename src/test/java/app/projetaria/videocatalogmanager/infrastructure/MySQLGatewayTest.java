package app.projetaria.videocatalogmanager.infrastructure;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@ComponentScan(
    includeFilters = {
    @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[MySQLGateway]")
})
@SpringBootTest
public @interface MySQLGatewayTest {
}
