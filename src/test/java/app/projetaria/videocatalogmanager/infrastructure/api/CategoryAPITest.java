package app.projetaria.videocatalogmanager.infrastructure.api;

import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryOutput;
import app.projetaria.videocatalogmanager.application.category.create.CreateCategoryUseCase;
import app.projetaria.videocatalogmanager.application.category.delete.DeleteCategoryUseCase;
import app.projetaria.videocatalogmanager.application.category.retrieve.get.CategoryOutput;
import app.projetaria.videocatalogmanager.application.category.retrieve.get.GetCategoryByIdUseCase;
import app.projetaria.videocatalogmanager.application.category.retrieve.list.ListCategoriesUseCase;
import app.projetaria.videocatalogmanager.application.category.update.UpdateCategoryUseCase;
import app.projetaria.videocatalogmanager.domain.category.Category;
import app.projetaria.videocatalogmanager.domain.category.CategoryId;
import app.projetaria.videocatalogmanager.domain.exception.DomainException;
import app.projetaria.videocatalogmanager.domain.exception.NotFoundException;
import app.projetaria.videocatalogmanager.domain.validation.Error;
import app.projetaria.videocatalogmanager.domain.validation.handler.Notification;
import app.projetaria.videocatalogmanager.infrastructure.ControllerTest;
import app.projetaria.videocatalogmanager.infrastructure.category.model.CreateCategoryApiRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @MockBean
    private ListCategoriesUseCase listCategoriesUseCase;

    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @MockBean
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @Test
    @DisplayName("Should create a category with success")
    public void givenAValidRequest_whenCallCreateCategoryAPI_ThenReturnACategoryCreated() throws Exception {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;

        final CreateCategoryApiRequest input =
            new CreateCategoryApiRequest(expectedName, expectedDescription, expectedStatus);

        when(createCategoryUseCase.execute(any()))
            .thenReturn(Right(CreateCategoryOutput.from("123")));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request)
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/categories/123"))
            .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id", equalTo("123")));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
            Objects.equals(expectedName, cmd.name()) &&
            Objects.equals(expectedDescription, cmd.description()) &&
            Objects.equals(expectedStatus, cmd.isActive())
        ));
    }

    @Test
    @DisplayName("Should throw exception when try create a category without name")
    public void givenARequestWithNullableName_whenCallCreateCategoryAPI_shouldReturnANotification() throws Exception {
        final String expectedName = null;
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final CreateCategoryApiRequest input =
                new CreateCategoryApiRequest(expectedName, expectedDescription, expectedStatus);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedErrorMessage))));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
            Objects.equals(expectedName, cmd.name()) &&
            Objects.equals(expectedDescription, cmd.description()) &&
            Objects.equals(expectedStatus, cmd.isActive())
        ));
    }

    @Test
    @DisplayName("Should throw DomainException when try create a category without name")
    public void givenARequestWithNullableName_whenCallCreateCategoryAPI_shouldReturnADomainException() throws Exception {
        final String expectedName = null;
        final String expectedDescription = "Comedy category";
        final Boolean expectedStatus = true;
        final String expectedErrorMessage = "'Name' should not be null or empty";

        final CreateCategoryApiRequest input =
                new CreateCategoryApiRequest(expectedName, expectedDescription, expectedStatus);

        when(createCategoryUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedErrorMessage)));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedStatus, cmd.isActive())
        ));
    }

    @Test
    @DisplayName("Should retrieve a category")
    public void givenAValidId_whenCallsGetById_shouldRetrieveACategory() throws Exception {
        final String expectedName = "Comedy";
        final String expectedDescription = "Comedy description";
        final Boolean expectedStatus = true;

        final Category category = Category.create(expectedName, expectedDescription, expectedStatus);

        final String expectedId = category.getId().getValue();

        when(getCategoryByIdUseCase.execute(any()))
                .thenReturn(CategoryOutput.from(category));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions response = this.mvc.perform(request)
                .andDo(print());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedId)))
                .andExpect(jsonPath("$.name", is(expectedName)))
                .andExpect(jsonPath("$.description", is(expectedDescription)))
                .andExpect(jsonPath("$.is_active", is(expectedStatus)))
                .andExpect(jsonPath("$.created_at", is(category.getCreatedAt().toString())))
                .andExpect(jsonPath("$.updated_at", is(category.getUpdatedAt().toString())))
                .andExpect(jsonPath("$.deleted_at", is(category.getDeletedAt())));

    }

    @Test
    @DisplayName("Should throw DomainException when try get a category with unknown id")
    public void givenAUnknownId_whenCallsGetById_shouldReturnDomainException() throws Exception {

        final String expectedErrorMessage = "Category with id 123 was not found";
        final CategoryId expectedId =  CategoryId.from("123");

        when(getCategoryByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, expectedId));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions response = this.mvc.perform(request)
                .andDo(print());

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(expectedErrorMessage)));

    }
}
