package sql;

import java.util.concurrent.atomic.AtomicInteger;

public class ProfileUtil {

	private static AtomicInteger counter = new AtomicInteger(0);

	/**
	 * 长生消息id
	 */
	public static long getAtomicCounter() {
		if (counter.get() > 999999) {
			counter.set(1);
		}
		long time = System.currentTimeMillis();
		long returnValue = time * 100 + counter.incrementAndGet();
		return returnValue;
	}

	private static long incrementAndGet() {
		return counter.incrementAndGet();
	}

	public static void main(String[] args) {
String p="dc610d49-67d2-4c16-a7c0-4fcf627dfd6a.jpg";
		System.out.println(p.length());
	}

}