package app.projetaria.videocatalogmanager.api.category;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.projetaria.videocatalogmanager.application.usecase.category.common.CategoryOutputData;
import app.projetaria.videocatalogmanager.application.usecase.category.create.CreateCategoryInputData;
import app.projetaria.videocatalogmanager.application.usecase.category.update.UpdateCategoryInputData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/categories")
@Api(value = "Categories")
public interface ICategoryEndpoint {
    
    @PostMapping
    @ResponseStatus(code = CREATED)
    @ApiOperation(value = "Create a new category")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public CategoryOutputData create(@RequestBody CreateCategoryInputData data);

    @GetMapping
    @ResponseStatus(code = OK)
    @ApiOperation(value = "List all success")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public List<CategoryOutputData> findAll();

    @GetMapping("/{id}")
    @ResponseStatus(code = OK)
    @ApiOperation(value = "Search a category")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Search success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public CategoryOutputData findById(@PathVariable UUID id);

    @PutMapping("/{id}")
    @ResponseStatus(code = NO_CONTENT)
    @ApiOperation(value = "Update a new category")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Updated success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public void update(@PathVariable UUID id, @RequestBody UpdateCategoryInputData data);

    @DeleteMapping("/{id}")
    @ResponseStatus(code = NO_CONTENT)
    @ApiOperation(value = "Delete a new category")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Deleted success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public void remove(@PathVariable UUID id);
}
