package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Block implements Serializable {
    private static final long serialVersionUID = 1L;

    private int minerId;
    private int id;
    private long timestamp;
    private String prevHash;
    private String hash;
    private List<Message> data;
    private int seed;
    private long totalTime;

    private String numOfN = "";

    public Block(
            int minerId,
            int id,
            long timestamp,
            String prevHash,
            int seed,
            String hash,
            List<Message> data,
            long totalTime
    ) {
        this.minerId = minerId;
        this.id = id;
        this.timestamp = timestamp;
        this.prevHash = prevHash;
        this.hash = hash;
        this.data = new ArrayList<>();
        this.data.addAll(data);
        this.seed = seed;
        this.totalTime = totalTime;
    }

    @Override
    public String toString() {
        return "Block: \n" +
                "Created by miner # " + minerId + "\n" +
                minerId + "gets 100 VC\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + seed + "\n" +
                "Hash of the previous block: \n" + prevHash + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                "Block data: \n" + viewChat() +
                "Block was generating for " + totalTime / 1000 + " seconds \n" +
                numOfN;
    }

    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public int getId() {
        return id;
    }

    public List<Message> getData() {
        return data;
    }

    public String viewChat() {
        if (data == null) {
            return "no messages";
        }

        StringBuilder sb = new StringBuilder();

        for (Message s : data) {
            sb.append(s.getText()).append("\n");
        }

        return sb.toString();
    }

    public int getSeed() {
        return seed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setN(Block block, int prefix) {
        int prevN = 0;

        for (int l = 0; l < block.getHash().length(); l++) {
            if (block.getHash().charAt(l) == '0') {
                prevN++;
            } else {
                break;
            }
        }

        if (prefix > prevN) {
            this.numOfN = "N was increased to " + prefix + "\n";
        } else if (prefix < prevN) {
            this.numOfN = "N was decreased by " + prefix + "\n";
        } else {
            this.numOfN = "N stays the same" + "\n";
        }
    }
}
