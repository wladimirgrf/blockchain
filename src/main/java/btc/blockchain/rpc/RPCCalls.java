package btc.blockchain.rpc;

public enum RPCCalls {
	
	GET_BLOCK_COUNT("getblockcount"),
	GET_NEW_ADDRESS("getnewaddress"),
    GET_ACCOUNT("getaccount"),
    GET_ACCOUNT_ADDRESS("getaccountaddress"),
    GET_ADDRESSES_BY_ACCOUNT("getaddressesbyaccount"),
    GET_RECEIVED_BY_ACCOUNT("getreceivedbyaccount"),
    GET_RECEIVED_BY_ADDRESS("getreceivedbyaddress"),
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
    
    SEND_FROM("sendfrom"),
    SEND_MANY("sendmany"),
    SEND_RAW_TRANSACTION("sendrawtransaction"),
    SEND_TO_ADDRESS("sendtoaddress"),

    DUMP_PRIVATE_KEY("dumpprivkey"),
    IMPORT_PRIV_KEY("importprivkey"),
    BACKUP_WALLET("backupwallet"),
    DECODE_RAW_TRANSACTION("decoderawtransaction"),
    CREATE_RAW_TRANSACTION("createrawtransaction"),
    SET_ACCOUNT("setaccount"),
    SIGN_RAW_TRANSACTION("signrawtransaction"),
    VALIDATE_ADDRESS("validateaddress"),
    ENCRYPT_WALLET("encryptwallet");
    

    private String value;

    private RPCCalls(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
