package btc.blockchain.dao;

import btc.blockchain.model.BThread;



public class BThreadDAO extends AbstractDAO<BThread> {

	private static final long serialVersionUID = -3658484244017967121L;

	@Override
	public Class<BThread> getServiceClass() {
		return BThread.class;
	}
}