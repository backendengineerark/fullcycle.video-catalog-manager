package app.projetaria.videocatalogmanager.domain.category;

import app.projetaria.videocatalogmanager.domain.validation.Error;
import app.projetaria.videocatalogmanager.domain.validation.ValidationHandler;
import app.projetaria.videocatalogmanager.domain.validation.Validator;

import java.util.Objects;

public class CategoryValidator extends Validator {

    private final Category category;
    private static final Integer NAME_MIN_LENGTH = 3;
    private static final Integer NAME_MAX_LENGTH = 255;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final String name = category.getName();

        if (Objects.isNull(name) || name.isBlank()) {
            this.validationHandler().append(new Error("'Name' should not be null or empty"));
            return;
        }

        final Integer lengthName = name.trim().length();
        if (lengthName < NAME_MIN_LENGTH || lengthName > NAME_MAX_LENGTH) {
            this.validationHandler().append(new Error("'Name' should have between 3 and 255 characters"));
        }
    }
}
