package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class ReadFile {
	
	public static String read(String adress) throws IOException {
	    		
		Path file = Paths.get(adress);	
		Optional<String> text = Optional.ofNullable("");
		try (Stream<String> stream = Files.lines(file)) {
		   text = stream.reduce(String::concat);
		} catch (IOException e) {
		   e.printStackTrace();
		}
				
		return text.get();
	}
}
