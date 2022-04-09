package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class BlockChainGenerator implements Callable {
    private final int id;
    private final long timestamp;
    private final String prevHash;
    private final List<Message> data;
    private int seed;
    private final int prefix;
    byte[] privateKey;
    long reward;

    public BlockChainGenerator(
            int id,
            long timestamp,
            String prevHash,
            List<Message> data,
            int prefix,
            byte[] privateKey,
            long reward
    ) {
        this.id = id;
        this.timestamp = timestamp;
        this.prevHash = prevHash;
        this.data = new ArrayList<>();
        this.data.addAll(data);
        this.prefix = prefix;
        this.privateKey = privateKey;
        this.reward = reward;
    }

    @Override
    public Object call() throws Exception {
        data.add(new Message(id, 100L, "Blockchain", "miner " + Thread.currentThread().getId(), privateKey));
        String hash = mineBlock(prefix);

        return new Block((int) Thread.currentThread().getId(), id, timestamp, prevHash, seed, hash, data, new Date().getTime() - timestamp);
    }

    Random random = new Random();

    public String mineBlock(int prefix) {
        String prefixString = "0".repeat(prefix);
        String hash;

        do {
            seed = random.nextInt(Integer.MAX_VALUE);
            hash = StringUtil.applySha256(id + timestamp + prevHash + data + seed);
        } while (!hash.subSequence(0, prefix).equals(prefixString));

        return hash;
    }
}
