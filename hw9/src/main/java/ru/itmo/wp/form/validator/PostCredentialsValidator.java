package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostCredentials;

@Component
public class PostCredentialsValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return PostCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostCredentials postForm = (PostCredentials) target;
            for (String word : postForm.getTags().split("\\s+")) {
                if (!word.matches("[a-zA-Z]+")) {
                    errors.rejectValue("tags", "tags.invalid-tag", "tags should only consist from latin letters");
                }
            }
        }
    }
}
