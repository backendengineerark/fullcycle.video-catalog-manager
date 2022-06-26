package app.projetaria.videocatalogmanager.infrastructure.configuration.usecases;

import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryUseCase;
import app.projetaria.videocatalogmanager.application.category.create.DefaultCreateCategoryUseCase;
import app.projetaria.videocatalogmanager.application.category.delete.DefaultDeleteCategoryUseCase;
import app.projetaria.videocatalogmanager.application.category.delete.DeleteCategoryUseCase;
import app.projetaria.videocatalogmanager.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import app.projetaria.videocatalogmanager.application.category.retrieve.get.GetCategoryByIdUseCase;
import app.projetaria.videocatalogmanager.application.category.retrieve.list.DefaultListCategoriesUseCase;
import app.projetaria.videocatalogmanager.application.category.retrieve.list.ListCategoriesUseCase;
import app.projetaria.videocatalogmanager.application.category.update.DefaultUpdateCategoryUseCase;
import app.projetaria.videocatalogmanager.application.category.update.UpdateCategoryUseCase;
import app.projetaria.videocatalogmanager.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CategoryUseCaseConfiguration {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfiguration(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }

}
