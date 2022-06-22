//package app.projetaria.videocatalogmanager.application.usecase.category.update;
//
//import java.util.UUID;
//
//import org.springframework.stereotype.Component;
//
//import app.projetaria.videocatalogmanager.application.exception.NotFoundException;
//import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;
//import lombok.AllArgsConstructor;
//
//@Component
//@AllArgsConstructor
//public class UpdateCategoryUseCase implements IUpdateCategoryUseCase {
//
//    private ICategoryRepository repository;
//
//    @Override
//    public void execute(UUID id, UpdateCategoryInputData data) {
//        repository.findById(id)
//            .map(category -> {
//                category.changeName(data.getName());
//                category.changeDescription(data.getDescription());
//                if (Boolean.TRUE.equals(data.getIsActive())) {
//                    category.activate();
//                } else {
//                    category.deactivate();
//                }
//                repository.update(category);
//                return category;
//            }).orElseThrow(() -> new NotFoundException(
//                String.format("Category %s not found to update", id)));
//    }
//
//}
//package app.projetaria.videocatalogmanager.application.usecase.category.update;
//
//import java.util.UUID;
//
//import org.springframework.stereotype.Component;
//
//import app.projetaria.videocatalogmanager.application.exception.NotFoundException;
//import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;
//import lombok.AllArgsConstructor;
//
//@Component
//@AllArgsConstructor
//public class UpdateCategoryUseCase implements IUpdateCategoryUseCase {
//
//    private ICategoryRepository repository;
//
//    @Override
//    public void execute(UUID id, UpdateCategoryInputData data) {
//        repository.findById(id)
//            .map(category -> {
//                category.changeName(data.getName());
//                category.changeDescription(data.getDescription());
//                if (Boolean.TRUE.equals(data.getIsActive())) {
//                    category.activate();
//                } else {
//                    category.deactivate();
//                }
//                repository.update(category);
//                return category;
//            }).orElseThrow(() -> new NotFoundException(
//                String.format("Category %s not found to update", id)));
//    }
//
//}
