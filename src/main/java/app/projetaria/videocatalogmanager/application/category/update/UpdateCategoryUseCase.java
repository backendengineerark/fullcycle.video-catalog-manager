package app.projetaria.videocatalogmanager.application.category.update;

import app.projetaria.videocatalogmanager.application.UseCase;
import app.projetaria.videocatalogmanager.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
