package co.edu.escuelaing.backend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class SearchController {
    private final SearchService service;

    public SearchController(SearchService service) {
        this.service = service;
    }

    @GetMapping(value = "/binarysearch", produces = "application/json")
    public String binarysearch(@RequestParam("list") String list, @RequestParam("value") String value) {
        int output = service.binarysearch(toArrayInt(list), Integer.parseInt(value));
        String result = String.valueOf(output);
        return toJson("binarysearch", list, value, result);
    }

    @GetMapping(value = "/fibonacci", produces = "application/json")
    public String fibonacci(@RequestParam("value") String value) {
        String result = service.fibonacci(Integer.parseInt(value));
        return toJson("fibonacci", null, value, result);
    }

    @GetMapping(value = "/catalan", produces = "application/json")
    public String catalan(@RequestParam("value") String value) {
        String result = service.catalan(Integer.parseInt(value));
        return toJson("catalan", null, value, result);
    }

    @GetMapping(value = "/productopunto", produces = "application/json")
    public String productopunto(@RequestParam("lista1") String lista1, @RequestParam("lista2") String lista2) {
        int output = service.productopunto(toArrayInt(lista1), toArrayInt(lista2));
        String result = String.valueOf(output);
        return toJson("productopunto", lista1, lista2, result);
    }

    private int[] toArrayInt(String list) {
        String[] listSplit = list.split(",");
        int[] result = new int[listSplit.length];
        for (int i = 0; i<listSplit.length; i++) {
            result[i] = Integer.parseInt(listSplit[i]);
        }
        return result;
    }

    private String toJson(String operation, String list, String value, String result) {
        if (list==null) {
            return "{\"operation\":\"" + operation + "\",\"value\":\"" 
                + value + "\",\"result\":\"" + result + "\"}";
        } else if (operation.equals("productopunto")) {
            return "{\"operation\":\"" + operation + "\",\"lista1\":\"" + list + "\",\"lista2\":\"" 
                + value + "\",\"result\":\"" + result + "\"}";
        }
        return "{\"operation\":\"" + operation + "\",\"list\":\"" + list + "\",\"value\":\"" 
                + value + "\",\"result\":\"" + result + "\"}";
    }
    
}
