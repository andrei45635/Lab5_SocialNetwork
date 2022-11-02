package validators;

public interface Validator<T> {
    void validate(T var1) throws ValidatorException;
}
