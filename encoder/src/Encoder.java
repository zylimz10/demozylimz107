public class Encoder {
    private String referenceTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";
    private char offsetCharacter;

    public Encoder(char offsetCharacter) {
        this.offsetCharacter = offsetCharacter;
    }

    public String encode(String plainText) {
        int offset = referenceTable.indexOf(offsetCharacter);
        if (offset == -1) {
            return "offset is invalid";
        }

        StringBuilder encodedText = new StringBuilder();
        encodedText.append(offsetCharacter);
        for (char c : plainText.toCharArray()) {
            int index = referenceTable.indexOf(c);
            if (index != -1) {
                int newIndex = (index - offset + referenceTable.length()) % referenceTable.length();
                encodedText.append(referenceTable.charAt(newIndex));
            } else {
                encodedText.append(c);
            }
        }
        return encodedText.toString();
    }

    public String decode(String encodedText) {
        int offset = referenceTable.indexOf(offsetCharacter);
        if (offset == -1) {
            return "offset is invalid";
        }

        StringBuilder decodedText = new StringBuilder();
        for (char c : encodedText.toCharArray()) {
            int index = referenceTable.indexOf(c);
            if (index != -1) {
                int newIndex = (index + offset) % referenceTable.length(); 
                decodedText.append(referenceTable.charAt(newIndex));
            } else {
                decodedText.append(c);
            }
        }
        decodedText.deleteCharAt(0);
        return decodedText.toString();
    }
    public void printDecoded(String encodedText) {
        String decodedText = decode(encodedText);
        System.out.println("Decoded: " + decodedText);
    }
    public void printEncoded(String plainText) {
        String encodedText = encode(plainText);
        System.out.println("Encoded: " + encodedText);
    }
}


