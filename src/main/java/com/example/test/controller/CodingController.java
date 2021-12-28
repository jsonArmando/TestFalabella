package com.example.test.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.FalabellaCoding;
import com.example.test.FalabellaDecoding;

@RestController
@RequestMapping("/falabella")
public class CodingController {

	static Random r = new Random();
	static char c = (char) (r.nextInt(26) + 'a');

	private static final String key = String.valueOf(c) + Math.log(2) / 3;

	// coding
	@GetMapping("/coding")
	@ResponseBody
	public FalabellaCoding getCoding(@RequestParam(required = false) String id) {
		String obfuscate = CodingController.getObfuscate(id);
		FalabellaCoding falabella = new FalabellaCoding();
		falabella.setObfuscate(obfuscate);
		return falabella;
	}

	// Decoding
	@GetMapping("/decoding")
	@ResponseBody
	public FalabellaDecoding getDecoding(@RequestParam(required = false) String texto) {
		String decode = CodingController.getValidate(texto);
		FalabellaDecoding falabella = new FalabellaDecoding();
		falabella.setDecoding(decode);
		return falabella;

	}
	
	public static boolean isValidIdentifier(String identifier) {
		String regex = "^([a-zA-Z_][a-zA-Z\\d_]*)";
		Pattern p = Pattern.compile(regex);
		if (identifier == null) {
            return false;
        }
		Matcher m = p.matcher(identifier);
		return m.matches();
		
	}

	public static String getObfuscate(String id) {
		char[] result = new char[id.length()];
		int s = id.length();

		IntStream.range(0, s).forEach(idx -> {
			result[idx] = (char) (id.charAt(idx) + key.charAt(idx % key.length()));
		});
		return new String(result.toString());
	}
	
	public static String getValidate(String texto) {
		int tamanio = texto.length();
		int columnas = (int) Math.ceil((float) tamanio / 6);
		char[][] matriz = new char[9][columnas];

		int columna = 0;
		int fila = 0;
		for (int x = 0; x < tamanio; x++) {
			columna = x / 6;
			fila = x % 6;
			matriz[fila][columna] = texto.charAt(x);
		}
		
		List<String> output = new ArrayList<>();
		for (int x=0; x < matriz.length; x++) {
		    for (int y=0; y < matriz[x].length; y++) {
		    	String s=String.valueOf(matriz[x][y]);  
		    	for (int t=0;t<s.length();t++)
		    		if(isValidIdentifier(String.valueOf(s.charAt(t)))) {
		    			output.add(String.valueOf(s.charAt(t)));
		    			System.out.println(s.charAt(t));
		    		}
		    	
		    }
		    //System.out.println("");
		}
		
		String result = output.stream()
			      .map(n -> String.valueOf(n))
			      .collect(Collectors.joining("", "{", "}"));
		return result;
	}

}
