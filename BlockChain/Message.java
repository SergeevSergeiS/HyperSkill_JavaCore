package blockchain;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Message implements Callable, Serializable {
    private static final long serialVersionUID = 3L;

    private int messageId;
    private long sentAmount;
    private String fromName;
    private String toName;
    private StringBuilder text;
    private byte[] privateKeyBytes;
    private List<byte[]> signature;

    public Message(int messageId, Long sentAmount, String fromName, String toName, byte[] privateKeyBytes) {
        this.messageId = messageId;
        this.sentAmount = sentAmount;
        this.fromName = fromName;
        this.toName = toName;
        this.privateKeyBytes = privateKeyBytes;
        this.text = new StringBuilder(" sent " + sentAmount + " to " + toName);
        this.signature = new ArrayList<>();
    }

    public byte[] sign(String data, byte[] privateKeyBytes) throws InvalidKeyException, Exception {
        Signature dsa = Signature.getInstance("SHA1withRSA");
        dsa.initSign(getPrivate(privateKeyBytes));
        dsa.update(data.getBytes());

        return dsa.sign();
    }

    public PrivateKey getPrivate(byte[] privateKeyBytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePrivate(spec);
    }

    @Override
    public Message call() throws Exception {
        if ("".equals(this.fromName)) {
            this.fromName = "miner" + Thread.currentThread().getId();
        }

        text.insert(0, "MsgID: " + messageId + " " + fromName);
        signature.add(text.toString().getBytes());
        signature.add(sign(text.toString(), privateKeyBytes));

        return this;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getText() {
        return text.toString();
    }

    public String getToName() {
        return toName;
    }

    public String getFromName() {
        return fromName;
    }

    public Long getSentAmount() {
        return sentAmount;
    }
}
