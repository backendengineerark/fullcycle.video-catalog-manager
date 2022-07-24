package app.projetaria.videocatalogmanager.domain.exception;

import app.projetaria.videocatalogmanager.domain.AggregateRoot;
import app.projetaria.videocatalogmanager.domain.Identifier;
import app.projetaria.videocatalogmanager.domain.validation.Error;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot> anAggregate,
            final Identifier id
    ) {
        final String anError = "%s with id %s was not found".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );

        return new NotFoundException(anError, Collections.emptyList());
    }
}
