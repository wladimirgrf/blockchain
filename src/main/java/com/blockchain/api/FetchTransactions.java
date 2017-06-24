package com.blockchain.api;


import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.FullPrunedBlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBag;
import org.bitcoinj.core.TransactionBroadcast;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.listeners.DownloadProgressTracker;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.MySQLFullPrunedBlockStore;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.wallet.WalletTransaction.Pool;
import org.spongycastle.crypto.digests.RIPEMD160Digest;

import com.google.common.util.concurrent.ListenableFuture;




public class FetchTransactions {
		

	public static void main(String[] args) throws Exception  {
		NetworkParameters networkParameters = TestNet3Params.get();
		


		//MySQLFullPrunedBlockStore chainStore = createStore(networkParameters, 1000);
		//FullPrunedBlockChain chain = new FullPrunedBlockChain(networkParameters, chainStore);
		
		File chainFile = new File("/Users/Wlad/Documents/eclipse/blockchain","restore-from-seed.spvchain");
        if (chainFile.exists()) {
            chainFile.delete();
        }
		SPVBlockStore chainStore = new SPVBlockStore(networkParameters, chainFile);
        BlockChain chain = new BlockChain(networkParameters, chainStore);
        
        PeerGroup peers = new PeerGroup(networkParameters, chain);

        //peers.setDownloadTxDependencies(1000);
        //peers.setBloomFilteringEnabled(false);
        
        peers.addPeerDiscovery(new DnsDiscovery(networkParameters));

        
        DownloadProgressTracker bListener = new DownloadProgressTracker() {
            @Override
            public void doneDownload() {
                System.out.println("blockchain downloaded");
            }
        };

        peers.start();
        peers.startBlockChainDownload(bListener);
        
        peers.waitForPeers(1).get();
        Peer peer = peers.getConnectedPeers().get(0);

		//bListener.await();

        Sha256Hash blockHash = chainStore.getChainHead().getHeader().getHash();
		Block block = peer.getBlock(blockHash).get();
        List<Transaction> tx_list = block.getTransactions();
        
        for(Transaction t : tx_list){
//        	System.out.println("hash - "+t.getHashAsString());
//        	System.out.println("input sum - "+t.getInputSum());
//        	System.out.println("output sum - "+t.getOutputSum());
//        	System.out.println("fee - "+t.getFee());
//        	System.out.println("date - "+t.getUpdateTime());
        	
        	 byte[] pk = t.getOutput(0).getScriptPubKey().getPubKey();
        	
        	 Address a = new Address(networkParameters, hash160(pk));
             System.out.println(a);
        
        }

        peers.stop();
		
	}
	
	 static byte[] hash160(byte[] in) {
	        MessageDigest d1;
	        try {
	            d1 = MessageDigest.getInstance("SHA-256");
	        } catch(NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	        d1.update(in);
	        byte[] digest = d1.digest();
	        RIPEMD160Digest d2 = new RIPEMD160Digest();
	        d2.update(digest, 0, 32);
	        byte[] ret = new byte[20];
	        d2.doFinal(ret, 0);
	        return ret;
	    }
	
	 // Replace these with your mysql location/credentials and remove @Ignore to test
    private static final String DB_HOSTNAME = "localhost";
    private static final String DB_NAME = "blockchain";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";


    public static MySQLFullPrunedBlockStore createStore(NetworkParameters params, int blockCount)
            throws BlockStoreException {
        return new MySQLFullPrunedBlockStore(params, blockCount, DB_HOSTNAME, DB_NAME, DB_USERNAME, DB_PASSWORD);
    }
    
 

}
