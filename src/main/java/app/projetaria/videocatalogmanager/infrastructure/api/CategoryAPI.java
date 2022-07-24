package app.projetaria.videocatalogmanager.infrastructure.api;

import app.projetaria.videocatalogmanager.domain.pagination.Pagination;
import app.projetaria.videocatalogmanager.infrastructure.category.model.CategoryApiListResponse;
import app.projetaria.videocatalogmanager.infrastructure.category.model.CategoryApiResponse;
import app.projetaria.videocatalogmanager.infrastructure.category.model.CreateCategoryApiRequest;
import app.projetaria.videocatalogmanager.infrastructure.category.model.UpdateCategoryApiRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/categories")
@Tag(name = "Categories")
public interface CategoryAPI {

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created successfully"),
        @ApiResponse(responseCode = "422", description = "A validation error was throw"),
        @ApiResponse(responseCode = "500", description = "An internal server error was throw")
    })
    ResponseEntity<?> create(@RequestBody CreateCategoryApiRequest input);

    @GetMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "List all categories paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was throw")
    })
    Pagination<CategoryApiListResponse> list(
        @RequestParam(name = "search", required = false, defaultValue = "") final String search,
        @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
        @RequestParam(name = "per_page", required = false, defaultValue = "10") final Integer perPage,
        @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
        @RequestParam(name = "direction", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category retrieve successfully"),
            @ApiResponse(responseCode = "404", description = "Category was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was throw")
    })
    CategoryApiResponse getById(@PathVariable(name = "id") String anId);

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category update successfully"),
            @ApiResponse(responseCode = "404", description = "Category was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was throw")
    })
    ResponseEntity<?> update(@PathVariable(name = "id") String anId, @RequestBody UpdateCategoryApiRequest input);

    @DeleteMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category delete successfully"),
            @ApiResponse(responseCode = "404", description = "Category was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was throw")
    })
    void delete(@PathVariable(name = "id") String anId);
}
