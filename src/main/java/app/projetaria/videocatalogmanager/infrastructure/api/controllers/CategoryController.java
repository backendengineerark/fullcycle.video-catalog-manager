package app.projetaria.videocatalogmanager.infrastructure.api.controllers;


import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryCommand;
import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryOutput;
import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryUseCase;
import app.projetaria.videocatalogmanager.application.category.delete.DeleteCategoryUseCase;
import app.projetaria.videocatalogmanager.application.category.retrieve.get.GetCategoryByIdUseCase;
import app.projetaria.videocatalogmanager.application.category.retrieve.list.ListCategoriesUseCase;
import app.projetaria.videocatalogmanager.application.category.update.UpdateCategoryCommand;
import app.projetaria.videocatalogmanager.application.category.update.UpdateCategoryOutput;
import app.projetaria.videocatalogmanager.application.category.update.UpdateCategoryUseCase;
import app.projetaria.videocatalogmanager.domain.category.CategorySearchQuery;
import app.projetaria.videocatalogmanager.domain.pagination.Pagination;
import app.projetaria.videocatalogmanager.domain.validation.handler.Notification;
import app.projetaria.videocatalogmanager.infrastructure.api.CategoryAPI;
import app.projetaria.videocatalogmanager.infrastructure.category.model.CategoryApiListResponse;
import app.projetaria.videocatalogmanager.infrastructure.category.model.CategoryApiResponse;
import app.projetaria.videocatalogmanager.infrastructure.category.model.CreateCategoryApiRequest;
import app.projetaria.videocatalogmanager.infrastructure.category.model.UpdateCategoryApiRequest;
import app.projetaria.videocatalogmanager.infrastructure.category.presenter.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase,
                              final GetCategoryByIdUseCase getCategoryByIdUseCase,
                              final UpdateCategoryUseCase updateCategoryUseCase,
                              final DeleteCategoryUseCase deleteCategoryUseCase,
                              final ListCategoriesUseCase listCategoriesUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);
    }


    @Override
    public ResponseEntity<?> create(CreateCategoryApiRequest input) {
        final CreateCategoryCommand command =
            CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active()
            );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
            ResponseEntity.created(URI.create("/categories/".concat(output.id()))).body(output);

        return this.createCategoryUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<CategoryApiListResponse> list(
            final String search,
            final Integer page,
            final Integer perPage,
            final String sort,
            final String direction) {
        return this.listCategoriesUseCase
                .execute(new CategorySearchQuery(page, perPage, search, sort, direction))
                .map(CategoryApiPresenter::present);
    }

    @Override
    public CategoryApiResponse getById(final String anId) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(anId));
    }

    @Override
    public ResponseEntity<?> update(final String anId, final UpdateCategoryApiRequest input) {
        final UpdateCategoryCommand command =
                UpdateCategoryCommand.with(
                        anId,
                        input.name(),
                        input.description(),
                        input.active()
                );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(command)
                .fold(onError, onSuccess);
    }

    @Override
    public void delete(final String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }
}
