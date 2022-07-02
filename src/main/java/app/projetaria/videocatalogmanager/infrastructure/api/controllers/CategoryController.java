package app.projetaria.videocatalogmanager.infrastructure.api.controllers;


import app.projetaria.videocatalogmanager.domain.pagination.Pagination;
import app.projetaria.videocatalogmanager.infrastructure.api.CategoryAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryAPI {

    @Override
    public ResponseEntity<?> create() {
        return null;
    }

    @Override
    public Pagination<?> list(String search, Integer page, Integer perPage, String sort, String direction) {
        return null;
    }
}
