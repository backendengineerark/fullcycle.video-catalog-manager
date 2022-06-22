package app.projetaria.videocatalogmanager.application.category.update;

import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import app.projetaria.videocatalogmanager.domain.exception.DomainException;
import app.projetaria.videocatalogmanager.domain.validation.Error;
import app.projetaria.videocatalogmanager.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand anCommand) {
        final CategoryId anId = CategoryId.from(anCommand.id());

        final Category category = this.categoryGateway.findById(anId)
                .orElseThrow(notNull(anId));

        final Notification notification = Notification.create();

        category.update(anCommand.name(), anCommand.description(), anCommand.isActive())
                .validate(notification);

        return notification.hasErrors() ? Left(notification) : update(category);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notNull(final CategoryId anId) {
        return () -> DomainException.with(
            new Error("Category with ID %s was not found".formatted(anId.getValue())));
    }
}
