package btc.blockchain.rpc.model;

public enum Method {
	
	//In System
	GET_NEW_ADDRESS("getnewaddress"),
	GET_RECEIVED_BY_ADDRESS("getreceivedbyaddress"),
	SEND_FROM("sendfrom"),
	IMPORT_PRIV_KEY("importprivkey"),
	VALIDATE_ADDRESS("validateaddress"),
	//In System
	
	GET_BLOCK_COUNT("getblockcount"),
    GET_ACCOUNT("getaccount"),
    GET_ACCOUNT_ADDRESS("getaccountaddress"),
    GET_ADDRESSES_BY_ACCOUNT("getaddressesbyaccount"),
    GET_RECEIVED_BY_ACCOUNT("getreceivedbyaccount"),
    GET_BALANCE("getbalance"),
    GET_TRANSACTION("gettransaction"),
    GET_RAW_TRANSACTION("getrawtransaction"),
    GET_CONNECTION_COUNT("getconnectioncount"),
    GET_BLOCKCHAIN_INFO("getblockchaininfo"),
    GET_NETWORK_INFO("getnetworkinfo"),
    
    LIST_ACCOUNTS("listaccounts"),
    LIST_TRANSACTIONS("listtransactions"),
    LIST_ADDRESS_GROUPINGS("listaddressgroupings"),
    LIST_RECEIVED_BY_ACCOUNT("listreceivedbyaccount"),
    LIST_RECEIVED_BY_ADDRESS("listreceivedbyaddress"),
    LIST_UNSPENT("listunspent"),
    
    SEND_MANY("sendmany"),
    SEND_RAW_TRANSACTION("sendrawtransaction"),
    SEND_TO_ADDRESS("sendtoaddress"),

    DUMP_PRIVATE_KEY("dumpprivkey"),
    BACKUP_WALLET("backupwallet"),
    DECODE_RAW_TRANSACTION("decoderawtransaction"),
    CREATE_RAW_TRANSACTION("createrawtransaction"),
    SET_ACCOUNT("setaccount"),
    SIGN_RAW_TRANSACTION("signrawtransaction"),
    ENCRYPT_WALLET("encryptwallet");
    

    private String value;

    private Method(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
