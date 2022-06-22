//package app.projetaria.videocatalogmanager.application.usecase.category.findall;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.stereotype.Component;
//
//import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;
//import app.projetaria.videocatalogmanager.domain.repository.ICategoryRepository;
//import lombok.AllArgsConstructor;
//
//@Component
//@AllArgsConstructor
//public class FindAllCategoryUseCase implements IFindAllCategoryUseCase {
//
//    private ICategoryRepository repository;
//
//    @Override
//    public List<CategoryOutputData> execute() {
//        return repository.findAll()
//            .stream().map(category -> CategoryOutputData.builder()
//                .id(category.getId())
//                .name(category.getName())
//                .description(category.getDescription())
//                .isActive(category.getIsActive())
//                .build())
//            .collect(Collectors.toList());
//    }
//}
