package co.edu.escuelaing.proxy;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;

@RestController
public class ProxyController {
    private final String[] backends = System.getenv()
            .getOrDefault("BACKENDS", "http://localhost:45000,http://localhost:45001")
            .split(",");

    @GetMapping(value = "/binarysearch", produces = "application/json")
    public ResponseEntity<String> binarysearch(@RequestParam("list") String list, @RequestParam("value") String value) {
        return forward("/binarysearch?list=" + encode(list) + "&value=" + encode(value));
    }

    @GetMapping(value = "/fibonacci", produces = "application/json")
    public ResponseEntity<String> fibonacci(@RequestParam("value") String value) {
        return forward("/fibonacci?value=" + encode(value));
    }

    @GetMapping(value = "/catalan", produces = "application/json")
    public ResponseEntity<String> catalan(@RequestParam("value") String value) {
        return forward("/catalan?value=" + encode(value));
    }

    @GetMapping(value = "/productopunto", produces = "application/json")
    public ResponseEntity<String> productopunto(@RequestParam("lista1") String lista1, @RequestParam("lista2") String lista2) {
        return forward("/productopunto?lista1=" + encode(lista1) + "&lista2=" + encode(lista2));
    }

    private ResponseEntity<String> forward(String pathAndQuery) {
        for (String backend : backends) {
            String url = backend + pathAndQuery;
            System.out.println("Redirigiendo a " + url);
            try {
                HttpURLConnection con = (HttpURLConnection) URI.create(url).toURL().openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(2000);
                int status = con.getResponseCode();
                InputStream is = status < 400 ? con.getInputStream() : con.getErrorStream();
                return ResponseEntity.status(status).body(new String(is.readAllBytes(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.out.println("Falló " + backend + ", probando el siguiente");
            }
        }
        return ResponseEntity.status(503).body("{\"error\" : \"backend no responde\"}");
    }

    private String encode(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}