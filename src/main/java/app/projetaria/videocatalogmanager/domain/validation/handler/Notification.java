package app.projetaria.videocatalogmanager.domain.validation.handler;

import app.projetaria.videocatalogmanager.domain.exception.DomainException;
import app.projetaria.videocatalogmanager.domain.validation.Error;
import app.projetaria.videocatalogmanager.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    public static Notification create(Throwable anT) {
        return create(new Error(anT.getMessage()));
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler aHandler) {
        this.errors.addAll(aHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(Validation aValidation) {
        try {
            aValidation.validate();
        } catch (DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (Throwable th) {
            this.errors.add(new Error(th.getMessage()));
        }
        return this;
    }
}
