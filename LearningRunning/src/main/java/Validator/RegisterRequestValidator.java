package Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import command.RegisterCommand;


public class RegisterRequestValidator implements Validator{
	private static final String emailRegExp = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[_A-Za-z]{2,})$";
	private Pattern pattern;
	
	

	public RegisterRequestValidator() {
		super();
		pattern = Pattern.compile(emailRegExp);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegisterCommand regReq =(RegisterCommand) target;
		if (regReq.getM_email()==null||regReq.getM_email().trim().isEmpty()) {
			errors.rejectValue("email", "required");
		} else {
			Matcher matcher = pattern.matcher(regReq.getM_email());
			
			System.out.println(matcher.matches());
			if (!matcher.matches()) {
				errors.rejectValue("m_email", "bad");
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "m_name", "required");
		ValidationUtils.rejectIfEmpty(errors, "m_pass", "required");
		ValidationUtils.rejectIfEmpty(errors, "m_repass", "required");
		if (!regReq.isPasswordEqualToConfirmPassword()) {
			errors.rejectValue("m_repass", "nomatch");

		}
	}
	
}
