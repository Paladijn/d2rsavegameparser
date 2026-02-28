/**
 * Ensure Java 25+ is installed, then run `java HexDump.java file`
 * @param args The file to be converted to HEX
 */
void main(String[] args) {

    try (InputStream is = new FileInputStream(args[0])) {
        byte[] buffer = new byte[16];
        int bytesRead;

        while ((bytesRead = is.read(buffer)) != -1) {
            for (int i = 0; i < bytesRead; i++) {
                System.out.printf("%02X ", buffer[i]);
            }
            IO.println();
        }
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
    }
}
