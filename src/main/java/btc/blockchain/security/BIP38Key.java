package btc.blockchain.security;


public enum BIP38Key {
	one     (1,"d2A8b32c44b3e2c6aa805c6cc2a6c9ad"),
	two     (2,"cf62d3bcb1db224138113b80e7ASssa2"),
	three   (3,"299b60Fe459bc52817f41039ccdsfdfs"),
	four    (4,"4522Adcd6b871ec8bac2316fe734dssa"),
	five    (5,"f3f46a1fEWbb4bf1f3f487GHUHGFEddd"),
	six     (6,"d75645ed88E7f576881fa2a3Azxllw3f"),
	seven   (7,"b67ef9bf5F16da6fac9b0697DAC03b7a"),
	eight   (8,"ecaE5d9250558f7Bb8aAeaf3qrff332r"),
	nine    (9,"b25fcc474edadfw89hw9e8fhew0ffe0h"),
	ten     (10,"aed6607E96a41dfb9d44c5f11ac7BD7w"),
	eleven  (11,"y40c2081ddb3793f3fehf98q3h9fffh6"),
	twelve  (12,"b4876986be5d3cf03f283rf23r3rffl0"),
	thirteen(13,"s0b675549b2cc474exzcccegdv3443p5");
	
	
	BIP38Key(int key, String value) {
		this.key = key;
		this.value = value;
	}
	
	private int key;
	
	private String value;
	
	int getKey() {
		return key;
	}
	
	String getValue() {
		return value;
	}
	
	static BIP38Key getByKey(int key) {
		for (BIP38Key bKey: BIP38Key.values()) {
			if (bKey.getKey() == key) {
				return bKey;
			}
		}
		return null;
	}
}
