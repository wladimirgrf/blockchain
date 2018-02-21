package btc.blockchain.controller;

import java.io.Serializable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import btc.blockchain.dao.RequestDAO;
import btc.blockchain.model.Status;
import btc.blockchain.model.Request;
import btc.blockchain.rpc.controller.TransactionController;
import btc.blockchain.rpc.controller.WalletController;
import btc.blockchain.rpc.model.Method;
import btc.blockchain.rpc.model.Transaction;


@RestController
public class RequestController implements Validator, Serializable {

	private static final long serialVersionUID = 8155431350750583974L;

	@Autowired
	private RequestDAO dao;

	@Autowired
	private TransactionController transactionRPC;
	
	@Autowired
	private WalletController wrpc;
	
	private Transaction transaction;
	



	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/send")
	public String send(@ModelAttribute("transaction") Transaction transaction, BindingResult bindingResult) throws ParseException {

		if (bindingResult.hasErrors()) {
			//return 
		}
		
		JSONObject properties = (JSONObject)  new JSONParser().parse(transaction.toString());
		Request entity = new Request();
		
		entity.setProperties(properties);
		entity.setStatus(Status.INLINE);
		entity.setMethod(Method.SEND_FROM);

		dao.persist(entity);

		return entity.toString();
	}
	

	@RequestMapping(value = "/create")
	public String create() {
		return null;
	}
	
	@RequestMapping(value = "/balance/{address}")
	public String balance(@PathVariable("address") String address) {
		return wrpc.balance(address).toJSONString();
	}
	
	public static void main(String[] args) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject j = (JSONObject) parser.parse("{\"result\":0.1,\"id\":null,\"error\":null}");
		
		System.out.println(j.get("result"));
	}


	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}


	@Override
	public void validate(Object object, Errors errors) {
		if(object instanceof Transaction) {
			validateTransaction(object, errors);
		}
		
	}
	
	private void validateTransaction(Object object, Errors errors) {
		Transaction transaction = (Transaction) object;
		
		if(transaction.getBip38Cipher() == null || transaction.getBip38Key() <= 0 || transaction.getToAddress() == null || transaction.getSatoshiAmount() <= 0) {
			errors.reject("fields cannot be null");
			return;
		}
		if(!transactionRPC.isValidCipher(transaction.getBip38Cipher(), transaction.getBip38Key())) {
			errors.reject("incorrect cipher or key");
			return;
		}
		if(transactionRPC.isValidAddress(transaction.getToAddress())) {
			errors.reject("Address is not valid");
			return;
		}
	}
}
