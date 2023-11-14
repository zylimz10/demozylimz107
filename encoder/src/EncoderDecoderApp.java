public class EncoderDecoderApp {
    public static void main(String[] args) {
        char offsetCharacter = 'F';

        Encoder encoder = new Encoder(offsetCharacter);

        String plainText = "HELLO WOR$D";

        encoder.printEncoded(plainText);

        String encodedText = encoder.encode(plainText);

        encoder.printDecoded(encodedText);
    }
}