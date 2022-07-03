package app.projetaria.videocatalogmanager.application.category.create;

import app.projetaria.videocatalogmanager.application.UseCase;
import app.projetaria.videocatalogmanager.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends
        UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
