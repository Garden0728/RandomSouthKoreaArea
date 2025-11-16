package Service.RandomArea.Config.Json;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
@Component
@RequiredArgsConstructor
public class JsonReaderV1 implements JsonReader {
    private final ResourceLoader resourceLoader;
    @Override
    public String getJsonString(String classpath) throws Exception {
        InputStream is =  resourceLoader
                //.getResource("classpath:korea(small).json")
                .getResource("classpath:" + classpath)
                .getInputStream();
        return new String(is.readAllBytes());
    }
}
