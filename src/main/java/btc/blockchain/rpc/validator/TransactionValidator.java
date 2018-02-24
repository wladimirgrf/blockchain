package btc.blockchain.rpc.validator;

import java.io.Serializable;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import btc.blockchain.rpc.model.Transaction;
import btc.blockchain.security.BIP38;


public class TransactionValidator implements Validator, Serializable {

	private static final long serialVersionUID = 6526902470830718769L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Transaction.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toAddress",   "error.address.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bip38Cipher", "error.cipher.empty");

		Transaction transaction = (Transaction) target;

		if(!BIP38.isValidCipher(transaction.getBip38Cipher(), transaction.getBip38Key())) {
			errors.reject("error.cipher.incorrect");
		}
	}
}
