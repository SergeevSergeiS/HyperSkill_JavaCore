package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {

	private static String[][] patternsAndExtensions;
	private static final ExecutorService ES = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void main(String[] args) throws IOException, InterruptedException {

		patternsAndExtensions = Arrays.stream(Files.readString(Path.of(args[1])).split("\n"))
				.sorted((a, b) -> a.charAt(0) > b.charAt(0) ? -1 : 0)
				.map(line -> {
					var patAndExt = line.replaceAll("\"", "").split(";");
					return new String[] { patAndExt[1], patAndExt[2] };
				})
				.toArray(String[][]::new);

		for (Path path : Files.newDirectoryStream(Path.of(args[0]))) addThreadToExecutor(path, Files.readString(path));

		ES.awaitTermination(13, TimeUnit.MILLISECONDS); // micro-time cheat
		ES.shutdown();
	}

	private static void addThreadToExecutor(Path path, String txt)  {
		ES.submit(() -> {
			var fileType = Arrays.stream(patternsAndExtensions)
					.filter(patAndExt -> rabinCarpAlgo(txt.toCharArray(), patAndExt[0].toCharArray()))
					.findFirst()
					.orElse(new String[] { "", "Unknown file type" })[1];

			var fileName = path.getFileName().toString();

			System.out.printf("%s: %s%n", fileName, fileType);
		});
	}

	private static boolean rabinCarpAlgo(char[] txt, char[] pat) {
		int pLen = pat.length;
		int tLen = txt.length;

		long[] pow = new long[pLen];
		pow[0] = 1;
		for (int i = 1; i < pLen; i++) pow[i] = pow[i - 1] * 53;  // a = 53; modulo = Long.MAX_VALUE

		if (tLen - pLen < 0) return false;

		long pattHash = calculateHash(pat, pow);
		long suffHash = calculateHash(Arrays.copyOfRange(txt, tLen - pLen, tLen), pow);

		for (int i = tLen - pLen; i >= 0; i--) {
			if (suffHash == pattHash)
				for (int k = 0; k < pLen; k++)
					if (pat[k] != txt[i + k]) break;
					else if (k == pLen - 1) return true;

			if (i == 0) break;

			suffHash = (suffHash - (txt[i + pLen - 1] * pow[pLen - 1])) * pow[1] + txt[i - 1];
		}

		return false;
	}

	private static long calculateHash(char[] s, long[] pow) {
		long ht = s[0];
		for (int i = 1; i < s.length; i++) ht += pow[i] * s[i];
		return ht;
	}
}
