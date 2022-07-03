package app.projetaria.videocatalogmanager.domain;

import app.projetaria.videocatalogmanager.domain.validation.ValidationHandler;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {

    protected AggregateRoot(final ID id) {
        super(id);
    }
}
