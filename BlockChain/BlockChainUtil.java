package blockchain;

import java.util.List;

public class BlockChainUtil {
    public static boolean validateBlockChain(List<Block> input) {
        return input
                .stream()
                .allMatch(
                        b -> StringUtil
                                .applySha256(
                                        b.getId() + b.getTimestamp() + b.getPrevHash() + b.getData() + b.getSeed()
                                )
                                .equals(b.getHash())
                );
    }

    public static boolean validateBlock(Block block) {
        String compare = StringUtil
                .applySha256(
                        block.getId() + block.getTimestamp() + block.getPrevHash() + block.getData() + block.getSeed()
                );

        return compare.equals(block.getHash());
    }
}
