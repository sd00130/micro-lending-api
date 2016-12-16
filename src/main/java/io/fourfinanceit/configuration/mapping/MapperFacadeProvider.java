package io.fourfinanceit.configuration.mapping;

import io.fourfinanceit.model.Loan;
import io.fourfinanceit.model.LoanExtension;
import io.fourfinanceit.query.LoanDto;
import io.fourfinanceit.query.LoanExtensionDto;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cons on 15/12/16.
 */
@Configuration
public class MapperFacadeProvider {

    @Bean(name = "mapperFactory")
    public MapperFactory mapperFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Loan.class, LoanDto.class)
                .field("range.start", "start")
                .field("range.end", "end")
                .field("customerEmail", "customer")
                .byDefault()
                .register();
        mapperFactory.classMap(LoanExtension.class, LoanExtensionDto.class)
                .byDefault()
                .register();
        return mapperFactory;
    }

    @Bean(name = "mapperFacade")
    public MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }
}