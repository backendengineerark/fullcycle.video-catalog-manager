package app.projetaria.videocatalogmanager.infrastructure.api;

import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryUseCase;
import app.projetaria.videocatalogmanager.infrastructure.ControllerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;
}
