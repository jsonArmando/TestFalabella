package com.example.test.controller;

import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.FalabellaCoding;

@RestController
@RequestMapping("/falabella")
public class CodingController {
	
	static Random r = new Random();
	static char c = (char) (r.nextInt(26) + 'a');
	
	private static final String key = String.valueOf(c) + Math.log(2) / 3;

	@GetMapping("/coding")
	@ResponseBody
	public FalabellaCoding getCoding(@RequestParam(required = false) String id) {
		String obfuscate = CodingController.getObfuscate(id);
		FalabellaCoding falabella = new FalabellaCoding();
		falabella.setObfuscate(obfuscate);
		return falabella;
	}
	
	//Decoding

	public static String getObfuscate(String id) {
		char[] result = new char[id.length()];
		int s = id.length();
		
		IntStream.range(0, s).forEach(idx -> {
			result[idx] = (char) (id.charAt(idx) + key.charAt(idx % key.length()));
		});
		return new String(result.toString());
	}

}
