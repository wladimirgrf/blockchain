package btc.blockchain.rpc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import btc.blockchain.rpc.model.Transaction;

public class TransactionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Transaction.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		 ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
		
	}

}
