package btc.blockchain.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btc.blockchain.dao.RequestDAO;
import btc.blockchain.model.Status;
import btc.blockchain.model.Request;
import btc.blockchain.rpc.model.Method;
import btc.blockchain.rpc.model.Transaction;
import btc.blockchain.rpc.validator.TransactionValidator;


@RestController
public class RequestController implements Serializable {

	private static final long serialVersionUID = 8155431350750583974L;
	
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	@Autowired
	private TransactionValidator transactionValidator;

	@Autowired
	private RequestDAO dao;


	@InitBinder("transactionBinder")
	protected void transactionBinder(WebDataBinder binder) {
		binder.addValidators(transactionValidator);
	}

	@RequestMapping(value = "/send")
	public String send(@ModelAttribute("transactionBinder") @Validated Transaction transaction, BindingResult result) throws ParseException {
		if (result.hasErrors()) {
			return bindingResultToJson(result).toJSONString();
		}
		JSONObject properties = (JSONObject)  new JSONParser().parse(transaction.toString());
		Request entity = generateRequest(Method.SEND_FROM, properties);

		return entity.toString();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/balance/{address}")
	public String balance(@PathVariable("address") String address) {
		JSONObject properties = new JSONObject();
		properties.put("address", address);
		Request entity = generateRequest(Method.GET_RECEIVED_BY_ADDRESS, properties);
		
		return entity.toString();
	}
	
	@RequestMapping(value = "/create")
	public String create() {
		Request entity = generateRequest(Method.GET_NEW_ADDRESS);
		
		return entity.toString();
	}
	
	@RequestMapping(value = "/request/{txId}")
	public String getByTxId(@PathVariable("txId") String txId) {
		Request entity = dao.getByTxId(txId);
		
		return entity.toString();
	}
	
	private Request generateRequest(Method method) {
		return generateRequest(method, null);
	}
	
	private Request generateRequest(Method method, JSONObject properties) {
		Request entity = new Request();
		if(properties != null) {
			entity.setProperties(properties);
		}
		entity.setStatus(Status.INLINE);
		entity.setMethod(method);
		dao.persist(entity);

		return entity;
	}

	@SuppressWarnings("unchecked")
	private JSONObject bindingResultToJson(BindingResult result) {
		List<String> errors = new ArrayList<String>();
		for (ObjectError objectError : result.getAllErrors()) {
			errors.add(messageSource.getMessage(objectError, null));
		}
		JSONObject exceptions = new JSONObject();
		exceptions.put("errors", errors);
		
		return exceptions;
	}
}
