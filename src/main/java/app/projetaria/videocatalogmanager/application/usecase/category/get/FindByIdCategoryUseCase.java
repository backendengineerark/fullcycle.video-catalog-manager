//package app.projetaria.videocatalogmanager.application.usecase.category.get;
//
//import java.util.UUID;
//
//import org.springframework.stereotype.Component;
//
//import app.projetaria.videocatalogmanager.application.exception.NotFoundException;
//import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;
//import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;
//import lombok.AllArgsConstructor;
//
//@Component
//@AllArgsConstructor
//public class FindByIdCategoryUseCase implements IFindByIdCategoryUseCase {
//
//    private ICategoryRepository repository;
//
//    @Override
//    public CategoryOutputData execute(UUID id) {
//        return repository.findById(id)
//            .map(category ->
//                CategoryOutputData.builder()
//                .id(category.getId())
//                .name(category.getName())
//                .description(category.getDescription())
//                .isActive(category.getIsActive())
//                .build())
//            .orElseThrow(() -> new NotFoundException(String.format("Category %s not found", id)));
//    }
//}
