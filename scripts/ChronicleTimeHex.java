/**
 * Helper method to determin the hex value of the timestamp of a chronicle item in the shared stash
 * @param args a LocalDateTime in minutes, for example "2026-02-22T21:39:00" (quotes not needed)
 */

void main(String args[]) {
	LocalDateTime ldt = LocalDateTime.parse(args[0]);

	long epochSeconds = ldt.atZone(ZoneId.systemDefault()).toEpochSecond();

	long epochMinutes = epochSeconds / 60;

	IO.println("%s -> %d minutes from %d".formatted(args[0], epochMinutes, epochSeconds));

	byte[] bytes = ByteBuffer.allocate(4)
			.order(ByteOrder.LITTLE_ENDIAN)
			.putInt((int)epochMinutes)
			.array();

	for (byte b : bytes) {
		System.out.printf("%02X ", b);
	}
	IO.println("\n");
}