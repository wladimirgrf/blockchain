package com.blockchain.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.SPVBlockStore;


public class Network {
	
	protected static NetworkParameters networkParameters = MainNetParams.get();

	protected static File WALLET_FOLDER;
	
	private static File BROADCAST_FOLDER;
	
	
    public void init(){
    	Properties properties = new Properties();
    	InputStream input = getClass().getResourceAsStream("/path.properties");
    	try {
			properties.load(input);
			
			WALLET_FOLDER 	 = new File (properties.getProperty("wallet-folder"));
			BROADCAST_FOLDER = new File (properties.getProperty("broadcast-folder"));
			
			broadcast();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	private void broadcast(){
		File chainFile = new File(BROADCAST_FOLDER,"restore-from-seed.chain");
        if (chainFile.exists()) {
            chainFile.delete();
        }
		try {
			SPVBlockStore chainStore = new SPVBlockStore(networkParameters, chainFile);
			BlockChain chain = new BlockChain(networkParameters, chainStore);
			PeerGroup peers = new PeerGroup(networkParameters, chain);
			peers.addPeerDiscovery(new DnsDiscovery(networkParameters));

			peers.start();
			peers.stop();
		} catch (BlockStoreException e) {
			e.printStackTrace();
		}
	}
}
