package btc.blockchain.rpc.controller;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

import btc.blockchain.rpc.model.Method;


public class TransactionController extends AbstractController {

	private static final long serialVersionUID = 1520564735287357940L;

	private static final long satoshiQuotation  = 100000000;


	public JSONObject send(Long id, String bip38Cipher, int bip38Key, String toAddress, long satoshiAmount) {
		JSONObject importWallet = importPrivateKey(id, bip38Cipher, bip38Key);

		if(importWallet.containsKey("error")) {
			return importWallet;
		}

		return invoke(Method.SEND_FROM, id.toString(), toAddress, satoshiToBtc(satoshiAmount));
	}

	private String satoshiToBtc(long satoshiAmount) {
		BigDecimal coin = BigDecimal.valueOf(satoshiAmount);
		coin = coin.divide(new BigDecimal(satoshiQuotation));
		coin = coin.setScale(8, BigDecimal.ROUND_HALF_UP);
		return coin.toPlainString();
	}
}
