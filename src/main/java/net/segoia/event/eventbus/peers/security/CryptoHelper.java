package net.segoia.event.eventbus.peers.security;

public interface CryptoHelper {

    byte[] base64Decode(String input);

    byte[] base64EncodeToBytes(byte[] input);

    byte[] base64DecodeToBytes(byte[] input);

    String base64Encode(byte[] input);
}
